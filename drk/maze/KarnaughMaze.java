package drk.maze;

import drk.*;
import java.util.*;
import java.io.*;

public class KarnaughMaze //extends Maze
{
	
	private int timelimit;
	private String nextmap; 

	//some accessor methods
	public int timeLimit(){return timelimit;}
	public String nextLevel(){return nextmap;}

	String filename;
	//constructor, takes width, height, timelimit, next map, and an arraylist of components to populate the maze with.
	public KarnaughMaze(int w, int h, int t, String n, ArrayList<MazeItem> c){
		
//		super(w,h);
		
		timelimit = t;
		nextmap = n;
		
		//needs to associate  each element of c with a node in super
		
	}

	//loads a map.  adds extension to f
	public static KarnaughMaze loadMaze(String f){
		
		KarnaughLog.log("\nLoading map "+f+".kar");
		
		int mazewidth = 0;
		int mazeheight = 0;
		int timelimit = 0;
		String nextmap = null; //"LAST_LEVEL" means that this is the last map of the campaign
		int numcomponents = 0;
		ArrayList<MazeItem> components = new ArrayList<MazeItem>();
		
		//TODO : we will add the win conditions to the map format when we've finalized the gameplay
		
		try{ //scan the data.  throw exceptions where appropriate.  if successful create a new KarnaughMaze with the data
		 Scanner s = new Scanner(new File(f+".kar"));
		
		 mazewidth = s.nextInt();	   //dimensions of the maze, in number of rooms
		 mazeheight = s.nextInt();	   //
		 
		 if(mazewidth <= 0 || mazeheight <=0){
		 	throw new Exception("Invalid Maze Dimensions");
		 }
		 
		 KarnaughLog.log("Maze is "+mazewidth+" by "+mazeheight);
		 
		 timelimit = s.nextInt();      //how many seconds the maze allows the player before they face gruesome death
		 
		 if(timelimit <= 0){
		 	throw new Exception("Invalid Time Limit");
		 }
		 
		 KarnaughLog.log("TimeLimit is "+timelimit+" seconds");
		 
		 nextmap = s.next();           //when the map is finished, start this one as part of its "campaign"
		 numcomponents = s.nextInt();  //map file stores number of components that we should expect in the file
		 
		 if(nextmap.equals("LAST_LEVEL")){
		 	KarnaughLog.log("This is the last level of this campaign");
		 }else{
		 	KarnaughLog.log("Next map is "+nextmap);
		 }
		 
		 //minus 2 because the entrance and exit will be hard coded into the maze generation.  only guarantee that there will
		 //be at least one of each
		 //the map designer can put multiples of each in to help the player escape the bunny and or confuse the fuck out of them  
		 if(numcomponents > (mazewidth*mazeheight)-2 || numcomponents < 0){
		 	throw new Exception("Invalid number of components for maze");
		 }
		 
		 KarnaughLog.log("There are "+numcomponents+" components in this maze");
		 
		 //generates the classes from the string given.  need some kind of extra processing for things like notes which 
		 //can't be instantiated without further arguments to be loaded from the file
		 //also exception handling to search the internet for component/mapname if not found can be added at a future date
		for(int i = 0; i < numcomponents;i++){
		  Class n = Class.forName(s.next());
		  Object ob=n.newInstance();
		  if(ob instanceof MazeItem)
			  components.add((MazeItem)ob);
		  else
			  KarnaughLog.log("Loading" + n.getSimpleName());
		  
		
		  
		  //if ! instanceof test return null and throw exception 
		}	 
		
		
		}catch(FileNotFoundException e){
		  KarnaughLog.log("Map file "+f+" not found");
		  return null;
		}catch(ClassNotFoundException e){
		  KarnaughLog.log(e);
		  	//do other stuff here if we add the component browser
		  	return null;
		}
		catch(Exception e){
		  KarnaughLog.log(e);
		  KarnaughLog.log("File contains invalid data");
		  return null;
		}
		
		KarnaughLog.log("\n");
		
		//if we got all the data create a new maze object for use in the game
		return new KarnaughMaze(mazewidth, mazeheight, timelimit, nextmap, components);
				
	}
	
	
	public static void main(String args[]){
		KarnaughLog.log("Please run KarnaughGame.class to play");
	}
	
	
	
}