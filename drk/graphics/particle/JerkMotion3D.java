package drk.graphics.particle;

import drk.Vector3D;

public class JerkMotion3D extends AccelerationMotion3D
{
	public Vector3D Jerk;
	public JerkMotion3D()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public void update()
	{
		super.update();
		double t=dt.ddt;
		
		Position.eplus(Jerk.times(t*t*t*0.16666666666));
		Velocity.eplus(Jerk.times(t*t*0.5));
		Acceleration.eplus(Jerk.times(t));
	}
}
