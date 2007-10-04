package drk.graphics.particle;

import drk.Vector3D;

public class VelocityMotion3D extends NewtonMotion3D
{
	public Vector3D Velocity;
	public VelocityMotion3D()
	{
		super();
		Velocity=new Vector3D();
		// TODO Auto-generated constructor stub
	}

	public VelocityMotion3D(Vector3D tPosition)
	{
		super(tPosition);
		Velocity=new Vector3D();
		// TODO Auto-generated constructor stub
	}
	
	public VelocityMotion3D(Vector3D tPosition,Vector3D tVelocity)
	{
		super(tPosition);
		Velocity=new Vector3D(tVelocity);
	}
	public void update()
	{
		super.update();
		Position.eplus
		(
			Velocity.times(dt.ddt)
		);
	}

}
