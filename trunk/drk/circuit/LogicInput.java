package drk.circuit;
import javax.media.opengl.*;

public class LogicInput extends OutputSystem
{
	boolean output;
	public LogicInput(boolean value)
	{
		output=value;
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
