package drk.circuit;

//Steve:  Implements a nor gate that can have more than two inputs
public class MultiNor extends MultiGate
{
	public MultiNor(int a)
	{
		super(a);
	}
	public MultiNor(OutputSystem[] osa)
	{
		super(osa);
	}
	
	public boolean evaluate()
	{
		//for each input, if any are true, return false
		for(OutputSystem cs:osarray)
		{
			if(cs.evaluate()) return false;
		}
		//if none are true, return true
		return true;
	}


}
