//a class to write messages to a log file

package drk;
import java.io.*;


public class KarnaughLog extends Object{

private static final File LOG = new File("log.txt");

public static final boolean enabled = true;

	
	//clears the log
	public static void clearLog(){
		
		try{
    		FileWriter f = new FileWriter(LOG);
    		if(f == null)throw new Exception("Could Not Open LOG");
    		f.close();
    	}catch(Exception ex){
    		System.out.println("Could not write to log file :"+ex);
    	}
	}

	
	//writes something to the log and displays to stdout 
	public static void log(String e){
    	
    	if(!enabled)return;
    	
    	try{
    		FileWriter f = new FileWriter(LOG,true);
    		
    		if(f == null)throw new Exception("Could Not Open LOG");
    		
    		f.write(e);
    		f.write("\n");
    		
    		f.close();
    	}catch(Exception ex){
    		System.out.println("Could not write to log file");
    	}
    	
    
    	
    	
    	System.out.println(e);
	}
	
	public static final boolean debugmode=true;
	public static void debug(String s,boolean deb)
	{
		if(debugmode && deb)
			System.err.println(s);
	}
	
	
	//an overloaded version of log that writes an exception
	public static void log(Exception e){
		log(e.toString());
	}
	
}