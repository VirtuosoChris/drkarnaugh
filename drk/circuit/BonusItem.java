package drk.circuit;
import drk.maze.MazeItem;
import drk.game.KarnaughGame;

public class BonusItem extends MazeItem{

public boolean pickedUp=false;


public String toString(){
	return "BonusItem";
}



public void onMazeItemHighlighted(KarnaughGame k){

if(!pickedUp){
	k.updateInfo("Pick up journals to learn about this place and increase your score");
}

}
	


}
