package drk.circuit;
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
		for(OutputSystem cs:osarray)
		{
			if(!cs.evaluate()) return true;
		}
		return false;
	}


}
