//I'm trying to lay the framework for the node graph to generate the floyd's table to generate the Vector3D linkedLists for use in the wire generation and bunny 
//pathfinding redux.  This is entirely untested, so if you see something that looks funny or have a better idea on how to do something let me know.  

package drk.maze;

import drk.*;
import drk.graphics.game.*;
import java.util.*; 

//these nodes compose a graph containing doorways and unoccupied centers of rooms
//used to generate wires and for bunny pathfinding
public class MazeNode {

  public int roomID = -1;
  public int roomLocation = -1;
  
  public Vector3D position = null;
  
  boolean active = true; // this flag is disabled if a mazenode is in the middle of a wall or mazeitem
 	//determines whether the floyd's table generates paths containing this node

  public static int mazeSize = 0;
  public static int mazeWidth = 0;
  public static int mazeHeight = 0;
  
  public static final int TOP = 0;
  public static final int BOTTOM = 1;
  public static final int CENTER = 2;
  public static final int LEFT = 3;
  public static final int RIGHT = 4;
  public static final int ULEFT = 5;
  public static final int URIGHT = 6;
  public static final int LLEFT = 7;
  public static final int LRIGHT = 8;
  
  public int[] connectionList = null;

//constructor.  duh.  
 public MazeNode(Room r, Vector3D rc,  int l){
 
 	roomID = r.getID();
 	roomLocation = l;
 	
 	position = new Vector3D(rc.x, rc.y, rc.z);
 	
 	//i'm not entirely sure about this -- i might be getting my axes and my length vs. width mixed up
 	//at any rate this sets the node location and connections array
 	//connections array contains indices into the karnaughmaze nodelist
 	boolean tmp = false;
 	
 	switch(l){
 	
 		case TOP:		position = new Vector3D(rc.x, rc.y, rc.z - HorrorWallMaze.ROOM_LENGTH);
 						tmp = false;
 						
 						//if there exists a room to the north
 						if(roomID - mazeWidth >= 0 && roomID / mazeWidth != 0){tmp = true;}
 						
 						if(tmp)connectionList = new int[4];
 						else connectionList = new int[3];
 						
 						connectionList[0] = ULEFT + roomID;
 						connectionList[1] = URIGHT + roomID;
 						connectionList[2] = CENTER + roomID;
 						
 						
 						if(tmp){
 							connectionList[3] = roomID - mazeWidth + BOTTOM;
 						}
 						
 						break;
 		case BOTTOM: 	position = new Vector3D(rc.x, rc.y, rc.z + HorrorWallMaze.ROOM_LENGTH);
 						
 						tmp = false;
 						
 						//if there exists a room to the south
 						if(roomID + mazeWidth < mazeSize && roomID / mazeWidth < (mazeHeight-1)){tmp = true;}
 						
 						if(tmp)connectionList = new int[4];
 						else connectionList = new int[3];
 						
 						connectionList[0] = LLEFT + roomID;
 						connectionList[1] = LRIGHT + roomID;
 						connectionList[2] = CENTER + roomID;
 						
 						
 						if(tmp){
 							connectionList[3] = roomID + mazeWidth + TOP;
 						}
 		
 		
 						break;
 		case CENTER: 	position = new Vector3D(rc.x, rc.y, rc.z);
 						connectionList = new int[8]; 
 						 connectionList[0] = roomID + ULEFT; 
 						 connectionList[1] = roomID + URIGHT; 
 						 connectionList[2] = roomID + LLEFT; 
 						 connectionList[3] = roomID + LRIGHT; 
 						 connectionList[4] = roomID + TOP;
 						 connectionList[5] = roomID + BOTTOM; 
 						 connectionList[6] = roomID + LEFT;
 						 connectionList[7] = roomID + RIGHT;
 		
 						break;
 		case LEFT: 		position = new Vector3D(rc.x - HorrorWallMaze.ROOM_WIDTH, rc.y, rc.z);
 						
 						 tmp = false;
 						
 						//if there exists a room to the left
 						if(roomID != 0 && roomID % mazeWidth!= 0){tmp = true;}
 						
 						if(tmp)connectionList = new int[4];
 						else connectionList = new int[3];
 						
 						connectionList[0] = ULEFT + roomID;
 						connectionList[1] = LLEFT + roomID;
 						connectionList[2] = CENTER + roomID;
 						
 						
 						if(tmp){
 							connectionList[3] = roomID - 1 + RIGHT;
 						}
 						
 						
 						
 		
 						break;
 		case RIGHT: 	position = new Vector3D(rc.x + HorrorWallMaze.ROOM_WIDTH, rc.y, rc.z);
 						
 						 tmp = false;
 						
 						//if there exists a room to the right
 						if(roomID  +1 != mazeSize  && roomID % mazeWidth!= mazeWidth - 1){tmp = true;}
 						
 						if(tmp)connectionList = new int[4];
 						else connectionList = new int[3];
 						
 						connectionList[0] = URIGHT + roomID;
 						connectionList[1] = LRIGHT + roomID;
 						connectionList[2] = CENTER + roomID;
 						
 						
 						if(tmp){
 							connectionList[3] = roomID + 1 + LEFT;
 						}
 						
 		
 		
 						break;
 		case ULEFT: 	position = new Vector3D(rc.x - HorrorWallMaze.ROOM_WIDTH, rc.y, rc.z - HorrorWallMaze.ROOM_LENGTH);
 						connectionList = new int[4]; 
 						 connectionList[0] = roomID + LEFT; 
 						 connectionList[1] = roomID + TOP; 
 						 connectionList[2] = roomID + CENTER; 
 						 connectionList[3] = roomID + LLEFT;
 					
 						break;
 		case URIGHT: 	position = new Vector3D(rc.x + HorrorWallMaze.ROOM_WIDTH, rc.y, rc.z - HorrorWallMaze.ROOM_LENGTH);
 						connectionList = new int[4]; 
 						 connectionList[0] = roomID + RIGHT; 
 						 connectionList[1] = roomID + TOP; 
 						 connectionList[2] = roomID + CENTER; 
 						 connectionList[3] = roomID + LRIGHT;
 						
 						break;
 		case LLEFT: 	position = new Vector3D(rc.x - HorrorWallMaze.ROOM_WIDTH, rc.y, rc.z + HorrorWallMaze.ROOM_LENGTH);
 						connectionList = new int[4]; 
 						 connectionList[0] = roomID + LEFT; 
 						 connectionList[1] = roomID + BOTTOM; 
 						 connectionList[2] = roomID + CENTER; 
 						 connectionList[3] = roomID + ULEFT;
 		
 						break;
 		case LRIGHT: 	position = new Vector3D(rc.x + HorrorWallMaze.ROOM_WIDTH, rc.y, rc.z + HorrorWallMaze.ROOM_LENGTH);
 						 connectionList = new int[4]; 
 						 connectionList[0] = roomID + RIGHT; 
 						 connectionList[1] = roomID + BOTTOM; 
 						 connectionList[2] = roomID + CENTER; 
 						 connectionList[3] = roomID + URIGHT;

 						break;
 		default:  break;
 	}
 	
 	
    
 	
 }
 
 
 /*
 public boolean searchNodeConnection(int a, int b){
 	
 	for(int[] x: connectionList){
 		
 		if(x[0] == a && x[1] == b){
 			return true;
 		}
 		
 	}
	return false; 	
 }*/
 
 
 /*
 public boolean searchNodeConnection(MazeNode a){
 	
 	
 	for(int[] x: connectionList){
 		
 		if(x[0] == a.roomID && x[1] == a.roomLocation)return true;
 		
 	}return false;
 	
 }*/
 
 
 
