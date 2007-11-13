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
	double turnRate = 90;//deg/s
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
		//Vector3D FactorMatrix=new Vector3D(1.0,0.0,1.0);//boolean matrix for motion
		
	//	if(direction)
		
		Vector3D Zdir=mGame.ec.ZNormal.times(new Vector3D(1.0,0.0,1.0));
		Vector3D Xdir=mGame.ec.XNormal.times(new Vector3D(1.0,0.0,1.0));
		
		
		
		double ddt=mGame.getFrameDt();
		
		//Position.y=height;
		//System.err.println("Output Keypress");
		
		xrotation+=mGame.getYMousePercentFovy()*(-turnRate*fovy*ddt);
		yrotation+=mGame.getXMousePercentFovy()*turnRate*fovy*ddt;
		
		if(xrotation < -90.0) xrotation = -90.0;
		if(xrotation > 90.0) xrotation = 90.0;
	/*	float[]  direction_dist=mGame.m.distanceToWalls();
		final double cwidth=0.125;
		if(direction_dist[0] < cwidth)
		{
			if(Zdir.z < 0.0)
				Zdir.z = 0.0;
			if(Xdir.z < 0.0)
				Xdir.z = 0.0;
		}
		if(direction_dist[2] < cwidth)
		{
			if(Zdir.z > 0.0)
				Zdir.z = 0.0;
			if(Xdir.z > 0.0)
				Xdir.z = 0.0;
		}
		if(direction_dist[1] < cwidth)
		{
			if(Zdir.x > 0.0)
				Zdir.x = 0.0;
			if(Xdir.x > 0.0)
				Xdir.x = 0.0;
		}
		if(direction_dist[3] < cwidth)
		{
			if(Zdir.x < 0.0)
				Zdir.x = 0.0;
			if(Xdir.x < 0.0)
				Xdir.x = 0.0;
		}*/
		
		Xdir.enormal();
		Zdir.enormal();
		
		final double sideStepPercent=.3f;
		if(mGame.isKeyPressed(KeyEvent.VK_UP)){	
			Position.eplus((Zdir.times(walkRate*ddt)));
		}
		
		if(mGame.isKeyPressed(KeyEvent.VK_DOWN)){
			Position.eplus((Zdir.times(-sideStepPercent*walkRate*ddt)));
		}
		
		if(mGame.isKeyPressed(KeyEvent.VK_LEFT)){
			Position.eplus((Xdir.times(-sideStepPercent*walkRate*ddt)));
		}
		
		if(mGame.isKeyPressed(KeyEvent.VK_RIGHT)){
			Position.eplus((Xdir.times(sideStepPercent*walkRate*ddt)));
		}
		
	}

}
