package drk;
import drk.graphics.*;
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
	   ec = new EulerCamera();
	}
	
	public void init(GLAutoDrawable a){}
	
	public void display(GLAutoDrawable a){}
	
	public void initialize(GL gl){}
	
	public void render(GL gl){}
	
	public void update(){
		
		timeElapsed = frameTimer.getSecondsSinceLastUpdate();
	
		frameTimer.update();
	
		//check for key presses and update camera accordingly
		
		double walkRate = 1.0;
		
		if(upKeyPressed){	
			ec.Position.eplus((ec.ZNormal.times(walkRate*timeElapsed)));
		}
		
		if(downKeyPressed){
			ec.Position.eplus((ec.ZNormal.times(-walkRate*timeElapsed)));
		}
		
		if(leftKeyPressed){
			ec.Position.eplus((ec.XNormal.times(-walkRate*timeElapsed)));
		}
		
		if(rightKeyPressed){
			ec.Position.eplus((ec.XNormal.times(walkRate*timeElapsed)));
		}
		
	}

	public void keyTyped(KeyEvent k){
		return;
	}
	
	public void mouseDragged(MouseEvent m){
		
	}
	
	public void mouseMoved(MouseEvent m){
		
		int x=m.getX();
		int y=m.getY();
		
		double xs=(double)x/(double)width;
		double ys=(double)y/(double)height;
		
		ec.yrotation = -xs*(180.0*Math.PI);
		ec.xrotation = -ys*(180.0*Math.PI);

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
	
	
	


}
