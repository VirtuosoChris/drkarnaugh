package drk.circuit;
public class BinaryNor extends BinaryGate
{
	public boolean evaluate()
	{
		return !(Ain.evaluate() || Bin.evaluate());
	}	
}
