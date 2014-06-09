package info.u250.casanova;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.casanova.scenes.GameScene;

public class CasanovaEngineDrive implements EngineDrive {

	@Override
	public EngineOptions onSetupEngine() {
		EngineOptions opt = new EngineOptions(new String[]{"data/"}, 1280, 720);
		opt.autoResume = true;
		opt.debug = false;
		opt.catchBackKey = true;
		opt.configFile = "info.u250.casanova.cf";
		return opt;
	}

	@Override
	public void onLoadedResourcesCompleted() {
		GameScene gameScene = new GameScene();
		Engine.setMainScene(gameScene);
		Engine.getMusicManager().playMusic("MusicBg", true);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void onResourcesRegister(AliasResourceManager<String> reg) {
		reg.textureAtlas("Main", "data/main.atlas");
		reg.music("MusicBg", "data/music/bg.mp3");
		reg.sound("SoundKiss0", "data/sound/kiss1.wav");
		reg.sound("SoundKiss1", "data/sound/kiss2.mp3");
		reg.font("FontCommon", "data/common.fnt");
		reg.font("FontCount", "data/count.fnt");
	}

}
