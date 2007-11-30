//bunny draft one
//just goes to the room the player is in
//probably very easy to avoid
//is just a pink sphere and not actually a bunny

package drk.maze;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import java.util.*;
import drk.game.KarnaughGame;
import drk.graphics.game.HorrorWallMaze;
import drk.Vector3D;
import drk.Updatable;
import drk.circuit.Entrance;


//the bunny should work as follows
//rate to get from one doorway to another
//stores target room
//if enough time elapsed, we're "there"
//get shortest path
//get entrance to that room
//set movement vector

public class Bunny extends MazeItem implements drk.graphics.GLRenderable, MazeItemListener, Updatable {

public static final long moveTimeStraight = 5000; //in milliseconds, the time it takes to move across the room straight

//once we have path finding in, we need different move times for moving diagonally and moving straight across the room
//public static final long moveTimeDiag = (long)((double)moveTimeStraight / (double)(Math.sqrt(2)));



//setRoom
//setMaze

//room is no longer representing the room we're in, but the target room of a particular path

Vector3D position = null;
Vector3D direction = null;
long moveStart = 0;
KarnaughGame k = null;


//spawn in maze
//TODO -- make the bunneh spawn in the room furthest away from the player
//for now it just spawns at the entrance
public Bunny(KarnaughGame kg){
	distanceRadius = 1.5;
	this.k = kg;
	
	Entrance e = null;
	
	this.setMaze(k.getMaze());
	
	for(MazeItem i: k.getMaze().components){
		if(i instanceof Entrance){e = (Entrance)i;break;}
	}
	
	this.position=((HorrorWallMaze)rm).getRoomMiddle(e.getRoom());
	this.direction = new Vector3D(0,0,0); 
	
	setRoom(e.getRoom());
	
	
	this.update();
	
}



public String toString(){
	return "Hi, my name is flapjack!";
}



public void update(){
	
	long timeSinceUpdate = System.currentTimeMillis() - moveStart;
	
	
	double percentage = (double)timeSinceUpdate/(double)moveTimeStraight; //how far along this path should we be at this time?
	
	
	position = position.plus(direction.times(percentage));
	
	
	if(timeSinceUpdate > moveTimeStraight){//if we've arrived at our destination
		
		Room targetRoom = this.rm.getCurrentRoom(); // target room is where the player is
		//Room targetRoom = getMaze().
			
		LinkedList<Room> path= new LinkedList<Room>();
		
		path = rm.shortestPath(this.getRoom(), targetRoom, path); // returns a linked list containing the shortest path to the player
		
		//set room to the first element of that list
		//determine which path (NSEW) we should go through -- keeping in mind that if the room we're in has a mazeitem in the middle
		//we can't walk through it
		//get the position of that room's entrance, and set direction to that position minus current position
		
		moveStart = System.currentTimeMillis();
		
	}
	
	
	//if the camera intersects the bunny-sphere
	if((k.ec.isCollidedWith(this.position, distanceRadius))){
		k.gameOver();
	}
	

}



public void render(GL gl){
	
	//translate to the appropriate location
	//draw a pink sphere with radius 1.5
	
	
}



}
