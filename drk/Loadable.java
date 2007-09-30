package drk;

public interface Loadable
{
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
	
	public void load() throws LoadException;
}
