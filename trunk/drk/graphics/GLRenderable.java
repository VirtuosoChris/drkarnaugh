package drk.graphics;

import javax.media.opengl.GL;

public interface GLRenderable
{
	public boolean isRendered=false;
	public void render(GL gl);
}
