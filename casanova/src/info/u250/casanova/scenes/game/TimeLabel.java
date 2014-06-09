package info.u250.casanova.scenes.game;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class TimeLabel extends Group{
	CountDownTimer timer;
	
	public CountDownTimer getTimer() {
		return timer;
	}
	public void setTimer(CountDownTimer timer) {
		this.timer = timer;
	}
	public TimeLabel() {
		this.setSize(150, 50);
		Label lbl =new  Label("", new LabelStyle(Engine.resource("FontCommon",BitmapFont.class), Color.MAROON));
//		Image bg = new Image(new NinePatchDrawable(Engine.resource("Main",TextureAtlas.class).createPatch("dialog-bg")));
//		bg.setSize(this.getWidth(), this.getHeight());
//		this.addActor(bg);
		this.addActor(lbl);
		timer = new CountDownTimer(lbl, 60);
		timer.setPause(true);
		timer.start();
	}
	
}
