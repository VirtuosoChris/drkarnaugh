package drk.maze;
import drk.*;
import java.util.*;
import java.io.*;

public class KarnaughMaze extends Maze
{
	
	private int timelimit = 0;
	private String nextmap = null; 
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
			String classStr = s.next();
			
			classStr = "drk.maze."+classStr;
			
		  Class n = Class.forName(classStr);
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
		
		KarnaughLog.log("Successfully loaded map data");
		
		return new KarnaughMaze(mazewidth, mazeheight, timelimit, nextmap, components);
				
	}
	
	
	public static void main(String args[]){
		
		System.out.println(KarnaughMaze.loadMaze("test"));
		
	}
	
	
	
}