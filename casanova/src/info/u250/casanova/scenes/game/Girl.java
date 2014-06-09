package info.u250.casanova.scenes.game;

import info.u250.c2d.engine.Engine;
import info.u250.casanova.scenes.GameScene;
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


public class Girl extends Group{
	final Image head ;
	final Image neck ;
	final Image body ;
	final GameScene gameScene;
	
	boolean fire = false;
	float speed = 200;
	
	
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public boolean isFire() {
		return fire;
	}
	public void setFire(boolean fire) {
		this.fire = fire;
	}
	public void setNeckLength(float length){
		this.neck.setHeight(length);
	}
	public Girl(GameScene gameScene) {
		this.gameScene = gameScene;
		final TextureAtlas atlas = Engine.resource("Main");
		
		TextureRegion[] npcRegions = new TextureRegion[26];
		for(int i=1;i<10;i++){
			npcRegions[i-1] = atlas.findRegion("girl000"+i);
		}
		for(int i=10;i<=26;i++){
			npcRegions[i-1] = atlas.findRegion("girl00"+i);
		}
		
		Animation npcAnim = new Animation(0.03f, npcRegions);
		body = new Image(new AnimationDrawable(npcAnim));
		
		neck = new Image(atlas.findRegion("line"));
		neck.setPosition(90, 180);
		neck.setOrigin(neck.getWidth()/2, 0);
		neck.setRotation(15);
		neck.setHeight(100);
		
		head = new Image(atlas.findRegion("girl-head"));
		head.setOrigin(this.getWidth()/2, 0);
		
		this.addActor(neck);
		this.addActor(body);
		this.addActor(head);
		
		neck.addAction(Actions.forever(
				Actions.parallel(
						Actions.sequence(Actions.rotateBy(5,0.5f),Actions.rotateBy(-5,0.5f)),
						Actions.sequence(Actions.moveBy(-2,5,0.5f),Actions.moveBy(2,-5,0.5f)))
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
	public void act(float delta) {
		super.act(delta);
		this.setX(this.getX()-this.speed*delta );
		if(this.getX()<-300){
			gameScene.girlPool.free(this);
			this.remove();
		}
	}
	Rectangle r = new Rectangle();
	Vector2 tmp = new Vector2();
	public Rectangle getRect(){
		tmp.set(localToStageCoordinates(new Vector2(this.head.getX(),this.head.getY())));
		r.width = 24;
		r.height= 24;
		r.x = tmp.x;
		r.y = tmp.y;
		return r;
	}
}
