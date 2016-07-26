package net.pixelstatic.plasmo.entities;

import net.pixelstatic.gdxutils.Textures;
import net.pixelstatic.plasmo.Plasmo;

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
			for(Entity entity : Plasmo.i.entities.values()){
				if(!(entity instanceof Bullet)) continue;
				Bullet bullet = (Bullet)entity;
				bullet.owner = Plasmo.i.player;
				bullet.color = Plasmo.i.player.sprite.getColor();
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
