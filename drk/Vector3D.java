package drk;
/*	Vector3D.java: Defines a double precision 3-Dimensional vector
 * 	Steven Braeger
 * 	Sun, Sep  23
 */
import java.lang.Math;

//Steve: This is my 3D vector class.
public class Vector3D
{
	//these are the components
	public double x,y,z;
	//a temporary allocated vector...to prevent constant allocation for speed-intensive code
	public static Vector3D tmpv;
	
	static
	{
		tmpv=new Vector3D();
	}
	
	public Vector3D()
	{
		x=y=z=0.0;
	}
	public Vector3D(Vector3D nv)
	{
		//copy constructor
		x=nv.x;
		y=nv.y;
		z=nv.z;
	}
	public Vector3D(double tx,double ty,double tz)
	{
		x=tx;
		y=ty;
		z=tz;
	}
	//set equal to
	public final Vector3D equals(double t)
	{
		x=t;
		y=t;
		z=t;
		return this;
	}
	//set equal to
	public final Vector3D equals(Vector3D nv)
	{
		x=nv.x;
		y=nv.y;
		z=nv.z;
		return this;
	}
	//set equal to
	public final Vector3D equals(float nvx,float nvy,float nvz)
	{
		x=nvx;
		y=nvy;
		z=nvz;
		return this;
	}
	
	
	
	
	public double distance(Vector3D b){
		
		
		return (this.minus(b)).mag();//Math.sqrt(   (this.x - b.x) + (this.y - b.y) + (this.z - b.z)   );
		
		
	}
	
	
	
	
	//add and return new (A+B)
	public final Vector3D plus(Vector3D nv)
	{
		return new Vector3D(x+nv.x,y+nv.y,z+nv.z);
	}
	public final Vector3D plus(double t)
	{
		return new Vector3D(x+t,y+t,z+t);
	}
	//add and return old (A+=B)
	public final Vector3D eplus(Vector3D nv)
	{
		x+=nv.x;
		y+=nv.y;
		z+=nv.z;
		return this;
	}
	public final Vector3D eplus(double t)
	{
		x+=t;
		y+=t;
		z+=t;
		return this;
	}
	//multiply and return new (A*B)
	public final Vector3D times(Vector3D nv)
	{
		return new Vector3D(x*nv.x,y*nv.y,z*nv.z);
	}
	public final Vector3D times(double t)
	{
		return new Vector3D(x*t,y*t,z*t);
	}
	//multiply and return old (A*=B)
	public final Vector3D etimes(Vector3D nv)
	{
		x*=nv.x;
		y*=nv.y;
		z*=nv.z;
		return this;
	}
	public final Vector3D etimes(double t)
	{
		x*=t;
		y*=t;
		z*=t;
		return this;
	}
	//subtract and return new(A-B)
	public final Vector3D minus(Vector3D nv)
	{
		return new Vector3D(x-nv.x,y-nv.y,z-nv.z);
	}
	public final Vector3D minus(double t)
	{
		return new Vector3D(x-t,y-t,z-t);
	}
	//subtract and return old(A-=B)
	public final Vector3D eminus(Vector3D nv)
	{
		x-=nv.x;
		y-=nv.y;
		z-=nv.z;
		return this;
	}
	public final Vector3D eminus(double t)
	{
		x-=t;
		y-=t;
		z-=t;
		return this;
	}
	//divide and return new(A/B)
	public final Vector3D dividedby(Vector3D nv)
	{
		return new Vector3D(x/nv.x,y/nv.y,z/nv.z);
	}
	public final Vector3D dividedby(double t)
	{
		t=1.0/t;
		return new Vector3D(x*t,y*t,z*t);
	}
	//divide and return old(A/=B)
	public final Vector3D edividedby(Vector3D nv)
	{
		x/=nv.x;
		y/=nv.y;
		z/=nv.z;
		return this;
	}
	public final Vector3D edividedby(double t)
	{
		t=1.0/t;
		x*=t;
		y*=t;
		z*=t;
		return this;
	}
	
	//calculate 3D dot product
	public final double dot3(Vector3D nv)
	{
		Vector3D tv=this.times(nv);
		return tv.x+tv.y+tv.z;
	}
	//calculate 3D cross product and return new (AxB)
	public final Vector3D cross3(Vector3D nv)
	{
		return new Vector3D(y*nv.z - z*nv.y,
				    z*nv.x - x*nv.z,
				    x*nv.y - y*nv.x);
	}	
	
	//calculate 3D cross product and return old(A=AxB)
	public final Vector3D ecross3(Vector3D nv)
	{
		double tx,ty,tz;
		tx=y*nv.z - z*nv.y;
		ty=z*nv.x - x*nv.z;
		tz=x*nv.y - y*nv.x;
		x=tx;
		y=ty;
		z=tz;
		return this;
	}
	//calculate magnitude squared
	public final double mag2()
	{
		return this.dot3(this);
	}
	//calculate magnitude
	public final double mag()
	{
		return Math.sqrt(this.mag2());
	}
	//calculate normalized version anad return new
	public final Vector3D normal()
	{
		return this.dividedby(this.mag());
	}
	//normalize this vector
	public final Vector3D enormal()
	{
		return this.edividedby(this.mag());
	}
	
	//linearly interpolate a float
	public double lerp(double a,double b,double t)
	{
		return a*(1.0-t)+b*(t);
	}
	
	public final Vector3D lerp(Vector3D b,float t)
	{
		return new Vector3D(lerp(x,b.x,t),lerp(y,b.y,t),lerp(z,b.z,t));
	}
	public final Vector3D elerp(Vector3D b,float t)
	{
		x=lerp(x,b.x,t);
		y=lerp(y,b.y,t);
		z=lerp(z,b.z,t);
		return this;
	}
	
	
	public Vector3D midpoint(Vector3D b){
		
		return new Vector3D( (this.x + b.x) /2,(this.y + b.y) /2,(this.z + b.z) /2);
		
	}
	
	
	public String toString(){
		return "("+x+","+y+","+z+")";
	}
	
}
