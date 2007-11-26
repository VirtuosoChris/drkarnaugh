package drk.circuit;

//Steve:  A class that implements a NAND operation with more than one input
public class MultiNand extends MultiGate
{
	public MultiNand(int a)
	{
		super(a);
	}
	public MultiNand(OutputSystem[] osa)
	{
		super(osa);
	}
	
	public boolean evaluate()
	{
		//for each input, if any are false, return true
		for(OutputSystem cs:osarray)
		{
			if(!cs.evaluate()) return true;
		}
		//else return false
		return false;
	}


}
