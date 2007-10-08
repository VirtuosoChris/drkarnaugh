package drk.graphics.particle;

import drk.Vector3D;

public class AccelerationMotion3D extends VelocityMotion3D
{
	public Vector3D Acceleration;
	public AccelerationMotion3D()
	{
		super();
		Acceleration=new Vector3D();
		// TODO Auto-generated constructor stub
	}
	public void update()
	{
		super.update();
		Vector3D at=Acceleration.times(dt.ddt);
		Position.eplus(at.times(0.5*dt.ddt));
		Velocity.eplus(at);
	}
}
