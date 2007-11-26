//critical -- cycle through combinations of inputs when click on exit with wire plugged in
//if matches truth table, call winMap();

package drk.game;
import java.awt.event.KeyEvent;
import java.io.*;
import drk.KarnaughLog;
import drk.*;
import drk.maze.*;
import drk.circuit.*;
import drk.sound.*;
import javax.media.opengl.*;
import java.awt.event.*;
import java.util.*;

public class KarnaughGame extends MazeGame implements Updatable, MouseListener{

	public int currentOutput = 0; //current index into the solution truth table

	public int Score = 0; //should be self explanatory
	
	private long Time; //milliseconds remaining
	
	public boolean paused; //should also be self explanatory
	
	long cycleTime = 0; //time since the truth table last cycled
	
	private long lastUpdate = 0;  //system time at which the game loop updated last
	
	public MazeItem inputSource = null; //reference to the mazeitem that starts a wired connection
	
	public KarnaughOverlays overlays; //object to manage the Karnaugh Game's HUD
	
	protected int songID = 0; //id for use with the sound engine

	public boolean hasWire = false;
	
//	public ArrayList<Wire> wires = null; //collection of wires within the maze
	
	
	public static final int GAME_WIDTH=1024;//resolution constants
	public static final int GAME_HEIGHT=768;
	

	//called when the user is carrying a wire attached to a component's output, and they change their mind and decide to discard
	//and start somewhere else
	public void discardWire(){
		inputSource = null;
		hasWire = false;
		doubleClickLeft = doubleClickRight = leftClick = rightClick = false;
		overlays.currentCursor = overlays.cursor;
	}
	
	
	//does super's initialization
	//sets up the game GUI
	public void initialize(GL gl){
		super.initialize(gl);
		
		overlays=new KarnaughOverlays(this);
		overlays.initialize(gl);
		
	}

	
	
	
	//constructor-- initialize the variables
	public KarnaughGame(){
		
	//	wires = new ArrayList<Wire>(); 
		Score = 0;
		Time = 0;
		paused = false;
		hasWire = false;
		songID = 0;
		inputSource = null;
		lastUpdate = 0;
		cycleTime = 0;
		currentOutput = 0;
	}
	
	
	
	
	//loads a game from a particular file
	public void setSingleMapCampaign(KarnaughGame m, String map){
		
		KarnaughLog.clearLog();
		
		KarnaughLog.log("Starting Dr. Karnaugh's Lab");
		
		KarnaughLog.log(""+map);
		
		m.loadMap(map);
		m.camera.fovy = 30;
	    m.doMain(800,600,null,true);
		/*if(!((KarnaughMaze)this.m).nextmap.equals("LAST_LEVEL")){
			loadMap(((KarnaughMaze)this.m).nextmap);
		}
		else{
			gameOver();
		}*/
	}
	
	
	
	//renders the maze, then renders the guiOverlays on top of the screen
	public void render(GL gl){
		
		super.render(gl);
					
		overlays.render(gl);
		
	}
	
	
	
