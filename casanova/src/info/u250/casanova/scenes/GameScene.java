package info.u250.casanova.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;
import info.u250.c2d.graphic.AdvanceSprite;
import info.u250.c2d.graphic.parallax.ParallaxGroup;
import info.u250.c2d.graphic.parallax.ParallaxLayer;
import info.u250.casanova.scenes.game.Boy;
import info.u250.casanova.scenes.game.CountLabel;
import info.u250.casanova.scenes.game.Dialog;
import info.u250.casanova.scenes.game.Girl;
import info.u250.casanova.scenes.game.TimeLabel;
import info.u250.casanova.ui.CommonDialog;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;

public class GameScene extends SceneStage{
	final Boy boy ;
	private Girl lastGirl;
	private CountLabel countLabel;
	private TimeLabel timeLabel;
	private Dialog dialog ;
	int count = 0;
	Random random = new Random();
	boolean begin = false;
	private CommonDialog quitDialog ;
	public final Pool<Girl> girlPool = new Pool<Girl>(){
		@Override
		protected Girl newObject() {
			return new Girl(GameScene.this);
		}
		public Girl obtain() {
			lastGirl = super.obtain();
			lastGirl.setNeckLength(Boy.MIN_HEIGHT+(Boy.MAX_HEIGHT-Boy.MIN_HEIGHT)*random.nextFloat());
			lastGirl.setFire(false);
			lastGirl.setSpeed(200+200*random.nextFloat());
			return lastGirl;
		};
	};
	public GameScene(){
		quitDialog = new CommonDialog(new String[]{"Are you sure you want to close?"}, new Runnable() {
			public void run() {
				System.exit(0);
			}
		});
		
		final TextureAtlas atlas = Engine.resource("Main");
		ParallaxGroup rbg = new ParallaxGroup(Engine.getWidth(), Engine.getHeight(), new Vector2(200,100));
		rbg.addActor(new ParallaxLayer(rbg,
				new Image(new AdvanceSprite(atlas.findRegion("bg") )),
				new Vector2(1f,0),new Vector2(0,1000),
				new Vector2(0,0)));
		rbg.addActor(new ParallaxLayer(rbg,
				new Image(new AdvanceSprite(atlas.findRegion("cloud") )),
				new Vector2(0.21f,0),new Vector2(300,1000),
				new Vector2(0,400)));
		this.addActor(rbg);
		//boy
		boy = new Boy();
		this.addActor(boy);
		Girl girl = girlPool.obtain();
		girl.setX(Engine.getWidth());
		this.addActor(girl);
		//time
		timeLabel = new TimeLabel();
		timeLabel.setPosition(Engine.getWidth()-100, Engine.getHeight()-timeLabel.getHeight()-30);
		this.addActor(timeLabel);
		//count
		countLabel = new CountLabel();
		countLabel.setPosition(Engine.getWidth()/2, Engine.getHeight()-countLabel.getHeight()-50);
		countLabel.setOrigin(countLabel.getWidth()/2, countLabel.getHeight()/2);
		//dialog
		dialog = new Dialog();
		this.addActor(dialog);
		dialog.button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialog.addAction(Actions.sequence(Actions.fadeOut(1),Actions.run(new Runnable() {
					@Override
					public void run() {
						dialog.remove();
						begin();
					}
				})));
				super.clicked(event, x, y);
			}
		});
		dialog.show(0);
	}
	void begin(){
		count=0;
		timeLabel.getTimer().setPause(false);
		timeLabel.getTimer().setSceonds(60);
		begin = true;
	}
	void end(){
		timeLabel.getTimer().setPause(true);
		timeLabel.getTimer().setSceonds(0);
		this.addActor(dialog);
		dialog.addAction(Actions.fadeIn(1));
		begin = false;
		dialog.show(count);
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		boy.neckUp();
		return super.touchDown(screenX, screenY, pointer, button);
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		boy.neckDown();
		return super.touchUp(screenX, screenY, pointer, button);
	}
	
	public void update(float delta) {
		if(begin && timeLabel.getTimer().getSceonds()<=0){
			end();
		}
		if(lastGirl.getX() < Engine.getWidth()){
			Girl girl = girlPool.obtain();
			girl.setX( Engine.getWidth() + 150+300*random.nextFloat() );
			this.addActor(girl);
			boy.remove();
			this.addActor(boy);
			if(this.getActors().contains(dialog, true)){
				dialog.remove();
				this.addActor(dialog);
			}
			if(this.getActors().contains(quitDialog, true)){
				quitDialog.remove();
				this.addActor(quitDialog);
			}
			
		}
		
		if(begin)/////////////////////////////////////////////////////////////////////
		for(Actor actor:this.getActors()){
			if(actor instanceof Girl){
				Girl girl = Girl.class.cast(actor);
				if(!girl.isFire() && girl.getRect().overlaps(boy.getRect())){
					//kiss
					Engine.getSoundManager().playSound("SoundKiss"+random.nextInt(2));
					this.addActor(countLabel);
					countLabel.setText(String.valueOf(++count));
					countLabel.clearActions();
					countLabel.setScale(0);
					countLabel.getColor().a = 1;
					countLabel.addAction(Actions.sequence(
							Actions.scaleTo(1, 1, 0.3f),
							Actions.fadeOut(0.5f),
							Actions.run(new Runnable() {
								@Override
								public void run() {
									countLabel.remove();
								}
							})
							));
					//kiss effect
					for(int i=0;i<10;i++){
						final TextureAtlas atlas = Engine.resource("Main");
						final Image image = new Image(atlas.findRegion("heart"));
						image.setPosition(girl.getRect().x+(random.nextBoolean()?1:-1)*random.nextFloat()*30, girl.getRect().y+(random.nextBoolean()?1:-1)*random.nextFloat()*30);
						this.addActor(image);
						float du = 0.4f+0.5f*random.nextFloat();
						image.setScale(du);
						image.addAction(Actions.sequence(
								Actions.parallel(
										Actions.moveBy((random.nextBoolean()?1:-1)*random.nextFloat()*100, (random.nextBoolean()?1:-1)*random.nextFloat()*100,du),
										Actions.fadeOut(du)),
								Actions.run(new Runnable() {
							@Override
							public void run() {
								image.remove();
							}
						})));
					}
					girl.setFire(true);
				}
			}
		}
		super.update(delta);
	};
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(254f/255f, 255f/255f, 249f/255f, 1);
		super.render(delta);
	}
	@Override
	public boolean keyDown(int keycode) {
		if (Gdx.app.getType() == ApplicationType.Android) {
			if (keycode == Keys.BACK) {
				this.addActor(quitDialog);
			}
		} else {
			if (keycode == Keys.DEL) {
				this.addActor(quitDialog);
			}
		}
		return super.keyDown(keycode);
	}
}