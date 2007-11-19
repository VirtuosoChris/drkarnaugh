package drk.maze;
import drk.game.KarnaughGame;

public interface MazeItemListener
{
	public boolean isMazeItemHighlighted(KarnaughGame k);
	public void onMazeItemHighlighted(KarnaughGame k);
	public void onMazeItemEntered(MazeItem m);
}
