package info.u250.casanova.scenes.game;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class CountLabel extends Group{
	Label lbl;
	
	public CountLabel() {
		this.setSize(150, 50);
		lbl =new  Label("0", new LabelStyle(Engine.resource("FontCount",BitmapFont.class), Color.PINK));
		this.addActor(lbl);
	}
	public void setText(String txt){
		lbl.setText(txt);
	}

	
}
