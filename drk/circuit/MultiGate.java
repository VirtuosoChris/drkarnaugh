package drk.circuit;

public abstract class MultiGate extends OutputSystem
{
	OutputSystem[] osarray;
	public MultiGate()
	{
		this(3);
	}
	public MultiGate(int a)
	{
		osarray=new OutputSystem[a];
	}
	public MultiGate(OutputSystem[] osa)
	{
		osarray=osa;
	}
	public abstract boolean evaluate();
	public int getNumInputs()
	{
		return osarray.length;
	}
	public OutputSystem getInput(int i)
	{
		if(i < 0 || i >= osarray.length)
		{
			return null;
		}
		else
		{
			return osarray[i];
		}
	}
	public OutputSystem setInput(OutputSystem os,int i)
	{
		if(i < 0 || i >= osarray.length)
		{
			return null;
		}
		else
		{
			OutputSystem pi=osarray[i];
			osarray[i]=os;
			return pi;
		}
	}
}
