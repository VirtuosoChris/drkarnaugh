package drk.graphics;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

public class hdrTexture
{
	int width;
	int height;
	int target;
	int format;
	int texobject;
	public hdrTexture()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public void createTexture(int t,int internalf,int w,int h,int dataf,int datat,java.nio.Buffer pixelsbuffer)
	{
		GL gl=GLU.getCurrentGL();
		int [] s=new int[1];
		gl.glGenTextures(1,s,0);
		texobject=s[0];
		width=w;
		height=h;
		target=t;
		format=internalf;
		bind();
		setTexParameteri(GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);
		setTexParameteri(GL.GL_TEXTURE_MAG_FILTER,GL.GL_NEAREST);
		setTexParameteri(GL.GL_TEXTURE_WRAP_T,GL.GL_CLAMP);
		setTexParameteri(GL.GL_TEXTURE_WRAP_S,GL.GL_CLAMP);
		gl.glTexImage2D(target,0,internalf,w,h,0,dataf,datat,pixelsbuffer);
	}
	public void enable()
	{
		GL gl=GLU.getCurrentGL();
		gl.glEnable(target);
	}
	public void bind()
	{
		GL gl=GLU.getCurrentGL();
		gl.glBindTexture(target,texobject);
	}
	public void disable()
	{
		GL gl=GLU.getCurrentGL();
		gl.glDisable(target);
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public int getTextureObject()
	{
		return texobject;
	}
	public int getTarget()
	{
		return target;
	}
	public void dispose() throws GLException {
		    GLU.getCurrentGL().glDeleteTextures(1, new int[] {texobject}, 0);
		    texobject = 0;
		  }
	public void setTexParameteri(int name,int paramvalue)
	{
		GLU.getCurrentGL().glTexParameteri(target,name,paramvalue);
	}
	

}
