package drk.circuit;

//Steve: Class that implements an OR with more than two inputs.  
public class MultiOr extends MultiGate
{
	public MultiOr(int a)
	{
		super(a);
	}
	public MultiOr(OutputSystem[] osa)
	{
		super(osa);
	}
	
	//evaluates them all
	public boolean evaluate()
	{
		//for each input
		for(OutputSystem cs:osarray)
		{
			//if any are true, return true
			if(cs.evaluate()) return true;
		}
		return false;
	}


}
