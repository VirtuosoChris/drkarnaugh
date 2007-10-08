package drk.graphics.test;

import javax.media.opengl.*;
import drk.*;
import java.awt.event.*;
import drk.graphics.*;

public class Test2GraphicsListener extends GLRenderedGraphicsListener 
implements GLRenderable,Updatable,KeyListener,MouseMotionListener
{
	double timePassed;
	int numframes;
	EulerCamera ec;
	//KarnaughGame

	public Test2GraphicsListener()
	{
		super();
		timePassed=0.0;
		numframes=0;
	}
	public void init(GLAutoDrawable arg0)
	{
	// TODO Auto-generated method stub
		arg0.addKeyListener(this);
		//arg0.
		arg0.addMouseMotionListener(this);
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
		//KarnaughGame.update();
		
		//ec.xrotation=(180.0)*frameTimer.ddt;
		double vx=180.0;
		double t=frameTimer.ddt;
		ec.xrotation+=((up ? vx : 0.0)+(down ? -vx : 0.0))*t;
		ec.yrotation+=((left ? vx : 0.0)+(right ? -vx : 0.0))*t;
	}
	
	boolean left=false,up=false,right=false,down=false;
	public void keyPressed(KeyEvent e)
	{
		System.err.println("KeyPressed");
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:left=true;break;
			case KeyEvent.VK_RIGHT:right=true;break;
			case KeyEvent.VK_UP:up=true;break;
			case KeyEvent.VK_DOWN:down=true;break;			
		}
		System.err.println("KeyPressed");
	}
     
	public void 	keyReleased(KeyEvent e)
	{
		System.err.println("KeyReleased");
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:left=false;break;
			case KeyEvent.VK_RIGHT:right=false;break;
			case KeyEvent.VK_UP:up=false;break;
			case KeyEvent.VK_DOWN:down=false;break;			
		}
		System.err.println(e);
	}
       
	public void 	keyTyped(KeyEvent e)
	{
		System.err.println("KeyTyped");
	}
	public void mouseDragged(MouseEvent e)
	{
		
	}
	public void mouseMoved(MouseEvent e)
	{
		int x=e.getX();
		int y=e.getY();
		
		double xs=(double)x/(double)width;
		double ys=(double)y/(double)height;
		
		ec.yrotation = -xs*(180.0*Math.PI);
		ec.xrotation = -ys*(180.0*Math.PI);
		
		System.err.println("MOved!");
	}
	
	
	
	public void render(GL gl)
	{	
		
		gl.glColor3ub((byte)0x00,(byte)0xCC,(byte)0xFF);
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
