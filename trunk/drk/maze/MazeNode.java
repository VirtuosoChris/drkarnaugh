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
  protected Room associatedRoom;
  public int[] connectionList = null;



public boolean searchConnections(int s){
	
	for( int t : connectionList){
		if(t == s) return true;
	}
	return false;
}


//constructor.  duh.  
 public MazeNode(Room r, Vector3D rc,  int l){
 	active = true;
 	associatedRoom = r;
 	roomID = r.getID();
 	roomLocation = l;
 	
 	position = new Vector3D(rc.x, rc.y, rc.z);
 	
 	Vector3D vTop = new Vector3D(rc.x, rc.y, rc.z - HorrorWallMaze.ROOM_LENGTH*.5);
 	Vector3D vBottom = new Vector3D(rc.x, rc.y, rc.z + HorrorWallMaze.ROOM_LENGTH*.5);
 	Vector3D vLeft = new Vector3D(rc.x - HorrorWallMaze.ROOM_WIDTH*.5, rc.y, rc.z);					
 	Vector3D vRight = new Vector3D(rc.x + HorrorWallMaze.ROOM_WIDTH*.5, rc.y, rc.z);
 	Vector3D vCenter = new Vector3D(rc.x, rc.y, rc.z);
 	Vector3D vUL = vTop.midpoint(vLeft);
 	Vector3D vUR = vTop.midpoint(vRight);
 	Vector3D vLL = vBottom.midpoint(vLeft);
 	Vector3D vLR = vBottom.midpoint(vRight);
 	
 
 	//i'm not entirely sure about this -- i might be getting my axes and my length vs. width mixed up
 	//at any rate this sets the node location and connections array
 	//connections array contains indices into the karnaughmaze nodelist
 	boolean tmp = false;
 	
 	switch(l){
 	
 		case TOP:		position = vTop;
 						tmp = false;
 						
 						//if there exists a room to the north
 						if(roomID - mazeWidth >= 0 && roomID / mazeWidth != 0){tmp = true;}
 						
 						if(tmp)connectionList = new int[4];
 						else connectionList = new int[3];
 						
 						connectionList[0] = ULEFT + roomID*9;
 						connectionList[1] = URIGHT + roomID*9;
 						connectionList[2] = CENTER + roomID*9;
 						
 						
 						if(tmp){
 							connectionList[3] = (roomID - mazeWidth)*9 + BOTTOM;
 						}
 						
 						
 					    active = associatedRoom.Up;
 						
 						break;
 		case BOTTOM: 	position = vBottom;
 						tmp = false;
 						
 						//if there exists a room to the south
 						if(roomID + mazeWidth < mazeSize && roomID / mazeWidth < (mazeHeight-1)){tmp = true;}
 						
 						if(tmp)connectionList = new int[4];
 						else connectionList = new int[3];
 						
 						connectionList[0] = LLEFT + roomID*9;
 						connectionList[1] = LRIGHT + roomID*9;
 						connectionList[2] = CENTER + roomID*9;
 						
 						
 						if(tmp){
 							connectionList[3] = (roomID + mazeWidth)*9 + TOP;
 						}
 		
 					
 					    active = associatedRoom.Down;
 						
 						break;
 		case CENTER: 	position =vCenter;
 						connectionList = new int[8]; 
 						 connectionList[0] = roomID*9 + ULEFT; 
 						 connectionList[1] = roomID*9 + URIGHT; 
 						 connectionList[2] = roomID*9 + LLEFT; 
 						 connectionList[3] = roomID*9 + LRIGHT; 
 						 connectionList[4] = roomID*9 + TOP;
 						 connectionList[5] = roomID*9 + BOTTOM; 
 						 connectionList[6] = roomID*9 + LEFT;
 						 connectionList[7] = roomID*9 + RIGHT;
 							
 						active = (associatedRoom.localItem == null);
 								
 						break;
 		case LEFT: 		position = vLeft;
 						 tmp = false;
 						
 						//if there exists a room to the left
 						if(roomID != 0 && roomID % mazeWidth!= 0){tmp = true;}
 						
 						if(tmp)connectionList = new int[4];
 						else connectionList = new int[3];
 						
 						connectionList[0] = ULEFT + roomID*9;
 						connectionList[1] = LLEFT + roomID*9;
 						connectionList[2] = CENTER + roomID*9;
 						
 						
 						if(tmp){
 							connectionList[3] = (roomID - 1)*9 + RIGHT;
 						}
 						
 						
 						
 					    active = associatedRoom.Left;
 						
 		
 						break;
 		case RIGHT: 	position = vRight;
 						 tmp = false;
 						
 						//if there exists a room to the right
 						if(roomID  +1 != mazeSize  && roomID % mazeWidth!= mazeWidth - 1){tmp = true;}
 						
 						if(tmp)connectionList = new int[4];
 						else connectionList = new int[3];
 						
 						connectionList[0] = URIGHT + roomID*9;
 						connectionList[1] = LRIGHT + roomID*9;
 						connectionList[2] = CENTER + roomID*9;
 						
 						
 						if(tmp){
 							connectionList[3] = (roomID + 1)*9 + LEFT;
 						}
 						
 						
 					    active = associatedRoom.Right;
 		
 						break;
 		case ULEFT: 	position = vUL;
 		
 						active = true;
 						connectionList = new int[4]; 
 						 connectionList[0] = roomID*9 + LEFT; 
 						 connectionList[1] = roomID*9+ TOP; 
 						 connectionList[2] = roomID*9 + CENTER; 
 						 connectionList[3] = roomID*9 + LLEFT;
 					
 						break;
 		case URIGHT: 	position = vUR;
 						active = true;
 						connectionList = new int[4]; 
 						 connectionList[0] = roomID*9 + RIGHT; 
 						 connectionList[1] = roomID*9 + TOP; 
 						 connectionList[2] = roomID*9 + CENTER; 
 						 connectionList[3] = roomID*9 + LRIGHT;
 						
 						break;
 	
 		case LLEFT: 	
 					active = true;
 					position = vLL;
 						connectionList = new int[4]; 
 						 connectionList[0] = roomID*9 + LEFT; 
 						 connectionList[1] = roomID*9 + BOTTOM; 
 						 connectionList[2] = roomID*9 + CENTER; 
 						 connectionList[3] = roomID*9 + ULEFT;
 		
 						break;
 		case LRIGHT: 	 active = true;
 						position =vLR;
 						 connectionList = new int[4]; 
 						 connectionList[0] = roomID*9 + RIGHT; 
 						 connectionList[1] = roomID*9 + BOTTOM; 
 						 connectionList[2] = roomID*9 + CENTER; 
 						 connectionList[3] = roomID*9 + URIGHT;

 						break;
 		default:  
 			
 			KarnaughLog.log("INVALID NODE POSITION");
 			System.exit(0);
 			
 			break;
 	}
 	
 	
 }
 
 

 
 
 

}
