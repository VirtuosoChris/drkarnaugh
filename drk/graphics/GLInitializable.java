package drk.graphics;
import javax.media.opengl.GL;

public interface GLInitializable extends GLRenderable
{
	public class GLInitializeException extends Exception
	{

		/**
		 * 
		 */
		private static final long	serialVersionUID	= 3468097630200547843L;

		public GLInitializeException()
		{
			super("GLInitializeException:");
			// TODO Auto-generated constructor stub
		}

		public GLInitializeException(String message)
		{
			super("GLInitializeException:"+message);
			// TODO Auto-generated constructor stub
		}

		public GLInitializeException(String message, Throwable cause)
		{
			super("GLInitializeException:"+message, cause);
			// TODO Auto-generated constructor stub
		}

		public GLInitializeException(Throwable cause)
		{
			super("GLInitializeException:",cause);
			// TODO Auto-generated constructor stub
		}

	}

	//public boolean setInitialized(boolean si);
	public boolean isInitialized();
	public void initialize(GL gl);
	
}
