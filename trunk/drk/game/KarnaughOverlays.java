package drk.game;

import javax.media.opengl.GL;

import drk.Updatable;
import drk.graphics.GLRenderable;
import drk.graphics.GLInitializable;
import drk.graphics.guiOverlayItem;

public class KarnaughOverlays implements Updatable, GLInitializable {

	KarnaughGame kg;

	guiOverlayItem cursor;
	guiOverlayItem interactHand;
	guiOverlayItem wireHand;
	guiOverlayItem cancel;
	
	guiOverlayItem colon;
	guiOverlayItem digits[];
	guiOverlayItem bunny;
	guiOverlayItem cChar;
	guiOverlayItem eChar;
	guiOverlayItem rChar;
	
	public KarnaughOverlays(KarnaughGame tkg)
	{
		kg=tkg;
	}
	
	boolean init=false;
	public boolean isInitialized()
	{
		return init;
	}

	
	public void initialize(GL gl)
	{
		init=true;
		bunny = new guiOverlayItem();
		cursor = new guiOverlayItem();
		colon = new guiOverlayItem();
		interactHand = new guiOverlayItem();
		wireHand = new guiOverlayItem();
		cancel = new guiOverlayItem();
		
		int resWidth = kg.getWidth();
		int resHeight = kg.getHeight();
		
		
		System.out.println("Shit should be drawn in relation to :");
		System.out.println(""+resWidth + "x" + resHeight);
		
		
		int digitWidth = (int)(.03*resWidth);
	//	int upperLeftY = (int)(.99*resHeight);
	//	int upperLeftX = (int)(.01*resWidth);
		
		cursor.setWidth(digitWidth);
		cursor.setHeight(digitWidth);
		cursor.setTexture("hand.png"); 
		cursor.setPosition(resWidth/2 - digitWidth/2, resHeight/2 - digitWidth/2);
		
		cancel.setWidth(digitWidth);
		cancel.setHeight(digitWidth);
		cancel.setTexture("cancel.png"); 
		cancel.setPosition(resWidth/2 - digitWidth/2, resHeight/2 - digitWidth/2);
		
		
		interactHand.setWidth(digitWidth);
		interactHand.setHeight(digitWidth);
		interactHand.setTexture("interact.png"); 
		interactHand.setPosition(resWidth/2 - digitWidth/2, resHeight/2 - digitWidth/2);

		wireHand.setWidth(digitWidth);
		wireHand.setHeight(digitWidth);
		wireHand.setTexture("wire.png"); 
		wireHand.setPosition(resWidth/2 - digitWidth/2, resHeight/2 - digitWidth/2);
	
		colon.setWidth(digitWidth);
		colon.setHeight(digitWidth);
		colon.setTexture("digital.png");
		
		colon.texStartU = .5f;
		colon.texStartV = .5f;
		colon.texSizeU =  .25f;
		colon.texSizeV = .25f;
		colon.setPosition(0,0);
		
		cChar = new guiOverlayItem();
		cChar.setWidth(digitWidth);
		cChar.setHeight(digitWidth);
		cChar.setTexture(colon);
		cChar.texStartU = 0f;
		cChar.texStartV = .75f;
		cChar.texSizeU =  .25f;
		cChar.texSizeV = .25f;	
		
	
		
		eChar = new guiOverlayItem();
		eChar.setWidth(digitWidth);
		eChar.setHeight(digitWidth);
		eChar.setTexture(colon);
		eChar.texStartU = .5f;
		eChar.texStartV = .75f;
		eChar.texSizeU =  .25f;
		eChar.texSizeV = .25f;

		rChar = new guiOverlayItem();
		rChar.setWidth(digitWidth);
		rChar.setHeight(digitWidth);
		rChar.setTexture(colon);
		rChar.texStartU = .25f;
		rChar.texStartV = .75f;
		rChar.texSizeU =  .25f;
		rChar.texSizeV = .25f;
	
		//3,3 and 3,4
		
		bunny.setWidth(digitWidth);
		bunny.setHeight(digitWidth);
		bunny.setTexture(colon);
		bunny.texSizeU = .25f;
		bunny.texSizeV = .25f;
		bunny.texStartU = 11f*.25f;
		bunny.texStartV = .5f;
		bunny.setPosition(0,0);
		
		
		digits = new guiOverlayItem[10];
		for(int i = 0; i < digits.length; i++){
			
	    	digits[i] = new guiOverlayItem();	
			digits[i].texSizeU = .25f;
			digits[i].texSizeV = .25f;
			digits[i].texStartU = (float)(i*.25);
			digits[i].texStartV = (float)(((i)/4)*.25);
		    digits[i].setWidth(digitWidth);
		    digits[i].setHeight(digitWidth);
		    digits[i].setTexture(colon);
		    digits[i].setPosition(0,0);
			
		}
		
	}
	public void update() {
		// TODO Auto-generated method stub

	}

