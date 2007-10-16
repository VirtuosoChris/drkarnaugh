package drk.maze;
import drk.graphics.*;
import javax.media.opengl.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.LinkedList;
import java.util.AbstractCollection;

public class Maze implements GLRenderable{ //I had other things I needed to get done so I didnt have time to test and fix the shortest path
					// and other stuff. I will get on it as soon as I can and update it. I just wanted to give the basic
					// working room and maze class.
	
	protected ArrayList<Room> RoomList; //Holds a list of all the rooms in the maze.
	protected int width, height;

	public static final double RENDER_HEIGHT = 1.0;
	public static final double RENDER_WIDTH = .75;
	
	
	public void render(GL gl)
	{
		double xoff=0.0,zoff=0.0,scale=1.0;
		double ws=0.02;
		
		float alpha=1.0f;
		
		//Room r=RoomList.get(i);
		Room r;
		gl.glPushMatrix();
		gl.glScaled(scale,scale,scale);
		//fix this for 0-1 with rescaling and translation like chris suggested
		gl.glBegin(GL.GL_QUADS);
		{
			for(int i=0;i<RoomList.size();i++)
			{
				r=RoomList.get(i);
				xoff=(double)(i % width)*RENDER_WIDTH;
				zoff=(double)(i / width)*RENDER_WIDTH;
				
				switch(i & 0x3)
				{
					case 0x0:
						gl.glColor4f(0.5f,0.5f,0.0f,alpha);
						break;
					case 0x1:
						gl.glColor4f(0.0f,0.0f,0.5f,alpha);
						break;
					case 0x2:
						gl.glColor4f(0.0f,0.5f,0.0f,alpha);
						break;
					case 0x3:
						gl.glColor4f(0.5f,0.0f,0.0f,alpha);
						break;
				}
				gl.glVertex3d((xoff+ws),0.0,(zoff+ws));
				gl.glVertex3d((xoff+ws),0.0,(zoff-ws+RENDER_WIDTH));
				gl.glVertex3d((xoff-ws+RENDER_WIDTH),0.0,(zoff-ws+RENDER_WIDTH));
				gl.glVertex3d((xoff-ws+RENDER_WIDTH),0.0,(zoff+ws));
				
				switch(i & 0x3)
				{
					case 0x0:
						gl.glColor4f(1.0f,1.0f,0.0f,alpha);
						break;
					case 0x1:
						gl.glColor4f(0.0f,0.0f,1.0f,alpha);
						break;
					case 0x2:
						gl.glColor4f(0.0f,1.0f,0.0f,alpha);
						break;
					case 0x3:
						gl.glColor4f(1.0f,0.0f,0.0f,alpha);
						break;
				}
			
				
				if(!r.Left())
				{
					//gl
					gl.glVertex3d((xoff+ws),0.0,(zoff+ws));
					gl.glVertex3d((xoff+ws),RENDER_HEIGHT,(zoff+ws));
					gl.glVertex3d((xoff+ws),RENDER_HEIGHT,((zoff-ws)+RENDER_WIDTH));
					gl.glVertex3d((xoff+ws),0.0,((zoff-ws)+RENDER_WIDTH));
				}
				if(!r.Right())
				{
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),0.0,(zoff+ws));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),RENDER_HEIGHT,(zoff+ws));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),RENDER_HEIGHT,((zoff-ws)+RENDER_WIDTH));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),0.0,((zoff-ws)+RENDER_WIDTH));
				}
				if(!r.Down())
				{
					gl.glVertex3d((xoff+ws),0.0,((zoff-ws)+RENDER_WIDTH));
					gl.glVertex3d((xoff+ws),RENDER_HEIGHT,((zoff-ws)+RENDER_WIDTH));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),RENDER_HEIGHT,((zoff-ws)+RENDER_WIDTH));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),0.0,((zoff-ws)+RENDER_WIDTH));
				
				}
				if(!r.Up())
				{
					gl.glVertex3d((xoff+ws),0.0,(zoff+ws));
					gl.glVertex3d((xoff+ws),RENDER_HEIGHT,(zoff+ws));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),RENDER_HEIGHT,(zoff+ws));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),0.0,(zoff+ws));
				}
			}	
		}
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	
	public String toString(){
	  String str = "\n\nMaze Contains Rooms:\n\n";
	  for(Room r:RoomList){
	  	str+=r;
	  }
	  
	  str+= '\n';
	  
	  for(int i = 0; i < this.height; i++){
	  	
	  	for(int k = 0; k < this.width; k++){
	  		
	  		str+=(i*width+k);
	  		Room r = RoomList.get((i*width+k));
	  		
	  		str+= r.Up ? "u":" ";
	  		str+= r.Down ? "d":" ";
	  		str+= r.Left ? "l":" ";
	  		str+= r.Right ? "r":" ";
	  			
	  		str+="\t";
	  		
	  		
	  	}
	  	
	  	str+='\n';
	  	
	  }
	  
	  return str;
	  
	}
	
	
	//Constructor
	public Maze(int x, int y){
		
		RoomList = new ArrayList<Room>();
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
		
		while(NumVisit < (width*height)){
		
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
	
	//Will find the shortest path using the depth first search algorithm.
	/*I have tested this and it seems to work pretty well. If you guys want a different implementation just let me know and
	 *I will take care of it. This takes in rooms and outputs rooms, but then I have a print function to print out
	 *the rooms in number order including the source and destination room. I did this in case you needed to actually keep
	 *track of the rooms. Also I needed the Path feature that I used in the Room Class before. Its just a basic Path that is set to
	 *false. Then I need a Path() function to return path, and a setPath() function to set the path to true. I am trying to upload
	 *the room class but its telling me there is a conflict.
	public LinkedList shortestPath(Room Source, Room Dest, LinkedList MazePath){
		
		//System.out.println("This is Room #: "+Source.getID());
		if(MazePath.size() < 1){
			if(Source.Path() == false){ //Adds in the first case and recursively wll not be called again.
				Source.setPath();
				MazePath.add(Source);
			}
			else
				return null; //If it gets to the end, and removes all the elements and all Paths are set to true, 
				//then no Path was found.
		}
			
		if(Source.getID() == Dest.getID())
			return MazePath; //Return the LinkedList holding the Rooms that connect the Source and Destination.
		
		if((Source.Up() == true) && (RoomList.get(Source.getID()-width).Path() == false)){ //Case for moving up.
			RoomList.get(Source.getID()-width).setPath();
			MazePath.add(RoomList.get(Source.getID()-width));
			return shortestPath(RoomList.get(Source.getID()-width), Dest, MazePath);
		}
		
		if((Source.Right() == true) && (RoomList.get(Source.getID()+1).Path() == false)){ //Case for moving right.
			RoomList.get(Source.getID()+1).setPath();
			MazePath.add(RoomList.get(Source.getID()+1));
			return shortestPath(RoomList.get(Source.getID()+1), Dest, MazePath);
		}
		
		if((Source.Down() == true) && (RoomList.get(Source.getID()+width).Path() == false)){ //Case for moving down.
			RoomList.get(Source.getID()+width).setPath();
			MazePath.add(RoomList.get(Source.getID()+width));
			return shortestPath(RoomList.get(Source.getID()+width), Dest, MazePath);
		}
		
		if((Source.Left() == true) && (RoomList.get(Source.getID()-1).Path() == false)){ //Case for moving left.
			RoomList.get(Source.getID()-1).setPath();
			MazePath.add(RoomList.get(Source.getID()-1));
			return shortestPath(RoomList.get(Source.getID()-1), Dest, MazePath);	
		}
		
		if(((Source.Up() == false) || (RoomList.get(Source.getID()-width).Path() == true)) &&
		  ((Source.Right() == false) || (RoomList.get(Source.getID()+1).Path() == true)) &&
		  ((Source.Down() == false) || (RoomList.get(Source.getID()+width).Path() == true)) &&
		  ((Source.Left() == false) || (RoomList.get(Source.getID()-1).Path() == true))){ //This is the case of moving backwards.
		  	MazePath.removeLast(); //Remove the last Room.
		  	return shortestPath((Room)MazePath.getLast(), Dest, MazePath); //Call the method with the last room.
		}
		
		return null; //Default return null.	
	}
	
	
	public void printPath(LinkedList MazePath){
		
		Room TempRoom = new Room();	
		while(MazePath.size() > 0){
			TempRoom = (Room)MazePath.removeFirst();
			System.out.println("Room: "+TempRoom.getID());
		}
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
		
		Maze m = new Maze(4,4);
		System.out.println(m.toString());
		
		
		/*
		//Test to show the walls should be right.
		for(int i = 0; i < 16; i++){
			System.out.print("Number: "+m.getRoom(i).getID()+" ");
			if(m.getRoom(i).Up() == true)
				System.out.print("Up = true  ");
			if(m.getRoom(i).Up() == false)
				System.out.print("Up = false  ");
			if(m.getRoom(i).Right() == true)
				System.out.print("Right = true  ");
			if(m.getRoom(i).Right() == false)
				System.out.print("Right = false  ");
			if(m.getRoom(i).Down() == true)
				System.out.print("Down = true  ");
			if(m.getRoom(i).Down() == false)
				System.out.print("Down = false  ");
			if(m.getRoom(i).Left() == true)
				System.out.print("Left = true  ");
			if(m.getRoom(i).Left() == false)
				System.out.print("Left = false  ");
			System.out.println("");
		} */
		
				
		/*Test Case for shortestPath method.
		LinkedList NewPath = new LinkedList();
		NewPath = m.shortestPath(m.getRoom(4),m.getRoom(9),NewPath);
		m.printPath(NewPath);*/
		
	}
}