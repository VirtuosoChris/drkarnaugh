//guiOverlayItem class
//used to manage and draw an object representing a textured quad overlaid as a HUD item
//in an opengl program. 


package drk.graphics;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import com.sun.opengl.util.texture.*;

public class guiOverlayItem {

public int width; //in pixels
public int height; //in pixels
public int x; public int y; //lower left coordinates
public Texture t;

public float texStartU = 0;
public float texStartV = 0;
public float texSizeU = 1;
public float texSizeV = 1;

public static float r = 1.0f;
public static float g = 1.0f;
public static float b = 1.0f;
public static float opacity = 1.0f;


//the guiOverlayItem class uses a static set of fields for color and opacity--
//I did this instead of having instance variables because I thought it would be useful to 
//be able to draw the same gui element in multiple colors without having to create a new object
//for instance, the jack bauer clock
public static void setGUIColor(float a, float x, float c){
	r = a;
	g = x;
	b = c;
}

public static void setGUIColor(float a, float x, float c, float t){
	setGUIColor(a,x,c);
	opacity = t;
}


//instantiates a new guiOverlayItem.  Doesn't do anything.
public guiOverlayItem(){}


//loads in a bitmap file, uses the data to texture the guiOverlay Item
public void setTexture(String s){
	
	try
		{
		BufferedImage im=ImageIO.read(guiOverlayItem.class.getResource(s));
		t = TextureIO.newTexture(im,true);
		t.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER,GL.GL_LINEAR_MIPMAP_LINEAR);
		t.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
		t.setTexParameteri(GL.GL_TEXTURE_WRAP_S,GL.GL_REPEAT);
		t.setTexParameterf(GL.GL_TEXTURE_WRAP_T,GL.GL_REPEAT);
		//im=ImageIO.read(KarnaughGame.class.getResource("hand.jpg"));
		}catch(Exception e)
		{
			drk.KarnaughLog.log("Failure loading texture map: :"+s+" "+e);
		}
	
	
}


//this sets the texture field as a reference to the texture object of the guiOverlay item
//passed into the method.  Useful for guiOverlays that will share the same texture.  For instance, 
//Bunny, Colon, and all the digits for the jack bauer clock
public void setTexture(guiOverlayItem x){
	this.t = x.t;
}


//sets the position.  The way I envisioned it, in screen coordinates, in pixels.  
public void setPosition(int X, int Y){
	x = X; y = Y;
}



//Methods to set the dimensions of the object.  In my mind, this is in pixels because 
//of my 2d opengl settings for which this class was designed.  
public void setWidth(int w){
	width = w;
}

public void setHeight(int h){
	height = h;
}

public void setSize(int w, int h){
	width = w; height = h;
}



//draws the item at the currently set location
public void draw(GL gl){
	drawAt(x,y,gl);
}


//draws the object at a particular location, ignoring the x and y values
//i thought *this* was useful in the event that you had sprites you wanted 
//to draw multiple times in multiple locations on the screen.  Again, see the Jack Bauer clock
//assumes that drawing mode is already set to opengl 2d mode
public void drawAt(int X, int Y, GL gl){
	
	t.enable();
	
	t.bind();
	
	gl.glBegin(GL.GL_QUADS);
	
	gl.glColor4f(r,g,b,opacity); //sets to the 
		
		    gl.glTexCoord2f(texStartU,texStartV + texSizeV);
		 	gl.glVertex2i(X, Y);	
			
		    gl.glTexCoord2f(texStartU + texSizeU,texStartV + texSizeV);
			gl.glVertex2i(X + width, Y);	
			
		    gl.glTexCoord2f(texStartU + texSizeU,texStartV);
			gl.glVertex2i(X+width,Y+height);	
			
		    gl.glTexCoord2f(texStartU,texStartV);
			gl.glVertex2i(X, Y+ height);
	
	gl.glEnd();
}



}
