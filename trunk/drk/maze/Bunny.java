//bunny draft one
//just goes to the room the player is in and initializes a chase mode
//if the user "escapes" into another room while the bunny is chasing then the bunny moves to the center of the room
//to get back on the path grid
//probably very easy to avoid
//is just a pink sphere and not actually a bunny

//TODO : corner-node based pathfinding
//make the bunny always move at a constant speed, maybe better recovery from kill failures

package drk.maze;
import drk.KarnaughLog;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import java.util.*;
import drk.game.KarnaughGame;
import drk.graphics.game.HorrorWallMaze;
import drk.Vector3D;
import drk.Updatable;
import drk.circuit.Entrance;

public class Bunny implements drk.graphics.GLRenderable, Updatable {

//public static final long moveTimeStraight = 2000; //in milliseconds, the time it takes to move across the room straight

int currentNode = 0;
int targetNode = 0;

//the room the bunny is moving to in its current state
//Room movingTo = null;

//the room the bunny is actually in
//Room room = null;

//self explanatory
Vector3D position = null;

//self explanatory
Vector3D direction = null;

//the time the bunny started its current state
//long moveStart = 0;

//reference to the game the bunny is part of
KarnaughGame k = null;

//collision radius
public static final double distanceRadius = 0.5;

//reference to the maze the bunny is in
KarnaughMaze rm = null;

//system time at which the bunny was last updated
long lastUpdate = 0;

public int bunnyState = MOVINGSTATE;

public static final int KILLSTATE = 0;

public static final int MOVINGSTATE = 2;

public static final double BUNNYSPEED = .001;

public long moveUntilTime = 0;
public long moveStartedTime = 0;


//spawn in maze
//TODO -- make the bunneh spawn in the room furthest away from the player
//for now it just spawns at the entrance
public Bunny(KarnaughGame kg){
	
	this.k = kg;
	this.rm = k.getMaze();
	
	Entrance e = null;
	for(MazeItem i: k.getMaze().components){
		if(i instanceof Entrance){e = (Entrance)i;break;}
	}
	
	Room room = e.getRoom();
	currentNode = MazeNode.ULEFT + room.getID()*9;
	targetNode = currentNode;
	
	position = rm.nodeGraph[currentNode].position;
	
	
	//set target node
	//targetNode = rm.PathTable[currentNode][closestNodePlayer()];
	
	if(targetNode <0){
		System.out.println("Target = -1");
		System.exit(0);
		}
		if(currentNode <0){
		System.out.println("current = -1");
		System.exit(0);
		}
	direction = new Vector3D(0,0,0);
	
    //direction = (rm.nodeGraph[targetNode].position).minus(rm.nodeGraph[currentNode].position);
    //direction = direction.normal();//.times(BUNNYSPEED);
	
	//set arrival time							 //distance / rate
	moveUntilTime = System.currentTimeMillis();// + (long)(((rm.nodeGraph[targetNode].position).distance(rm.nodeGraph[currentNode].position)) / BUNNYSPEED);
	moveStartedTime = lastUpdate = System.currentTimeMillis();
	
	System.out.println("Starting at node "+currentNode+"moving to "+targetNode);
	
	this.update();
	
/*
//	this.position=((HorrorWallMaze)rm).getRoomMiddle(e.getRoom());
	this.direction = new Vector3D(0,0,0); 
	
	
//	room = e.getRoom();
	//movingTo = room;
	lastUpdate = System.currentTimeMillis();
	
	
	for(int i = 0; i < rm.nodeGraph.length; i++){
	if(rm.nodeGraph[i].active){
	this.targetNode = rm.nodeGraph[i];}
	}
	
	room = rm.RoomList.get(targetNode.roomID);
	
	this.position=((HorrorWallMaze)rm).getRoomMiddle(room);
	
	
	moveUntilTime = System.currentTimeMillis();
	
	this.update();
	*/
}



public int closestNodePlayer(){
	
	double shortestSoFar = 10000; //really high value
	int rid = rm.getCurrentRoom().getID();
	
	int nodeID = rid;
	
	for(int i = 0; i < 9; i++){
		
		if( !rm.nodeGraph[(rid*9)+i].active)continue;
		Vector3D tLoc = rm.nodeGraph[(rid*9) + i].position;
		
		//distance from node to player
		double tDist = k.ec.Position.distance(tLoc);
		
		if( tDist < shortestSoFar){
			
			nodeID = i + (rid*9);
			shortestSoFar = tDist;
			
		}	
		
	}
	
	return nodeID;
	
}


/*
public MazeNode getClosestNode(){
	
	double closest = -1;
	double temp = 0;
	MazeNode closestRoom = null;

	
	for(int i = 0; i < rm.nodeGraph.length; i++){
		
		if(rm.nodeGraph[i].active == false)continue;
		
		
	if(closest < 0 || k.ec.Position.distance(rm.nodeGraph[i].position) < closest) {
	  	
	  closestRoom = rm.nodeGraph[i];
	  closest =  k.ec.Position.distance(rm.nodeGraph[i].position);
	  
	  }
		
		
	}
	
	
	return closestRoom;
	
}
*/








public String toString(){
	return "Hi, my name is Stanford Flapjack Karnaugh II!";
}



public void update(){
	
	
	
	System.out.println("Bunny"+position+" "+direction);
	//if the camera intersects the bunny-sphere its game over
	//if((k.ec.isCollidedWith(this.position.plus(new Vector3D(0,k.ec.Position.y,0)), distanceRadius))){
	//	k.die();
	//}
	
	//update position given current paramaters
	
	//Vector3D tdirection = direction.times((System.currentTimeMillis() - lastUpdate));
	//if(tdirection.x!= Float.NaN && 
	//	tdirection.y != Float.NaN && tdirection.z != Float.NaN){
	
	//position = position.plus(tdirection);
	//}
	
	if( System.currentTimeMillis() >= moveUntilTime){
		
		System.out.println("CHANGING PATHS");
		
		moveStartedTime = System.currentTimeMillis();
		
		currentNode = targetNode;
		
     	//	position = rm.nodeGraph[currentNode].position;
	
	
	   //set target node
		targetNode = rm.PathTable[currentNode][closestNodePlayer()];
	
	
		if(currentNode == targetNode){
			direction = new Vector3D(0,0,0);
			moveUntilTime= System.currentTimeMillis();
			
		}else{
			
	
	if(targetNode <0){
		System.out.println("Target = -1");
		System.exit(0);
		}
		if(currentNode <0){
		System.out.println("current = -1");
		System.exit(0);
		}			
			
	
    	direction = (rm.nodeGraph[targetNode].position).minus(rm.nodeGraph[currentNode].position);
    	direction = direction.normal();//.times(BUNNYSPEED);
	
	//set arrival time							 //distance / rate
		moveUntilTime = System.currentTimeMillis() + (long)(((rm.nodeGraph[targetNode].position).distance(rm.nodeGraph[currentNode].position)) / BUNNYSPEED);
		}
		
	System.out.println("Changing targets "+currentNode+"moving to "+targetNode);
		
	}
	
	double percent = (double)((double)System.currentTimeMillis() - (double)moveStartedTime) / (double)((double)moveUntilTime - (double)moveStartedTime);
	
	if(moveUntilTime!= moveStartedTime){
	position = rm.nodeGraph[currentNode].position.plus(
		
		(rm.nodeGraph[targetNode].position.minus(rm.nodeGraph[currentNode].position)).times(percent));
	}
		//direction.times(BUNNYSPEED*(System.currentTimeMillis() - moveStartedTime)));
	
	
	lastUpdate = System.currentTimeMillis();
	
	/*
	if(bunnyState == KILLSTATE){
		
			//test for killstate exit conditions and return to the nearest node if so
			//THIS WILL WORK but i'd rather not have the bunny going in circles
	if(room != rm.getCurrentRoom() || getClosestNode() != targetNode)
			{   
				bunnyState = MOVINGSTATE;
				
				//if(getClosestNodeBunny()!=null)
		     	//	targetNode = getClosestNodeBunny();
				
				
				this.direction = targetNode.position.minus(this.position);
				this.direction = this.direction.normal();
				this.direction = this.direction.times(BUNNYSPEED);
			
				moveUntilTime = System.currentTimeMillis() + (long)( (position.distance(targetNode.position)) / BUNNYSPEED );
					
			
				
			}
			
			else{
		//run right at the player
			this.direction = k.ec.Position.minus(position);
			
			direction.y = 0;
		
			this.direction = this.direction.normal();
			
			this.direction = this.direction.times(BUNNYSPEED);
			}
	}
	
	
	else if(bunnyState == MOVINGSTATE){
		
		
		if(System.currentTimeMillis() >= moveUntilTime){
			
			//we've arrived at room and targetNode
			
			//stay in movestate or enter killstate
			
			
			//if we're as close as we can get using the node graph enter kill mode
			if(room == rm.getCurrentRoom() && targetNode == getClosestNode()){
				bunnyState = KILLSTATE;
				
			} 
			
			else{
			
			MazeNode origin = targetNode;
			
			targetNode = getClosestNode();
			//get the active node closest to the player
			
			
			int x = 0;
			//get the next node on the path from the node we just arrived at to the node closest to the player
			
				x = rm.PathTable[3*origin.roomID+origin.roomLocation][3*targetNode.roomID+targetNode.roomLocation]; 
			
			
			targetNode = rm.nodeGraph [x];
			
			
			this.room = rm.RoomList.get(targetNode.roomID);
			
			this.direction = targetNode.position.minus(this.position);
			this.direction = this.direction.normal();
			this.direction = this.direction.times(BUNNYSPEED);
			
			
			moveUntilTime = System.currentTimeMillis() + (long)( (position.distance(targetNode.position)) / BUNNYSPEED );
			}	
		} 
		
		
	}*/
	


}




//draw a pink sphere at the bunny's current location
public void render(GL gl){
	
	//translate to the appropriate location
	//draw a pink sphere with radius 1.5
	
	gl.glColor3f(1.0f,.75f, .75f);
	
	gl.glPushMatrix();
	
	gl.glTranslated(position.x, position.y + distanceRadius, position.z);
	
		GLU glu=new GLU();
			
		GLUquadric s= glu.gluNewQuadric();
		
		glu.gluQuadricTexture(s,false);
		
	  glu.gluSphere(s, distanceRadius*2, 10,10);
	
 	gl.glPopMatrix();
	
	
}



}
