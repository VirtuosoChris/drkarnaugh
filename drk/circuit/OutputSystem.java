package drk.circuit;

import java.io.*;
import drk.maze.MazeItem;
import drk.game.KarnaughGame;
import drk.graphics.game.HorrorWallMazeGeometry;

import com.sun.opengl.util.texture.*;

import javax.imageio.ImageIO;
import javax.media.opengl.*;

import java.awt.image.*;
import drk.Vector3D;

public abstract class OutputSystem extends MazeItem {

    public static int NormalMapMinFilter = GL.GL_LINEAR_MIPMAP_LINEAR;
    public static int NormalMapMagFIlter = GL.GL_LINEAR;

    public abstract boolean evaluate();

    public abstract int getNumInputs();

    public abstract OutputSystem getInput(int i);

    public abstract OutputSystem setInput(OutputSystem os, int i);
    public String type = "outputSystem";

    public String toString() {
        boolean e = evaluate();
        if (e) {
            return "1";
        } else {
            return "0";
        }
    }

    public static void printTruthTable(PrintStream out, LogicInput[] lin, OutputSystem root) {
        int i, j;
        for (j = lin.length - 1; j >= 0; j--) {
            System.out.print(j + " ");
        }
        System.out.println("| r");
        for (j = lin.length - 1; j >= 0; j--) {
            System.out.print("--");
        }
        System.out.println("---");
        for (i = 0; i < (1 << lin.length); i++) {
            for (j = 0; j < lin.length; j++) {
                lin[j].setValue(((i >> j) & 0x01) == 1);
            }
            for (j = lin.length - 1; j >= 0; j--) {
                System.out.print(lin[j] + " ");
            }
            System.out.println("| " + root);
        }
    }

    boolean init = false;
    Texture metal;
    Texture metalsurf;

