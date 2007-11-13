package drk.game;
import java.awt.event.KeyEvent;
import java.io.*;
import drk.KarnaughLog;
import drk.*;
import drk.maze.*;
import drk.circuit.*;
import drk.sound.*;
import javax.media.opengl.*;
import drk.graphics.guiOverlayItem;
public class KarnaughGame extends MazeGame implements Updatable{

	private static int resWidth  = 1024;
	private static int resHeight = 768;

	private int Score = 0;
	private long Time; 
	public boolean paused;
	
	guiOverlayItem cursor;
	guiOverlayItem interactHand;
	guiOverlayItem wireHand;
	guiOverlayItem colon;
	guiOverlayItem digits[];
	guiOverlayItem bunny;
	guiOverlayItem cChar;
	guiOverlayItem eChar;
	guiOverlayItem rChar;
	
	private long lastUpdate = 0;
	
	private int songID = 0;
	
	public void initialize(GL gl){
		super.initialize(gl);
		
		bunny = new guiOverlayItem();
		cursor = new guiOverlayItem();
		colon = new guiOverlayItem();
		interactHand = new guiOverlayItem();
		wireHand = new guiOverlayItem();
		
		
		int digitWidth = (int)(.03*resWidth);
		int upperLeftY = (int)(.99*resHeight);
		int upperLeftX = (int)(.01*resWidth);
		
		cursor.setWidth(digitWidth);
		cursor.setHeight(digitWidth);
		cursor.setTexture("hand.png"); 
		cursor.setPosition(resWidth/2 - digitWidth/2, resHeight/2 - digitWidth/2);
		
		
		
		interactHand.setWidth(digitWidth);
		interactHand.setHeight(digitWidth);
		interactHand.setTexture("interact.png"); 
		interactHand.setPosition(resWidth/2 - digitWidth/2, resHeight/2 - digitWidth/2);
		
		
		
		wireHand.setWidth(digitWidth);
		wireHand.setHeight(digitWidth);
		wireHand.setTexture("wire.png"); 
		wireHand.setPosition(resWidth/2 - digitWidth/2, resHeight/2 - digitWidth/2);
		
		
		
		colon.setWidth(digitWidth);
		colon.setHeight(digitWidth);
		colon.setTexture("digital.png");
		
		colon.texStartU = .5f;
		colon.texStartV = .5f;
		colon.texSizeU =  .25f;
		colon.texSizeV = .25f;
		colon.setPosition(0,0);
		
		cChar = new guiOverlayItem();
		cChar.setWidth(digitWidth);
		cChar.setHeight(digitWidth);
		cChar.setTexture(colon);
		cChar.texStartU = 0f;
		cChar.texStartV = .75f;
		cChar.texSizeU =  .25f;
		cChar.texSizeV = .25f;	
		
	
		
		eChar = new guiOverlayItem();
		eChar.setWidth(digitWidth);
		eChar.setHeight(digitWidth);
		eChar.setTexture(colon);
		eChar.texStartU = .5f;
		eChar.texStartV = .75f;
		eChar.texSizeU =  .25f;
		eChar.texSizeV = .25f;

		rChar = new guiOverlayItem();
		rChar.setWidth(digitWidth);
		rChar.setHeight(digitWidth);
		rChar.setTexture(colon);
		rChar.texStartU = .25f;
		rChar.texStartV = .75f;
		rChar.texSizeU =  .25f;
		rChar.texSizeV = .25f;
	
		//3,3 and 3,4
		
		bunny.setWidth(digitWidth);
		bunny.setHeight(digitWidth);
		bunny.setTexture(colon);
		bunny.texSizeU = .25f;
		bunny.texSizeV = .25f;
		bunny.texStartU = 11f*.25f;
		bunny.texStartV = .5f;
		bunny.setPosition(0,0);
		
		
		digits = new guiOverlayItem[10];
		for(int i = 0; i < digits.length; i++){
			
	    	digits[i] = new guiOverlayItem();	
			digits[i].texSizeU = .25f;
			digits[i].texSizeV = .25f;
			digits[i].texStartU = (float)(i*.25);
			digits[i].texStartV = (float)(((i)/4)*.25);
		    digits[i].setWidth(digitWidth);
		    digits[i].setHeight(digitWidth);
		    digits[i].setTexture(colon);
		    digits[i].setPosition(0,0);
			
		}
		
		
	
	
	}

	
	
	
	
	public KarnaughGame(){
		Score = 0;
		Time = 0;
		paused = false;
	}
	
	
	
	public void setSingleMapCampaign(KarnaughGame m, String map){
		
		
		
		KarnaughLog.clearLog();
		
		
		KarnaughLog.log("Starting Dr. Karnaugh's Lab");
		
		KarnaughLog.log(""+map);
		
		m.loadMap(map);
		m.camera.fovy = 30;
	    m.doMain(resWidth,resHeight,null,true);
		/*if(!((KarnaughMaze)this.m).nextmap.equals("LAST_LEVEL")){
			loadMap(((KarnaughMaze)this.m).nextmap);
		}
		else{
			gameOver();
		}*/
	}
	
	
	
