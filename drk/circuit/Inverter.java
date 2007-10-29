package drk.circuit;
import javax.media.opengl.*;

public class Inverter extends Wire
{
	
	public Inverter(){
		
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
