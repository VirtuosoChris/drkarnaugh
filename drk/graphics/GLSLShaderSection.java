package graphics;

import graphics.ShaderException;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class GLSLShaderSection
{
	int my_fragment_shader_object;
	String shader_source;
	boolean is_compiled;
	boolean is_vertex;
	
	public GLSLShaderSection()
	{
		is_compiled=false;
		is_vertex=false;
	}
	public GLSLShaderSection(boolean is_v)
	{
		is_compiled=false;
		is_vertex=is_v;
	}
	public void setVertexShader(boolean is_v)
	{
		is_vertex=is_v;
	}
	
	public GLSLShaderSection(String s)
	{
		is_compiled=false;		
		shader_source="#version 120\n"+s;
	}
	public void setSource(String s)
	{
		is_compiled=false;
		shader_source="#version 120\n"+s;
	}
	public void compileSection() throws ShaderException
	{
		GL gl=GLU.getCurrentGL();
		my_fragment_shader_object=gl.glCreateShaderObjectARB
		(
				is_vertex ?
				GL.GL_VERTEX_SHADER_ARB 
				:
				GL.GL_FRAGMENT_SHADER_ARB
		);
		String[] stra=new String[1];
		int[] inta=new int[1];
		inta[0]=shader_source.length();
		stra[0]=shader_source;
		System.err.println("Now Compiling: "+shader_source);
		gl.glShaderSource(my_fragment_shader_object,1,stra,inta,0);
		
		gl.glCompileShaderARB(my_fragment_shader_object);
		gl.glGetObjectParameterivARB(my_fragment_shader_object,GL.GL_OBJECT_COMPILE_STATUS_ARB,inta,0);
		boolean fragerror=false;
		is_compiled=true;

		if((inta[0]) == 0)
		{
			byte[] bb=new byte[4096];
			gl.glGetInfoLogARB(my_fragment_shader_object,4096,null, 0, bb, 0);
			is_compiled=false;
			throw new ShaderException("Shader compile error:"+new String(bb));
		}
		
	}
	public void invalidate()
	{
		is_compiled=false;
	}
	public boolean isCompiled()
	{
		return is_compiled;
	}
}
