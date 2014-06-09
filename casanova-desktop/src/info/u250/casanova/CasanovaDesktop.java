package info.u250.casanova;

import info.u250.c2d.engine.Engine;
import info.u250.casanova.Casanova;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class CasanovaDesktop {
	public static void main(String[] args) {
		Casanova game = new Casanova(new LoadingFeedback() {
			
			@Override
			public void finish() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void feedback(float percent) {
				// TODO Auto-generated method stub
				
			}
		});
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int)Engine.getWidth();
		config.height= (int)Engine.getHeight();
		new LwjglApplication(game,config);
	}
}
