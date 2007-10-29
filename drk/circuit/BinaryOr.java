package drk.circuit;
import javax.media.opengl.*;

public class BinaryOr extends BinaryGate
{
	public BinaryOr(OutputSystem ac,OutputSystem bc)
	{
		super(ac,bc);
	}
	public boolean evaluate()
	{
		return Ain.evaluate() || Bin.evaluate();
	}
}
