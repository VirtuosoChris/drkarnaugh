package drk;
//Steve:Represents an object that can be loaded from a file or from something else

public interface Loadable
{
//Comment
	public static class LoadException extends Exception
	{

		/**
		 * 
		 */
		private static final long	serialVersionUID	= 6833656535683758411L;

		public LoadException()
		{
			super("LoadException");
			// TODO Auto-generated constructor stub
		}

		public LoadException(String message)
		{
			super("LoadException:"+message);
			// TODO Auto-generated constructor stub
		}

		public LoadException(String message, Throwable cause)
		{
			super("LoadException:"+message, cause);
			// TODO Auto-generated constructor stub
		}

		public LoadException(Throwable cause)
		{
			super("LoadException:",cause);
			// TODO Auto-generated constructor stub
		}

	}

	public boolean isLoaded=false;
	//attempt data load;
	public void load() throws LoadException;
}
