package drk.graphics;
import javax.media.opengl.*;




import javax.media.opengl.glu.GLU;

import drk.KarnaughLog;
public class FrameBufferObject
{
	int fbo_handle;
	hdrTexture tex;
	
	public static int getCurrentBoundFBO()
	{
		GL gl=GLU.getCurrentGL();
		int[] tm=new int[1];
		gl.glGetIntegerv(GL.GL_FRAMEBUFFER_BINDING_EXT,tm,0);
		return tm[0]; 
	}
	public FrameBufferObject()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public void genObject(int width,int height,int format,int datatype)
	{
		GL gl=GLU.getCurrentGL();
		tex=new hdrTexture();
		tex.createTexture(GL.GL_TEXTURE_RECTANGLE_ARB,format,width,height,GL.GL_RGB,datatype,null);
		//tex.createTexture
	
	//	glGenTextures(1,&color_tex);
//		initialize textures
		/*glBindTexture(GL_TEXTURE_2D,color_tex);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGB,512,512,0,GL_RGB,GL_UNSIGNED_BYTE,0);*/

//(1, &depth_rb);
//		initialize depth renderbuffer
//		glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depth_rb);
//		glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_COMPONENT24, 512, 512);

		int[] tm=new int[1];
		gl.glGenFramebuffersEXT(1, tm,0);
//		initialize frame buffers 
		gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, tm[0]); 
		gl.glFramebufferTexture2DEXT(GL.GL_FRAMEBUFFER_EXT,GL.GL_COLOR_ATTACHMENT0_EXT,tex.getTarget(),tex.getTextureObject(), 0);
		fbo_handle=tm[0];
		int status = gl.glCheckFramebufferStatusEXT(GL.GL_FRAMEBUFFER_EXT);
		System.err.println(fbo_error(status));
		//gl.glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, depth_rb);
		               
		//gl.glFramebufferTex
		//char str[255];
		//sprintf(str,"Error: %Xh",glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT));
		//MessageBox(0,str,"Message",MB_OK);

		//int []tmp= new int[1];
		//gl.glGenFramebuffersEXT(1, tmp,0);
		//fbo_handle=tmp[0];
	}
	/*public void attach_texture(Texture t)
	{
		tex=t;
		GL gl=GLU.getCurrentGL();
		this.bind();
		gl.glFramebufferTexture2DEXT(GL.GL_FRAMEBUFFER_EXT,GL.GL_COLOR_ATTACHMENT0_EXT, t.getTarget(), t.getTextureObject(), 0);	
	}
	public void deleteObject(GL gl)
	{
		int[]tmp=new int[1];
		tmp[0]=fbo_handle;
		gl.glDeleteFramebuffersEXT(1,tmp,0);		
	}*/
	public void genObject(hdrTexture t)
	{
		tex=t;
		tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER,GL.GL_NEAREST);
		tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER,GL.GL_NEAREST);
		tex.setTexParameteri(GL.GL_TEXTURE_WRAP_T,GL.GL_CLAMP_TO_EDGE);
		tex.setTexParameteri(GL.GL_TEXTURE_WRAP_S,GL.GL_CLAMP_TO_EDGE);
		
		GL gl=GLU.getCurrentGL();
		int[] tm=new int[1];
		gl.glGenFramebuffersEXT(1, tm,0);
//		initialize frame buffers 
		gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, tm[0]); 
		gl.glFramebufferTexture2DEXT(GL.GL_FRAMEBUFFER_EXT,GL.GL_COLOR_ATTACHMENT0_EXT,tex.getTarget(),tex.getTextureObject(), 0);
		fbo_handle=tm[0];
		int status = gl.glCheckFramebufferStatusEXT(GL.GL_FRAMEBUFFER_EXT);
		System.err.println(fbo_error(status));
	
	}
	public int getFboHandle()
	{
		return fbo_handle;
	}
	public int getHeight()
	{
		return tex.getHeight();
	}
	public int getWidth()
	{
		return tex.getWidth();
	}
	public hdrTexture getTexture()
	{
		return tex;
	}
	String fbo_error(int error)
	{
		switch(error)
		{
			case GL.GL_FRAMEBUFFER_COMPLETE_EXT:
				return "GL.GL_FRAMEBUFFER_COMPLETE_EXT";
				//break;
			case GL.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT:
				return "GL.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT";
				//break;
			case GL.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT:
				return "GL.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT";
				//break;
			case GL.GL_FRAMEBUFFER_INCOMPLETE_DUPLICATE_ATTACHMENT_EXT:
				return "GL.GL_FRAMEBUFFER_INCOMPLETE_DUPLICATE_ATTACHMENT_EXT";
				//break;
			case GL.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT:
				return "GL.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT";
				//break;
			case GL.GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT:
				return "GL.GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT";
				//break;
			case GL.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT:
				return "GL.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT";
				//break;
			case GL.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT:
				return "GL.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT";
				//break;
			case GL.GL_FRAMEBUFFER_UNSUPPORTED_EXT:
				return "GL.GL_FRAMEBUFFER_UNSUPPORTED_EXT";
				//break;
			default:
				return "GL_UNKNOWN";
				//break;
		}
		
	}
	int pushbf;
	public void bind()
	{
		GL gl=GLU.getCurrentGL();
		pushbf=FrameBufferObject.getCurrentBoundFBO();
		gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, fbo_handle);
		gl.glDrawBuffer(GL.GL_COLOR_ATTACHMENT0_EXT);
		
		//glPolygonMode(GL_FRONT,GL_FILL);
		int status = gl.glCheckFramebufferStatusEXT(GL.GL_FRAMEBUFFER_EXT);
		if(status != GL.GL_FRAMEBUFFER_COMPLETE_EXT)
		{
			KarnaughLog.log(fbo_error(status));
		}
		//System.err.println(fbo_error(status));
	//	gl.glPushAttrib(GL.GL_VIEWPORT_BIT);
	//	gl.glViewport(0,0,tex.getWidth(),tex.getHeight());
		
	}
	public void unbind(int i)
	{
		GL gl=GLU.getCurrentGL();
	//	gl.glPopAttrib();
		gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT,i);
		
	}
	public void unbind()
	{
		unbind(pushbf);
	}
	//public void 

}
