package drk.game;

import drk.Vector3D;
import drk.graphics.Camera;
import drk.graphics.EulerCamera;
import drk.Updatable;
import java.awt.event.*;

public class MazeCamera extends EulerCamera implements MazeGameTracker,Updatable
{
	MazeGame mGame;
	double walkRate = 1.0;//units/s
	double turnRate = .1;//deg/s
	public MazeCamera()
	{
		super();
		mGame=null;
		//mGame.ec.Position.eplus(new Vector3D(0.0,30.0,0.0));
		
		//mGame.ec.xrotation = -60.0;
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
	public void update()
	{
		//super.update();
		Vector3D Zdir=mGame.ec.ZNormal.times(new Vector3D(1.0,0.0,1.0)).enormal();
		Vector3D Xdir=mGame.ec.XNormal.times(new Vector3D(1.0,0.0,1.0)).enormal();
		double ddt=mGame.getFrameDt();
		
		//System.err.println("Output Keypress");
		
		xrotation=mGame.getYMousePercentFovy()*(-turnRate);
		yrotation=mGame.getXMousePercentFovy()*turnRate;
		
		if(mGame.isKeyPressed(KeyEvent.VK_UP)){	
			Position.eplus((Zdir.times(walkRate*ddt)));
		}
		
		if(mGame.isKeyPressed(KeyEvent.VK_DOWN)){
			Position.eplus((Zdir.times(-walkRate*ddt)));
		}
		
		if(mGame.isKeyPressed(KeyEvent.VK_LEFT)){
			Position.eplus((Xdir.times(-walkRate*ddt)));
		}
		
		if(mGame.isKeyPressed(KeyEvent.VK_RIGHT)){
			Position.eplus((Xdir.times(walkRate*ddt)));
		}
		
		if(mGame.isKeyPressed(KeyEvent.VK_ESCAPE)){
			System.exit(0);
		}
		
	}

}
