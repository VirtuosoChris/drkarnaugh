//generic class referring to any object that can be found in a room in the maze


package drk.maze;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import drk.game.KarnaughGame;
import drk.graphics.game.HorrorWallMaze;


public abstract class MazeItem implements drk.graphics.GLRenderable, MazeItemListener{
	protected Room room = null;
	protected KarnaughMaze rm = null;

	public static double boundingRadius = 1.0; //radius of a sphere the user should be looking at in order for a mouse event to flag
	public static double distanceRadius = 3.0; //radius of a sphere the user should be inside in order for the mouse event to flag
	
	//are both of the sphere collision/look conditions above met?
	public boolean isMazeItemHighlighted(KarnaughGame k){
	return (
		k.ec.isPointingAt( ((HorrorWallMaze)k.m).getRoomMiddle(this.getRoom()), boundingRadius) 
	&& k.ec.isCollidedWith(((HorrorWallMaze)k.m).getRoomMiddle(this.getRoom()), distanceRadius)
		);
	}
	
	//when the item turns out to be highlighted
	public void onMazeItemHighlighted(KarnaughGame k){
	}
	
	//what to do if the camera is inside the mazeitem
	public void onMazeItemEntered(MazeItem m){}




	//this sets the fields in the room object and this to refer to each other.  
	//I feel that the objects referring to each other is cause enough to make the fields in each not public and instead go through accessors and mutators
	//not that any one of us is dumb enough to, say, put an object in a room and then set the object's room variable to null.
	//yeah figure that one out.  The room contains an object that's not in the room, and in fact is in NO room.  What is this, PORTAL?
	public void setRoom(Room r){
		room = r;
		if(r != null){
			
		  if(r.getItem() != this){	
		  r.setItem(this);
		  
		  }
		}
	}
	
	
	//sets a reference to the maze the object is in
	public void setMaze(KarnaughMaze m){
		rm = m;
	}
	
	
	//draws the mazeitem -- for testing purposes the base class draws a sphere in the center of the room
	public void render(GL gl)
	{
			
			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glPushMatrix();
		//	Vector3D centermiddle=rm.getRoomMiddle(room);
		//	gl.glTranslated(centermiddle.x,centermiddle.y,centermiddle.z);
			
			GLU glu=new GLU();
			
			GLUquadric s= glu.gluNewQuadric();
		glu.gluQuadricTexture(s, true);
		
	///	gl.glLineWidth(5);
		//this would be useful for debugging but doesn't work
			
			gl.glColor3f(0f,0f,0f);
			
			glu.gluSphere(s, boundingRadius, 10,10);
			
			gl.glPopMatrix();	
	}
	
	
	public Room getRoom(){
	  return room;
	}
	
	public abstract String toString();
	
//	public double getPosition();
	
	
	
	
	
	
}

