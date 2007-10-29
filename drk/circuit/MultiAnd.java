package drk.circuit;
import javax.media.opengl.*;

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
		for(OutputSystem cs:osarray)
		{
			if(!cs.evaluate()) return false;
		}
		return true;
	}


}
