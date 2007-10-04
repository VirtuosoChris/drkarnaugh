package drk.graphics.test;

import javax.media.opengl.*;
import drk.*;
import drk.graphics.*;

public class Test2GraphicsListener extends GLRenderedGraphicsListener implements GLRenderable,Updatable
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
		
		gl.glClearColor(1.0f,0.0f,1.0f,1.0f);
		gl.glClearDepth(1.0f);							// Depth Buffer Setup
		gl.glEnable(GL.GL_DEPTH_TEST);						// Enables Depth Testing
		gl.glDepthFunc(GL.GL_LEQUAL);							// The Type Of Depth Test To Do
		camera.fovy=50.0;
		ec.initialize(gl);	
	}

	public void display(GLAutoDrawable arg0)
	{
		frameTimer.update();
		
		timePassed+=frameTimer.getDeltaTimeSeconds();
		numframes++;
		if(timePassed > 1.0)
		{
			System.err.println("AverageFramerate: " + 
					DeltaTimer.getMicrosecondsPerFrame(timePassed,(double)numframes)
					+" uspf, "+
					DeltaTimer.getFramesPerSecond(timePassed,(double)numframes)+ "fps");
			timePassed=0.0;
			numframes=0;
		}
		
		GL gl=arg0.getGL();
		//gl.glClearColor(1.0f,0.0f,1.0f,1.0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		ec.render(gl);
		render(gl);
		
		update();
		
		gl.glFlush();
		//gl.glFlush();
	// TODO Auto-generated method stub

	}
	
	public void update()
	{
		//ec.xrotation=60.0*frameTimer.ddt;
		//ec.yrotation+=10.0*frameTimer.ddt;
	}
	
	public void render(GL gl)
	{	
		
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
	}
	
	
	
	public static void main(String[] argv)
	{
		Test2GraphicsListener tgl=new Test2GraphicsListener();
		tgl.camera.fovy=30.0;
		
		tgl.doMain(640,480,null,true);
		
	}

}
