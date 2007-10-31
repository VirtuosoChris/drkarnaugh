package drk.maze;

import drk.graphics.GLInitializable;
import drk.graphics.particle.TimeUpdatable;
import drk.Vector3D;
import drk.DeltaTimer;

public abstract class RenderableMaze extends Maze implements GLInitializable,TimeUpdatable
{
	public abstract Vector3D getRoomMiddle(Room r);
	DeltaTimer dt;
	public RenderableMaze(int w,int h)
	{
		super(w,h);
	}
	
	public void setDeltaTimer(DeltaTimer det)
	{
		dt=det;
	}
	//public public MazeCamera getNewMazeCameraInstance();
}
