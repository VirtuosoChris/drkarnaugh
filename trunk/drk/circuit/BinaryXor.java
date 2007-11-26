package drk.circuit;


public class BinaryXor extends BinaryGate
{
	
	public BinaryXor(){
		super();
		
		type = "XOR gate";
	}
	
	public BinaryXor(OutputSystem ac,OutputSystem bc)
	{
		super(ac,bc);
	}
	public boolean evaluate()
	{
		return Ain.evaluate() != Bin.evaluate();
	}
}
