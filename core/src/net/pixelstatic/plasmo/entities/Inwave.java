package net.pixelstatic.plasmo.entities;

import net.pixelstatic.gdxutils.Textures;
import net.pixelstatic.plasmo.Plasmo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Inwave extends Entity{
	static Vector2 vector = new Vector2();
	float size = 40f;
	float maxsize = 300;
	float speed = 2f;
	boolean linked = true;
	
	public Inwave(){
		color.set(Plasmo.i.player.color);
	}
	
	public Inwave(float size, float speed){
		this.size = size;
		this.speed = speed;
	}
	
	public Inwave unlink(){
		linked = false;
		return this;
	}
	
	@Override
	public void update(){
		if(linked)set(Plasmo.i.player);
		size -= speed;
		if(size <= 0) remove();
	}

	@Override
	public void draw(SpriteBatch batch){
		batch.setColor(color.r, color.g, color.b, 1f);
		batch.draw(Textures.get("wave"), x - size/2, y - size/2, size, size);
		batch.setColor(Color.WHITE);
	}
}
