package drk.game;

import drk.Vector3D;
import drk.graphics.Camera;
import drk.graphics.EulerCamera;
import drk.Updatable;

public class MazeCamera extends EulerCamera implements MazeGameTracker,Updatable
{
	MazeGame mGame;
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
		double walkRate = 1.0;
		double ddt=mGame.getFrameDt();
		
		
		
		if(mGame.upKeyPressed){	
			mGame.ec.Position.eplus((Zdir.times(walkRate*ddt)));
		}
		
		if(mGame.downKeyPressed){
			mGame.ec.Position.eplus((Zdir.times(-walkRate*ddt)));
		}
		
		if(mGame.leftKeyPressed){
			mGame.ec.Position.eplus((Xdir.times(-walkRate*ddt)));
		}
		
		if(mGame.rightKeyPressed){
			mGame.ec.Position.eplus((Xdir.times(walkRate*ddt)));
		}
	}

}
