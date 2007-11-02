package drk.graphics.game;

import javax.imageio.*;
import javax.media.opengl.GL;
import com.sun.opengl.util.texture.*;
import java.awt.image.BufferedImage;
import drk.Vector3D;
import drk.maze.RenderableMaze;
import drk.maze.Room;

public class HorrorWallMaze extends RenderableMaze
{
	final static float ROOM_LENGTH=10.0f;	//all units are in meters
	final static float ROOM_HEIGHT=3.0f;
	final static float ROOM_WIDTH=10.0f;	//bricks are .5 meters texture
	final static float WALL_WIDTH=.5f;
	final static float DOOR_WIDTH=1.0f;
	Texture bricks;
	final static float BRICK_SCALE=.5f;
	final static float PLANK_SCALE=.9f;
	Texture planks;
	final static boolean ALWAYSROOM=false;
	public HorrorWallMaze(int w, int h)
	{
		super(w, h);
		isinit=false;
		// TODO Auto-generated constructor stub
	}
	boolean isinit;

	public boolean isInitialized()
	{
		return isinit;
	}
	@Override
	public Vector3D getRoomMiddle(Room r)
	{
		float x,y,z;
		x=r.getID() % getWidth();
		z=r.getID() / getWidth();
		x+=0.5f;
		z+=0.5f;
		z*=ROOM_LENGTH;
		x*=ROOM_WIDTH;
		// TODO Auto-generated method stub
		return new Vector3D(x,0.0,z);
	}

