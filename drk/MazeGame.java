package drk;
import drk.graphics.*;
import drk.graphics.test.Test2GraphicsListener;
import drk.maze.*;
import java.awt.event.*;
import javax.media.opengl.*;

import java.awt.Robot;

//class that allows the user to generate 
public class MazeGame extends GLRenderedGraphicsListener implements KeyListener,MouseListener,Updatable, GLInitializable, GLRenderable, MouseMotionListener 
{
	
	//refers generically to whatever key is bound to these actions, not the arrow keys particularly
	public boolean upKeyPressed = false;    //forward
	public boolean downKeyPressed = false;  //backpedal
	public boolean leftKeyPressed = false;  //strafe left
	public boolean rightKeyPressed = false; //strafe right
	
	public Maze m;
	public EulerCamera ec;
	
	double timeElapsed = 1.0;
	
	public MazeGame(){	   
		super(new EulerCamera());
		ec = (EulerCamera)this.camera;
	}
	
	public void init(GLAutoDrawable a)
	{
		GL gl=a.getGL();
		frameTimer.update();
		initialize(gl);
		
	}

	int numFrames=0;
	double timePassed=0.0;
	public void display(GLAutoDrawable a)
	{
		frameTimer.update();
		
		timePassed+=frameTimer.getDeltaTimeSeconds();
		numFrames++;
		if(timePassed > 1.0)
		{
			System.err.println("AverageFramerate: " + 
					DeltaTimer.getMicrosecondsPerFrame(timePassed,(double)numFrames)
					+" uspf, "+
					DeltaTimer.getFramesPerSecond(timePassed,(double)numFrames)+ "fps");
			timePassed=0.0;
			numFrames=0;
		}
		//camera.render()
		render(a.getGL());
		update();
	
	}
	
	public void initialize(GL gl)
	{
		gl.glClearColor(1.0f,0.0f,1.0f,1.0f);
		gl.glClearDepth(1.0f);							// Depth Buffer Setup
		gl.glEnable(GL.GL_DEPTH_TEST);						// Enables Depth Testing
		gl.glDepthFunc(GL.GL_LEQUAL);							// The Type Of Depth Test To Do
		camera.fovy=50.0;
		ec.initialize(gl);	
	}
	
	public void render(GL gl)
	{
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		camera.render(gl);
		
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
	
	public void update(){
		
		//check for key presses and update camera accordingly
		
		double walkRate = 1.0;
		
		if(upKeyPressed){	
			ec.Position.eplus((ec.ZNormal.times(walkRate*frameTimer.ddt)));
		}
		
		if(downKeyPressed){
			ec.Position.eplus((ec.ZNormal.times(-walkRate*frameTimer.ddt)));
		}
		
		if(leftKeyPressed){
			ec.Position.eplus((ec.XNormal.times(-walkRate*frameTimer.ddt)));
		}
		
		if(rightKeyPressed){
			ec.Position.eplus((ec.XNormal.times(walkRate*frameTimer.ddt)));
		}
		
	}

	public void keyTyped(KeyEvent k){
		return;
	}
	
	public void mouseDragged(MouseEvent m){
		
	}
	
	public void mouseMoved(MouseEvent m){
		
		System.err.println("MouseMove event caught");
		int x=m.getX();
		int y=m.getY();
		
		double xs=(double)x/(double)width;
		double ys=(double)y/(double)height;
		
		ec.yrotation= -xs*(180.0*Math.PI);
		ec.xrotation= -ys*(180.0*Math.PI);

	}
	
	public void mouseExited(MouseEvent m){
		
	}
	
	public void mouseEntered(MouseEvent m){
		
	}
	
	public void mouseReleased(MouseEvent m){
		
	}
	
	public void mousePressed(MouseEvent m){
		
	}
	
	public void mouseClicked(MouseEvent m){
		
	}
	
	
	//handle keyboard input from the user
	public void keyPressed(KeyEvent k){
		System.err.println("KeyboardEvent event caught");
	  
	  switch(k.getKeyCode()){
	  	
	  	case KeyEvent.VK_UP:
	  	 upKeyPressed = true;
	  	  break;
	  	
	  	case KeyEvent.VK_DOWN:
	  	  downKeyPressed = true;
	  	  break;	
	  	
	  	case KeyEvent.VK_LEFT:
	  		leftKeyPressed = true;
	  	  break;
	  	
	  	case KeyEvent.VK_RIGHT:
	  	    rightKeyPressed = true;
	  	  break;	
	  	
	  	default:return;
	  	
	  }
	  
	  return;
	}//end method
	
	
	
	public void keyReleased(KeyEvent k){
	
		switch(k.getKeyCode()){
	  	
	  	case KeyEvent.VK_UP:
	  	 upKeyPressed = false;
	  	  break;
	  	
	  	case KeyEvent.VK_DOWN:
	  	  downKeyPressed = false;
	  	  break;	
	  	
	  	case KeyEvent.VK_LEFT:
	  		leftKeyPressed = false;
	  	  break;
	  	
	  	case KeyEvent.VK_RIGHT:
	  	    rightKeyPressed = false;
	  	  break;	
	  	
	  	default:return;
	  	
	  }
	 
	}//end method
	
	public static void main(String[] argv)
	{
		MazeGame tgl=new MazeGame();
		tgl.camera.fovy=30.0;
		
		tgl.doMain(640,480,null,true);
		
	}

	
	


}
