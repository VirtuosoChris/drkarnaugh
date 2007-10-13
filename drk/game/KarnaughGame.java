package drk.game;
import drk.KarnaughLog;
import drk.*;
import drk.maze.*;
import javax.swing.*;
import java.awt.*;


public class KarnaughGame extends MazeGame implements Updatable{

	private static int resWidth  = 1024;
	private static int resHeight = 768;

	private int Score;
	private long Time; 
	public boolean paused;
	
	private long lastUpdate = 0;
	
	public KarnaughGame(){
		Score = 0;
		Time = 0;
		paused = false;
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
			loadMap(((KarnaughMaze)this.m).nextmap);
		}
		else{
			gameOver();
		}
		
	}
	
	
	//loads a level.
	//TODO : will also set the user camera to an appropriate location within the maze and do other stuff
	public boolean loadMap(String m){
		this.m = (KarnaughMaze)KarnaughMaze.loadMaze(m);
		if(this.m == null)return false;
		
		Time = ((KarnaughMaze)this.m).timelimit*1000;
		
		lastUpdate = System.currentTimeMillis();
		
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
		
		//test for win/die
		
		
		//if time <= release the bunny or otherwise kill the player
		
		long tmp = System.currentTimeMillis() - lastUpdate;

		Time -= tmp;
		
		lastUpdate = System.currentTimeMillis();
		
		System.out.println(""+minutesLeft()+":"+secondsLeft());
		
	}
	
	
	//how much time is left, minutes only
	public long minutesLeft(){
		return (Time/1000)/60;
	}
	
	//returns the seconds portion of the time remaining, not counting whole minutes
	public long secondsLeft(){
		return (Time/1000)%60;
	}
	
	
	//point of entry
	public static void main(String args[]){
	
		KarnaughLog.clearLog();
		KarnaughLog.log("Starting Dr. Karnaugh's Lab");
		
		KarnaughGame m = new KarnaughGame();
		m.loadMap("test");
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