	public void render(GL gl){
		
		super.render(gl);
				
			
		String bauerClock = "";
			
	
	    if(minutesLeft() < 10)bauerClock+="0";
		 bauerClock += minutesLeft()+":";
		if(secondsLeft()< 10)bauerClock+="0";
		bauerClock+= secondsLeft();
		
		
		//System.out.println(bauerClock);
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		
		gl.glPushMatrix();
		gl.glLoadIdentity();
		
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPushMatrix();
		
		gl.glLoadIdentity();
		gl.glEnable (GL.GL_BLEND); gl.glBlendFunc (GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glOrtho(0, resWidth, 0, resHeight, -1.0, 1.0);
		
		
		//You should like, totally draw your gui elements here
		 
	    guiOverlayItem.setGUIColor(1f,1f,1f);
	    
	    
	 	wireHand.draw(gl);
	 	
	 	guiOverlayItem.setGUIColor(0,1f,0);
	 	
	    String scoreString = "5c0re:"+Score;
	    
	    for(int i = 0; i < scoreString.length(); i++){
	    	
	    	char a =scoreString.charAt(i);
	    	int b = 0;
	    	
	    	if(a == ':')
	    		colon.drawAt(0+i*colon.width,0,gl);
	    		
	    	else if(a == 'c')
	    		cChar.drawAt(0+i*cChar.width,0,gl);
	    	
	    	
	    	else if(a == 'r')
	    		rChar.drawAt(0+i*rChar.width,0,gl);
	    		
	    	
	    	else if(a == 'e')
	    		eChar.drawAt(0+i*eChar.width,0,gl);
	    	
	    	else {
	    	b = Integer.valueOf(""+a);
	    	digits[b].drawAt(0+i*digits[b].width,0,gl);
	    	}
	    	
	    }
	 	
	 	
	    if(minutesLeft()==0 && secondsLeft() <=30){
	    	guiOverlayItem.setGUIColor(1f,0f,0f);}
	    
	    for(int i = 0; i < bauerClock.length(); i++){
	    	
	    	char a = bauerClock.charAt(i);
	    	int b = 0;
	    	
	    	if(a == ':')
	    		colon.drawAt(0+i*colon.width,resHeight-colon.height,gl);
	    	else if (secondsLeft() > 0 || minutesLeft() > 0){
	    	
	    	b = Integer.valueOf(""+a);
	    	digits[b].drawAt(0+i*digits[b].width,resHeight-digits[b].height,gl);
	    	}else{
	    		bunny.drawAt(0+i*bunny.width,resHeight-bunny.height,gl);
	    		
	    	}
	    	
	    }
	    
	    
	    guiOverlayItem.setGUIColor(1f,1f,1f);
	    
	    
	  
	    
	    //you should like, totally STOP drawing them here
	
		gl.glDisable (GL.GL_BLEND);
		gl.glPopMatrix();
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		
		gl.glPopMatrix();
		
	}
	
	
	public void keyReleased(KeyEvent k){
	
	super.keyReleased(k);
	//if(k.getKeyCode() == KeyEvent.VK_SHIFT){
						
	//		if(!((KarnaughMaze)m).nextmap.equals("LAST_LEVEL"))
	//		this.loadMap(((KarnaughMaze)m).nextmap);
			
	//}
	}
	
	
	
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
		
		if(!((KarnaughMaze)this.m).nextmap.equals("LAST_LEVEL")){
			
			
			/*
			x points for every second left
			MBRAM32 (11:01:50 PM): constant*x for every minute
			MBRAM32 (11:02:02 PM): constant2 for each photo/journal/whatever
			MBRAM32 (11:02:22 PM): and a bonus for using as small a number of wires as possible
			MBRAM32 (11:02:54 PM): large bonus for finishing the map after time runs out
			*/
			
			loadMap( ((KarnaughMaze)this.m).mapDirectory+((KarnaughMaze)this.m).nextmap);
			
			
		}
		else{
			gameOver();
		}
		
	}
	
	
	//loads a level.
	//TODO : Spawn the Camera so that it's always looking down a hallway and not at a wall
	public boolean loadMap(String m){
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
		ec.Position	=this.m.getRoomMiddle(this.m.getRoom(rm));
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
		
		super.update();
		
		if(paused)return;
		
		if(isKeyPressed(KeyEvent.VK_ESCAPE)){
			System.exit(0);
		}
		
	
		
		//test for win/die
		
		
		//if time <= release the bunny or otherwise kill the player
		
		
		if(Time > 0){
		long tmp = System.currentTimeMillis() - lastUpdate;

		Time -= tmp;
		
		lastUpdate = System.currentTimeMillis();
		
		if(Time < 0)Time = 0;
		
		if(Time <=0){
			final File f = new File("drk/sound/music/mission.mp3");
	
			SoundStreamer.stopPlayImmediately(songID);
		
			if(f!=null){
			songID = SoundStreamer.playThreadedStreamedLooped(f);
			}else{
			KarnaughLog.log("Could not open song file");
			}
		}
		}
		
		
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
	
	public static void mainGame(){
		KarnaughLog.clearLog();
		KarnaughLog.log("Starting Dr. Karnaugh's Lab");
		
		KarnaughGame m = new KarnaughGame();
		m.loadMap("map01.kar");
		m.camera.fovy = 30;
	    m.doMain(resWidth,resHeight,null,true);
	}
	
	//point of entry
	public static void main(String args[]){
	
		KarnaughLog.clearLog();
		KarnaughLog.log("Starting Dr. Karnaugh's Lab");
		
		KarnaughGame m = new KarnaughGame();
		m.loadMap("map01.kar");
		m.camera.fovy = 30;
	    m.doMain(resWidth,resHeight,null,true);
		
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