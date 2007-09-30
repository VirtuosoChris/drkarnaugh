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
	}
	
	public void update()
	{
		long tmp=last;
		last=System.nanoTime();
		dt=last-tmp;
	}
	
	public double getDeltaTimeSeconds()
	{
		double td=(double)last;
		return td*1e-9;
	}
}
