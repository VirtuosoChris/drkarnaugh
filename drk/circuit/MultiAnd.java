package drk.circuit;

//Steve:  This is a gate that implements an and with multiple inputs
public class MultiAnd extends MultiGate
{
	public MultiAnd(int a)
	{
		super(a);
	}
	public MultiAnd(OutputSystem[] osa)
	{
		super(osa);
	}
	
	public boolean evaluate()
	{
		//for all the 
		for(OutputSystem cs:osarray)
		{
			if(!cs.evaluate()) return false;
		}
		return true;
	}


}
