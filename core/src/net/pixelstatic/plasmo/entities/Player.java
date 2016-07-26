package net.pixelstatic.plasmo.entities;

import net.pixelstatic.plasmo.Plasmo;
import net.pixelstatic.utils.graphics.Hue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends SpriteEntity implements Collidable{
	float speed = 3;
	float maxshield = 10;
	float wavereload = 200;
	float wavetime = 0;
	float dashtime = 0;
	public final int maxhealth = 40;
	public int health = maxhealth;
	int reload = 2;
	int reloadtime = 0;
	int xp;
	int level;
	int xpperlevel = 20;
	float maxrange = 1000;
	float hittime;

	{
		color = Color.valueOf("9bffd8");
	}

	@Override
	public void draw(SpriteBatch batch){
		super.draw(batch);
	}

	@Override
	public void update(){
		Vector3 v = Plasmo.i.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

		Vector2 v2 = new Vector2(v.x, v.y);
		v2.sub(x, y);
		
		sprite.setRotation(v2.angle() - 90);

		if(Gdx.input.isKeyPressed(Keys.W)) y += speed;
		if(Gdx.input.isKeyPressed(Keys.A)) x -= speed;
		if(Gdx.input.isKeyPressed(Keys.S)) y -= speed;
		if(Gdx.input.isKeyPressed(Keys.D)) x += speed;
		
		wavereload = Math.max(200 - level*6, 0);

		if(wavetime > 0){
			wavetime -= Gdx.graphics.getDeltaTime() * 60f;
			if(wavetime <= 0){
				new Inwave().set(x, y).add();
			}
		}

		if(Vector2.dst(x, y, 0, 0) > maxrange){
			set(0, 80);
			new Inwave(80, 4).setColor(Color.PURPLE).add();
		}

		if(tryShoot() && Gdx.input.isButtonPressed(Buttons.LEFT)){

			int bullets = 1 + (int)(level/3);
			
			if(bullets > 4) bullets = 4;

			for(int i = 0;i < bullets;i ++){
				shoot(3.5f+sprite.getRotation() - bullets * 4 + i * 8);
			}

		}
		
		

		if(hittime > 0){
			sprite.setColor(Hue.blend(Color.RED, color, hittime / 5f));
			hittime -= Gdx.graphics.getDeltaTime()*60f;
		}else{
			sprite.setColor(Hue.blend(new Color(1, 0.0f, 0.0f, 1f), color, (float)health / maxhealth));
		}
	}

	void shoot(float angle){
		new Bullet(sprite.getColor(), this, angle).setSpeed(Math.min(7.5f, 5f + level/3f)).set(x, y).add();
	}

	public int totalXP(){
		return level * xpperlevel + xp;
	}

	public void addXP(int axp){
		if( !Plasmo.i.entities.containsKey(id)) return;
		xp += axp;
		if(xp >= xpperlevel){
			level ++;
			new Inwave(90, 4).setColor(Color.WHITE).set(x, y).add();
			Plasmo.i.bloom(10);
			xp = 0;
		}
	}

	public void rightclick(){
		if(wavetime <= 0){
			Plasmo.i.playSound("wave", 0.15f);
			new Wave(true).set(x, y).add();
			Plasmo.i.bloom(13);
			wavetime = wavereload;
		}
	}

	boolean tryShoot(){
		if(reloadtime > reload){
			reloadtime = 0;
			return true;
		}else{
			reloadtime += Gdx.graphics.getDeltaTime() * 60f;
		}
		return false;
	}

	@Override
	public float hitboxSize(){
		return 5;
	}

	@Override
	public boolean collides(Entity other){
		return other instanceof Bullet;
	}

	@Override
	public void collided(Entity other){

		health -= ((Bullet)other).damage;
		hittime = 5;
		if(health <= 0){
			Plasmo.i.dead = true;
			Plasmo.i.shake(20);
			for(int i = 0;i < 90;i ++)
				new Bullet(color, this, i * 4).setSpeed(5f).set(x, y).add();
			remove();
		}
	}
}
