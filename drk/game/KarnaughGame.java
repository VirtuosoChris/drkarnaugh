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
import drk.menu.*;
import javax.media.opengl.*;
import java.awt.event.*;
import java.util.*;

public class KarnaughGame extends MazeGame implements Updatable, MouseListener {

    public int currentOutput = 0; //current index into the solution truth table

    public int Score = 0; //should be self explanatory

    public int tempScore = 0;

    protected long Time; //milliseconds remaining

    public boolean paused; //should also be self explanatory

    long cycleTime = 0; //time since the truth table last cycled

    private long lastUpdate = 0;  //system time at which the game loop updated last

    public MazeItem inputSource = null; //reference to the mazeitem that starts a wired connection

    public KarnaughOverlays overlays; //object to manage the Karnaugh Game's HUD

    protected int songID = 0; //id for use with the sound engine

    public boolean hasWire = false;

    //public boolean ttMatched[] = null;
    protected String mapName = "map01.kar";

    protected static String tempmapName = "map01.kar";

    private long longest = 0;

    Bunny bunneh = null;

//	public ArrayList<Wire> wires = null; //collection of wires within the maze
    public static final int GAME_WIDTH = 1024;//resolution constants
    public static final int GAME_HEIGHT = 768;

    //called when the user is carrying a wire attached to a component's output, and they change their mind and decide to discard
    //and start somewhere else
    public void discardWire() {
        inputSource = null;
        hasWire = false;
        doubleClickLeft = doubleClickRight = leftClick = rightClick = false;
        overlays.currentCursor = overlays.cursor;
    }

    //does super's initialization
    //sets up the game GUI
    public void initialize(GL gl) {
        super.initialize(gl);

        overlays = new KarnaughOverlays(this);
        overlays.initialize(gl);

    }

    public KarnaughMaze getMaze() {
        return (KarnaughMaze) m;

    }

