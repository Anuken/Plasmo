package net.pixelstatic.plasmo.entities;

import net.pixelstatic.plasmo.Plasmo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public abstract class Entity{
	private static long lastid;
	public final long id;
	public final long startime;
	public Color color = Color.WHITE;
	public float x, y;
	
	public Entity(){
		id = lastid ++;
		startime = TimeUtils.millis();
	}
	
	public abstract void update();
	
	public abstract void draw(SpriteBatch batch);
	
	public Entity setColor(Color color){
		this.color = color;
		return this;
	}
	
	public Entity set(float x, float y){
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Entity set(Entity other){
		this.x = other.x;
		this.y = other.y;
		return this;
	}
	
	public float playerDistance(){
		return Vector2.dst(x, y, Plasmo.i.player.x, Plasmo.i.player.y);	
	}
	
	public boolean loaded(){
		return playerDistance() < 400;	
	}
	
	public float lifetime(){
		return (TimeUtils.timeSinceMillis(startime) / 1000f) * 60f;
	}
	
	public Entity spawn(){
		if(playerDistance() < 50) return this;
		new Inwave(20, 2).unlink().setColor(color).set(x, y).add();
		return add();
	}
	
	public float delta(){
		return Gdx.graphics.getDeltaTime()*60f;
	}
	
	public void move(float x, float y){
		this.x += x*delta();
		this.y += y*delta();
	}
	
	public void move(Vector2 v){
		move(v.x, v.y);
	}
	
	public Entity add(){
		Plasmo.i.addingEntities.add(this);
		return this;
	}
	
	public void remove(){
		Plasmo.i.removingEntities.add(this);
	}

}
