package drk;

public class DeltaTimer implements Updatable
{
	long last;
	long dt;
	
	static DeltaTimer StaticTimer;
	
	static
	{
		StaticTimer=new DeltaTimer();
	}
	
	public DeltaTimer()
	{
		last=0;
		dt=0;
		update();
	}
	
	public void update()
	{
		long tmp=last;
		last=System.nanoTime();
		dt=last-tmp;
	}
	
	public double getDeltaTimeSeconds()
	{
		double td=(double)dt;
		return td*1e-9;
	}
	public double getSecondsSinceLastUpdate()
	{
		long tmp=System.nanoTime()-last;
		
		double td=(double)tmp;
		return td*1e-9;
	}
}
