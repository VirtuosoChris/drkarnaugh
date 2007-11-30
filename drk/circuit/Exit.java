//class representing the exit -- where the user leaves the maze after connecting their solution
//it takes one input, has no output

package drk.circuit;
import drk.game.KarnaughGame;


public class Exit extends OutputSystem {
	
	OutputSystem input = null;
	
	public Exit(){}
	
	
	
	public void onMazeItemHighlighted(KarnaughGame k){
		k.updateInfo("Attach the puzzle solution to the exit's input to move to the next floor");
	//	k.overlays.currentCursor = k.overlays.interactHand;
	
	
	if(k.leftClick||k.rightClick){
	
	if(k.hasWire){
		
		
		
		setInput((OutputSystem)k.inputSource, 0);
		
		k.hasWire = false;
		
		k.inputSource = null;
		
		
	
		
		}
		
		
		//checkTruthTable takes int. i convert it here
		//temp
		//we could allow don't cares.  taht would be cool
		int[] x = new int[k.getMaze().solution.length];
		for(int i = 0; i < x.length; i++){
			x[i] = k.getMaze().solution[i]?1:0;
		}
	
	
		boolean ready = false;
		try{
		ready = OutputSystem.checkTruthTable(x,k.getMaze().inputsA, this);
		}catch(Exception e){
			ready = false;
		}
		//for(boolean b: k.ttMatched){
		//	if(!b)ready = false;
		//	}
		
		if(ready){
		k.winMap();
		}
	
	
	}
		
		
	
	}

	public  boolean evaluate(){
		return input.evaluate();
	}
	
	public int getNumInputs(){
		return 1;
	}
	
	public OutputSystem getInput(){
		return input;
	}
	
	public OutputSystem getInput(int i){
		return input;	
	}
	
	public OutputSystem setInput(OutputSystem os,int i){
		input = os;
		return os;
	}
	
	
	
	public String toString(){
		return "Exit";
	}
	
}
