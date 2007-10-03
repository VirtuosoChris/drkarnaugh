package drk.graphics.particle;

import drk.Updatable;
import drk.Vector3D;
import drk.DeltaTimer;

public class NewtonMotion3D implements Updatable
{
	public Vector3D Position;
	public NewtonMotion3D()
	{
		Position=new Vector3D();
	}
	public NewtonMotion3D(Vector3D tPosition)
	{
		Position=new Vector3D(tPosition);
	}
	public void update(DeltaTimer dt)
	{

	}

}
