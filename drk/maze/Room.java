package drk.maze;

import java.util.*;

public class Room{
	
	protected boolean Up, Down, Left, Right;
	protected int RoomID; //Holds the ID which will initially be in order.
	protected boolean Visited, Path;
	
	protected MazeItem localItem = null;

	//Constructors
	public Room(){
		Up = false; Down = false; Left = false; Right = false;
	}
	
	public Room(int ID){
		Up = false; Down = false; Left = false; Right = false;
		RoomID = ID;
		Visited = false;
		Path = false;
	}
	

	//methods to set and return the item stored in this particular room
	public void setItem(MazeItem i){
		localItem = i;
	}
	
	public MazeItem getItem(){
	     return localItem;
	}	
	
	
	
	//Used to open up the boolean walls in the maze.
	public void MoveUp(){
		Up = true;
	}
	public void MoveDown(){
		Down = true;
	}
	public void MoveLeft(){
		Left = true;
	}
	public void MoveRight(){
		Right = true;
	}
	
	public boolean Up(){
		return Up;
	}
	public boolean Down(){
		return Down;
	}
	public boolean Left(){
		return Left;
	}
	public boolean Right(){
		return Right;
	}
	
	public int getID(){ //Get the ID of the room.
		return RoomID;
	}
	
	public void setVisit(){
		Visited = true;
	}
	
	public boolean Visited(){
		return Visited;
	}
	
	public void setPath(){
		Path = true;
	}
	
	public boolean Path(){
		return Path;
	}
}