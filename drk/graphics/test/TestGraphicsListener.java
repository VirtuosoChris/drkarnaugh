package drk.graphics.test;

import javax.media.opengl.*;

import drk.graphics.GLRenderedGraphicsListener;
import drk.graphics.EulerCamera;


public class TestGraphicsListener extends GLRenderedGraphicsListener
{
	double timePassed;
	int numframes;
	EulerCamera ec;
	public TestGraphicsListener()
	{
		super();
		timePassed=0.0;
		numframes=0;
	}
	public void init(GLAutoDrawable arg0)
	{
	// TODO Auto-generated method stub
		frameTimer.update();
		ec=(EulerCamera)camera;
	}

	public void display(GLAutoDrawable arg0)
	{
		GL gl=arg0.getGL();
		gl.glClearColor(1.0f,0.0f,1.0f,1.0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		
		frameTimer.update();
		
		timePassed+=frameTimer.getDeltaTimeSeconds();
		numframes++;
		if((numframes & 0xFFF)==0x000)
		{
			System.err.println("AverageFramerate: " + timePassed/(0.256*16.0)+"mspf, "+(16.0*256.0)/timePassed + "fps");
			timePassed=0.0;
			numframes=1;
		}
		
		ec.render(gl);
		
		//ec.xrotation+=30.0*frameTimer.getDeltaTimeSeconds();
		
		
		gl.glBegin(GL.GL_QUADS);
		{
			gl.glVertex3f(1.0f,-1.0f,10.0f);
			gl.glVertex3f(-1.0f,-1,10.0f);
			gl.glVertex3f(-1.0f,1.0f,10.0f);
			gl.glVertex3f(1.0f,1.0f,10.0f);
		}
		gl.glEnd();
	// TODO Auto-generated method stub

	}
	
	public static void main(String[] argv)
	{
		TestGraphicsListener tgl=new TestGraphicsListener();
		tgl.camera.fovy=30.0;
		
		tgl.doMain(640,480,null);
		
	}

}
