package drk.graphics.particle;

import drk.Updatable;
import drk.DeltaTimer;

public interface TimeUpdatable extends Updatable
{
	public void setDeltaTimer(DeltaTimer deltat);
}
