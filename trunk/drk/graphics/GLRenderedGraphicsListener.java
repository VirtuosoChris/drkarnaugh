package drk.graphics;
import drk.KarnaughLog;
import drk.graphics.Camera;
import drk.DeltaTimer;
import javax.swing.*;
import javax.media.opengl.*;
import java.awt.*;
import com.sun.opengl.util.*;
import java.awt.event.*;
import java.util.Map;
import java.util.TreeMap;
import java.awt.Robot;


public abstract class GLRenderedGraphicsListener implements GLEventListener, KeyListener,MouseMotionListener
{
	protected int width,height;
	public Camera camera;
	protected DeltaTimer frameTimer;
	protected Map<Integer,Boolean> keyPressedMap;
	
	protected double xpercentfovy,ypercentfovy;
	
	protected Robot r;
	protected boolean recenteringMouse = false;

	int xPrev, yPrev;

	public void recenterMouse(){
		r.mouseMove(width/2, height/2);
		xPrev= width/2;
		yPrev = height/2;
		recenteringMouse = true;
	}
	
	public GLRenderedGraphicsListener()
	{
		frameTimer=new DeltaTimer();
		camera=new EulerCamera();
		
		createRobot();

		keyPressedMap=new TreeMap<Integer,Boolean>();
	}
	
	public GLRenderedGraphicsListener(Camera c)
	{
		frameTimer=new DeltaTimer();
		camera=c;
		
		createRobot();
		
		keyPressedMap=new TreeMap<Integer,Boolean>();
	}
	
	
	public void createRobot(){
		
		xPrev = width/2;
		yPrev = height/2;
		
		try{r = new Robot();}
		catch(Exception e){
			KarnaughLog.log("Could not create the Robot for mouselook.  Please contact tech support");
			System.exit(0);
		}	
			
		recenterMouse();
	}
	
	
	public double getXMousePercentFovy()
	{
		return xpercentfovy;
	}
	public double getYMousePercentFovy()
	{
		return ypercentfovy;
	}
	
	public void mouseMoved(MouseEvent m){
		
		/*if(isKeyPressed(KeyEvent.VK_CONTROL))
		{
			//if()
			return;
		}*/
		
		if(recenteringMouse){recenteringMouse = false;
		
		xpercentfovy = 0;
		ypercentfovy = 0;
		return;}
		
		//System.err.println("MouseMove event caught");
		int x=m.getX();
		int y=m.getY();
		
//		angle is in percentage of y field of view, so we just have to divide by height...thats w
		//why the angle per second didn't work right....
		
		if((int)x != (int)xPrev){
			xpercentfovy = ((double)(x - xPrev))/((double)(height))* 12000* frameTimer.getSecondsSinceLastUpdate();
		}else{xpercentfovy = 0;}
		
		if((int)y != (int)yPrev){
			ypercentfovy = ((double)(y - yPrev))/((double)(height))* 12000 * frameTimer.getSecondsSinceLastUpdate();
		}else{ypercentfovy = 0;}
		
		
		
		
		
		
		
		
		//////////////////
		
	
		xPrev = x;
		yPrev = y;
		
		//xpercentfovy=(double)(x+(width>>1))/(double)height;
		//ypercentfovy=(double)(y+(height>>1))/(double)height;
		
		recenterMouse();
	}
	
	
	public double getFrameDt()
	{
		return frameTimer.ddt;
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
	
	public void keyTyped(KeyEvent k){
		return;
	}
	
	public void mouseDragged(MouseEvent m){
		recenterMouse();
		
	}

	public void mouseExited(MouseEvent m){

		recenterMouse();

	}
	
	public void mouseEntered(MouseEvent m){
	recenterMouse();	
	}
	
	public void mouseReleased(MouseEvent m){
		recenterMouse();
	}
	
	public void mousePressed(MouseEvent m){
		recenterMouse();
	}
	
	public void mouseClicked(MouseEvent m){
		recenterMouse();
	}
	public boolean isKeyPressed(int k)
	{
		Boolean result;
		result=keyPressedMap.get(k);
		return (result==null) ? false : result;
	}
	
//	handle keyboard input from the user
	public void keyPressed(KeyEvent k){
		//System.err.println("KeyboardEvent event caught");
	  
	  keyPressedMap.put(k.getKeyCode(),true);
	  
	  return;
	}//end method
	
	
	
	public void keyReleased(KeyEvent k){
	
		keyPressedMap.put(k.getKeyCode(),false);
	 
	}//end method
	
	
	public void doMain(int w,int h,GLCapabilities glcaps,boolean runasFast)
	{
		JFrame jf=new JFrame("Dr. Karnaugh's Lab");
		GLCanvas ad = new GLCanvas(glcaps);
		//ad.addKeyListener(this);
		
		
		GraphicsEnvironment ge = null;
		GraphicsDevice gd = null;
		boolean fullscreen = false;
		//attempt to get the graphics device in order to set the application to a fullscreen window
		
		try{
		  ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		  
		  if(ge == null){
		  	throw new Exception("graphics environment returned null");
		  }
		  
		  gd = ge.getDefaultScreenDevice();
		  
		  if(gd == null){
		  	throw new Exception("graphics device returned null");
		  }
		   
		  KarnaughLog.log("Fullscreen available = " + (fullscreen = gd.isFullScreenSupported()));
		  
		}catch(Exception e){
			KarnaughLog.log(e);
			KarnaughLog.log("Could not get the graphics device.  Program will run in windowed mode");
			//return;
		}
		
		
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
		jf.getContentPane().setLayout(new BorderLayout());
		jf.getContentPane().add(ad,BorderLayout.CENTER);
		JPanel box=new JPanel();
		box.add(new JButton("Coming Soon to a game near you: Status Bar"));
		jf.getContentPane().add(box,BorderLayout.SOUTH);
		jf.setSize(w,h);
		
		if(fullscreen){
	    	jf.setResizable(false);
	    	jf.setUndecorated(true); //removes the title bar and borders from the window.
	  		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  		gd.setFullScreenWindow(jf);
		}
		
		
		//you know, simply letting me do jf.showCursor(false) woulda been AWESOME
		ImageIcon fake = new ImageIcon("lols this file does not exist");
		Cursor cr = Toolkit.getDefaultToolkit().createCustomCursor(fake.getImage(), new Point(16, 16), "Blank");
		jf.setCursor(cr);
		//adapted from message board post 
		//http://forum.java.sun.com/thread.jspa?threadID=359516&messageID=3935453
		//i didnt have a blank png on hand but apparently from my tests ImageIcon returns a blank image if the file isnt found.
		//just giving it bullshit data is easy.... and seems to work
		//which is interesting.  But I wanna make sure it doesnt ONLY work on mine...   
	  	
		jf.setVisible(true);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ad.requestFocus();
		while(!jf.isShowing());
		anim.start();
		//jf
	}
	
}
