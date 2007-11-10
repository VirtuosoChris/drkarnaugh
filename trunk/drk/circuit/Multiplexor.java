package drk.circuit;

//implements a multiplexor 
public class Multiplexor extends MultiGate
{
	int width;
	public Multiplexor(int switch_width)
	{
		super((1 << switch_width) + switch_width);	//2^n + n inputs...in[0:(n-1)] are selector inputs, in[n:(2^n+n-1)] are inputs;
		width=switch_width;
	}

	public Multiplexor(OutputSystem[] osa)
	{
		for(width=0;((1<<width) + width) < osa.length; width++);	//linear search for the switch width (max of 32 steps, so its fast)
		
		osarray=osa;
	}
	
	public boolean evaluate()
	{
		int index=0;

		for(int i=0;i<width;i++) index+=osarray[i].evaluate() ? (1 << i) : 0;  //binary to decimal conversion
		
		return osarray[index+width].evaluate();
	}
}
