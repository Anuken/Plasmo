package net.pixelstatic.plasmo.entities;

import net.pixelstatic.plasmo.Plasmo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends SpriteEntity implements Collidable{
	static Vector2 vector = new Vector2();
	int maxhealth = 100;
	int health = maxhealth;
	int reload = 10;
	int reloadtime = 0;
	int xp = 1;
	final float bctime = 3;
	float ctime = 0;

	@Override
	final public void update(){
		if(ctime > 0){
			float s = 1f - (ctime / bctime) * 0.5f;
			sprite.setColor(new Color(1, color.g * s, color.b * s, 1));
			//sprite.setColor(new Color(1,0.5f + 0.5f - (ctime/bctime)*0.5f,0.5f + 0.5f - (ctime/bctime)*0.5f,1));
			ctime -= Gdx.graphics.getDeltaTime() * 60f;
		}else{
			sprite.setColor(color);
		}

		if(playerDistance() > 300){
			vector.set(Plasmo.i.player.x, Plasmo.i.player.y).sub(x, y).nor().setAngle(vector.angle()).scl(1);
		//	x += vector.x;
		//	y += vector.y;
		}else{
			behavior();
		}

	}

	public abstract void behavior();

	boolean tryShoot(){
		if(reloadtime > reload){
			reloadtime = 0;
			return true;
		}else{
			reloadtime += Gdx.graphics.getDeltaTime() * 60f;
		}
		return false;
	}

	public float playerAngle(){
		return vector.set(Plasmo.i.player.x, Plasmo.i.player.y).sub(x, y).angle() - 90;
	}

	public void shoot(float angle){
		new Bullet(color, this, angle).set(x, y).add();
	}

	abstract void death();

	@Override
	public boolean collides(Entity other){
		return (other instanceof Bullet && ((Bullet)other).owner instanceof Player);
	}
	
	void bulletwave(int a){
		for(int i = 0; i < a; i ++ )
			shoot(i*360/a);
	}

	@Override
	public void collided(Entity other){
		ctime = bctime;
		health -= ((Bullet)other).damage;
		if(health <= 0){
			Plasmo.i.playSound("laser", 0.1f);
			Plasmo.i.player.addXP(xp);
			new Wave(30, 4).setColor(color).set(this).add();
			death();
		}
	}
}
