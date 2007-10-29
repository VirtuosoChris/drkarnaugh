package drk.circuit;
import javax.media.opengl.*;

public class BinaryNor extends BinaryGate
{
	public boolean evaluate()
	{
		return !(Ain.evaluate() || Bin.evaluate());
	}	
}
