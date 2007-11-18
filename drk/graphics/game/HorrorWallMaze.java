package drk.graphics.game;

import javax.imageio.*;
import javax.media.opengl.GL;
import com.sun.opengl.util.texture.*;
import java.awt.image.BufferedImage;
import drk.Vector3D;
import drk.maze.RenderableMaze;
import drk.maze.Room;
import drk.graphics.GLSLShader;


public class HorrorWallMaze extends RenderableMaze
{
	
	final static float ROOM_LENGTH=10.0f;	//all units are in meters
	final static float ROOM_HEIGHT=3.0f;
	final static float ROOM_WIDTH=10.0f;	//bricks are .5 meters texture
	final static float WALL_WIDTH=.5f;
	final static float DOOR_WIDTH=1.0f;
	final static float DOOR_HEIGHT=3.0f*.75f;
	Texture bricks;
	final static float BRICK_SCALE=.5f;
	final static float PLANK_SCALE=.9f;
	Texture planks;
	final static boolean ALWAYSROOM=false;
	GLSLShader FinalOutputShader;
	public HorrorWallMaze(int w, int h)
	{
		super(w, h);
		isinit=false;
		//temporary
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
		float x,z;
		x=r.getID() % getWidth();
		z=r.getID() / getWidth();
		x+=0.5f;
		z+=0.5f;
		z*=ROOM_LENGTH;
		x*=ROOM_WIDTH;
		// TODO Auto-generated method stub
		return new Vector3D(x,0.0,z);
	}
	//temporary stuff
	
	int numv;
	int WallsVBO;
	int thandle;
	float time=0.0f;
	public void initializedatabuffers(GL gl)
	{
		WallsVBO=HorrorWallMazeGeometry.buildWallsVBO(gl);
		numv=HorrorWallMazeGeometry.WallsVertices;
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
			drk.KarnaughLog.log("Failure loading texture map: roughbricks.jpg:"+e);
		}
		
		isinit=true;
		
