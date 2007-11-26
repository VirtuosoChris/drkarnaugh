package drk.circuit;

//This implements a wire by itself..it has only one input and mirrors that to the output
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
	
	//set the input high if selected, otherwise do nothing
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
