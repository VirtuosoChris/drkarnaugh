package drk.maze;

import javax.media.opengl.GL;

import drk.graphics.GLInitializable;
import drk.graphics.GLRenderable;

public class RenderableMaze extends Maze implements GLRenderable,
		GLInitializable
{

	public static final double RENDER_HEIGHT = 1.0;
	public static final double RENDER_WIDTH = .75;
	
	
	public void render(GL gl)
	{
		double xoff=0.0,zoff=0.0,scale=1.0;
		double ws=0.02;
		
		float alpha=1.0f;
		
		//Room r=RoomList.get(i);
		Room r;
		gl.glPushMatrix();
		gl.glScaled(scale,scale,scale);
		//fix this for 0-1 with rescaling and translation like chris suggested
		gl.glBegin(GL.GL_QUADS);
		{
			for(int i=0;i<RoomList.size();i++)
			{
				r=RoomList.get(i);
				xoff=(double)(i % width)*RENDER_WIDTH;
				zoff=(double)(i / width)*RENDER_WIDTH;
				
				switch(i & 0x3)
				{
					case 0x0:
						gl.glColor4f(0.5f,0.5f,0.0f,alpha);
						break;
					case 0x1:
						gl.glColor4f(0.0f,0.0f,0.5f,alpha);
						break;
					case 0x2:
						gl.glColor4f(0.0f,0.5f,0.0f,alpha);
						break;
					case 0x3:
						gl.glColor4f(0.5f,0.0f,0.0f,alpha);
						break;
				}
				gl.glVertex3d((xoff+ws),0.0,(zoff+ws));
				gl.glVertex3d((xoff+ws),0.0,(zoff-ws+RENDER_WIDTH));
				gl.glVertex3d((xoff-ws+RENDER_WIDTH),0.0,(zoff-ws+RENDER_WIDTH));
				gl.glVertex3d((xoff-ws+RENDER_WIDTH),0.0,(zoff+ws));
				
				switch(i & 0x3)
				{
					case 0x0:
						gl.glColor4f(1.0f,1.0f,0.0f,alpha);
						break;
					case 0x1:
						gl.glColor4f(0.0f,0.0f,1.0f,alpha);
						break;
					case 0x2:
						gl.glColor4f(0.0f,1.0f,0.0f,alpha);
						break;
					case 0x3:
						gl.glColor4f(1.0f,0.0f,0.0f,alpha);
						break;
				}
			
				
				if(!r.Left())
				{
					//gl
					gl.glVertex3d((xoff+ws),0.0,(zoff+ws));
					gl.glVertex3d((xoff+ws),RENDER_HEIGHT,(zoff+ws));
					gl.glVertex3d((xoff+ws),RENDER_HEIGHT,((zoff-ws)+RENDER_WIDTH));
					gl.glVertex3d((xoff+ws),0.0,((zoff-ws)+RENDER_WIDTH));
				}
				if(!r.Right())
				{
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),0.0,(zoff+ws));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),RENDER_HEIGHT,(zoff+ws));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),RENDER_HEIGHT,((zoff-ws)+RENDER_WIDTH));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),0.0,((zoff-ws)+RENDER_WIDTH));
				}
				if(!r.Down())
				{
					gl.glVertex3d((xoff+ws),0.0,((zoff-ws)+RENDER_WIDTH));
					gl.glVertex3d((xoff+ws),RENDER_HEIGHT,((zoff-ws)+RENDER_WIDTH));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),RENDER_HEIGHT,((zoff-ws)+RENDER_WIDTH));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),0.0,((zoff-ws)+RENDER_WIDTH));
				
				}
				if(!r.Up())
				{
					gl.glVertex3d((xoff+ws),0.0,(zoff+ws));
					gl.glVertex3d((xoff+ws),RENDER_HEIGHT,(zoff+ws));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),RENDER_HEIGHT,(zoff+ws));
					gl.glVertex3d(((xoff-ws)+RENDER_WIDTH),0.0,(zoff+ws));
				}
			}	
		}
		gl.glEnd();
		gl.glPopMatrix();
	}
	

	public RenderableMaze(int x, int y)
	{
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	public void initialize(GL gl)
	{
	// TODO Auto-generated method stub

	}

}
