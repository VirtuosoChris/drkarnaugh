package drk.graphics.particle;

import drk.Vector3D;
import drk.DeltaTimer;

public class AccelerationMotion3D extends VelocityMotion3D
{
	public Vector3D Acceleration;
	public AccelerationMotion3D()
	{
		super();
		Acceleration=new Vector3D();
		// TODO Auto-generated constructor stub
	}
	public void update(DeltaTimer dt)
	{
		super.update(dt);
		Vector3D at=Acceleration.times(0.5*dt.ddt);
		Position.eplus(at.times(dt.ddt));
		Velocity.eplus(at);
	}
}
