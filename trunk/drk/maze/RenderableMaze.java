package drk.maze;

import drk.graphics.GLRenderable;
import drk.graphics.particle.TimeUpdatable;
import drk.game.MazeCamera;
import drk.Vector3D;
import drk.DeltaTimer;

public abstract class RenderableMaze extends Maze implements GLRenderable,TimeUpdatable
{
	protected MazeCamera cam;
	public MazeCamera getCamera()
	{
		return cam;
	}
	public abstract Room getCurrentRoom();
	
	public void setCamera(MazeCamera c)
	{
		cam=c;
	}
	public abstract Vector3D getRoomMiddle(Room r);
	protected DeltaTimer dt;
	public RenderableMaze(int w,int h)
	{
		super(w,h);
		cam=null;
		dt=null;
	}
	
	public void setDeltaTimer(DeltaTimer det)
	{
		dt=det;
	}
	
	public abstract float[] distanceToWalls();
	//public public MazeCamera getNewMazeCameraInstance();
}