	//
	//public void keyReleased(KeyEvent k){
	//super.keyReleased(k);
	//if(k.getKeyCode() == KeyEvent.VK_SHIFT){
	//	winMap();		
	//}
	//}
	
	
//	public void keyPressed(KeyEvent k){
	//	if(k.getKeyChar() == 'p' || k.getKeyChar() == 'P'){
	//	
	//	  if(!paused)paused = true;
	//	  else unPause();
	//	
	//	}
	//	
	//	if(!paused)
	//	super.keyPressed(k);
//	}
	
	
	//when the game is over by death or winning manage endgame situation
	//TODO URGENT*****
	public void gameOver(){
		//submit score
		//high score table
		//return to menu
	}
	
	
	//you can only die if bunny kills you
	//if you die, it's game over
	public void die(){
		//do stuff -- like knock the camera over and shoot blood everywhere
		gameOver();
		
	}
	
	
	//what happens when the user exits a map
	public void winMap(){
		
		//compute the user's score for this map and add it to score, 
		//show loading/score tally splash screen
		
			
			//close call bonus
			if(minutesLeft() ==0 && secondsLeft() <30){
				Score+=30; 
			}
			
			Score += secondsLeft();
			Score += minutesLeft()*65;//five point bonus for each whole minute
			
			//bonus items score
			//wire amount bonus
			
			
		if(!((KarnaughMaze)this.m).nextmap.equals("LAST_LEVEL")){
			loadMap( ((KarnaughMaze)this.m).mapDirectory+((KarnaughMaze)this.m).nextmap);
		}	
		
		else{
			gameOver();
		}
		
	}
	
	
	//loads a level.
	//TODO : Spawn the Camera so that it's always looking down a hallway and not at a wall
	public boolean loadMap(String m){
		LogicInput.inputNumber = 0;
		//wires = new ArrayList<Wire>(); 
		hasWire = false;
		cycleTime = 0;
		currentOutput = 0;
		inputSource = null;
		
		if(overlays!=null)
		overlays.currentCursor = overlays.cursor;
		
		this.m = (KarnaughMaze)KarnaughMaze.loadMaze(m);

	
		if( this.m == null) return false;
		
		int rm = 0;
		KarnaughMaze km=(KarnaughMaze)this.m;
		//finds the first room in the maze with an entrance
		//sets the camera to be located within this room
		for(rm = 0; rm < this.m.getWidth()*this.m.getHeight();rm++){
		  if(this.m.getRoom(rm).getItem() instanceof Entrance)break;	
		}
		
		//this is awesome chris...I'm going to modify Maze a bit so it's a nicer call...but this works awesome.
		//double xoff=(double)(rm % this.m.getWidth())*RenderableMaze.RENDER_WIDTH;
		//double zoff=(double)(rm /this.m.getWidth())*RenderableMaze.RENDER_WIDTH;
		
		//ec.Position = new Vector3D(xoff+RenderableMaze.RENDER_WIDTH/2,.7*RenderableMaze.RENDER_HEIGHT,zoff+.5*RenderableMaze.RENDER_WIDTH);
		ec.Position	= this.m.getRoomMiddle(this.m.getRoom(rm));
		ec.setHeight(1.5);//59 inches in meters (avg human height)
		
		Time = km.timelimit*1000;
		
		lastUpdate = System.currentTimeMillis();
		this.m.setDeltaTimer(this.frameTimer);
		
		///jgshdkjshdfkjdshg
		final File f = new File("drk/sound/music/"+km.songfile+".mp3");
		
		//File f = new File(""+(((KarnaughMaze)this.m).songfile)+".mp3");
		
		SoundStreamer.stopPlayImmediately(songID);
		
		if(f!=null){
		songID = SoundStreamer.playThreadedStreamedLooped(f);
		}else{
			KarnaughLog.log("Could not open song file");
		}
		
		return true;
	}
	
	
	
