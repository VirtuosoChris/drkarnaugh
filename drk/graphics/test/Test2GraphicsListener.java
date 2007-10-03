package drk.graphics.test;

import javax.media.opengl.*;
import drk.graphics.*;

public class Test2GraphicsListener extends GLRenderedGraphicsListener implements GLRenderable
{
	double timePassed;
	int numframes;
	EulerCamera ec;

	public Test2GraphicsListener()
	{
		super();
		timePassed=0.0;
		numframes=0;
		
	}
	public void init(GLAutoDrawable arg0)
	{
	// TODO Auto-generated method stub
		GL gl=arg0.getGL();
		frameTimer.update();
		ec=(EulerCamera)camera;
		
		gl.glClearDepth(1.0f);							// Depth Buffer Setup
		gl.glEnable(GL.GL_DEPTH_TEST);						// Enables Depth Testing
		gl.glDepthFunc(GL.GL_LEQUAL);							// The Type Of Depth Test To Do
		
		ec.initialize(gl);
		
	}

	public void display(GLAutoDrawable arg0)
	{
		frameTimer.update();
		
		timePassed+=frameTimer.getDeltaTimeSeconds();
		numframes++;
		if((numframes & 0xFFF)==0x000)
		{
			System.err.println("AverageFramerate: " + timePassed/(0.256*16.0)+"mspf, "+(16.0*256.0)/timePassed + "fps");
			timePassed=0.0;
			numframes=1;
		}
		
		GL gl=arg0.getGL();
		gl.glClearColor(1.0f,0.0f,1.0f,1.0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		ec.render(gl);
		
		ec.xrotation+=30.0*frameTimer.getDeltaTimeSeconds();
		
		gl.glColor3ub((byte)0xFF,(byte)0x00,(byte)0x00);
		gl.glBegin(GL.GL_QUADS);
		{
			gl.glVertex3f(1.0f,-1.0f,1.0f);
			gl.glVertex3f(-1.0f,-1,1.0f);
			gl.glVertex3f(-1.0f,1.0f,1.0f);
			gl.glVertex3f(1.0f,1.0f,1.0f);
			
			gl.glVertex3f(1.0f,-1.0f,-1.0f);
			gl.glVertex3f(-1.0f,-1,-1.0f);
			gl.glVertex3f(-1.0f,1.0f,-1.0f);
			gl.glVertex3f(1.0f,1.0f,-1.0f);
			
			//gl.glVertex3f()
		}
		gl.glEnd();
		
		gl.glFlush();
	// TODO Auto-generated method stub

	}
	
	
	
	public static void main(String[] argv)
	{
		Test2GraphicsListener tgl=new Test2GraphicsListener();
		tgl.camera.fovy=30.0;
		
		tgl.doMain(640,480,null);
		
	}

}
