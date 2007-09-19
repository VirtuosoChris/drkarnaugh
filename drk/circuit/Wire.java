package drk.circuit;
public class Wire extends OutputSystem
{
	OutputSystem input;
	public Wire()
	{
		input=null;
	}
	public Wire(OutputSystem in)
	{
		input=in;
	}
	
	public boolean evaluate()
	{
		return input.evaluate();
	}
	public int getNumInputs()
	{
		return 1;
	}
	public OutputSystem getInput(int i)
	{
		return (i==0 ? input : input);	//null input?
	}
	public OutputSystem setInput(OutputSystem os, int i)
	{
		if(i==0)
		{	
			OutputSystem pi=input;
			input=os;
			return pi;
		}
		else return input;
	}
}	
