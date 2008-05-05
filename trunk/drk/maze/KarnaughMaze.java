package drk.maze;

import drk.*;
import java.util.*;
import java.io.*;
import drk.circuit.*;

public class KarnaughMaze extends drk.graphics.game.HorrorWallMaze
{
	
	public double FloydTable[][] = null;
	public int PathTable[][] = null;
	
	MazeNode nodeGraph[] = null;
	public int timelimit = 0; //what is the timelimit for this map?
	public String nextmap = null; //what is the name of the map to go to after this one is finished?
	public ArrayList<MazeItem> components = null;
	public String songfile = null; //string name of the file containing the song for this map.
	public String mapDirectory = null; //string containing the path to the current map -- used to access the next map, by appending the mapname to this string
	
	//some accessor methods
	public int timeLimit(){return timelimit;}
	public String nextLevel(){return nextmap;}
	
	public Exit mazeExit;
		
	public boolean solution[];//truth table solution for the current map
	
	public int numInputs;//how many inputs are there on this map?
	
	public static final int MAX_INPUTS = 8;//with 8, it will still take 512 seconds to scroll through the entire truth table once. this should
	//be plenty

	String filename;

	public LogicInput inputsA[];

	
	
	//constructor, takes all the variables needed to initailize the game level
	public KarnaughMaze(int w, int h, int t, String n, ArrayList<MazeItem> c, String sf, String d, boolean s[], int inputs){
		
		super(w,h);
		
		solution = s;
		
		songfile = sf;
		
		c.add(new Entrance());
		c.add(mazeExit = new Exit());
	
		
		numInputs = inputs;
		
		
		inputsA = new LogicInput[numInputs];
		
		for(int i = 0; i < numInputs; i++){
			
			LogicInput li = new LogicInput(false);
			
			c.add(li);
			
			inputsA[i] = li;
			
		}
		
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
		
		//set some class constants
		MazeNode.mazeSize = RoomList.size();
		MazeNode.mazeWidth = this.width;
		MazeNode.mazeHeight = this.height;
		
		//nine nodes for each room
		nodeGraph = new MazeNode[RoomList.size() * 9];
		
		
		//for each room
		for(int i = 0; i < RoomList.size(); i++){
			

		  Room tRm = RoomList.get(i);
		  Vector3D tmpV = getRoomMiddle(tRm);
			
			//for each node position in the room
			for(int j = 0; j < 9; j++){
				
			  nodeGraph[9*i + j] = new MazeNode(tRm,tmpV,j);
			
			}
			
			
			//nodeGraph[MazeNode.positionToIndex(i, MazeNode.CENTER)] = new MazeNode(i, MazeNode.CENTER,  tmpV);
			
			//nodeGraph[MazeNode.positionToIndex(i, MazeNode.NORTH)] = new MazeNode(i, MazeNode.NORTH, tmpV);
			
			//nodeGraph[MazeNode.positionToIndex(i, MazeNode.WEST)] = new MazeNode(i, MazeNode.WEST, tmpV);
		}
		
		
	
		//MazeNode.setActiveFlags(this, nodeGraph);
		
		
		//GENERATE THE FLOYD TABLE AND PATH TABLE FROM THE NODE GRID
		
		FloydTable = new double[nodeGraph.length][nodeGraph.length];
		PathTable = new int[nodeGraph.length][nodeGraph.length];
		
		
		//initialize tables
		
		//set everything to null to start
	//	for(int i = 0; i < nodeGraph.length; i++){
	//		for(int j = 0; j < nodeGraph.length; j++){
	//			
	//			FloydTable[i][j] = -1.0;
	//			PathTable[i][j]= -1;
			
	//		}
	//	}
		
		
		
		for(int i = 0; i < nodeGraph.length; i++){
			
		//	if(nodeGraph[i].active == false)continue;
			
			for(int j = 0; j < nodeGraph.length; j++){
				
		//	if(nodeGraph[j].active == false)continue;
				
				//the distance and path for each active node to itself is set.
				if(i == j){
					FloydTable[i][i] = 0.0;
					PathTable[i][i] = i; //we don't have to take any path to get here
					continue;
				}
				
						
				//connect the adjacent nodes
				else if(nodeGraph[i].searchConnections(j) && nodeGraph[i].active && nodeGraph[j].active){
					FloydTable[i][j] = nodeGraph[i].position.distance(nodeGraph[j].position);
					PathTable[i][j] = j;
					continue;
				}
					
				else{	
				FloydTable[i][j] = 100000.0;  //can't have a negative distance, so this is an invalid value
				PathTable[i][j] = j; //there is no negative index into the array, so no path exists
				}
				
				}
			}
		
		
		
		//loop and make paths through valid nodes
		///loop though, use k as intermediate nodes		
		for(int k = 0; k < nodeGraph.length; k++){
			if(!nodeGraph[k].active)continue;
		//loop through start nodes
		for(int i = 0; i < nodeGraph.length; i++){
			
		 if(!nodeGraph[i].active)continue; 
			
			//loop through destination nodes
			for(int j = 0; j < nodeGraph.length; j++){
				if(!nodeGraph[j].active)continue;
				
			
					
					//if distance from i to j is less than the distance from i to k + the distance from k to j and i, j, and k have connections
					//OR the current distance is infinity
					
					//make sure the connections actually exist
					//if(FloydTable[i][k] >= 0.0 && FloydTable[k][j] >= 0.0){
				
						double tDist = 0;
						
						tDist = (FloydTable[i][k] + FloydTable[k][j]);
						if(tDist < FloydTable[i][j]){
							
							FloydTable[i][j] = tDist;
							PathTable[i][j] = PathTable[i][k];
							
								
						}
						
					//}
					
					
				}		
			}	
		}
		
		
		for(int i = 0; i <  nodeGraph.length; i++){
			
			if(nodeGraph[i] == null){
			System.out.println("HUGE PROBLEM WITH NODE GRAPH GENERATION");	
			}
			
			MazeNode m = nodeGraph[i];
			KarnaughLog.log("node "+m.roomID*9 + m.roomLocation+":"+m.position+"active?"+m.active);
		}
		
				

	}
	
	
	
	
	
	

	//loads a map.
	public static KarnaughMaze loadMaze(String f){
		
		KarnaughLog.log("\nLoading map "+f+"");
		
		//variables to store data loaded in from file
		String sf; //song filename
		boolean solution[] = null;
		int numinputs = 0;
		int mazewidth = 0;
		int mazeheight = 0;
		int timelimit = 0;
		String nextmap = null; //"LAST_LEVEL" means that this is the last map of the campaign
		int numcomponents = 0;
		ArrayList<MazeItem> components = new ArrayList<MazeItem>(); //arraylist to store the list of components for the maze
		String d;//the path of the current mapFile.
		File mapFile=null;
		
		//TODO : we will add the win conditions to the map format when we've finalized the gameplay
		
		try{ //scan the data.  log exceptions where appropriate.  if successful create a new KarnaughMaze with the data
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
		
		//if the user exceeds the max number of inputs they're an asshole that hates the player.  If i knew how to format their hard drive from Java
		//I would
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
		
		//log the truth table as well as an "approximation" (eg leading zeroes dropped) of their binary value
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
	
	
	//test
	public static void main(String args[]){
		KarnaughLog.clearLog();
		KarnaughMaze x;
		System.out.println(x=KarnaughMaze.loadMaze("map05.kar"));
		
		System.out.println("XXXX:"+x.mapDirectory);
		
	}
	
	
	
}