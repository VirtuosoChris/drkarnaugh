package drk.graphics.game;
import java.nio.FloatBuffer;
import drk.Vector3D;
import com.sun.opengl.util.*;
import javax.media.opengl.*;

public class HorrorWallMazeGeometry
{
	static int WallsVertices;

	static int ceil(float a)
	{
		return (int)(a+0.5f);
	}
	static float abs(float a)
	{
		return (a > 0 ? a : -a);
	}
	
	public static Vector3D getTangent(Vector3D Normal)
	{
		Vector3D Tangent=new Vector3D();
		if(Normal.x > 0.0)
		{
			Tangent.z=1.0;
		}
		else if(Normal.x < 0.0)
		{
			Tangent.z=-1.0;
		}
		else if(Normal.y > 0.0)
		{
			Tangent.x=1.0;
		}
		else if(Normal.y < 0.0)
		{
			Tangent.x=-1.0;
		}
		else if(Normal.z > 0.0)
		{
			Tangent.y=1.0;
		}
		else
		{
			Tangent.y=-1.0;
		}
		return Tangent;
	}
	static int buildWallsVBO(GL gl)
	{
		final float w=HorrorWallMaze.ROOM_WIDTH*0.5f-HorrorWallMaze.WALL_WIDTH*0.5f;
		final float l=HorrorWallMaze.ROOM_LENGTH*0.5f-HorrorWallMaze.WALL_WIDTH*0.5f;;
		//final float BRICKS_WIDTH;
		
		final float dheight=HorrorWallMaze.DOOR_HEIGHT;
		final float dw=HorrorWallMaze.DOOR_WIDTH*0.5f;
		final float bs=1.0f/HorrorWallMaze.BRICK_SCALE;
		
		final float ROOM_HEIGHT=HorrorWallMaze.ROOM_HEIGHT;
		final float WALL_WIDTH=HorrorWallMaze.WALL_WIDTH;
		
		//72 vertices...72 TC...,x2
		
		int numv=72;
		int WallsVBO;
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
		
		float[] TangentFloats=new float[NFloats.length];
		Vector3D temptangent=Vector3D.tmpv;
		for(int i=0; i < TangentFloats.length/3;i++)
		{
			temptangent.equals(NFloats[3*i],NFloats[3*i+1],NFloats[3*i+2]);
			temptangent=getTangent(temptangent);
			TangentFloats[3*i]=(float)temptangent.x;
			TangentFloats[3*i+1]=(float)temptangent.y;
			TangentFloats[3*i+2]=(float)temptangent.z;
		}
		
	
		
		//Tesselator.  Tesselates the base blocks below into subquads arranged by brick scale.  This is so there may be as little repetition
		//as possible through random assignment of texture coordinates
		/*
		Vector<Float> NewVertices=new Vector<Float>();
		Vector<Float> NewTexCoords=new Vector<Float>();
		Vector<Float> NewNormals=new Vector<Float>();
		
		//for each surface
	//	drk.Vector3D dims=new drk.Vector3D();
		Vector3D[] vquad=new Vector3D[4];
		Vector3D[] tquad=new Vector3D[4];
		Vector3D[] tmpquad=new Vector3D[4];
		for(int i=0;i<4;i++)
		{
			vquad[i]=new Vector3D();
			tquad[i]=new Vector3D();
			tmpquad[i]=new Vector3D();
		}
		Vector3D   normal=new Vector3D();
	//	System.err.println("Error1");
		for(int s=0;s<(VFloats.length/12);s++)
		{
		//	System.err.println("Error2");
			for(int v=0;v<4;v++)
			{
				vquad[v].equals(VFloats[s+v*3],VFloats[s+v*3+1],VFloats[s+v*3+2]);
				tquad[v].equals(TFloats[s+v*2],TFloats[s+v*2+1],0.0f);
				normal.equals(NFloats[s],NFloats[s+1],NFloats[s+2]);
			//	System.err.println("Error3");
			}
			//System.err.println("Error4");
			int numx=ceil(abs((float)(tquad[2].minus(tquad[0]).x)));
			int numy=ceil(abs((float)(tquad[2].minus(tquad[0]).y)));
			
			
			for(int i=0;i<numx;i++)
			{
				for(int j=0;j<numy;j++)
				{
					//Vector3D.tmpv.equals((double)i/(double)numx,(double)i/(double)numx,0.0);
					tmpquad[0]=vquad[0].lerp(vquad[3],(float)i/(float)numx);
					Vector3D.tmpv.equals(vquad[1].lerp(vquad[2],(float)i/(float)numx));
					tmpquad[0].elerp(Vector3D.tmpv,(float)j/(float)numy);
					//tmpquad[0].eplus
					
					tmpquad[1]=vquad[0].lerp(vquad[3],(float)i/(float)numx);
					Vector3D.tmpv.equals(vquad[1].lerp(vquad[2],(float)i/(float)numx));
					tmpquad[1].elerp(Vector3D.tmpv,(float)(j+1)/(float)numy);
					
					tmpquad[2]=vquad[0].lerp(vquad[3],(float)(i+1)/(float)numx);
					Vector3D.tmpv.equals(vquad[1].lerp(vquad[2],(float)(i+1)/(float)numx));
					tmpquad[2].elerp(Vector3D.tmpv,(float)(j+1)/(float)numy);
					
					tmpquad[3]=vquad[0].lerp(vquad[3],(float)(i+1)/(float)numx);
					Vector3D.tmpv.equals(vquad[1].lerp(vquad[2],(float)(i+1)/(float)numx));
					tmpquad[3].elerp(Vector3D.tmpv,(float)j/(float)numy);
					
					for(int k=0;k<4;k++)
					{
						NewVertices.add((float)tmpquad[k].x);
						NewVertices.add((float)tmpquad[k].y);
						NewVertices.add((float)tmpquad[k].z);
						NewTexCoords.add((float)((k & 2)>>1));
						NewTexCoords.add((float)(k & 1));
						NewNormals.add((float)normal.x);
						NewNormals.add((float)normal.y);
						NewNormals.add((float)normal.z);
					}
					
					//randomizeTexCoords(tquad);
					
					
				}
			}
			
			//dims.eminus(drk.Vector3D.tmpv)
		System.err.println("The surface tesselator is on:"+s);
		}
		//
	
		numv=NewVertices.size()/3;
		 */
		FloatBuffer databuf=BufferUtil.newFloatBuffer(numv*(2+3+3+3)); //per pixel data size
	//	float[] array=NewVertices.toArray();
		/*float[] array=new float[NewVertices.size()];
		for(int i=0;i<NewVertices.size();i++)
			array[i]=NewVertices.get(i).floatValue();
		databuf.put(array);
		
		array=new float[NewNormals.size()];
		for(int i=0;i<NewNormals.size();i++)
			array[i]=NewNormals.get(i).floatValue();
		databuf.put(array);
		
		array=new float[NewTexCoords.size()];
		for(int i=0;i<NewTexCoords.size();i++)
			array[i]=NewTexCoords.get(i).floatValue();
		databuf.put(array);
		*/
	//	databuf.put
		databuf.put(VFloats);
		databuf.put(NFloats);
		databuf.put(TangentFloats);
	//	databuf.put(BitangentFloats);
		databuf.put(TFloats);
		databuf.rewind();
		
		int [] tmp=new int[1];
		
		
		gl.glGenBuffers(1,tmp,0);
		WallsVBO=tmp[0];
		WallsVertices=numv=72;
		gl.glBindBufferARB( GL.GL_ARRAY_BUFFER_ARB, WallsVBO);
		 // Copy data to the server into the VBO.
		 gl.glBufferDataARB( GL.GL_ARRAY_BUFFER_ARB,
		                     numv*4*(3+3+3+2),databuf,  //load 4bytes/float*8floatspervertex into GPU memory
		                     GL.GL_STATIC_DRAW_ARB );
		 return WallsVBO;
	}
}
