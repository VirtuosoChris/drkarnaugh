package drk;
/*	Vector3D.java: Defines a double precision 3-Dimensional vector
 * 	Steven Braeger
 * 	Sun, Sep  23
 */
import java.lang.Math;


public class Vector3D
{
	public double x,y,z;
	//protected static Vector3D tmpr;
	
	public Vector3D()
	{
		x=y=z=0.0;
	}
	public Vector3D(Vector3D nv)
	{
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
	
	public final Vector3D plus(Vector3D nv)
	{
		return new Vector3D(x+nv.x,y+nv.y,z+nv.z);
	}
	public final Vector3D plus(double t)
	{
		return new Vector3D(x+t,y+t,z+t);
	}
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
	
	public final Vector3D times(Vector3D nv)
	{
		return new Vector3D(x*nv.x,y*nv.y,z*nv.z);
	}
	public final Vector3D times(double t)
	{
		return new Vector3D(x*t,y*t,z*t);
	}
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
	
	public final Vector3D minus(Vector3D nv)
	{
		return new Vector3D(x-nv.x,y-nv.y,z-nv.z);
	}
	public final Vector3D minus(double t)
	{
		return new Vector3D(x-t,y-t,z-t);
	}
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
	
	public final Vector3D dividedby(Vector3D nv)
	{
		return new Vector3D(x/nv.x,y/nv.y,z/nv.z);
	}
	public final Vector3D dividedby(double t)
	{
		t=1.0/t;
		return new Vector3D(x*t,y*t,z*t);
	}
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
	
	public final double dot3(Vector3D nv)
	{
		Vector3D tv=this.times(nv);
		return tv.x+tv.y+tv.z;
	}
	
	public final Vector3D cross3(Vector3D nv)
	{
		return new Vector3D(y*nv.z - z*nv.y,
				    z*nv.x - x*nv.z,
				    x*nv.y - y*nv.x);
	}	
	
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
	
	public final double mag2()
	{
		return this.dot3(this);
	}
	public final double mag()
	{
		return Math.sqrt(this.mag2());
	}
	public final Vector3D normal()
	{
		return this.dividedby(this.mag());
	}
	public final Vector3D enormal()
	{
		return this.edividedby(this.mag());
	}
}