	public void unPause(){
		lastUpdate = System.currentTimeMillis();//that way the timer doesn't count off for when the game was paused
		paused = false;		
	}
	
	
	//one iteration of the game loop
	public void update(){
		
		
		if(paused)return;
		
		super.update();
	
	
		//set the cursor based on the current gamestate
		if(!hasWire)
		overlays.currentCursor = overlays.cursor;
		else
			overlays.currentCursor = overlays.wireHand;
		
		//if the user double clicks while holding a wire, discard it
		if(hasWire&&(doubleClickLeft||doubleClickRight))discardWire();
		
		if(!hasWire){
		
		
		if(minutesLeft() >= 0 && secondsLeft() > 0)updateInfo("");
		else updateInfo("RUN! The Bunny is after you!");
		
		
		}
		else updateInfo("Double Click to discard wire"); //tutorial tip on the status bar
		
	
		
		//TODO URGENT**** get rid of this, go to the menu instead, pause game, SOMETHING
		if(isKeyPressed(KeyEvent.VK_ESCAPE)){
			System.exit(0);
		}
		
	
		//get the object in the room the player is currently in
		MazeItem x = ((KarnaughMaze)m).getCurrentRoom().getItem();
		
		//handle mouse hovering, clicks, etc....
		if(x!=null){
		//handles clicking and highlighting items
			if(x.isMazeItemHighlighted(this)){
				x.onMazeItemHighlighted(this);		
			}	
		}	
			
			
		//whether events happened or not, reset click flags
		leftClick = false;
		rightClick = false;
		doubleClickLeft = false;
		doubleClickRight = false;
		
		//test for win/die
		//**** TODO URGENT *****
		
		
		//if time <= release the bunny or otherwise kill the player
		
		//cycle through the truth table solutions every 2 seconds, 
		//and show them in the status bar so the user can see what they should be matching with their puzzle
		//TODO URGENT: check here 
		if(System.currentTimeMillis() - cycleTime >= 2000){
			cycleTime = System.currentTimeMillis();
			currentOutput = (currentOutput+1)%truthTableSize();
			updateTT(currentOutput,((KarnaughMaze)m).numInputs,((KarnaughMaze)m).solution[currentOutput]);
		//	Score = getCurrentSolution() == true?1:0;
			 
		}
		
		
		
		if(Time > 0){
		
		Time -= System.currentTimeMillis() - lastUpdate;
	
		
		if(Time <=0){
			
			Time = 0;
			final File f = new File("drk/sound/music/mission.mp3");
	
			SoundStreamer.stopPlayImmediately(songID);
		
			if(f!=null){
			songID = SoundStreamer.playThreadedStreamedLooped(f);
			}else{
			KarnaughLog.log("Could not open song file");
			}
			
			die();
		}
		}
		
		lastUpdate = System.currentTimeMillis();
		
	//	if(Time % 1000 == 0)
	//		System.out.println(""+minutesLeft()+":"+secondsLeft());
		
	}
	
	//how much time is left, minutes only
	public long minutesLeft(){
		return (Time/1000)/60;
	}
	
	//returns the seconds portion of the time remaining, not counting whole minutes
	public long secondsLeft(){
		return (Time/1000)%60;
	}
	
	
	
	//starts a new game at map01
	public static void mainGame(){
		KarnaughLog.clearLog();
		KarnaughLog.log("Starting Dr. Karnaugh's Lab");
		
		KarnaughGame m = new KarnaughGame();
		m.loadMap("map01.kar");
		m.camera.fovy = 30;
	    m.doMain(GAME_WIDTH,GAME_HEIGHT,null,true);
	}
	
	
	//returns the value at the current truth table index
	public boolean getCurrentSolution(){
		return ((KarnaughMaze)m).solution[currentOutput];	
	}
	
	
	//returns the size of the solution truth table -- which is 2^(number of inputs)
	public int truthTableSize(){
		return ((KarnaughMaze)m).solution.length;
	}
	

	
	
	
	//runs a test iteration of the game
	public static void main(String args[]){
			
		KarnaughLog.clearLog();
		KarnaughLog.log("Starting Dr. Karnaugh's Lab");
		
		KarnaughGame m = new KarnaughGame();
		
		m.camera.fovy = 30;
	    
	    	m.loadMap("map01.kar");
	    	
	    	
	    m.doMain(GAME_WIDTH,GAME_HEIGHT,null,true);
	
		for(boolean b:((KarnaughMaze)m.m).solution){	
			System.out.println(b);	
		}
		
	   //MazeGame mg = new MazeGame();
	   //mg.m = KarnaughMaze.loadMaze("test");
	   //mg.camera.fovy = 30;
	   //mg.doMain(resWidth,resHeight,null,true);
	  
	  //if(mg.m == null){
		//KarnaughLog.log("Could not load test level.  Program shutting down");
		//System.exit(0);	  	
	  //}else {
	  	//KarnaughLog.log("Map loaded successfully, game loop may begin");
	  //}
	  
   }//end main	
	
}//end class