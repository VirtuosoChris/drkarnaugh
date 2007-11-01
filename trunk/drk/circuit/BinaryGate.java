package drk.circuit;

public abstract class BinaryGate extends OutputSystem
{
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
	
	public abstract boolean evaluate();
	public int getNumInputs()
	{
		return 2;
	}
	public OutputSystem getInput(int i)
	{
		switch(i)
		{
			case 0: return Ain;
			case 1: return Bin;
			default: return null;
		}
	}
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
}
