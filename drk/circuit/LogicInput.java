package drk.circuit;

import drk.game.KarnaughGame;


public class LogicInput extends OutputSystem
{
	public static int inputNumber = 0;
		
		public int inputNum = 0;
		
	boolean output;
	public LogicInput(boolean value)
	{	
		inputNum = inputNumber;
		inputNumber++;
		type = "Logical Input "+inputNum;
		output=value;
	}
	
	public LogicInput(){
		super();
		inputNum = inputNumber;
		inputNumber++;
		type = "Logical Input "+inputNum;
	}
	
	
		public void onMazeItemHighlighted(KarnaughGame k){
		super.onMazeItemHighlighted(k);
		
		//if the user has a wire and they click, attach to appropriate input
		//if they click without one, this is the new source for them to drag a wire
		//adjusts cursors appropriately
		//updates tutorial hints appropriately
		
		if(!k.hasWire){
			k.updateInfo("Click to attach a wire to the "+type+" output");
		}else{
				
			k.updateInfo("Logical inputs cannot accept wire inputs");
			}
		
		
		
		if(k.leftClick || k.rightClick){
		if(!k.hasWire){
			k.hasWire = true;
			k.inputSource = this;
			//k.overlays.currentCursor = k.overlays.wireHand;
		}
	
		}
		
		
		else{
			if(!k.hasWire){
		  k.overlays.currentCursor = k.overlays.interactHand;}
		}
		
		}
	
	
	public void setValue(boolean value)
	{
		output=value;
	}
	public int getNumInputs()
	{
		return 0;
	}
	public OutputSystem getInput(int i)
	{
		return null;	//null input?
	}
	public OutputSystem setInput(OutputSystem os, int i)
	{
		return null;
	}
	
	public boolean evaluate()
	{
		return output;
	}
}
