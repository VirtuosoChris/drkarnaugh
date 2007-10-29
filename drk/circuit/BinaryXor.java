package drk.circuit;
import javax.media.opengl.*;

public class BinaryXor extends BinaryGate
{
	public BinaryXor(OutputSystem ac,OutputSystem bc)
	{
		super(ac,bc);
	}
	public boolean evaluate()
	{
		return Ain.evaluate() != Bin.evaluate();
	}
}
