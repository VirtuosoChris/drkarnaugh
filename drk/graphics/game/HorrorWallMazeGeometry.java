package drk.graphics.game;
import java.nio.FloatBuffer;

import com.sun.opengl.util.*;
import java.util.*;
import javax.media.opengl.*;



public class HorrorWallMazeGeometry
{
	static int WallsVertices;
	static float lerp(float a,float b,float t)
	{
		return a*(1.0f-t)+b*(t);
	}
	static int buildWallsVBO(GL gl)
	{
		final float w=HorrorWallMaze.ROOM_WIDTH*0.5f-HorrorWallMaze.WALL_WIDTH*0.5f;
		final float l=HorrorWallMaze.ROOM_LENGTH*0.5f-HorrorWallMaze.WALL_WIDTH*0.5f;;
		//final float BRICKS_WIDTH;
		
		final float dheight=HorrorWallMaze.DOOR_HEIGHT;
		final float dw=HorrorWallMaze.DOOR_WIDTH*0.5f;
		final float bs=1.0f/HorrorWallMaze.BRICK_SCALE;
		final float brick_scale=HorrorWallMaze.BRICK_SCALE;
		final float ROOM_HEIGHT=HorrorWallMaze.ROOM_HEIGHT;
		final float WALL_WIDTH=HorrorWallMaze.WALL_WIDTH;
		
		//72 vertices...72 TC...,x2
		
		int numv=72;
		int WallsVBO;
		FloatBuffer databuf=BufferUtil.newFloatBuffer(numv*(2+3+3)); //per pixel data size
		final float[] TFloats=
		{
		dw*bs,0.0f,
		w*bs,0.0f,
		w*bs,ROOM_HEIGHT*bs,
		dw*bs,ROOM_HEIGHT*bs,
		ROOM_HEIGHT*bs,-dw*bs,
		ROOM_HEIGHT*bs,-l*bs,
		0.0f,-l*bs,
		0.0f,-dw*bs,
		ROOM_HEIGHT*bs,dw*bs,
		ROOM_HEIGHT*bs,-dw*bs,
		dheight*bs,-dw*bs,
		dheight*bs,dw*bs,
		(w+WALL_WIDTH)*bs,0.0f,
		w*bs,0.0f,
		w*bs,dheight*bs,
		(w+WALL_WIDTH)*bs,dheight*bs,
		(w+WALL_WIDTH)*bs,dw*bs,
		w*bs,dw*bs,
		w*bs,-dw*bs,
		(w+WALL_WIDTH)*bs,-dw*bs,
		(w+WALL_WIDTH)*bs,dheight*bs,
		w*bs,dheight*bs,
		w*bs,0.0f,
		(w+WALL_WIDTH)*bs,0.0f,
		0.0f,dw*bs,
		0.0f,l*bs,
		ROOM_HEIGHT*bs,l*bs,
		ROOM_HEIGHT*bs,dw*bs,
		w*bs,0.0f,
		dw*bs,0.0f,
		dw*bs,ROOM_HEIGHT*bs,
		w*bs,ROOM_HEIGHT*bs,
		dw*bs,dheight*bs,
		-dw*bs,dheight*bs,
		-dw*bs,ROOM_HEIGHT*bs,
		dw*bs,ROOM_HEIGHT*bs,
		dheight*bs,l*bs,
		dheight*bs,(l+WALL_WIDTH)*bs,
		0.0f,(l+WALL_WIDTH)*bs,
		0.0f,l*bs,
		dw*bs,l*bs,
		dw*bs,(l+WALL_WIDTH)*bs,
		-dw*bs,(l+WALL_WIDTH)*bs,
		-dw*bs,l*bs,
		0.0f,l*bs,
		0.0f,(l+WALL_WIDTH)*bs,
		dheight*bs,(l+WALL_WIDTH)*bs,
		dheight*bs,(l)*bs,
		-w*bs,ROOM_HEIGHT*bs,
		-dw*bs,ROOM_HEIGHT*bs,
		-dw*bs,0.0f,
		-w*bs,0.0f,
		ROOM_HEIGHT*bs,dw*bs,
		ROOM_HEIGHT*bs,l*bs,
		0.0f,l*bs,
		0.0f,dw*bs,
		dheight*bs,dw*bs,
		dheight*bs,-dw*bs,
		ROOM_HEIGHT*bs,-dw*bs,
		ROOM_HEIGHT*bs,dw*bs,
		0.0f,-dw*bs,
		0.0f,-l*bs,
		ROOM_HEIGHT*bs,-l*bs,
		ROOM_HEIGHT*bs,-dw*bs,
		-w*bs,0.0f,
		-dw*bs,0.0f,
		-dw*bs,ROOM_HEIGHT*bs,
		-w*bs,ROOM_HEIGHT*bs,
		-dw*bs,dheight*bs,
		dw*bs,dheight*bs,
		dw*bs,ROOM_HEIGHT*bs,
		-dw*bs,ROOM_HEIGHT*bs,
		
		};
		
		final float []NFloats=
		{
		0.0f,0.0f,1.0f,
		0.0f,0.0f,1.0f,
		0.0f,0.0f,1.0f,
		0.0f,0.0f,1.0f,
		-1.0f,0.0f,0.0f,
		-1.0f,0.0f,0.0f,
		-1.0f,0.0f,0.0f,
		-1.0f,0.0f,0.0f,
		-1.0f,0.0f,0.0f,
		-1.0f,0.0f,0.0f,
		-1.0f,0.0f,0.0f,
		-1.0f,0.0f,0.0f,
		0.0f,0.0f,-1.0f,
		0.0f,0.0f,-1.0f,
		0.0f,0.0f,-1.0f,
		0.0f,0.0f,-1.0f,
		0.0f,-1.0f,0.0f,
		0.0f,-1.0f,0.0f,
		0.0f,-1.0f,0.0f,
		0.0f,-1.0f,0.0f,
		0.0f,0.0f,1.0f,
		0.0f,0.0f,1.0f,
		0.0f,0.0f,1.0f,
		0.0f,0.0f,1.0f,
		-1.0f,0.0f,0.0f,
		-1.0f,0.0f,0.0f,
		-1.0f,0.0f,0.0f,
		-1.0f,0.0f,0.0f,
		0.0f,0.0f,-1.0f,
		0.0f,0.0f,-1.0f,
		0.0f,0.0f,-1.0f,
		0.0f,0.0f,-1.0f,
		0.0f,0.0f,-1.0f,
		0.0f,0.0f,-1.0f,
		0.0f,0.0f,-1.0f,
		0.0f,0.0f,-1.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		0.0f,-1.0f,0.0f,
		0.0f,-1.0f,0.0f,
		0.0f,-1.0f,0.0f,
		0.0f,-1.0f,0.0f,
		-1.0f,0.0f,0.0f,
		-1.0f,0.0f,0.0f,
		-1.0f,0.0f,0.0f,
		-1.0f,0.0f,0.0f,
		0.0f,0.0f,-1.0f,
		0.0f,0.0f,-1.0f,
		0.0f,0.0f,-1.0f,
		0.0f,0.0f,-1.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		1.0f,0.0f,0.0f,
		0.0f,0.0f,1.0f,
		0.0f,0.0f,1.0f,
		0.0f,0.0f,1.0f,
		0.0f,0.0f,1.0f,
		0.0f,0.0f,1.0f,
		0.0f,0.0f,1.0f,
		0.0f,0.0f,1.0f,
		0.0f,0.0f,1.0f
		};
		
		final float[] VFloats=
		{
		dw,0.0f,-l,
		w,0.0f,-l,
		w,ROOM_HEIGHT,-l,
		dw,ROOM_HEIGHT,-l,
		w,ROOM_HEIGHT,-dw,
		w,ROOM_HEIGHT,-l,
		w,0.0f,-l,
		w,0.0f,-dw,
		w,ROOM_HEIGHT,dw,
		w,ROOM_HEIGHT,-dw,
		w,dheight,-dw,
		w,dheight,dw,
		w+WALL_WIDTH,0.0f,dw,
		w,0.0f,dw,
		w,dheight,dw,
		w+WALL_WIDTH,dheight,dw,
		w+WALL_WIDTH,dheight,dw,
		w,dheight,dw,
		w,dheight,-dw,
		w+WALL_WIDTH,dheight,-dw,
		w+WALL_WIDTH,dheight,-dw,
		w,dheight,-dw,
		w,0.0f,-dw,
		w+WALL_WIDTH,0.0f,-dw,
		w,0.0f,dw,
		w,0.0f,l,
		w,ROOM_HEIGHT,l,
		w,ROOM_HEIGHT,dw,
		w,0.0f,l,
		dw,0.0f,l,
		dw,ROOM_HEIGHT,l,
		w,ROOM_HEIGHT,l,
		dw,dheight,l,
		-dw,dheight,l,
		-dw,ROOM_HEIGHT,l,
		dw,ROOM_HEIGHT,l,
		-dw,dheight,l,
		-dw,dheight,l+WALL_WIDTH,
		-dw,0.0f,l+WALL_WIDTH,
		-dw,0.0f,l,
		dw,dheight,l,
		dw,dheight,l+WALL_WIDTH,	
		-dw,dheight,l+WALL_WIDTH,
		-dw,dheight,l,
		dw,0.0f,l,
		dw,0.0f,l+WALL_WIDTH,
		dw,dheight,l+WALL_WIDTH,	
		dw,dheight,l,
		-w,ROOM_HEIGHT,l,
		-dw,ROOM_HEIGHT,l,
		-dw,0.0f,l,
		-w,0.0f,l,
		-w,ROOM_HEIGHT,dw,
		-w,ROOM_HEIGHT,l,
		-w,0.0f,l,
		-w,0.0f,dw,
		-w,dheight,dw,
		-w,dheight,-dw,
		-w,ROOM_HEIGHT,-dw,
		-w,ROOM_HEIGHT,dw,
		-w,0.0f,-dw,
		-w,0.0f,-l,
		-w,ROOM_HEIGHT,-l,
		-w,ROOM_HEIGHT,-dw,
		-w,0.0f,-l,
		-dw,0.0f,-l,
		-dw,ROOM_HEIGHT,-l,
		-w,ROOM_HEIGHT,-l,
		-dw,dheight,-l,
		dw,dheight,-l,
		dw,ROOM_HEIGHT,-l,
		-dw,ROOM_HEIGHT,-l,
		};		
		
		
		//Tesselator.  Tesselates the base blocks below into subquads arranged by brick scale.  This is so there may be as little repetition
		//as possible
		
		Vector<Float> NewVertices=new Vector<Float>();
		Vector<Float> NewTexCoords=new Vector<Float>();
		Vector<Float> NewNormals=new Vector<Float>();
		
		//for each surface
		drk.Vector3D dims=new drk.Vector3D();
		for(int s=0;s<(VFloats.length/12);s++)
		{
			drk.Vector3D.tmpv.equals(VFloats[s],VFloats[s+1],VFloats[s+2]);
			dims.equals(VFloats[s+3*2],VFloats[s+3*2+1],VFloats[s+3*2+2]);
			dims.eminus(drk.Vector3D.tmpv);
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		databuf.put(VFloats);
		databuf.put(NFloats);
		databuf.put(TFloats);//put the data into the client side array to be uploaded into the vbo
		databuf.rewind();
		int [] tmp=new int[1];
		
		
		gl.glGenBuffers(1,tmp,0);
		WallsVBO=tmp[0];
		WallsVertices=72;
		gl.glBindBufferARB( GL.GL_ARRAY_BUFFER_ARB, WallsVBO);
		 // Copy data to the server into the VBO.
		 gl.glBufferDataARB( GL.GL_ARRAY_BUFFER_ARB,
		                     numv*4*8,databuf,  //load 4bytes/float*8floatspervertex into GPU memory
		                     GL.GL_STATIC_DRAW_ARB );
		 return WallsVBO;
	}
}
