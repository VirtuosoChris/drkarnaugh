package drk.game;

import drk.graphics.Camera;

public class DebugMazeCamera extends MazeCamera
{

	public DebugMazeCamera()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public DebugMazeCamera(MazeGame mg)
	{
		super(mg);
		// TODO Auto-generated constructor stub
	}

	public DebugMazeCamera(Camera c, MazeGame mg)
	{
		super(c, mg);
		// TODO Auto-generated constructor stub
	}
	
	public void update()
	{
		super.update();
		//System.err.println("DebugOutput");
		if(mGame.isKeyPressed(java.awt.event.KeyEvent.VK_Q))
		{
			System.err.println("Q Pressed");
			Position.y+=walkRate*mGame.getFrameDt();
		}
		if(mGame.isKeyPressed(java.awt.event.KeyEvent.VK_A))
		{
			Position.y-=walkRate*mGame.getFrameDt();
		}
	}

}
