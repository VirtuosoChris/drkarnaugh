package drk.game;
import java.io.*;

@SuppressWarnings("serial")
public class SaveLoad extends KarnaughGame implements Serializable{
	
	protected KarnaughGame karGame;
	
	public void loadgame (String filename){
		if(filename.length() > 0){
			if (filename.indexOf(".karfig") < 1){
				filename = filename.concat(".karfig");
			}
		
			try{
				FileInputStream fis = new FileInputStream(filename);
				ObjectInputStream ois = new ObjectInputStream(fis);
				karGame = (KarnaughGame)(ois.readObject());
				ois.close();
				fis.close();
			}
			catch(Exception e){
				e.printStackTrace ();
			}
		}
	}
 
	public void savegame (String filename){
		if (filename.length() > 0){
			if (filename.indexOf(".karfig") < 1){
				filename = filename.concat(".karfig");
			}
		
			try{
				FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(karGame);
				oos.close();
				fos.close();
			}
			catch(Exception e){
				e.printStackTrace ();
			}
		}
	}
	
	public static void main(String args[]){
			
	}
	
}