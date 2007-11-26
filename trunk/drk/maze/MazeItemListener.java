package drk.maze;
import drk.game.KarnaughGame;


//interface for mazeitems to process mouse hovering, mouse clicks, etc
public interface MazeItemListener
{
	public boolean isMazeItemHighlighted(KarnaughGame k);
	public void onMazeItemHighlighted(KarnaughGame k);
	public void onMazeItemEntered(MazeItem m);
}