	public void render(GL gl) {
		// TODO Auto-generated method stub
		gl.glMatrixMode(GL.GL_MODELVIEW);
		
		gl.glPushMatrix();
		gl.glLoadIdentity();
		int height=kg.getHeight();
		int width=kg.getWidth();
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPushMatrix();
		
		gl.glLoadIdentity();
		gl.glEnable (GL.GL_BLEND); gl.glBlendFunc (GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glOrtho(0, width, 0, height, -1.0, 1.0);
		
		
		
		//You should like, totally draw your gui elements here
		 
	    guiOverlayItem.setGUIColor(1f,1f,1f);
	    
	    
	 	cursor.draw(gl);
	 	
	 	guiOverlayItem.setGUIColor(0,1f,0);
	 	
	    String scoreString = "5c0re:"+kg.Score;
	    
	    for(int i = 0; i < scoreString.length(); i++){
	    	
	    	char a =scoreString.charAt(i);
	    	int b = 0;
	    	
	    	if(a == ':'){
	    		
	    	
	    		
	    		colon.drawAt(0+i*colon.width,0,gl);
	    	}
	    	else if(a == 'c')
	    		cChar.drawAt(0+i*cChar.width,0,gl);
	    	
	    	
	    	else if(a == 'r')
	    		rChar.drawAt(0+i*rChar.width,0,gl);
	    		
	    	
	    	else if(a == 'e')
	    		eChar.drawAt(0+i*eChar.width,0,gl);
	    	
	    	else {
	    	b = Integer.valueOf(""+a);
	    	digits[b].drawAt(0+i*digits[b].width,0,gl);
	    	}
	    	
	    }

		
		String bauerClock = "";
			
	    if(kg.minutesLeft() < 10)bauerClock+="0";
		 bauerClock += kg.minutesLeft()+":";
		if(kg.secondsLeft()< 10)bauerClock+="0";
		bauerClock+= kg.secondsLeft();
		
	 	
	    if(kg.minutesLeft()==0 && kg.secondsLeft() <=30){
	    	guiOverlayItem.setGUIColor(1f,0f,0f);}
	    
	    for(int i = 0; i < bauerClock.length(); i++){
	    	
	    	char a = bauerClock.charAt(i);
	    	int b = 0;
	    	
	    	if(a == ':'){
	    		
	    		if(kg.secondsLeft() == 0 && kg.minutesLeft() == 0){
	    			guiOverlayItem.setGUIColor(1f,0f,0f);
	    		}
	    		colon.drawAt(0+i*colon.width,height-colon.height,gl);}
	    	else if (kg.secondsLeft() > 0 || kg.minutesLeft() > 0){
	    	
	    	b = Integer.valueOf(""+a);
	    	digits[b].drawAt(0+i*digits[b].width,height-digits[b].height,gl);
	    	}else{
	    		guiOverlayItem.setGUIColor(1f,1f,1f);
	    		bunny.drawAt(0+i*bunny.width,height-bunny.height,gl);
	    		
	    	}
	    	
	    }
	    
	    
	    guiOverlayItem.setGUIColor(1f,1f,1f);
	    
	    
	  
	    
	    //you should like, totally STOP drawing them here
	
		gl.glDisable (GL.GL_BLEND);
		gl.glPopMatrix();
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		
		gl.glPopMatrix();
		
	}

}
