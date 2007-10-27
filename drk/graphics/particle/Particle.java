package drk.graphics.particle;

import javax.media.opengl.GL;
import drk.DeltaTimer;
import drk.graphics.GLRenderable;


public abstract class Particle implements TimeUpdatable, GLRenderable
{
	DeltaTimer dtimer;
	NewtonMotion3D motion;
	public Particle()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public void setDeltaTimer(DeltaTimer deltat)
	{
		dtimer=deltat;
		motion.setDeltaTimer(deltat);
	// TODO Auto-generated method stub

	}
	public void render(GL gl)
	{
		
	}
	public void update()
	{
		motion.update();
	}
}
