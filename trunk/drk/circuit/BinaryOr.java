package drk.circuit;


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
