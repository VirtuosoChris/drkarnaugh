package drk.graphics;
import javax.media.opengl.GL;
import drk.Vector3D;
import javax.media.opengl.glu.GLU;

public abstract class Camera implements GLRenderable,GLInitializable
{
	public Vector3D Position;
	public Vector3D XNormal,YNormal,ZNormal;
	
	public double aspect_ratio,fovy,zNear,zFar;
	
	public void initialize(GL gl)
	{
		gl.glMatrixMode(GL.GL_PROJECTION);
		
		gl.glLoadIdentity();
		GLU glu=new GLU();
		
		glu.gluPerspective(fovy,aspect_ratio,zNear,zFar);
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
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
		aspect_ratio=c.aspect_ratio;
		fovy=c.fovy;
		zNear=c.zNear;
		zFar=c.zFar;
	}
}
