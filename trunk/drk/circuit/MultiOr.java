package drk.circuit;


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
	
	public boolean evaluate()
	{
		for(OutputSystem cs:osarray)
		{
			if(cs.evaluate()) return true;
		}
		return false;
	}


}
