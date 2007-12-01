package drk.game;
import java.io.*;

public class SaveLoad extends KarnaughGame{
	
	public static void loadgame(String filename){
		if(filename.length() > 0){
			filename = filename.concat(".karfig");
					
			try{
				FileInputStream fis = new FileInputStream(filename);
				DataInputStream dis = new DataInputStream(fis);
				
				tempTime = dis.readLong();
				tempScore = dis.readInt();
				//tempmapName = dis.readBytes();
				
				dis.close();
				fis.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
 
	public static void savegame(String filename, long T, int S, String MN){
		if(filename.length() > 0){
			filename = filename.concat(".karfig");
		
			try{
				FileOutputStream fos = new FileOutputStream(filename);
				DataOutputStream dos = new DataOutputStream(fos);
			 	dos.writeLong(T);
			 	dos.writeInt(S);
			 	//dos.writeBytes(MN);
			 	
				dos.close();
				fos.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]){
			
	}
	
}