		initializedatabuffers(gl);
		FinalOutputShader=KarnaughShaders.getOutputShader();
		try
		{
			thandle=FinalOutputShader.getUniformObject("t");
		}catch(Exception e)
		{
			drk.KarnaughLog.log(e);
		}
	}
	
	public float[] distanceToWalls()
	{
		drk.graphics.Camera mc=this.getCamera();
		Room r=getCurrentRoom();
		Vector3D crp=mc.Position.minus(this.getRoomMiddle(r));
		float [] f=new float[4];
	/*	f[0]=(float)crp.y-(float)(RENDER_WIDTH*0.5);
		f[1]=(float)(RENDER_WIDTH*0.5)-(float)crp.x;
		f[2]=(float)(RENDER_WIDTH*0.5)-(float)crp.y;
		f[3]=(float)crp.x-(float)(RENDER_WIDTH*0.5);*/
		
		
		return f;
	}

	
	public void renderRoom(GL gl, Room r)
	{
		gl.glPushMatrix();
		Vector3D centerbottom=getRoomMiddle(r);
		gl.glTranslated(centerbottom.x,centerbottom.y,centerbottom.z);
		
		
		float w=ROOM_WIDTH*0.5f;
		float l=ROOM_LENGTH*0.5f;
		
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
			
			
			
			gl.glTexCoord2f(0.0f,l*2.0f/PLANK_SCALE);
			//gl.glColor3f(0.0f,1.0f,0.0f);
			gl.glVertex3f(-w,ROOM_HEIGHT,l);
			
			gl.glTexCoord2f(0.0f,0.0f);
			gl.glVertex3f(-w,ROOM_HEIGHT,-l);
			
			gl.glTexCoord2f(w*2.0f/PLANK_SCALE,0.0f);
			//gl.glColor3f(0.0f,0.0f,1.0f);
			gl.glVertex3f(w,ROOM_HEIGHT,-l);
			
			gl.glTexCoord2f(w*2.0f/PLANK_SCALE,l*2.0f/PLANK_SCALE);
			//gl.glColor3f(0.0f,1.0f,1.0f);
			gl.glVertex3f(w,ROOM_HEIGHT,l);
			
			//gl.glEnd();
		}
		gl.glEnd();
		
		l-=WALL_WIDTH*0.5f;
		w-=WALL_WIDTH*0.5f;
		float dheight=DOOR_HEIGHT;
		float dw=DOOR_WIDTH*0.5f;
		bricks.bind();
		float bs=1.0f/BRICK_SCALE;
		
		gl.glEnableClientState(GL.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL.GL_NORMAL_ARRAY);
		gl.glEnableClientState(GL.GL_TEXTURE_COORD_ARRAY);
		gl.glBindBuffer( GL.GL_ARRAY_BUFFER_ARB, WallsVBO);
		gl.glVertexPointer( 3, GL.GL_FLOAT, 0, 0);
		gl.glNormalPointer(GL.GL_FLOAT,0,numv*3*4);
		gl.glTexCoordPointer(2,GL.GL_FLOAT,0,numv*6*4);
		gl.glDrawArrays(GL.GL_QUADS,0,numv);
		gl.glDisableClientState(GL.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL.GL_NORMAL_ARRAY);
		gl.glDisableClientState(GL.GL_VERTEX_ARRAY);
		
		gl.glBegin(GL.GL_QUADS);
		{
			
			if(!r.Up())
			{
				gl.glTexCoord2f(dw*bs,dheight*bs);
				gl.glVertex3f(dw,dheight,-l);
				gl.glTexCoord2f(-dw*bs,dheight*bs);
				gl.glVertex3f(-dw,dheight,-l);
				gl.glTexCoord2f(-dw*bs,0.0f);
				gl.glVertex3f(-dw,0.0f,-l);
				gl.glTexCoord2f(dw*bs,0.0f);
				gl.glVertex3f(dw,0.0f,-l);
			}
			if(!r.Left())
			{
				gl.glTexCoord2f(dheight*bs,-dw*bs);
				gl.glVertex3f(-w,dheight,-dw);
				gl.glTexCoord2f(dheight*bs,dw*bs);
				gl.glVertex3f(-w,dheight,dw);
				gl.glTexCoord2f(0.0f,dw*bs);
				gl.glVertex3f(-w,0.0f,dw);
				gl.glTexCoord2f(0.0f,-dw*bs);
				gl.glVertex3f(-w,0.0f,-dw);
			}
			if(!r.Down())
			{
				gl.glTexCoord2f(-dw*bs,dheight*bs);
				gl.glVertex3f(-dw,dheight,l);
				gl.glTexCoord2f(dw*bs,dheight*bs);
				gl.glVertex3f(dw,dheight,l);
				gl.glTexCoord2f(dw*bs,0.0f);
				gl.glVertex3f(dw,0.0f,l);
				gl.glTexCoord2f(-dw*bs,0.0f);
				gl.glVertex3f(-dw,0.0f,l);
			}
			if(!r.Right())
			{
				gl.glTexCoord2f(0.0f,-dw*bs);
				gl.glVertex3f(w,0.0f,-dw);
				gl.glTexCoord2f(0.0f,dw*bs);
				gl.glVertex3f(w,0.0f,dw);
				gl.glTexCoord2f(dheight*bs,dw*bs);
				gl.glVertex3f(w,dheight,dw);
				gl.glTexCoord2f(dheight*bs,-dw*bs);
				gl.glVertex3f(w,dheight,-dw);
			}
			/*switch()*/
		}
		gl.glEnd();
		//}
		
		if(r.getItem()!=null){
			r.getItem().render(gl);
		}
		
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
		FinalOutputShader.applyShader();
		time+=900.0f*this.dt.ddt;
		FinalOutputShader.setUniformFloat(thandle,time);
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
		GLSLShader.applyShader(0);
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
