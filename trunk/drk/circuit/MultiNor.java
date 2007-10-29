package drk.circuit;
import javax.media.opengl.*;

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
		for(OutputSystem cs:osarray)
		{
			if(cs.evaluate()) return false;
		}
		return true;
	}


}
