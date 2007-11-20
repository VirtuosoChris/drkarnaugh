package drk.maze;

import drk.*;
import java.util.*;
import java.io.*;
import drk.circuit.*;

public class KarnaughMaze extends drk.graphics.game.HorrorWallMaze
{
	
	public int timelimit = 0;
	public String nextmap = null; 
	public ArrayList<MazeItem> components = null;
	public String songfile = null;
	public String mapDirectory = null;

	//some accessor methods
	public int timeLimit(){return timelimit;}
	public String nextLevel(){return nextmap;}
	
	public boolean solution[];
	
	public int numInputs;
	
	public static final int MAX_INPUTS = 32;//a map may have no more than 32 inputs.  Because an int can only
	//store 2^32 for the truth table index.  And over like 4 is starting to get motherfucking ridiculous

	String filename;
	//constructor, takes width, height, timelimit, next map, and an arraylist of components to populate the maze with.
	public KarnaughMaze(int w, int h, int t, String n, ArrayList<MazeItem> c, String sf, String d, boolean s[], int inputs){
		
		super(w,h);
		
		solution = s;
		
		songfile = sf;
		
		c.add(new Entrance());
		c.add(new Exit());
		
		numInputs = inputs;
		
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
		
		ArrayList<Room> copy = new ArrayList<Room>(RoomList);
		ArrayList<MazeItem> cCopy = new ArrayList<MazeItem>(c);
		
		
	
		
		Random r = new Random();
		
		while(!cCopy.isEmpty()){
			
			int rnd = r.nextInt()%copy.size();
			if(rnd <0)rnd*=-1;
			
			Room rtmp = copy.remove(rnd);
			
			rtmp.setItem(cCopy.remove(0));
			rtmp.getItem().setMaze(this);	
			
		}
		
		
		mapDirectory = d;
		
		//temp-- this was a lazy test to make sure the camera-set-coordinates in karnaughgame were correct and the 
		//camera would not spawn in the incorrect location
		//RoomList.get(0).setItem(new Entrance());
	}

	//loads a map.  adds extension to f
	public static KarnaughMaze loadMaze(String f){
		
		KarnaughLog.log("\nLoading map "+f+"");
		
		String sf;
		boolean solution[] = null;
		int numinputs = 0;
		int mazewidth = 0;
		int mazeheight = 0;
		int timelimit = 0;
		String nextmap = null; //"LAST_LEVEL" means that this is the last map of the campaign
		int numcomponents = 0;
		ArrayList<MazeItem> components = new ArrayList<MazeItem>();
		String d;//the path of the current mapFile.
		File mapFile=null;
		
		//TODO : we will add the win conditions to the map format when we've finalized the gameplay
		
		try{ //scan the data.  throw exceptions where appropriate.  if successful create a new KarnaughMaze with the data
		 Scanner s = new Scanner(mapFile = new File(f));
		
		 d = mapFile.getPath().substring(0,mapFile.getPath().length() - mapFile.getName().length());
		
		 mazewidth = s.nextInt();	   //dimensions of the maze, in number of rooms
		 mazeheight = s.nextInt();	   //
		 
		 if(mazewidth <= 0 || mazeheight <=0){
		 	KarnaughLog.log("Invalid Maze Dimensions");
		 	return null;
		 }
		 
		 KarnaughLog.log("Maze is "+mazewidth+" by "+mazeheight);
		 
		 timelimit = s.nextInt();      //how many seconds the maze allows the player before they face gruesome death
		 
		 if(timelimit <= 0){
		 	KarnaughLog.log("Invalid Time Limit");
		 	return null;
		 }
		 
		 KarnaughLog.log("TimeLimit is "+timelimit+" seconds");
		 
		 nextmap = s.next();           //when the map is finished, start this one as part of its "campaign"
		 numcomponents = s.nextInt();  //map file stores number of components that we should expect in the file
		 
		 if(nextmap.equals("LAST_LEVEL")){
		 	KarnaughLog.log("This is the last level of this campaign");
		 }else{
		 	KarnaughLog.log("Next map is "+d+nextmap);
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
		  
		}
		
		numinputs = s.nextInt();
		
		if(numinputs > MAX_INPUTS){
			KarnaughLog.log("Map contains way too many inputs.  Please discard your map and start over.  Bastard");
			return null;
		}
		
		
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
		 	KarnaughLog.log("Invalid number of components for maze");
		 	return null;
		 }	
		 	
		 	
		 //sound file
		 sf = s.next(); 
			KarnaughLog.log("Song is "+ sf);
		
		
		}catch(FileNotFoundException e){
		  KarnaughLog.log("Map file "+f+" not found");
		  return null;
		}catch(ClassNotFoundException e){
		  KarnaughLog.log("Could not find component:\n"+e);
		  	//do other stuff here if we add the component browser
		  	return null;
		}
		catch(Exception e){
		  KarnaughLog.log("unhandled exception:"+e);
		  KarnaughLog.log("File possibly contains invalid data");
		  return null;
		}
		
		KarnaughLog.log("\n");
		
		//if we got all the data create a new maze object for use in the game
		
		KarnaughLog.log("Successfully loaded map data");
		
		return new KarnaughMaze(mazewidth, mazeheight, timelimit, nextmap, components,sf,d,solution,numinputs);
				
	}
	
	
	public static void main(String args[]){
		KarnaughLog.clearLog();
		KarnaughMaze x;
		System.out.println(x=KarnaughMaze.loadMaze("map05.kar"));
		
		System.out.println("XXXX:"+x.mapDirectory);
		
	}
	
	
	
}