//bunny draft one
//just goes to the room the player is in and initializes a chase mode
//if the user "escapes" into another room while the bunny is chasing then the bunny moves to the center of the room
//to get back on the path grid
//probably very easy to avoid
//is just a pink sphere and not actually a bunny

//TODO : corner-node based pathfinding
//make the bunny always move at a constant speed, maybe better recovery from kill failures

package drk.maze;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import java.util.*;
import drk.game.KarnaughGame;
import drk.graphics.game.HorrorWallMaze;
import drk.Vector3D;
import drk.Updatable;
import drk.circuit.Entrance;

public class Bunny implements drk.graphics.GLRenderable, Updatable {

public static final long moveTimeStraight = 2000; //in milliseconds, the time it takes to move across the room straight


//the room the bunny is moving to in its current state
Room movingTo = null;

//the room the bunny is actually in
Room room = null;

//self explanatory
Vector3D position = null;

//self explanatory
Vector3D direction = null;

//the time the bunny started its current state
long moveStart = 0;

//reference to the game the bunny is part of
KarnaughGame k = null;

//collision radius
public static final double distanceRadius = 0.5;

//reference to the maze the bunny is in
KarnaughMaze rm = null;

//system time at which the bunny was last updated
long lastUpdate = 0;

//the distance the bunny is traveling in its current state
double distance = 0;

//flag for the bunny's state machine
//it can either be moving to the room the player is in using the node system, or moving right at the player if its in the same room
boolean kill = false;




//spawn in maze
//TODO -- make the bunneh spawn in the room furthest away from the player
//for now it just spawns at the entrance
public Bunny(KarnaughGame kg){
	
	this.k = kg;
	
	Entrance e = null;
	
	this.rm = k.getMaze();
	
	for(MazeItem i: k.getMaze().components){
		if(i instanceof Entrance){e = (Entrance)i;break;}
	}

	this.position=((HorrorWallMaze)rm).getRoomMiddle(e.getRoom());
	this.direction = new Vector3D(0,0,0); 
	
	room = e.getRoom();
	movingTo = room;
	lastUpdate = System.currentTimeMillis();
	
	this.update();
	
}



public String toString(){
	return "Hi, my name is flapjack!";
}



public void update(){
	
	//if the camera intersects the bunny-sphere its game over
	if((k.ec.isCollidedWith(this.position.plus(new Vector3D(0,k.ec.Position.y,0)), distanceRadius))){
		k.die();
	}
	
	//USE A DELTA TIMER HERE...
	//Specifically, get the delta timer from the karnaughgame
//	long timeSinceUpdate = System.currentTimeMillis() - moveStart; //the time since the PATHFINDING was updated
	
	//update position given current paramaters
	position = position.plus(
		(direction.times((distance / (double)moveTimeStraight))).times((double)(System.currentTimeMillis() - lastUpdate))
				);
	
	
	
	if(kill){ //if bunny and player are still in same room move towards him otherwise move towards center of room
		
		if(room == rm.getCurrentRoom()){
			
			this.direction = k.ec.Position.minus(position);
			
			direction.y = 0;
			
			this.distance = direction.mag();
			this.direction = this.direction.normal();
			
		}else {//enter kill recovery mode if the player "escapes" to the next room
			kill = false;	
			
			this.direction = rm.getRoomMiddle(movingTo).minus(position);
			this.distance = direction.mag();
			
			direction = direction.normal();
			
			moveStart = System.currentTimeMillis();
			//set target to center of current room
		
		}
		
	}		
			
	
	else if(System.currentTimeMillis() - moveStart > moveTimeStraight){ 
		//if we've arrived at our destination
		
		
		
		room = movingTo;
		
		System.out.print("BunnyPath starts at ");
		System.out.println(room);
		
		
		Room targetRoom = this.rm.getCurrentRoom(); // target room is where the player is
			
		System.out.println("To get to ");
		System.out.println(targetRoom);
		
		LinkedList<Room> path  = new  LinkedList<Room>();
		
		rm.clearPaths();
		
		path = rm.shortestPath(room, targetRoom, path); // returns a linked list containing the shortest path to the player
		
		
			
		if(path == null || path.size()==1 || path.size() == 0){ //if the bunny and the player are in the same room, enter kill modee
			
			//the bunny and the player are in the same room
			
			movingTo = room;
			
			this.direction = k.ec.Position.minus(position);
			direction.y = 0;
			
			this.distance = direction.mag();
			this.direction = this.direction.normal();
			
			
			kill = true;
			
			//this.distance = 0;
			//return;
			
		}else{
			
			
			
		System.out.println("Path is");
			for(Room r: path){
			System.out.print(r);
			}
			System.out.println();
		
			
			moveStart = System.currentTimeMillis();
			
			
			path.removeFirst(); // eliminate the start room from the list
			
			
			movingTo = path.removeFirst(); // the room the bunny should move to next
			
			
			this.direction = ((HorrorWallMaze)rm).getRoomMiddle(movingTo).minus(((HorrorWallMaze)rm).getRoomMiddle(room));
			this.distance = direction.mag();
			this.direction = this.direction.normal();
			
			
			
			
		}
		
		//for(Room rx: path){
		//	System.out.print(rx);
		//}
		
		//System.out.println();
		
		
		//set room to the first element of that list
		//determine which path (NSEW) we should go through -- keeping in mind that if the room we're in has a mazeitem in the middle
		//we can't walk through it
		//get the position of that room's entrance, and set direction to that position minus current position
		

		
	}

	lastUpdate = System.currentTimeMillis();
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
		
	  glu.gluSphere(s, distanceRadius, 10,10);
	
 	gl.glPopMatrix();
	
	
}



}
