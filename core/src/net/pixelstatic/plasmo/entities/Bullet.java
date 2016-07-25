package net.pixelstatic.plasmo.entities;

import net.pixelstatic.plasmo.Plasmo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends SpriteEntity implements Collidable{
	final int lifetime = 70;
	float life = 0;
	Vector2 vector = new Vector2();
	Entity owner;
	int damage = 1;
	
	public float speed = 3;
	
	public Bullet(Color color, Entity owner, float rotation){
		sprite.setRotation(rotation);
		sprite.setColor(color);
		this.color = color;
		this.owner = owner;
		
		Plasmo.i.playSound("laser2");
	}
	
	public Bullet setSpeed(float speed){
		this.speed = speed;
		return this;
	}
	
	@Override
	public void update(){
		life += Gdx.graphics.getDeltaTime()*60f;
		if(life > lifetime){
			remove();
			return;
		}
		vector.set(0, speed);
		vector.setAngle(sprite.getRotation() + 90);
		x += vector.x;
		y += vector.y;
	}

	@Override
	public float hitboxSize(){
		return 4;
	}

	@Override
	public boolean collides(Entity other){
		
		return other != owner && !(other instanceof Bullet);
	}

	@Override
	public void collided(Entity other){
		remove();
	}

}