    //constructor-- initialize the variables
    public KarnaughGame() {

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
    public void setSingleMapCampaign(KarnaughGame m, String map) {

        KarnaughLog.clearLog();

        KarnaughLog.log("Starting Dr. Karnaugh's Lab");

        KarnaughLog.log("" + map);

        m.loadMap(map);
        m.camera.fovy = 30;
        m.doMain(GAME_WIDTH, GAME_HEIGHT, null, true);
        /*if(!((KarnaughMaze)this.m).nextmap.equals("LAST_LEVEL")){
			loadMap(((KarnaughMaze)this.m).nextmap);
		}
		else{
			gameOver();
		}*/
    }

    //renders the maze, then renders the guiOverlays on top of the screen
    public void render(GL gl) {

        super.render(gl);

        if (bunneh != null) {
            bunneh.render(gl);
        }

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
    public void gameOver() {
    }

    //you can only die if bunny kills you
    //if you die, it's game over
    public void die() {

        frameClose();
        SoundStreamer.stopPlayImmediately(songID);

        //do stuff -- like knock the camera over and shoot blood everywhere
        GameOver.gameisOver();
        gameOver();

    }

    //what happens when the user exits a map
    public void winMap() {

        //compute the user's score for this map and add it to score, 
        //show loading/score tally splash screen
        //close call bonus
        if (minutesLeft() == 0 && secondsLeft() < 30) {
            Score += 30;
        }

        Score += secondsLeft();
        Score += minutesLeft() * 65;//five point bonus for each whole minute
        //bonus items score
        //wire amount bonus

        if (!((KarnaughMaze) this.m).nextmap.equals("LAST_LEVEL")) {
            loadMap(((KarnaughMaze) this.m).mapDirectory + ((KarnaughMaze) this.m).nextmap);
            mapName = ((KarnaughMaze) this.m).nextmap;
            tempmapName = ((KarnaughMaze) this.m).nextmap;
        } else {
            SoundStreamer.stopPlayImmediately(songID);
            frameClose();
            WinMenu.WinGame();
            Menu mainMenu = new Menu();
            mainMenu.GameGUI();
            gameOver();
        }

    }

    //ttMatched keeps track of whether the truth table solution matches the current puzzle solution
    //this method resets ttMatched
//	public void resetMatched(){
//		for(int xb = 0; xb < ttMatched.length; xb++){
//			ttMatched[xb] = false;
//		}
//	}
    //loads a level.
    //TODO : Spawn the Camera so that it's always looking down a hallway and not at a wall
    public boolean loadMap(String m) {
        LogicInput.inputNumber = 0;
        //wires = new ArrayList<Wire>(); 
        hasWire = false;
        cycleTime = 0;
        currentOutput = 0;
        inputSource = null;
        bunneh = null;

        if (overlays != null) {
            overlays.currentCursor = overlays.cursor;
        }

        this.m = (KarnaughMaze) KarnaughMaze.loadMaze(m);

        if (this.m == null) {
            return false;
        }

        int rm = 0;
        KarnaughMaze km = (KarnaughMaze) this.m;
        //finds the first room in the maze with an entrance
        //sets the camera to be located within this room
        for (rm = 0; rm < this.m.getWidth() * this.m.getHeight(); rm++) {
            if (this.m.getRoom(rm).getItem() instanceof Entrance) {
                break;
            }
        }

        //this is awesome chris...I'm going to modify Maze a bit so it's a nicer call...but this works awesome.
        //double xoff=(double)(rm % this.m.getWidth())*RenderableMaze.RENDER_WIDTH;
        //double zoff=(double)(rm /this.m.getWidth())*RenderableMaze.RENDER_WIDTH;
        //ec.Position = new Vector3D(xoff+RenderableMaze.RENDER_WIDTH/2,.7*RenderableMaze.RENDER_HEIGHT,zoff+.5*RenderableMaze.RENDER_WIDTH);
        ec.Position = this.m.getRoomMiddle(this.m.getRoom(rm));
        ec.setHeight(1.5);//59 inches in meters (avg human height)

        Time = km.timelimit * 1000;

        lastUpdate = System.currentTimeMillis();
        this.m.setDeltaTimer(this.frameTimer);

        ///jgshdkjshdfkjdshg
		final File f = new File("drk/sound/music/" + km.songfile + ".mp3");

        //File f = new File(""+(((KarnaughMaze)this.m).songfile)+".mp3");
        SoundStreamer.stopPlayImmediately(songID);

        if (f != null) {
            songID = SoundStreamer.playThreadedStreamedLooped(f);
        } else {
            KarnaughLog.log("Could not open song file");
        }

        //int xa = km.solution.length;
        //ttMatched = new boolean[xa];
        //resetMatched();
        return true;
    }

    public void frameVisible(int x) {
        super.frameVisible(x);
    }

    public void unPause() {
        lastUpdate = System.currentTimeMillis();//that way the timer doesn't count off for when the game was paused
        paused = false;
    }

    //one iteration of the game loop
    public void update() {

        tempScore = Score;
        if (paused) {
            return;
        }

        super.update();

        if (bunneh != null) {
            bunneh.update();
        }

        //set the cursor based on the current gamestate
        if (!hasWire) {
            overlays.currentCursor = overlays.cursor;

            if (Time > 0) {
                updateInfo("");
            } else {
                updateInfo("RUN! The Bunny is after you!");
            }

        } else {//if has wire
            if ((doubleClickLeft || doubleClickRight)) {
                discardWire();
            }
            overlays.currentCursor = overlays.wireHand;
            updateInfo("Double Click to discard wire"); //tutorial tip on the status bar
        }

        //if the user double clicks while holding a wire, discard it
        //TODO URGENT**** get rid of this, go to the menu instead, pause game, SOMETHING
        if (isKeyPressed(KeyEvent.VK_ESCAPE)) {
            SoundStreamer.stopPlayImmediately(songID);
            frameClose();
            Menu mainMenu = new Menu();
            mainMenu.GameGUI();
        }

        //Oh yea I wanna get me some score!
        //You can test the Reset map with this...
        /*if(isKeyPressed(KeyEvent.VK_SPACE)){
			Score = Score + 5;
		}*/
        //Really weird error here.
        /*SaveLoad sl = new SaveLoad();
		
		if(isKeyPressed(KeyEvent.VK_S)){
			sl.savegame(Menu.userName, Time, Score, mapName);
		}
		
		if(isKeyPressed(KeyEvent.VK_L)){
			sl.loadgame(Menu.userName);	
		}*/
        //Need to set a temp score so that score doesnt get reset.
        if (isKeyPressed(KeyEvent.VK_R)) {
            SoundStreamer.stopPlayImmediately(songID);
            if (tempScore == 0) {
                if (Score == 0) {
                    tempScore = Score;
                } else {
                    tempScore = Score - 10;
                }
            } else {
                tempScore = tempScore - 10;
            }
            frameClose();
            KarnaughGame m = new KarnaughGame();
            m.Score = this.tempScore;
            m.loadMap(mapName);
            m.camera.fovy = 30;
            m.doMain(GAME_WIDTH, GAME_HEIGHT, null, true);
        }

        /*if(isKeyPressed(KeyEvent.VK_M)){
			paused = true;
			SoundStreamer.stopPlayImmediately(songID);
			frameVisible(0);
			Menu mainMenu = new Menu();
			mainMenu.GameGUI();
		}*/
        //get the object in the room the player is currently in
        MazeItem x = ((KarnaughMaze) m).getCurrentRoom().getItem();

        //handle mouse hovering, clicks, etc....
        if (x != null) {
            //handles clicking and highlighting items
            if (x.isMazeItemHighlighted(this)) {
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
        if (System.currentTimeMillis() - cycleTime >= 2000) {

            //for each input, for each char of ttstring
            //set the inputs 
            cycleTime = System.currentTimeMillis();
            currentOutput = (currentOutput + 1) % truthTableSize();
            updateTT(currentOutput, ((KarnaughMaze) m).numInputs, ((KarnaughMaze) m).solution[currentOutput]);

            //ttMatched[currentOutput] = ((KarnaughMaze)m).mazeExit.evaluate() == getCurrentSolution(); 	
            //	Score = getCurrentSolution() == true?1:0;
        }

        //	System.out.println(ttString);
        if (Time > 0) {

            long frametime;
            Time -= (frametime = System.currentTimeMillis() - lastUpdate);

            //records the lowest framerate at a particular frame, excluding ones where it dips down to 1 fps to exclude serious hitches and loading
            if (frametime > longest && frametime < 1000) {
                longest = frametime;
                KarnaughLog.log("longest frametime: " + longest + "ms");
            }

            if (Time <= 0) {

                bunneh = new Bunny(this);

                System.out.println("new bunny");

                Time = 0;
                final File f = new File("drk/sound/music/mission.mp3");

                SoundStreamer.stopPlayImmediately(songID);

                if (f != null) {
                    songID = SoundStreamer.playThreadedStreamedLooped(f);
                } else {
                    KarnaughLog.log("Could not open song file");
                }
            }
        }

        lastUpdate = System.currentTimeMillis();

        //	if(Time % 1000 == 0)
        //		System.out.println(""+minutesLeft()+":"+secondsLeft());
    }

    //how much time is left, minutes only
    public long minutesLeft() {
        return (Time / 1000) / 60;
    }

    //returns the seconds portion of the time remaining, not counting whole minutes
    public long secondsLeft() {
        return (Time / 1000) % 60;
    }

    //starts a new game at map01
    public static void mainGame() {
        KarnaughLog.clearLog();
        KarnaughLog.log("Starting Dr. Karnaugh's Lab");

        KarnaughGame m = new KarnaughGame();
        m.loadMap("map01.kar");
        m.camera.fovy = 30;
        m.doMain(GAME_WIDTH, GAME_HEIGHT, null, true);
        Menu.story.dispose();
    }

    //returns the value at the current truth table index
    public boolean getCurrentSolution() {
        return ((KarnaughMaze) m).solution[currentOutput];
    }

    //returns the size of the solution truth table -- which is 2^(number of inputs)
    public int truthTableSize() {
        return ((KarnaughMaze) m).solution.length;
    }

    //runs a test iteration of the game
    public static void main(String args[]) {

        KarnaughLog.clearLog();
        KarnaughLog.log("Starting Dr. Karnaugh's Lab");

        KarnaughGame m = new KarnaughGame();

        m.camera.fovy = 30;

        m.loadMap("map03.kar");

        m.doMain(GAME_WIDTH, GAME_HEIGHT, null, true);

        for (boolean b : ((KarnaughMaze) m.m).solution) {
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
