package info.u250.casanova.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.casanova.ui.AnimationDrawable;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class Boy extends Group{
	public static float MIN_HEIGHT = 100;
	public static float MAX_HEIGHT = 480;
	
	static float SPEED = 100;
	boolean up = false;
	
	final Image head ;
	final Image neck ;
	final Image body ;
	
	public Boy() {
		
		final TextureAtlas atlas = Engine.resource("Main");
		
		TextureRegion[] npcRegions = new TextureRegion[26];
		for(int i=1;i<10;i++){
			npcRegions[i-1] = atlas.findRegion("boy000"+i);
		}
		for(int i=10;i<=26;i++){
			npcRegions[i-1] = atlas.findRegion("boy00"+i);
		}
		
		Animation npcAnim = new Animation(0.02f, npcRegions);
		body = new Image(new AnimationDrawable(npcAnim));
		
		neck = new Image(atlas.findRegion("line"));
		neck.setPosition(120, 180);
		neck.setOrigin(neck.getWidth()/2, 0);
		neck.setRotation(-20);
		neck.setHeight(MIN_HEIGHT);
		
		head = new Image(atlas.findRegion("boy-head"));
		head.setOrigin(this.getWidth()/2, 0);
		
		this.addActor(neck);
		this.addActor(body);
		this.addActor(head);
		
		neck.addAction(Actions.forever(
				Actions.parallel(
						Actions.sequence(Actions.rotateBy(5,0.4f),Actions.rotateBy(-5,0.4f)),
						Actions.sequence(Actions.moveBy(-2,5,0.4f),Actions.moveBy(2,-5,0.4f)))
				));
		head.addAction(Actions.forever(
				Actions.sequence(Actions.rotateBy(-10,0.4f),Actions.rotateBy(10,0.4f))
				));
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		head.setPosition(
				neck.getX()+neck.getHeight()*MathUtils.sinDeg(-neck.getRotation())-head.getWidth()/2+8,
				neck.getY()+neck.getHeight()*MathUtils.cosDeg(-neck.getRotation())-head.getHeight()/2+8);
		super.draw(batch, parentAlpha);
	}
	@Override
	public void act(float delta) {
		if(up){
			float aim = neck.getHeight()+SPEED*delta;
			if(aim<MAX_HEIGHT){
				neck.setHeight(aim);
			}
		}else{
			float aim = neck.getHeight()-SPEED*2*delta;
			if(aim>MIN_HEIGHT){
				neck.setHeight(aim);
			}
		}
		super.act(delta);
	}
	
	public void neckUp(){
		this.up = true;
	}
	public void neckDown(){
		this.up = false;
	}
	Rectangle r = new Rectangle();
	Vector2 tmp = new Vector2();
	public Rectangle getRect(){
		tmp.set(localToStageCoordinates(new Vector2(this.head.getX(),this.head.getY())));
		r.width = 24;
		r.height= 24;
		r.x = tmp.x+36;
		r.y = tmp.y;
		return r;
	}
}
