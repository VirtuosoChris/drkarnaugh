package drk;
import java.util.Random;

//Steve:generates random numbers in a static way...so I don't have to call constructors to Random everytime I need one
public final class RandGen
{
	public static Random rand;
	static
	{
		rand=new Random(System.nanoTime());
	}
	//generates a random double between l and u
	public static double ranged(double l,double u)
	{
		return l + rand.nextDouble()*(u-l);
	}
	//generates a random float between l and u
	public static float rangef(float l,float u)
	{
		return l + rand.nextFloat()*(u-l);
	}
	
	//public

}
