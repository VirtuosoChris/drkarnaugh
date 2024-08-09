package drk.game;

import drk.DeltaTimer;
import drk.Updatable;
import drk.graphics.*;
import drk.maze.*;
import java.awt.event.*;
import javax.media.opengl.*;

//class that allows the user to generate 
public class MazeGame extends GLRenderedGraphicsListener implements KeyListener, MouseListener, Updatable, GLInitializable, GLRenderable, MouseMotionListener {
    //refers generically to whatever key is bound to these actions, not the arrow keys particularly	

    public RenderableMaze m;
    public MazeCamera ec;

    public boolean isInitialized() {
        return true;
    }

    public MazeGame() {
        super(new DebugMazeCamera());
        //m=new RenderableMaze(100,50); //TEMPORARY CONSTRUCTOR

        ec = (MazeCamera) this.camera;
        ec.setMazeGame(this);

        //m.setDeltaTimer(this.frameTimer);
    }

    public void init(GLAutoDrawable a) {
        GL gl = a.getGL();
        frameTimer.update();
        initialize(gl);
        m.setDeltaTimer(frameTimer);

    }

    int numFrames = 0;
    double timePassed = 0.0;

    public void display(GLAutoDrawable a) {
        frameTimer.update();

        timePassed += frameTimer.getDeltaTimeSeconds();
        numFrames++;
        if (timePassed > 1.0) {
            System.err.println("AverageFramerate: "
                    + DeltaTimer.getMicrosecondsPerFrame(timePassed, (double) numFrames)
                    + " uspf, "
                    + DeltaTimer.getFramesPerSecond(timePassed, (double) numFrames) + "fps");
            timePassed = 0.0;
            numFrames = 0;
        }
        //camera.render()
        render(a.getGL());
        update();

    }

    public void initialize(GL gl) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClearDepth(1.0f);							// Depth Buffer Setup
        gl.glEnable(GL.GL_DEPTH_TEST);						// Enables Depth Testing
        gl.glDepthFunc(GL.GL_LEQUAL);							// The Type Of Depth Test To Do

        camera.fovy = 50.0;
        ec.initialize(gl);

        //gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE);
        //gl.glEnable(GL.GL_BLEND);
        /*ec.Position.eplus(new Vector3D(0.0,30.0,0.0));
		
		ec.xrotation = -60.0;*/
    }

    public void render(GL gl) {
        gl.glEnable(GL.GL_MULTISAMPLE);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        //camera.Position.y=0.5;
        camera.render(gl);
        m.setCamera(ec);
        m.render(gl);

        /*	gl.glColor3ub((byte)0x00,(byte)0xCC,(byte)0xFF);
		gl.glBegin(GL.GL_QUADS);
		{
			gl.glVertex3f(1.0f,-1.0f,1.0f);
			gl.glVertex3f(-1.0f,-1,1.0f);
			gl.glVertex3f(-1.0f,1.0f,1.0f);
			gl.glVertex3f(1.0f,1.0f,1.0f);
			
			gl.glVertex3f(1.0f,-1.0f,-1.0f);
			gl.glVertex3f(-1.0f,-1,-1.0f);
			gl.glVertex3f(-1.0f,1.0f,-1.0f);
			gl.glVertex3f(1.0f,1.0f,-1.0f);
			
			//gl.glVertex3f()
		}
		gl.glEnd();*/
    }

    public void update() {
        ec.update();
        //check for key presses and update camera accordingly

    }

    public void frameClose() {
        jf.dispose();
    }

    public void frameVisible(int x) {
        if (x == 0) {
            super.jf.setVisible(false); 
        }else {
            super.jf.setVisible(true);
        }
    }

    public static void main(String[] argv) {
        MazeGame tgl = new MazeGame();
        tgl.camera.fovy = 30.0;

        tgl.doMain(640, 480, null, true);

    }

}
