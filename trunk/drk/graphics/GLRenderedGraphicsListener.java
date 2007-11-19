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
		
		//System.err.println("MouseMove event caught");
		int x=m.getX();
		int y=m.getY();
		
//		angle is in percentage of y field of view, so we just have to divide by height...thats w
		//why the angle per second didn't work right....

		
		
//		angle is in percentage of y field of view, so we just have to divide by height...thats w
		//why the angle per second didn't work right....
		xpercentfovy -=((double)(x - xPrev))/((double)(height));
		ypercentfovy +=((double)(y - yPrev))/((double)(height));
		
		
		//////////////////
		
		
		
		
		
		
		
		
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
		mouseMoved(m);
		
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
	
//	public DisplayMode[]
	
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	
	public static boolean linuxhack=false;
	public void doMain(int w,int h,GLCapabilities glcaps,boolean runasFast)
	{
		JFrame jf=new JFrame("Dr. Karnaugh's Lab");
		GLCanvas ad = new GLCanvas(glcaps);
		//ad.addKeyListener(this);
		
		
		width = w;
		height = h;
		
		GraphicsEnvironment ge = null;
		GraphicsDevice gd = null;
		boolean fullscreen = false;
		
		DisplayMode dm=null;
		
		DisplayMode[] validmodes={new DisplayMode(640, 480, 32, 0),
        new DisplayMode(640, 480, 16, 0),
        new DisplayMode(640, 480, 8, 0)};
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
		  
		  if(fullscreen && gd.isDisplayChangeSupported())
		  {
			  dm=gd.getDisplayMode();
			  
			  DisplayMode[] modes=gd.getDisplayModes();
			  for (int x = 0; x < validmodes.length; x++) {
		          modes = gd.getDisplayModes();
				 System.err.println("getting");
		            for (int i = 0; i < modes.length; i++) {
				System.err.println("Display mode "+i);
		                if (modes[i].getWidth() == validmodes[x].getWidth()
		                   && modes[i].getHeight() == validmodes[x].getHeight()
		                   && modes[i].getBitDepth() == validmodes[x].getBitDepth()
		                   ) {
		                   dm=validmodes[x];
		                   x=validmodes.length;
		                   break;
		                }
		            }
		        }
			  	gd.setDisplayMode(dm);
		  }
		  else if(linuxhack)
		  {
			  gd.setDisplayMode(validmodes[0]);
			  fullscreen=true;
		  }
			if(fullscreen||linuxhack){
		    	jf.setResizable(false);
		    	jf.setUndecorated(true); //removes the title bar and borders from the window.
		  		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  		gd.setFullScreenWindow(jf);
		  		
		  		drk.KarnaughLog.log(""+new DisplayMode(1440,900,32,0));
		  		
		  		
		  		
		  		//gd.setDisplayMode(dm);
		  		
		  		gd.setDisplayMode(new DisplayMode(width,height,32,0));
			}
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
