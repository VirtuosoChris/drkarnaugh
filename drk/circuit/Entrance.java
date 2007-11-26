//class representing a spawnpoint for the player in the maze-- where they first entered the level
//basically, this object does nothing except act as a flag for camera initialization and look pretty

package drk.circuit;
import drk.maze.MazeItem;
import drk.game.KarnaughGame;

public class Entrance extends MazeItem {
	
	public Entrance(){}
	
	//when the item turns out to be highlighted
	public void onMazeItemHighlighted(KarnaughGame k){
		k.updateInfo("This is the entrance");
	
	}
	
	public String toString(){
		return "Entrance";
	}
	
}