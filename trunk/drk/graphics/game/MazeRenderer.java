package drk.graphics.game;

import javax.media.opengl.GL;
import drk.maze.Maze;

import drk.graphics.GLInitializable;

public abstract class MazeRenderer implements GLInitializable
{
	Maze m;
	public MazeRenderer(Maze tm)
	{
		//super();
		m=tm;
		// TODO Auto-generated constructor stub
	}

	public void initialize(GL gl)
	{
	// TODO Auto-generated method stub

	}

	public void render(GL gl)
	{
		
	}
}
