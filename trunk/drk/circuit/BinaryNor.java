package drk.circuit;


public class BinaryNor extends BinaryGate
{
	public BinaryNor(){
	super();
	 type = "Nor Gate";
	
	}
	
	public boolean evaluate()
	{
		return !(Ain.evaluate() || Bin.evaluate());
	}	
}
