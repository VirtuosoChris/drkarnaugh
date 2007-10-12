package drk.maze;

import java.util.ArrayList;
import java.util.Random;
import java.util.LinkedList;

public class Maze{ //I had other things I needed to get done so I didnt have time to test and fix the shortest path
					// and other stuff. I will get on it as soon as I can and update it. I just wanted to give the basic
					// working room and maze class.
	
	protected ArrayList<Room> RoomList; //Holds a list of all the rooms in the maze.
	protected int width, height;
	
	
	public String toString(){
	  String str = "\n\nMaze Contains Rooms:\n\n";
	  for(Room r:RoomList){
	  	str+=r;
	  }
	  
	  return str;
	  
	}
	
	
	//Constructor
	public Maze(int x, int y){
		
		RoomList = new ArrayList<Room>();;
		width = x;
		height = y;
		Create(); //Populates the maze with rooms.
	}

	
	public void Create(){
		
		int i;
		for(i = 0; i < width*height; i++){
			Room NewRoom = new Room(i); //The Room ID will go in order from 0 - Size of maze.
			RoomList.add(NewRoom);
		}
		Generate();	
	}
	
	public void Generate(){
		
		ArrayList<Room> RoomVisited = new ArrayList<Room>();
		Random Generator = new Random();
		int RoomNumber, NumVisit = 0;
		Room CellRoom = new Room();
		Room FronRoom = new Room();
		
		while(NumVisit < ((width*height)) - 1){
		
			if(NumVisit == 0){ //First case.
				RoomNumber = Generator.nextInt((width*height));
				CellRoom = RoomList.get(RoomNumber);
				CellRoom.setVisit();
				NumVisit++;
				RoomVisited.add(CellRoom);
			}
			
			else{
				RoomNumber = Generator.nextInt(RoomVisited.size());
				CellRoom = RoomVisited.get(RoomNumber);	
			}
		
			int[] Move = new int[4]; //Keeps track of up down left right.
			int Direction; //Used to randomly pick a direction from the move array.
			Move[0] = -1; Move[1] = 1; Move[2] = -width; Move[3] = width; //Set the directions to the move array.
			Direction = Generator.nextInt(4); //Used to pick a random direction.
		
			while(error(CellRoom, Direction) == false)
				Direction = Generator.nextInt(4);	
			
			FronRoom = RoomList.get(CellRoom.getID()+Move[Direction]); //Set the new room.
			//System.out.println("1:::"+NumVisit+"..."+CellRoom.getID() + "..." + FronRoom.getID());
			while(FronRoom.Visited() == true){
				
				while(allVisit(CellRoom, Move) == true){
					RoomVisited.remove(CellRoom);
					RoomNumber = Generator.nextInt(RoomVisited.size());
					CellRoom = RoomVisited.get(RoomNumber);
				}
				
				Direction = Generator.nextInt(4); //Used to pick a random direction.
				while(error(CellRoom, Direction) == false)
					Direction = Generator.nextInt(4);		
				FronRoom = RoomList.get(CellRoom.getID()+Move[Direction]); //Set the new room.
			}
			
			if(FronRoom.Visited() == false){
				FronRoom.setVisit();
				NumVisit++;
				RoomVisited.add(FronRoom);
			}
			genWalls(CellRoom, FronRoom, Direction); //Tears down the boolean walls from both rooms.
			//System.out.println("2:::"+NumVisit+"..."+CellRoom.getID() + "..." + FronRoom.getID());
		}
	}

	public boolean error(Room Cell, int Direct){
		if(Direct == 3){
			if(Cell.getID() >= RoomList.size()-width)
				return false;
		}
		if(Direct == 2){
			if(Cell.getID() < width)
				return false;
		}
		if(Direct == 1){	
			if(Cell.getID()%width == (width-1))
				return false;
		}
		if(Direct == 0){
			if(Cell.getID()%width == 0)
				return false;
		}
		
		return true;
	}
	
