package drk.maze;



public abstract class MazeItem implements drk.graphics.GLRenderable{
	protected Room room = null;
	
	//this sets the fields in the room object and this to refer to each other.  
	//I feel that the objects referring to each other is cause enough to make the fields in each not public and instead go through accessors and mutators
	//not that any one of us is dumb enough to, say, put an object in a room and then set the object's room variable to null.
	//yeah figure that one out.  The room contains an object that's not in the room, and in fact is in NO room.  What is this, PORTAL?
	public void setRoom(Room r){
		room = r;
		if(r != null){
		  r.setItem(this);
		}
	}
	
	public Room getRoom(){
	  return room;
	}
	public abstract String toString();
	
	
}

