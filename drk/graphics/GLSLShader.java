package drk.graphics;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import java.util.*;

public class GLSLShader
{
//	int my_fragment_shader_object;
//	int my_fragment_shader_object2;
	int my_program_object;
	Vector<GLSLShaderSection> sections;
//	String shaderProgram;
	int program_object_push;
	
	
	@SuppressWarnings("serial")
	public static class ShaderException extends Exception
	{
		public ShaderException(String s)
		{
			super(s);
		}
	}
	
	public void compileShader() throws ShaderException
	{
		//GLSLShaderSection fssi;
		for(GLSLShaderSection fssi: sections)
		{
			fssi.compileSection();
		}
	}
	
	public void addShaderSection(GLSLShaderSection fss)
	{
		
		sections.add(fss);
	}
	
	public int linkShader() throws ShaderException
	{
		int[] inta=new int[1];
		GL gl=GLU.getCurrentGL();
		my_program_object=gl.glCreateProgramObjectARB();
		//gl.glAttachObjectARB(my_program_object,my_fragment_shader_object);
		
		GLSLShaderSection fssi;
		for(Iterator<GLSLShaderSection> it=sections.iterator();
		   it.hasNext();)
		{
			fssi=it.next();
			if(!fssi.isCompiled()) fssi.compileSection();
			
			inta[0]=1;
			gl.glAttachObjectARB(my_program_object,fssi.my_fragment_shader_object);
			
		}
		
		gl.glLinkProgramARB(my_program_object);
		gl.glGetObjectParameterivARB(my_program_object,GL.GL_OBJECT_LINK_STATUS_ARB,inta,0);
		
		if((inta[0]) == 0)
		{
			byte[] bb=new byte[4096];
			gl.glGetInfoLogARB(my_program_object,4096,null, 0, bb, 0);
			throw new ShaderException("Shader link error:"+new String(bb));
		}
		return 0;
	}
	
	public int applyShader()
	{
		GL gl=GLU.getCurrentGL();
		gl.glUseProgramObjectARB(my_program_object);
		//gl.glGet
		//scalar_uniform=gl.glGetUniformLocationARB(my_program_object,"scale");
		//System.err.println("::"+scalar_uniform);
		return 0;
	}
	public static int applyShader(int i)
	{
		GL gl=GLU.getCurrentGL();
		gl.glUseProgramObjectARB(i);
		return 0;
	}
	
	public int addShaderText(String text)
	{
		//shaderProgram=text;
		sections.add(new GLSLShaderSection(text));
		
		return 0;
	}
	//public int addShaderText
	
	public int getUniformObject(String uniform) throws ShaderException
	{
		GL gl=GLU.getCurrentGL();
		return gl.glGetUniformLocationARB(my_program_object,uniform);
	}
	public int getAttributeObject(String attribute)
	{
		GL gl=GLU.getCurrentGL();
		return gl.glGetAttribLocation(my_program_object, attribute);
	}
	
	public void setUniformFloat(int obj,float f)
	{
		GL gl=GLU.getCurrentGL();
		gl.glUniform1f(obj,f);
	}
	public void setUniformInt(int obj,int f)
	{
		GL gl=GLU.getCurrentGL();
		gl.glUniform1i(obj, f);
	}
	public void setAttributeFloat(int obj,float f)
	{
		GL gl=GLU.getCurrentGL();
		gl.glVertexAttrib1f(obj,f);
	}

	public GLSLShader(GLSLShaderSection source)
	{
		//shaderProgram=source;
		// TODO Auto-generated constructor stub
	}
	public GLSLShader()
	{
		sections=new Vector<GLSLShaderSection>(1);
		//shaderProgram=new String();
	}
}

