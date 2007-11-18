package drk.circuit;
public class BinaryAnd extends BinaryGate
{
	
	public BinaryAnd(){
		
	}
	
	public BinaryAnd(OutputSystem ac,OutputSystem bc)
	{
		super(ac,bc);
	}
	public boolean evaluate()
	{
		return Ain.evaluate() && Bin.evaluate();
	}
	
	
}
