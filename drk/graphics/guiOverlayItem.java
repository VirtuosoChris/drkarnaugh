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

Texture t;


public guiOverlayItem(){	
}

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


public void setPosition(int X, int Y){
	x = X; y = Y;
}

public void setWidth(int w){
	width = w;
}

public void setHeight(int h){
	height = h;
}

public void setSize(int w, int h){
	width = w; height = h;
}

//assumes that drawing mode is already set to 2d
public void draw(GL gl){
	
	t.enable();
	
	t.bind();
	
	gl.glBegin(GL.GL_QUADS);
	
	gl.glColor3f(1,1,1);
		
		
		    gl.glTexCoord2f(0,1);
		 	gl.glVertex2i(x, y);
			
			
		    gl.glTexCoord2f(1,1);
			gl.glVertex2i(x + width, y);
			
			
		    gl.glTexCoord2f(1,0);
			gl.glVertex2i(x+width,y+height);
			
			
		    gl.glTexCoord2f(0,0);
			gl.glVertex2i(x, y+ height);
	
	gl.glEnd();
	
	
}


public void drawAt(int X, int Y, GL gl){
	
	t.enable();
	
	t.bind();
	
	gl.glBegin(GL.GL_QUADS);
	
	gl.glColor3f(1,1,1);
		
		
		    gl.glTexCoord2f(0,1);
		 	gl.glVertex2i(X+x, Y+y);
			
			
		    gl.glTexCoord2f(1,1);
			gl.glVertex2i(X+x + width, Y+y);
			
			
		    gl.glTexCoord2f(1,0);
			gl.glVertex2i(X+x+width,Y+y+height);
			
			
		    gl.glTexCoord2f(0,0);
			gl.glVertex2i(X+x, Y+y+ height);
	
	gl.glEnd();
	
	
}



}
