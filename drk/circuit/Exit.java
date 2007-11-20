//class representing the exit -- where the user leaves the maze after connecting their solution

package drk.circuit;


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
