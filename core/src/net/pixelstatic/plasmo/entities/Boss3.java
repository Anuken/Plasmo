package net.pixelstatic.plasmo.entities;

import net.pixelstatic.plasmo.Plasmo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class Boss3 extends Enemy{

	{
		color = Color.valueOf("ffbc74");
		reload = 20;
		xp = 4;
		health = 40;
	}
	
	@Override
	public void behavior(){
		
		sprite.rotate(4f);
		
		if(tryShoot()){
			for(int i = 0; i < 4; i ++)
			shoot(sprite.getRotation() + i*90);
		}
	}

	@Override
	public float hitboxSize(){
		return 16;
	}
	
	@Override
	public void collided(Entity other){
		Plasmo.i.bloom(3);
		super.collided(other);
	}

	@Override
	void death(){
		for(int i = 0; i < 4; i ++)
			new Enemy3().setRotation(i*4).set(x + MathUtils.random(-8, 8),  y + MathUtils.random(-8, 8)).add();
		
		Plasmo.i.effect(10, 20);
		for(int i = 0; i < 9; i ++ )
			shoot(i*40);
		remove();
	}
}
