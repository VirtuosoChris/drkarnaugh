package drk.maze;

import drk.*;
import java.util.*;
import java.io.*;

public class KarnaughMaze extends drk.graphics.game.LegoMaze
{
	
	public int timelimit = 0;
	public String nextmap = null; 
	private ArrayList<MazeItem> components = null;
	

	//some accessor methods
	public int timeLimit(){return timelimit;}
	public String nextLevel(){return nextmap;}

	String filename;
	//constructor, takes width, height, timelimit, next map, and an arraylist of components to populate the maze with.
	public KarnaughMaze(int w, int h, int t, String n, ArrayList<MazeItem> c){
		
		super(w,h);
		
		components = c;
		
		timelimit = t;
		nextmap = n;
		
		//associate each item in c with a room in super's ArrayList<Room> RoomList
		//so what we do is first copy the list of rooms, and for each component in c generate a random number within the size of the list of rooms.
		//then add the component to the room, and remove the room from the copied list so we don't hit it again.  If the size of the components list is larger than
		//the size of the room list  an exception would have been thrown in the map loader, so if the user somehow haxxed the game, and is an idiot, 
		//SOL
		
		//um what about our implicit entrances and exits?  need to add those in too
		
		//copies of the room and component lists in order to perform this operation
		ArrayList<Room> copy = (ArrayList<Room>)RoomList.clone();
		ArrayList<MazeItem> cCopy = (ArrayList<MazeItem>)c.clone();
		
		Random r = new Random();
		
		while(!cCopy.isEmpty()){
			
			int rnd = r.nextInt()%copy.size();
			if(rnd <0)rnd*=-1;
			
			Room rtmp = copy.remove(rnd);
			
			rtmp.setItem(cCopy.remove(0));	
			
		}
		
		//temp-- this was a lazy test to make sure the camera-set-coordinates in karnaughgame were correct and the 
		//camera would not spawn in the incorrect location
		//RoomList.get(0).setItem(new Entrance());
	
		
	}

	//loads a map.  adds extension to f
	public static KarnaughMaze loadMaze(String f){
		
		KarnaughLog.log("\nLoading map "+f+".kar");
		
		boolean solution[] = null;
		int numinputs = 0;
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
		 
		 KarnaughLog.log("There are "+numcomponents+" components in this maze");
		 
		 //generates the classes from the string given.  need some kind of extra processing for things like notes which 
		 //can't be instantiated without further arguments to be loaded from the file
		 //also exception handling to search the internet for component/mapname if not found can be added at a future date
		for(int i = 0; i < numcomponents;i++){
			String classStr = s.next();
			
			classStr = "drk.circuit."+classStr;
			
		  Class n = Class.forName(classStr);
		 Object ob=n.newInstance();
		  if(ob instanceof MazeItem)
			  components.add((MazeItem)ob);
		  else
			  KarnaughLog.log("Loading" + n.getSimpleName());
		  
		
		  
		  //if ! instanceof test return null and throw exception 
		}
		
		
		
		numinputs = s.nextInt();
		
		
		//read in 2^numinputs boolean values
		
		solution = new boolean[(1<<numinputs)];
		
		KarnaughLog.log("Maze has "+numinputs+"inputs, therefore "+(1<<numinputs)+" truth table size");
		
		for(int i = 0; i < (1<<numinputs); i++){
			
			solution[i] = s.nextBoolean();	
			
		}
		
		KarnaughLog.log("\nMap Truth Table Solution:");
		
		for(int i = 0; i < solution.length;i++){
			KarnaughLog.log(""+Integer.toString(i,2)+":"+solution[i]);
		}
		
		
		 //minus 2 because the entrance and exit will be hard coded into the maze generation.  only guarantee that there will
		 //be at least one of each
		 //the map designer can put multiples of each in to help the player escape the bunny and or confuse the fuck out of them  
		 if(numcomponents + numinputs > (mazewidth*mazeheight)-2 || numcomponents < 0 || numinputs <= 0){
		 	throw new Exception("Invalid number of components for maze");
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
		
		KarnaughLog.log("Successfully loaded map data");
		
		return new KarnaughMaze(mazewidth, mazeheight, timelimit, nextmap, components);
				
	}
	
	
	public static void main(String args[]){
		KarnaughLog.clearLog();
		
		System.out.println(KarnaughMaze.loadMaze("map05"));
		
	}
	
	
	
}