package drk.graphics.particle;

import drk.Vector3D;
import drk.DeltaTimer;

public class JerkMotion3D extends AccelerationMotion3D
{
	public Vector3D Jerk;
	public JerkMotion3D()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public void update(DeltaTimer dt)
	{
		super.update(dt);
		double t=dt.ddt;
		
		Position.eplus(Jerk.times(t*t*t*0.16666666666));
		Velocity.eplus(Jerk.times(t*t*0.5));
		Acceleration.eplus(Jerk.times(t));
	}
}
