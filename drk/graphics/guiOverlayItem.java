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


public static void setGUIColor(float a, float x, float c){
	r = a;
	g = x;
	b = c;
}


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

public void setTexture(guiOverlayItem x){
	this.t = x.t;
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
	
	drawAt(x,y,gl);
	
	
}


public void drawAt(int X, int Y, GL gl){
	
	t.enable();
	
	t.bind();
	
	gl.glBegin(GL.GL_QUADS);
	
	gl.glColor3f(r,g,b);
		
		
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