package drk.graphics;

import javax.media.opengl.GL;
import drk.Vector3D;
import drk.Updatable;

public class GLLightSource implements Updatable, GLInitializable {

	public Vector3D Position;
	public Vector3D Attenuation; //(constant, linear, quadratic);
	public Vector3D Color;
	public Vector3D Center;
	int source;
	public GLLightSource(int SOURCE)
	{
		Position=new Vector3D();
		Attenuation=new Vector3D(1.0,0.0,0.0);
		Color=new Vector3D(1.0,1.0,0.0);
		Center=new Vector3D();
		source=SOURCE;
	}
	public void update() {
		// TODO Auto-generated method stub

	}
	public void enable(GL gl)
	{
		gl.glEnable(source);
	}
	public void disable(GL gl)
	{
		gl.glDisable(source);
	}

	public void initialize(GL gl) {
		// TODO Auto-generated method stub

	}
	
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return true;
	}

	public void render(GL gl) {
		float[] lightv=new float[4];
		lightv[0]=(float)Position.x;
		lightv[1]=(float)Position.y;
		lightv[2]=(float)Position.z;
		lightv[3]=1.0f;
		gl.glLightfv(source, GL.GL_POSITION, lightv,0);
		gl.glLightf(source,GL.GL_CONSTANT_ATTENUATION,(float)Attenuation.x);
		gl.glLightf(source,GL.GL_LINEAR_ATTENUATION,(float)Attenuation.y);
		gl.glLightf(source,GL.GL_QUADRATIC_ATTENUATION,(float)Attenuation.z);
		lightv[0]=(float)Color.x;
		lightv[1]=(float)Color.y;
		lightv[2]=(float)Color.z;
		lightv[3]=1.0f;
		gl.glLightfv(source, GL.GL_DIFFUSE, lightv,0);
		// TODO Auto-generated method stub

	}

}