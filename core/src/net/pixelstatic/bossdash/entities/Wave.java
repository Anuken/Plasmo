package net.pixelstatic.bossdash.entities;

import net.pixelstatic.bossdash.BossDash;
import net.pixelstatic.utils.graphics.Textures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Wave extends Entity{
	static Vector2 vector = new Vector2();
	float size = 1f;
	float maxsize = 300;
	float speed  = 30f;
	boolean explode = false;
	
	{
		color.set(Color.CORAL);
	}
	
	public Wave(float maxsize, float speed){
		this.maxsize = maxsize;
		this.speed = speed;
	}
	
	public Wave(boolean explode){
		this.explode = explode;
	}
	
	@Override
	public void update(){
		if(explode)
			for(Entity entity : BossDash.i.entities.values()){
				if(!(entity instanceof Bullet)) continue;
				Bullet bullet = (Bullet)entity;
				bullet.owner = BossDash.i.player;
				bullet.color = BossDash.i.player.sprite.getColor();
				bullet.sprite.setColor(bullet.color);
				bullet.sprite.setRotation(vector.set(bullet.x, bullet.y).sub(x,y).angle() - 90);
			}
		size += speed;
		if(size > maxsize) remove();
	}

	@Override
	public void draw(SpriteBatch batch){
		batch.setColor(color.r, color.g, color.b, 1f);
		batch.draw(Textures.get("wave"), x - size/2, y - size/2, size, size);
		batch.setColor(Color.WHITE);
	}

}
