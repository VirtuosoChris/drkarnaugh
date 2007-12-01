//RankedGame class -- manages the ranked campaign for Dr. Karnaugh's Lab.  
//It's a little different than KarnaughGame because when it's done the map files will be
//in the binary instead of text files, its a final class, and it contains all the high score 
//table stuff.  Finally, it's the one part of the game for which we will not release the source code.

//todo:
//stop "refresh" bug -- save random to the database.  
//saving high score data to a file when unable to connect
//manage a local high score table
//make hash function a private method of KarnaughGame. 

package drk.game;

import drk.KarnaughLog;
import java.net.*;
import java.io.*;
import java.util.*;
 
public final class RankedGame extends KarnaughGame {

public static final String site = "http://soapforge.com/rbs/KarnaughGame/";//to be changed
public static final int MAXINPUTLENGTH = 25;

//used for verifying whether or not the php script for data retrieval is functioning properly
public static final String ERROR = "ERROR:";
public static final String SUCCESS = "SUCCESS:";


//returns a fixed length string of size 50.  each character has a different function associated with it.
//Hash key is returned in only alphanumeric characters, that is A-Z, a-z, and 0-9 characters
 static String hash(int score, String name, int rand){
	
	char[] hash = new char[50]; 
		
	int valTmp =  ((((name.charAt(0) + name.charAt(name.length()-1) + name.charAt(score%name.length())))));
	
	switch(valTmp%3){
			
		case 0:
			hash[0] = (char)((valTmp%10)+48);
			break;
		case 1:
			hash[0] = (char)((valTmp%26) + 65);
			break;
		case 2:	
			hash[0] = (char)((valTmp%26) + 97);
			break;
		default: hash[0] = 0;break;
			
		}
	
	
	//System.out.print(hash[0]);
	
	for(int i = 1; i < 50; i++){
		
		int val = 0;
		
		if(i % 2 == 0){
		
	//	System.out.println("val1 " + (score*score));
	//	System.out.println("val2 " + (int)hash[i-1]);
		
		
		val = score*score + hash[i-1] * i / 11 ;
		}
		else{
		
	//	System.out.println("\nvalA:"+(int)hash[rand%i]);
	//	System.out.println("valB:"+(int)hash[i-1]);
	//	System.out.println("valC:" + name.length());
		
		val = ((hash[rand % i ] * hash[i-1])*7) + name.length();
		
	//	System.out.println("Result:" + val);
		
		
		}
		
		
		//System.out.println(val);
		
		//make sure that our final character is only in alphanumerics
		
		switch(val%3){
			
		case 0:
			hash[i] = (char)((val%10)+48);
			break;
		case 1:
			hash[i] = (char)((val%26) + 65);
			break;
		case 2:	
			hash[i] = (char)((val%26) + 97);
			break;
		default: hash[i] = 0;break;
			
		}
		
		
	//	System.out.println("LHashVal of "+val+" = "+hash[i]);
		
		
		//hash[i] = (char)((val%94)+33);
		
	}	
		
	return new String(hash);
}


//returns a 2d array with the top 10 scores
//this is to be used to display the top 10 in a swing menu
//returns null and logs an error if a problem occurs.  
public static String[][] getHighScores(){
	
	InputStream is = null;
	
	try{
		is = new URL(""+site+"/highScores.php?html=no").openStream();
	}catch(Exception e){KarnaughLog.log("Could not connect to the high scores table.  Please try again later");
						return null;
	}
	
	Scanner s = new Scanner(is);
	
	//the problem now is that the strings from the input stream are read in until a space
	//there is no good way to do this without simply removing spaces from the user name... or forbidding them from having a number in their name
	//which i don't want to do.
	//so i'll simply append a string of length greater than the user can input and test for that case, in all alpha characters
	//we need to be absolutely sure that the user cannot enter a string of this length. In html this is easy, i dont recall about Swing/
	//either way it goes into the data verification part of the php script 
	//As you see below, score won't be but it doesn't matter anyways as we also test for alphabetical chars
	
	//scans for success or error connecting to the database
	
	String flag = s.next();
	
	if(!flag.equals(SUCCESS)){
			
		while(s.hasNext()){
			flag+= s.next();
		}	
			
		KarnaughLog.log(flag);
	
		return null;
	}
	
	
	
	String table[][] = new String[10][2];//storage for the top ten

	for(int q = 0; q < table.length;q++){
		for(int p = 0; p < table[q].length;p++){
			table[q][p] = "";
		}
	}
	
	int i = 0;
	
	while(i < 10 && s.hasNext()){
		
		String snx = null;
		
		while((snx = s.next()).length() < MAXINPUTLENGTH || Character.isDigit((snx.charAt(0)))){
			table[i][0]+=snx+" ";
		
		}
		
		table[i][1] = s.next();
		
		i++;
		
	}
	
	return table;
	
}



//posts the user's score to the table, and retrieves the string telling the user how they placed
static String postHighScore(int score, String name,int r, String hash){
	
	if(name.length() > MAXINPUTLENGTH)return "Could not post high score : input name string is invalid";
	
	String stx = "";
	
	InputStream is = null;
	
	//int r = new Random().nextInt()%256;
	//if(r < 0)r*=-1;
	
	try{
	  is = new URL(site + "/submitScore.php?name="+name+"&score="+score+ "&r="+r+"&hash="+hash).openStream();
	}catch(Exception e){
		String rs = "Could not connect to the database.  Please try again later";
		KarnaughLog.log(rs);
		return rs;
	}
	
	Scanner s = new Scanner(is);
	while(s.hasNext()){
		stx+=s.next()+" ";
	}
	
	
	return stx;
}


public static void main(String args[]){
	
	Random r = new Random();
		
	String name = "test";
	
	int score = 0;
String hv = null;
	
	
	
	for(int i = 0; i < 100; i++){
	
	
	score = i;
int rand = r.nextInt()%255;
	if(rand < 0) rand = -rand;
	
	hv = hash(score, name, rand);
	
	//hv = hash(score, name, rand));
	
	System.out.println(postHighScore(score, name, rand, hv));
	
	}
}


}
