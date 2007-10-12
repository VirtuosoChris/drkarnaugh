package drk.graphics;
import drk.graphics.Camera;
import drk.DeltaTimer;
import javax.swing.*;
import javax.media.opengl.*;
//import java.awt.*;
import com.sun.opengl.util.*;
import java.awt.event.*;

public abstract class GLRenderedGraphicsListener implements GLEventListener, KeyListener,MouseMotionListener
{
	protected int width,height;
	public Camera camera;
	protected DeltaTimer frameTimer;
	
	public GLRenderedGraphicsListener(Camera c)
	{
		frameTimer=new DeltaTimer();
		camera=c;
	}
	
	public GLRenderedGraphicsListener()
	{
		frameTimer=new DeltaTimer();
		camera=new EulerCamera();
	}
	
	public void reshape(GLAutoDrawable drawable,int x,int y,int w,int h)	//is xyheight order correct?
	{
		width=w;
		height=h;
		GL gl=drawable.getGL();
		gl.glViewport(0,0,width,height);
		camera.aspect_ratio=((double)width)/((double)height);
		camera.initialize(gl);
	}
	
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged)
	{
		
	}
	
	public void doMain(int w,int h)
	{
		
	}
	
	
	public void doMain(int w,int h,GLCapabilities glcaps,boolean runasFast)
	{
		JFrame jf=new JFrame("Dr. Karnaugh's Lab");
		GLCanvas ad = new GLCanvas(glcaps);
		//ad.addKeyListener(this);
		

		try
		{
			ad.addGLEventListener(this);
			ad.addMouseMotionListener(this);
			ad.addKeyListener(this);
			//jf.addMouseListener(this)
		}
		catch(Exception e)
		{
			System.err.println(e);
			//#ERROR
		}
		
		Animator anim=new Animator(ad);
		anim.setRunAsFastAsPossible(runasFast);
		jf.getContentPane().add(ad);
		jf.setSize(w,h);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ad.requestFocus();
		while(!jf.isShowing());
		anim.start();
		//jf
	}
	
}
