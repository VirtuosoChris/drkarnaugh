//class representing the exit -- where the user leaves the maze after connecting their solution
//it takes one input, has no output

package drk.circuit;
import drk.game.KarnaughGame;


public class Exit extends OutputSystem {
	public Exit(){}
	

	
	public void onMazeItemHighlighted(KarnaughGame k){
		k.updateInfo("Attach the puzzle solution to the exit's input to move to the next floor");
	//	k.overlays.currentCursor = k.overlays.interactHand;
	}

	public  boolean evaluate(){
		return false;
	}
	
	public int getNumInputs(){
		return 0;
	}
	
	public OutputSystem getInput(int i){
	return null;	
	}
	
	public OutputSystem setInput(OutputSystem os,int i){
		return null;
	}
	
	
	
	public String toString(){
		return "Exit";
	}
	
}
