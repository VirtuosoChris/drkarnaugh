package drk.game;

import drk.Vector3D;
import drk.graphics.Camera;
import drk.graphics.EulerCamera;
import drk.Updatable;
import java.awt.event.*;

public class MazeCamera extends EulerCamera implements MazeGameTracker,Updatable
{
	MazeGame mGame;
	double walkRate = 6.702;//m/s...average human running speed
	double turnRate = 180.0;//deg/s
	double height;
	public MazeCamera()
	{
		super();
		mGame=null;
	}
	public void setHeight(double h)
	{
		height=h;
		Position.y=height;
	}
	public MazeCamera(MazeGame mg)
	{
		super();
		mGame=mg;
		// TODO Auto-generated constructor stub
	}

	public MazeCamera(Camera c,MazeGame mg)
	{
		super(c);
		mGame=mg;
		// TODO Auto-generated constructor stub
	}
	public void setMazeGame(MazeGame mg)
	{
		mGame=mg;
	}
	//float lastheight;
	
	float bobrate;
	float bobheight;
	float dbobratedt;
	
	
	
	public void update()
	{
		//super.update();
		Vector3D Zdir=mGame.ec.ZNormal.times(new Vector3D(1.0,0.0,1.0)).enormal();
		Vector3D Xdir=mGame.ec.XNormal.times(new Vector3D(1.0,0.0,1.0)).enormal();
		double ddt=mGame.getFrameDt();
		
		//Position.y=height;
		//System.err.println("Output Keypress");
		
		
		double tX = mGame.getYMousePercentFovy();
		double tY = mGame.getXMousePercentFovy();
		double t3 = 0;
		
		if((t3 = xrotation - tX) < -90.0){
			tX += (t3+90);
		}
		else if((t3 = xrotation -tX) > 90.0){
			tX += (t3 - 90);
		}
		
		
		xrotation-= tX;
		yrotation-= tY;
			
		//thats all i did
		//if(xrotation <= -90.0)xrotation = -90.0;
		//if(xrotation >= 90.0)xrotation = 90.0;
		
		//System.out.println(xrotation);
		final double sideStepPercent=.3f;
		if(mGame.isKeyPressed(KeyEvent.VK_UP)){	
			Position.eplus((Zdir.times(walkRate*ddt)));
		}
		
		if(mGame.isKeyPressed(KeyEvent.VK_DOWN)){
			Position.eplus((Zdir.times(-walkRate*ddt)));
		}
		
		if(mGame.isKeyPressed(KeyEvent.VK_LEFT)){
			Position.eplus((Xdir.times(-sideStepPercent*walkRate*ddt)));
		}
		
		if(mGame.isKeyPressed(KeyEvent.VK_RIGHT)){
			Position.eplus((Xdir.times(sideStepPercent*walkRate*ddt)));
		}
		
	}

}
