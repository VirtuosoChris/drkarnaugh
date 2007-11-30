package drk.circuit;
import drk.game.KarnaughGame;


//Steve: implements a generic binary gate in terms of a circuit system
public abstract class BinaryGate extends OutputSystem
{
	//input A, input B
	OutputSystem Ain,Bin;
	
	public BinaryGate()
	{
		Ain=null;
		Bin=null;
		//Ain=new LogicInput(0);
	}
	
	public BinaryGate(OutputSystem ac,OutputSystem bc)
	{
		Ain=ac;
		Bin=bc;
	}
	
	//evaluates this gate to see if its true
	public abstract boolean evaluate();
	
	//for a binary input, its always true
	public int getNumInputs()
	{
		return 2;
	}
	
	//gets the current input based on an index
	public OutputSystem getInput(int i)
	{
		switch(i)
		{
			case 0: return Ain;
			case 1: return Bin;
			default: return null;
		}
	}
	
	//Set the input at the index, to the circuit passed into the function
	public OutputSystem setInput(OutputSystem os, int i)
	{
		OutputSystem pi=null;
		switch(i)
		{	
			case 0: pi=Ain;Ain=os; break;
			case 1: pi=Bin;Bin=os; break;
		};
		return pi;
	}
	
	
	//will adding 
	public boolean noLoop(OutputSystem x){
		
		if(this == x)return false;
		
		boolean ax = true;
		if(Ain != null){
			ax = Ain.noLoop(x);
		}
		
		boolean ay = true;
		
		if(Bin != null){
			ay = Bin.noLoop(x);
		}
		
		
		return (ax && ay);
		
	}
	
	
	
	
}
