package info.u250.casanova.scenes.game;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Dialog extends Group{
	final Label lblMax ;
	final Label lblCurrent;
	public final ImageButton button ;
	public Dialog(){
		final TextureAtlas atlas = Engine.resource("Main");
		final Image image = new Image(atlas.findRegion("dialog"));
		this.addActor(image);
		this.setSize(image.getWidth(), image.getHeight());
		this.setPosition(Engine.getWidth()/2-this.getWidth()/2, Engine.getHeight()/2-this.getHeight()/2);
		lblMax =new  Label("23", new LabelStyle(Engine.resource("FontCommon",BitmapFont.class), Color.BLACK));
		lblCurrent =new  Label("2321", new LabelStyle(Engine.resource("FontCommon",BitmapFont.class), Color.BLACK));
		button = new ImageButton(new TextureRegionDrawable(atlas.findRegion("btn-1")), new TextureRegionDrawable(atlas.findRegion("btn-2")));
		
		lblMax.setPosition(390, 240);
		lblCurrent.setPosition(390, 170);
		button.setPosition(115, 47);
		this.addActor(lblMax);
		this.addActor(lblCurrent);
		this.addActor(button);
	}
	public void show(int current){
		lblCurrent.setText(String.valueOf(current));
		int max = readMax();
		if(current>max){
			saveMax(current);
		}
		lblMax.setText(String.valueOf(max));
	}
	
	void saveMax(int max){
		Engine.getPreferences().putInteger("max", max);
		Engine.getPreferences().flush();
	}
	int readMax(){
		return Engine.getPreferences().getInteger("max");
	}
}
