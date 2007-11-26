package drk;

//Steve:
//DeltaTimer implements a Timer that measures the change in time between calls to Update
public class DeltaTimer implements Updatable
{
	//the last measured value
	long last;
	//the delta nanoseconds
	long dt;
	//the time difference in seconds
	public double ddt;
	
	//global timer
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
	
	//update timer with new information from last update
	public void update()
	{
		long tmp=last;
		last=System.nanoTime();
		dt=last-tmp;
		ddt=(double)dt;
		ddt*=1e-9;
	}
	
	//
	public double getDeltaTimeSeconds()
	{
		return ddt;
	}
	public double getSecondsSinceLastUpdate()
	{
		long tmp=System.nanoTime()-last;
		
		double td=(double)tmp;
		return td*1e-9;
	}
	
	//calculates amount of time per frame from number of frames and total time passed
	public static double getMicrosecondsPerFrame(double timePassed,double framesPassed)
	{
		return timePassed*1e6/framesPassed;
	}
	
	//calculatees current frames per second based on frames and total time
	public static double getFramesPerSecond(double timePassed,double framesPassed)
	{
		return framesPassed/timePassed;
	}
}