    void initialize(GL gl) {
        try {
            BufferedImage im = ImageIO.read(OutputSystem.class.getResource("metal.jpg"));
            metal = TextureIO.newTexture(im, true);
            metal.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);
            metal.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
            metal.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            metal.setTexParameterf(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
            gl.glActiveTexture(GL.GL_TEXTURE1);
            gl.glClientActiveTexture(GL.GL_TEXTURE1);
            im = ImageIO.read(OutputSystem.class.getResource("metalsurf.png"));
            metalsurf = TextureIO.newTexture(im, true);
            metalsurf.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, NormalMapMinFilter);
            metalsurf.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, NormalMapMagFIlter);
            metalsurf.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            metalsurf.setTexParameterf(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
            gl.glActiveTexture(GL.GL_TEXTURE0);
            gl.glClientActiveTexture(GL.GL_TEXTURE0);
        } catch (Exception e) {
            System.err.println("The output texure failed");
            drk.KarnaughLog.log(e);
        }

    }
    static final float CWIDTH = 1.0f;

    public void render(GL gl) {
        if (!init) {
            initialize(gl);
            init = true;
        }

        gl.glActiveTexture(GL.GL_TEXTURE1);
        metalsurf.enable();
        metalsurf.bind();
        gl.glActiveTexture(GL.GL_TEXTURE0);
        metal.enable();
        metal.bind();

        Vector3D Tangent = Vector3D.tmpv;

        gl.glBegin(GL.GL_QUADS);
        {
            gl.glNormal3f(0.0f, 1.0f, 0.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(0.0, 1.0, 0.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 0.0f, 0.0f);
            gl.glVertex3f(-CWIDTH, CWIDTH * .5f, -CWIDTH);

            gl.glNormal3f(0.0f, 1.0f, 0.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(0.0, 1.0, 0.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 0.0f, 1.0f);
            gl.glVertex3f(-CWIDTH, CWIDTH * .5f, CWIDTH);

            gl.glNormal3f(0.0f, 1.0f, 0.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(0.0, 1.0, 0.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 1.0f, 1.0f);
            gl.glVertex3f(CWIDTH, CWIDTH * .5f, CWIDTH);

            gl.glNormal3f(0.0f, 1.0f, 0.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(0.0, 1.0, 0.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 1.0f, 0.0f);
            gl.glVertex3f(CWIDTH, CWIDTH * .5f, -CWIDTH);

            gl.glNormal3f(0.0f, 0.0f, 1.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(0.0, 0.0, 1.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 0.0f, 0.0f);
            gl.glVertex3f(-CWIDTH, CWIDTH * .5f, CWIDTH);

            gl.glNormal3f(0.0f, 0.0f, 1.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(0.0, 0.0, 1.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 0.0f, 0.5f);
            gl.glVertex3f(-CWIDTH, 0.0f, CWIDTH);

            gl.glNormal3f(0.0f, 0.0f, 1.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(0.0, 0.0, 1.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 1.0f, 0.5f);
            gl.glVertex3f(CWIDTH, 0.0f, CWIDTH);

            gl.glNormal3f(0.0f, 0.0f, 1.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(0.0, 0.0, 1.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 1.0f, 0.0f);
            gl.glVertex3f(CWIDTH, CWIDTH * .5f, CWIDTH);

            gl.glNormal3f(0.0f, 0.0f, -1.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(0.0, 0.0, -1.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 1.0f, 0.0f);
            gl.glVertex3f(CWIDTH, CWIDTH * .5f, -CWIDTH);

            gl.glNormal3f(0.0f, 0.0f, -1.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(0.0, 0.0, -1.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 1.0f, 0.5f);
            gl.glVertex3f(CWIDTH, 0.0f, -CWIDTH);

            gl.glNormal3f(0.0f, 0.0f, -1.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(0.0, 0.0, -1.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 0.0f, 0.5f);
            gl.glVertex3f(-CWIDTH, 0.0f, -CWIDTH);

            gl.glNormal3f(0.0f, 0.0f, -1.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(0.0, 0.0, -1.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 0.0f, 0.0f);
            gl.glVertex3f(-CWIDTH, CWIDTH * .5f, -CWIDTH);

            gl.glNormal3f(1.0f, 0.0f, 0.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(1.0, 0.0, 0.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 1.0f, 0.0f);
            gl.glVertex3f(CWIDTH, CWIDTH * .5f, CWIDTH);

            gl.glNormal3f(1.0f, 0.0f, 0.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(1.0, 0.0, 0.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 1.0f, 0.5f);
            gl.glVertex3f(CWIDTH, 0.0f, CWIDTH);

            gl.glNormal3f(1.0f, 0.0f, 0.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(1.0, 0.0, 0.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 0.0f, 0.5f);
            gl.glVertex3f(CWIDTH, 0.0f, -CWIDTH);

            gl.glNormal3f(1.0f, 0.0f, 0.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(1.0, 0.0, 0.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 0.0f, 0.0f);
            gl.glVertex3f(CWIDTH, CWIDTH * .5f, -CWIDTH);

            gl.glNormal3f(-1.0f, 0.0f, 0.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(-1.0, 0.0, 0.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 0.0f, 0.0f);
            gl.glVertex3f(-CWIDTH, CWIDTH * .5f, -CWIDTH);

            gl.glNormal3f(-1.0f, 0.0f, 0.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(-1.0, 0.0, 0.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 0.0f, 0.5f);
            gl.glVertex3f(-CWIDTH, 0.0f, -CWIDTH);

            gl.glNormal3f(-1.0f, 0.0f, 0.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(-1.0, 0.0, 0.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 1.0f, 0.5f);
            gl.glVertex3f(-CWIDTH, 0.0f, CWIDTH);

            gl.glNormal3f(-1.0f, 0.0f, 0.0f);
            Tangent = HorrorWallMazeGeometry.getTangent(new Vector3D(-1.0, 0.0, 0.0));
            gl.glMultiTexCoord3f(GL.GL_TEXTURE1, (float) Tangent.x, (float) Tangent.y, (float) Tangent.z);
            gl.glMultiTexCoord2f(GL.GL_TEXTURE0, 1.0f, 0.0f);
            gl.glVertex3f(-CWIDTH, CWIDTH * .5f, CWIDTH);
        }
        gl.glEnd();
        //super.render(gl);

    }

    //when the object is highlighted, do this stuff
    public void onMazeItemHighlighted(KarnaughGame k) {
        super.onMazeItemHighlighted(k);

        //if the user has a wire and they click, attach to appropriate input
        //if they click without one, this is the new source for them to drag a wire
        //adjusts cursors appropriately
        //updates tutorial hints appropriately
        boolean noCycle = true;

        if (k.inputSource != null) {
            noCycle = ((OutputSystem) (k.inputSource)).noLoop(this);
        }

        if (!k.hasWire) {

            k.updateInfo("Click to attach a wire to the " + type + " output");
        } else {
            if (k.inputSource != this) {

                String leftTaken = "free";
                String rightTaken = "free";

                if (getInput(0) != null) {
                    leftTaken = "taken";
                }
                if (getInput(1) != null) {
                    rightTaken = "taken";
                }

                k.updateInfo("Left or Right click to connect wire to L or R input. L is " + leftTaken + ", R is " + rightTaken);
            }
        }

        if (k.leftClick || k.rightClick) {
            if (!k.hasWire) {
                k.hasWire = true;

                k.inputSource = this;
                //k.overlays.currentCursor = k.overlays.wireHand;
            } else if (noCycle) { //if adding the component will not cause a cycle
                //attach wire
                if (k.inputSource != this) {

                    Wire w = new Wire();

                    int inputHand = 0;
                    if (k.rightClick) {
                        inputHand = 1;
                    }

                    w.setInput((OutputSystem) k.inputSource, 0);

                    this.setInput(w, inputHand);

//			k.wires.add(w);
                    //	k.overlays.currentCursor = k.overlays.cursor;
                    k.hasWire = false;
                    k.inputSource = null;
                }
            }
        } else {
            if (!k.hasWire) {
                k.overlays.currentCursor = k.overlays.interactHand;
            }
        }

        if (!noCycle) {
            k.updateInfo("If you form a cycle the machine will explode.  No.");
            k.overlays.currentCursor = k.overlays.cancel;
        }

    }

    public static boolean checkTruthTable(int[] table, LogicInput[] lin, OutputSystem root) {
        for (int i = 0; i < (1 << lin.length) && i < table.length; i++) {
            for (int j = 0; j < lin.length; j++) {
                lin[j].setValue(((i >> j) & 0x01) == 1);
            }
            //for(int j=lin.length-1;j>=0;j--) System.out.print(lin[j]+" ");
            //System.out.println("| "+root);
            if ((table[i] == 0 || table[i] == 1)
                    && (root.evaluate() != (table[i] == 1))) {
                return false;
            }
        }
        return true;
    }

    public boolean noLoop(OutputSystem x) {
        return true;
    }

}