	public void initialize(GL gl)
	{
		try
		{
			BufferedImage im=ImageIO.read(HorrorWallMaze.class.getResource("planks.jpg"));
			this.planks = TextureIO.newTexture(im,true);
			planks.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER,GL.GL_LINEAR_MIPMAP_LINEAR);
			planks.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
			planks.setTexParameteri(GL.GL_TEXTURE_WRAP_S,GL.GL_REPEAT);
			planks.setTexParameterf(GL.GL_TEXTURE_WRAP_T,GL.GL_REPEAT);
			im=ImageIO.read(HorrorWallMaze.class.getResource("roughbricks.jpg"));
			this.bricks = TextureIO.newTexture(im,true);
			bricks.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER,GL.GL_LINEAR_MIPMAP_LINEAR);
			bricks.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
			bricks.setTexParameteri(GL.GL_TEXTURE_WRAP_S,GL.GL_REPEAT);
			bricks.setTexParameterf(GL.GL_TEXTURE_WRAP_T,GL.GL_REPEAT);
		}catch(Exception e)
		{
			drk.KarnaughLog.log("Failure loading texture map: roughbricks.png:"+e);
		}
		
		isinit=true;
		
	// TODO Auto-generated method stub

	}

	
	public void renderRoom(GL gl, Room r)
	{
		gl.glPushMatrix();
		Vector3D centerbottom=getRoomMiddle(r);
		gl.glTranslated(centerbottom.x,centerbottom.y,centerbottom.z);
		
		
		float w=ROOM_WIDTH*0.5f;
		float l=ROOM_LENGTH*0.5f;
		
		Room croom=this.getCurrentRoom();
		
		//if(croom.getID() == r.getID())
		//{
		planks.enable();
		planks.bind();
		gl.glBegin(GL.GL_QUADS);
		{
			//gl.glColor3f(1.0f,0.0f,0.0f);
			gl.glTexCoord2f(0.0f,0.0f);
			gl.glVertex3f(-w,0.0f,-l);
			
			gl.glTexCoord2f(0.0f,l*2.0f/PLANK_SCALE);
			//gl.glColor3f(0.0f,1.0f,0.0f);
			gl.glVertex3f(-w,0.0f,l);
			
			gl.glTexCoord2f(w*2.0f/PLANK_SCALE,l*2.0f/PLANK_SCALE);
			//gl.glColor3f(0.0f,1.0f,1.0f);
			gl.glVertex3f(w,0.0f,l);
			
			gl.glTexCoord2f(w*2.0f/PLANK_SCALE,0.0f);
			//gl.glColor3f(0.0f,0.0f,1.0f);
			gl.glVertex3f(w,0.0f,-l);
			
			//gl.glEnd();
		}
		gl.glEnd();
		
		l-=WALL_WIDTH*0.5f;
		w-=WALL_WIDTH*0.5f;
		float dheight=3.0f*.75f;
		float dw=DOOR_WIDTH*0.5f;
		bricks.bind();
		float bs=1.0f/BRICK_SCALE;
		gl.glBegin(GL.GL_QUADS);
		{
			//this really should be a vertex buffer, prolly even a vbo...it should be easy from here

			//gl.glColor3f(1.0f,0.0f,0.0f);
			gl.glTexCoord2f(dw*bs,0.0f);
			gl.glVertex3f(dw,0.0f,-l);
			gl.glTexCoord2f(w*bs,0.0f);
			gl.glVertex3f(w,0.0f,-l);
			gl.glTexCoord2f(w*bs,ROOM_HEIGHT*bs);
			gl.glVertex3f(w,ROOM_HEIGHT,-l);
			gl.glTexCoord2f(dw*bs,ROOM_HEIGHT*bs);
			gl.glVertex3f(dw,ROOM_HEIGHT,-l);
			
			gl.glTexCoord2f(ROOM_HEIGHT*bs,-dw*bs);
			gl.glVertex3f(w,ROOM_HEIGHT,-dw);
			gl.glTexCoord2f(ROOM_HEIGHT*bs,-l*bs);
			gl.glVertex3f(w,ROOM_HEIGHT,-l);
			gl.glTexCoord2f(0.0f,-l*bs);
			gl.glVertex3f(w,0.0f,-l);
			gl.glTexCoord2f(0.0f,-dw*bs);
			gl.glVertex3f(w,0.0f,-dw);
				
			gl.glVertex3f(w,ROOM_HEIGHT,dw);
			gl.glVertex3f(w,ROOM_HEIGHT,-dw);
			gl.glVertex3f(w,dheight,-dw);
			gl.glVertex3f(w,dheight,dw);
			
			if(!r.Right())
			{
				gl.glVertex3f(w,0.0f,-dw);
				gl.glVertex3f(w,0.0f,dw);
				gl.glVertex3f(w,dheight,dw);
				gl.glVertex3f(w,dheight,-dw);
			}
			
			
			gl.glVertex3f(w,0.0f,dw);
			gl.glVertex3f(w,0.0f,l);
			gl.glVertex3f(w,ROOM_HEIGHT,l);	
			gl.glVertex3f(w,ROOM_HEIGHT,dw);
			
			
			gl.glVertex3f(w,0.0f,l);
			gl.glVertex3f(dw,0.0f,l);	
			gl.glVertex3f(dw,ROOM_HEIGHT,l);
			gl.glVertex3f(w,ROOM_HEIGHT,l);
			
			//gl.glColor3f(1.0f,0.0f,1.0f);
			gl.glVertex3f(dw,dheight,l);
			gl.glVertex3f(-dw,dheight,l);
			gl.glVertex3f(-dw,ROOM_HEIGHT,l);
			gl.glVertex3f(dw,ROOM_HEIGHT,l);
			
			if(!r.Down())
			{
				gl.glVertex3f(-dw,dheight,l);
				gl.glVertex3f(dw,dheight,l);
				gl.glVertex3f(dw,0.0f,l);
				gl.glVertex3f(-dw,0.0f,l);
			}
			
			
			gl.glVertex3f(-w,ROOM_HEIGHT,l);
			gl.glVertex3f(-dw,ROOM_HEIGHT,l);
			gl.glVertex3f(-dw,0.0f,l);
			gl.glVertex3f(-w,0.0f,l);
			
			
			gl.glVertex3f(-w,ROOM_HEIGHT,dw);
			gl.glVertex3f(-w,ROOM_HEIGHT,l);
			gl.glVertex3f(-w,0.0f,l);
			gl.glVertex3f(-w,0.0f,dw);
			
			
			gl.glVertex3f(-w,dheight,dw);
			gl.glVertex3f(-w,dheight,-dw);
			gl.glVertex3f(-w,ROOM_HEIGHT,-dw);
			gl.glVertex3f(-w,ROOM_HEIGHT,dw);
			
			if(!r.Left())
			{
				
				gl.glVertex3f(-w,dheight,-dw);
				gl.glVertex3f(-w,dheight,dw);
				gl.glVertex3f(-w,0.0f,dw);
				gl.glVertex3f(-w,0.0f,-dw);
			}
			
			


			gl.glVertex3f(-w,0.0f,-dw);
			gl.glVertex3f(-w,0.0f,-l);
			gl.glVertex3f(-w,ROOM_HEIGHT,-l);
			gl.glVertex3f(-w,ROOM_HEIGHT,-dw);
			
			gl.glVertex3f(-w,0.0f,-l);
			gl.glVertex3f(-dw,0.0f,-l);
			gl.glVertex3f(-dw,ROOM_HEIGHT,-l);
			gl.glVertex3f(-w,ROOM_HEIGHT,-l);
			
			//gl.glColor3f(1.0f,0.0f,1.0f);
			
			gl.glVertex3f(-dw,dheight,-l);
			gl.glVertex3f(dw,dheight,-l);
			gl.glVertex3f(dw,ROOM_HEIGHT,-l);
			gl.glVertex3f(-dw,ROOM_HEIGHT,-l);
			
			if(!r.Up())
			{
				gl.glVertex3f(dw,dheight,-l);
				gl.glVertex3f(-dw,dheight,-l);
				gl.glVertex3f(-dw,0.0f,-l);
				gl.glVertex3f(dw,0.0f,-l);
			}
			
			
			
			/*switch()*/
		}
		gl.glEnd();
		//}
		
		gl.glPopMatrix();
		
	}
	public Room getCurrentRoom()
	{
		int id;
		drk.game.MazeCamera mc=this.getCamera();
		
		int xp=(int)(mc.Position.x / ROOM_WIDTH);
		int zp=(int)(mc.Position.z / ROOM_LENGTH);
		id=zp*getWidth()+xp;
		if(id < 0 || id >= RoomList.size()) return null;
		return RoomList.get(id);
	}
	public void render(GL gl)
	{
		if(!isInitialized())
			initialize(gl);
		gl.glEnable(GL.GL_CULL_FACE);
		//gl.glFrontFace(GL.GL_CCW);
		drk.game.MazeCamera mc=this.getCamera();
		Room croom=this.getCurrentRoom();
		int cx=getRoomX(croom);
		int cz=getRoomZ(croom);
		if(mc.Position.y > ROOM_HEIGHT || ALWAYSROOM)
		{
			for(Room r: this.RoomList)
			{
				renderRoom(gl,r);
			}
		}
		else
		{
			for(Room r: this.RoomList)
			{
				if(	(getRoomX(r)==cx)
				||	(getRoomZ(r)==cz)
				)
				{
					renderRoom(gl,r);
				}
			}
		}
		
		gl.glDisable(GL.GL_CULL_FACE);
	// TODO Auto-generated method stub

	}

	public void update()
	{
	// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
	// TODO Auto-generated method stub

	}

}
