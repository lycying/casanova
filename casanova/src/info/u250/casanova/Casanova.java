package info.u250.casanova;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.load.startup.StartupLoading;

public class Casanova extends Engine {
	public LoadingFeedback loadingFeedback;
	public Casanova(LoadingFeedback loadingFeedback){
		this.loadingFeedback = loadingFeedback;
	}
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new CasanovaEngineDrive();
	}
	@Override
	protected StartupLoading getStartupLoading() {
		return new Loading();
	}

}
