//bunny draft one
//just goes to the room the player is in
//probably very easy to avoid
//is just a pink sphere and not actually a bunny

//dosomething special if path size is 1

//todo: make sure that when you step on it at normal camera height it works
//finish chase
//add handling for when the player and the bunny are in the same room so it doesnt either go through the wall or some other ridiculous behavior

package drk.maze;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import java.util.*;
import drk.game.KarnaughGame;
import drk.graphics.game.HorrorWallMaze;
import drk.Vector3D;
import drk.Updatable;
import drk.circuit.Entrance;

//normalize move vector;
//the bunny should work as follows
//rate to get from one doorway to another
//stores target room
//if enough time elapsed, we're "there"
//get shortest path
//get entrance to that room
//set movement vector

public class Bunny implements drk.graphics.GLRenderable, Updatable {

public static final long moveTimeStraight = 5000; //in milliseconds, the time it takes to move across the room straight

//once we have path finding in, we need different move times for moving diagonally and moving straight across the room
//public static final long moveTimeDiag = (long)((double)moveTimeStraight / (double)(Math.sqrt(2)));

//also need a time duration for movign from center of room -- start positions etc

//setRoom
//setMaze

Room movingTo = null;
Room room = null;
Vector3D position = null;
Vector3D direction = null;
long moveStart = 0;
KarnaughGame k = null;
public static final double distanceRadius = 0.5;
KarnaughMaze rm = null;
long lastUpdate = 0;
double distance = 0;



//position = rate*time
//


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
	
	long timeSinceUpdate = System.currentTimeMillis() - moveStart; //the time since the PATHFINDING was updated
	
	//update position given current paramaters
	position = 
		
		position.plus(
			(direction.times((distance / (double)moveTimeStraight))).times((double)(System.currentTimeMillis() - lastUpdate))
				
			);
			
			
	
	if(System.currentTimeMillis() - moveStart > moveTimeStraight){ 
		//if we've arrived at our destination
		
		
		
		room = movingTo;
		
		System.out.print("BunnyPath starts at ");
		System.out.println(room);
		
		
		Room targetRoom = this.rm.getCurrentRoom(); // target room is where the player is
			
		System.out.println("To get to ");
		System.out.println(targetRoom);
		
		LinkedList<Room> path  = new  LinkedList<Room>();
		path = rm.shortestPath(room, targetRoom, path); // returns a linked list containing the shortest path to the player
		
		
			
		if(path == null || path.size()==1 || path.size() == 0){
		
			if(path == null)System.out.println("Shortest path returned null");
			
			//the bunny and the player are in the same room
			
			this.distance = 0;
			return;
			
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
	
	
	//if the camera intersects the bunny-sphere
	if((k.ec.isCollidedWith(this.position.plus(new Vector3D(0,k.ec.Position.y,0)), distanceRadius))){
		k.die();
	}

	lastUpdate = System.currentTimeMillis();
	

}



public void render(GL gl){
	
	//translate to the appropriate location
	//draw a pink sphere with radius 1.5
	
	gl.glColor3f(1.0f,.75f, .75f);
	
	gl.glPushMatrix();
	
	gl.glTranslated(position.x, position.y, position.z);
	
		GLU glu=new GLU();
			
		GLUquadric s= glu.gluNewQuadric();
		
		glu.gluQuadricTexture(s,false);
		
	  glu.gluSphere(s, distanceRadius, 10,10);
	
 	gl.glPopMatrix();
	
	
}



}
