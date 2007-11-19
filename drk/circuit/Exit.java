package drk.circuit;

import drk.maze.MazeItem;

public class Exit extends OutputSystem {
	public Exit(){}
	

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
