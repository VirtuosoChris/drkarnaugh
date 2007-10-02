package drk.graphics;
import drk.graphics.GLRenderable;

public interface GLSortableRenderable extends GLRenderable,Comparable
{
	//public int getSetupGroup();
	public int glGroupRender();
	public int glGroupUnRender();
	
}
