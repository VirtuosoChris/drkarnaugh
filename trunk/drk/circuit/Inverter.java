package drk.circuit;


public class Inverter extends Wire
{
	
	public Inverter(){
		type = "Inverter";
	}
	
	public Inverter(OutputSystem os)
	{
		super(os);
	}
	public boolean evaluate()
	{
		return !(input.evaluate());
	}
}
