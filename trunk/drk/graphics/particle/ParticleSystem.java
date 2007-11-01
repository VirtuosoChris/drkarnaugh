package drk.graphics.particle;

import javax.media.opengl.GL;

import drk.DeltaTimer;
import drk.graphics.GLInitializable;

public class ParticleSystem implements GLInitializable, TimeUpdatable
{
	DeltaTimer deltat;
	Particle [] particles;
	float []    poffsets;
	NewtonMotion3D emittermotion;
	public ParticleSystem(int n)
	{
		
		/*
		super();
		particles=new Particle[n];
		poffsets=new float[n*3];
		emittermotion=new AccelerationMotion3D();
		
		
		for(int i=0;i<n;i++)
		{
			particles[i]=new Particle()
			{
				public Particle()
				{
					motion=new VelocityMotion();
				}
			}
		}
		*/
		// TODO Auto-generated constructor stub
	}

	public void initialize(GL gl)
	{
	// TODO Auto-generated method stub

	}

	public void render(GL gl)
	{
		gl.glPushMatrix();
		gl.glTranslated(0.0,1.0,0.0);
		gl.glBegin(GL.GL_POINTS);
		{
			gl.glColor3f(1.0f,0.0f,1.0f);
		/*	for(Particle p:particles)
			{
				//gl.glVertex3d(p.)
			}*/
		}
		gl.glEnd();
	// TODO Auto-generated method stub

	}

	public void setDeltaTimer(DeltaTimer dt)
	{
		deltat=dt;
		for(Particle p:particles)
			p.update();
		
	// TODO Auto-generated method stub

	}

	public void update()
	{
		for(Particle p:particles)
			p.update();
	// TODO Auto-generated method stub

	}

}
