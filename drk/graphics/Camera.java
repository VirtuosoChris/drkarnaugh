package drk.graphics;
import javax.media.opengl.GL;
import drk.Vector3D;



public abstract class Camera implements GLRenderable,GLInitializable
{
	public Vector3D Position;
	public Vector3D XNormal,YNormal,ZNormal;
	
	public void initialize(GL gl)
	{
		return;
	}
	
	public Camera()
	{
		Position=new Vector3D(0.0,0.0,0.0);
		XNormal =new Vector3D(1.0,0.0,0.0);
		YNormal =new Vector3D(0.0,1.0,0.0);
		ZNormal =new Vector3D(0.0,0.0,1.0);
	}
	
	public Camera(Camera c)
	{
		Position=new Vector3D(c.Position);
		XNormal =new Vector3D(c.XNormal);
		YNormal =new Vector3D(c.YNormal);
		ZNormal =new Vector3D(c.ZNormal);
	}
}
