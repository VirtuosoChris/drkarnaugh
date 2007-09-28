package drk.graphics;
import javax.swing.*;
import javax.media.opengl.*;
import java.awt.*;
import com.sun.opengl.util.*;

public abstract class RenderedGraphicsListener implements GLEventListener
{
	public void reshape(GLAutoDrawable drawable,int x,int y,int width,int height)	//is xyheight order correct?
	{
		
	}
	
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged)
	{
		
	}
	public void doMain(String [] args)
	{
		JFrame jf=new JFrame("Dr. Karnaugh's Lab");
		GLCanvas ad = new GLCanvas();
		
		try
		{
			ad.addGLEventListener(this);
		}
		catch(Exception e)
		{
			System.err.println(e);
			//#ERROR
		}
		
		Animator anim=new Animator(ad);
		jf.getContentPane().add(ad);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		while(!jf.isShowing());
		anim.start();
		//jf
	}
	
}
