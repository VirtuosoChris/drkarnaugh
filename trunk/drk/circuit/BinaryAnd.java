package drk.circuit;
public class BinaryAnd extends BinaryGate
{
	
	public BinaryAnd(){
		super();
		
	   	type = "And Gate";
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
