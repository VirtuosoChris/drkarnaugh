//class representing a spawnpoint for the player in the maze-- where they first entered the level
//basically, this object does nothing except act as a flag for camera initialization and look pretty

package drk.circuit;
import drk.maze.MazeItem;
public class Entrance extends MazeItem {
	
	public Entrance(){}
	
	public String toString(){
		return "Entrance";
	}
	
}