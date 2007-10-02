package drk;

import drk.maze.*;`
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;


class KarnaughException extends Exception{
	public KarnaughException(String s){
		super(s);
	}
}


public class KarnaughGame{

	//i feel this resolution is the best balance between image quality and
	//compatibility with older monitors/video drivers.  We can use main's args[] to allow users to set a custom
	//resolution eventually, but for now thats low on the TODO priority
	private static int resWidth  = 1024;
	private static int resHeight = 768;
	
	//point of entry
	public static void main(String args[]){
		
		
	  	KarnaughGame.clearLog(); //we want a fresh log every time we run the program
		
		log("Starting Dr. Karnaugh's Lab");
		
		GraphicsEnvironment ge = null;
		GraphicsDevice gd = null;
		
		//attempt to get the graphics device in order to set the application to a fullscreen window
		//non-windowed runs are not currently supported, but would be easy to add if we're feeling ambitious
		try{
		  ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		  
		  if(ge == null){
		  	throw new KarnaughException("graphics environment returned null");
		  }
		  
		  gd = ge.getDefaultScreenDevice();
		  
		  if(gd == null){
		  	throw new KarnaughException("graphics device returned null");
		  }
		   
		}catch(Exception e){
			log(e);
			log("Could not get the graphics device.  Program will now close");
			return;
		}
		
		log("Fullscreen available = " + gd.isFullScreenSupported());
		
	  //The window object	
      JFrame j = new JFrame();
   		j.setSize(resWidth,resHeight);
	 	j.setLayout(new FlowLayout());
	    j.setResizable(false);
	    
	    //*****
	    //j.setUndecorated(true); //removes the title bar and borders from the window.  commented out until we have a "quit" button
	  	//*****
	  	j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
	  gd.setFullScreenWindow(j);
	  
	  KarnaughMaze k = KarnaughMaze.loadMaze("test");
	  
	  if(k == null){
		KarnaughGame.log("Could not load test level.  Program shutting down");
		System.exit(0);	  	
	  }else {
	  	KarnaughGame.log("Map loaded successfully, game loop may begin");
	  }
	  
	  
	  
   }//end main	
	
}//end class