 /*
 //returns a list of ordered pairs containing the <room id, position> of connected nodes in the graph
 public ArrayList<int[]> generateConnections(){
 	
 	ArrayList<int[]> conn = new ArrayList<int[]>();
 	
 	int[] tArray =  null;
 	
 	//if there's problems constructing the graph its likely that we forgot to set the mazeSize class field
 	KarnaughLog.log("\nWorking with maze of size " + mazeSize);
 	
 	switch(roomLocation){
 		
 		//if this is a northern doorway node
 		case NORTH:
 			
 			//connect to the west node of this room
 			tArray = new int[2];
 			tArray[0] = roomID;
 			tArray[1] = WEST;
 			conn.add(tArray);
 			
 			
 			//connect to the center node of this room
 			tArray = new int[2];
 			tArray[0] = roomID;
 			tArray[1] = CENTER;
 			conn.add(tArray);
 			
 			//connect to the east room's west node if the room exists
 			if(roomID + 1 < mazeSize && roomID % mazeWidth != mazeWidth-1){
 			 tArray = new int[2];
 			 tArray[0] = roomID+1;
 			 tArray[1] = WEST;
 			 conn.add(tArray);
 			}
 				
 			
 			//if there is a room directly to the north add its node connections
 			if(roomID - mazeWidth >= 0){
 			
 			    //room to the north's center node
 				tArray = new int[2];
 				tArray[0] = roomID - mazeWidth;
 				tArray[1] = CENTER;
 				conn.add(tArray);
 				
 				//room to the north's west node
 				tArray = new int[2];
 				tArray[0] = roomID - mazeWidth;
 				tArray[1] = WEST;
 				conn.add(tArray);
 				
 				//room to the northeast's west node -- requires extra check to make sure that there is a room to the northeast
 				if(roomID % mazeWidth != mazeWidth - 1 && roomID - mazeWidth + 1 >= 0){
 				 tArray = new int[2];
 				 tArray[0] = roomID - mazeWidth + 1;
 				 tArray[1] = WEST;
 				 conn.add(tArray);
 				}
 			}
 			break;
 			
 			
 			
 		case WEST:
 			
 			//connect to this room's north node
 			tArray = new int[2];
 			tArray[0] = roomID;
 			tArray[1] = NORTH;
 			conn.add(tArray);
 			
 			//connect to this room's center node
 			tArray = new int[2];
 			tArray[0] = roomID;
 			tArray[1] = CENTER;
 			conn.add(tArray);
 			
 			
 			//connect to west room's center and north nodes
 			if(roomID-1 >=0 && roomID % mazeWidth != 0){
 			
 			 tArray = new int[2];
 			 tArray[0] = roomID-1;
 			 tArray[1] = NORTH;
 			 conn.add(tArray);
 			
 			 tArray = new int[2];
 			 tArray[0] = roomID-1;
 			 tArray[1] = CENTER;
 			 conn.add(tArray);
 			}
 			
 			//connect to the southwest room's north node
 			if(roomID-1 + mazeWidth < mazeSize && roomID % mazeWidth != 0){
 			tArray = new int[2];
 			tArray[0] = roomID-1 + mazeWidth;
 			tArray[1] = NORTH;
 			conn.add(tArray);
 			}
 			
 			//connect to the south room's north node
 			if(roomID + mazeWidth < mazeSize){
 				if(roomID + 1 < mazeSize){
 			 	 tArray = new int[2];
 			 	 tArray[0] = roomID+mazeWidth;
 			 	 tArray[1] = NORTH;
 			 	 conn.add(tArray);
 				}
 			}
 			
 			
 			
 			
 			
 			break; 
 		
 		case CENTER: 
 			
 			//connect to this room's north node
 			tArray = new int[2];
 			tArray[0] = roomID;
 			tArray[1] = NORTH;
 			conn.add(tArray);
 			
 			//connect to this room's west node
 			tArray = new int[2];
 			tArray[0] = roomID;
 			tArray[1] = WEST;
 			conn.add(tArray);
 			
 			//connect to the south room's north node
 			if(roomID + mazeWidth < mazeSize){
 				if(roomID + 1 < mazeSize){
 			 	 tArray = new int[2];
 			 	 tArray[0] = roomID+mazeWidth;
 			 	 tArray[1] = NORTH;
 			 	 conn.add(tArray);
 				}
 			}
 			
 			//connect to the east room's west node
 			//connect to the east room's west node if the room exists
 			if(roomID + 1 < mazeSize && roomID % mazeWidth != mazeWidth-1){
 			 tArray = new int[2];
 			 tArray[0] = roomID+1;
 			 tArray[1] = WEST;
 			 conn.add(tArray);
 			}
 			
 			
 			break;
 		
 	}
 	
 	
 	//log the connections made
 	KarnaughLog.log("\nNode "+roomID + "," + roomLocation+":");
 	
 	for(int[] x : conn){
 		KarnaughLog.log(""+x[0] + ":" + x[1]);
 	}
 	KarnaughLog.log("\n");
 	
 	return conn;
 }
  
 */
 
 /*
 
 //THIS FUNCTION IS A STUB.  ILL GET TO IT SOON
 //given a maze and a list of mazenodes, determine which mazenodes correspond to valid path nodes in the maze and adjust the flags accordingly
 public static void setActiveFlags(Maze m, MazeNode[] nodegraph){
 	
 	for(Room r : m.RoomList){
	    
	    //if there's an item in the middle of the floor bunny/wires can't go through it 	
 		if(r.localItem!= null){
 			nodegraph[positionToIndex(r.RoomID, CENTER)].active = false; 		
 		}
 		
 		nodegraph[positionToIndex(r.RoomID, NORTH)].active = !r.Up;
 		nodegraph[positionToIndex(r.RoomID, WEST)].active =!r.Left;
 		
 	}
 	
 }*/
 
 /*
 //takes the room ID and the location constant within the room and gives the appropriate index into the one dimensional node graph array
 public static int positionToIndex(int room, int location){
 	return 3 * room + location; //three because there are 3 possible locations in each room
 	 //so the array looks like { [room 0, position1], [room 0, position 2], [room 0, position 3]........ [room n position 3]}
 }
 */
 

}
