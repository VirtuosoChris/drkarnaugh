package drk.circuit;
import drk.maze.MazeItem;
import drk.game.KarnaughGame;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;

public class BonusItem extends MazeItem{

public boolean pickedUp=false;


public String toString(){
	return "BonusItem";
}



public void onMazeItemHighlighted(KarnaughGame k){


if(!pickedUp){
	
	if(k.leftClick || k.rightClick){
	k.Score +=25;
	pickedUp = true;
	}
	
	k.updateInfo("Pick up journals to learn about this place and increase your score");
}

}



public void render(GL gl){
	if(!pickedUp)
	{
		super.render(gl);
	}
}
	


}