	public void genWalls(Room First, Room Second, int Direct){
		
		//These set the boolean walls to true, in opposite directions.
		if(Direct == 3){
			First.MoveDown();
			Second.MoveUp();	
		}
		if(Direct == 2){
			First.MoveUp();
			Second.MoveDown();	
		}
		if(Direct == 1){
			First.MoveRight();
			Second.MoveLeft();	
		}
		if(Direct == 0){
			First.MoveLeft();
			Second.MoveRight();	
		}
	}
	
	public boolean allVisit(Room Cell, int[] move){
		
		if(Cell.getID() >= RoomList.size()-width) //Cant move down.
			move[3] = 0;
		else
			move[3] = width;
				
		if(Cell.getID() < width) //Cant move up.
			move[2] = 0;	
		else
			move[2] = -width;
				
		if(Cell.getID()%width == (width-1)) //Cant move right.
			move[1] = 0;
		else
			move[1] = 1;
				
		if(Cell.getID()%width == 0) //Cant move left.
			move[0] = 0;
		else
			move[0] = -1;

		if((RoomList.get(Cell.getID()+move[0]).Visited() == true) && (RoomList.get(Cell.getID()+move[1]).Visited() == true) &&
		   (RoomList.get(Cell.getID()+move[2]).Visited() == true) && (RoomList.get(Cell.getID()+move[3]).Visited() == true))
			return true;
			
		return false;
	}
	/*
	
	//Will find the shortest path using the breadth first search algorithm.
	public LinkedList shortestPath(Room Source, Room Dest){ //Not yet tested.
		
		LinkedList OpenPath = new LinkedList();
		LinkedList ClosedPath = new LinkedList();
		/*heath...you don't need to arrange it like this...run dijkstra's algorithm using the rooms as nodes and their connections as weigths of 1.
		 * 	All I want is for it to be a linked list of integers that are the room ids.  This shoud actually be very similar to what we did on the 
		 * 	test but accessing the connection weight for a node isn't an adjacency matrix, its a lookup to see if it connects to the node...
		 *	
		 *	However, I didn't actually read your code, so maybe that's actually what you did.
		 *	all i did was notice you tried to change the definition of Room to have some kind of List object, and this object accessed members that 
		 *	were undefined.  Not a big deal, but it messed up the build so chris and I couldn't use it from svn, and it caused a temporary panic :)
		 *	Its ok, because I'm commenting out this function for now, but keep working on it and testing it, man.  Good Job and be careful to comment
		 *	out un-compilable code before you submit it, ok?
		 *//*
		Source.MazePath = null;
		OpenPath.add(Source);
		
		while(!OpenPath.isEmpty()){
			
			Room temp = (Room)OpenPath.removeFirst();
			if(temp == Dest) //The path is found
				return createPath(Dest);
				
			else{
				ClosedPath.add(temp);
				Iterator i = temp.MazePath.iterator(); //Iterate through all the rooms.
				while(i.hasNext()){
					Room NextRoom = (Room)i.next();
					if((!ClosedPath.contains(NextRoom)) && (!OpenPath.contains(NextRoom))){
          				NextRoom.MazePath = temp;
          				OpenPath.add(NextRoom);
        			}
				}
			}	
		}
		return null; //Couldnt find a path between the two rooms.
	}
	
	public LinkedList createPath(Room room){ //Creates the path between the Source item and the Destination item.
		LinkedList NewPath = new LinkedList();
		while(room.MazePath != null){
			NewPath.addFirst(room);
			room = room.MazePath;
		}
		return NewPath; //Return that path.
	}*/
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public Room getRoom(int ID){
		return RoomList.get(ID);
	}
	
	public static void main(String[] args){
		
		/*Maze m = new Maze(4,4);*/
			
		/*LinkedList Shortness = new LinkedList();            
		Room Start = new Room();
		Room End = new Room();
		Room Temp = new Room();
		Start = m.getRoom(5);
		End = m.getRoom(14);
		
		Shortness = m.shortestPath(Start, End); //Need to fix this method. 
		/*for(int i = 0; i < Shortness.size(); i++){
			Temp = (Room)Shortness.get(i);
			System.out.println("Room ID" + Temp.getID());
		}*/ 
	}
}