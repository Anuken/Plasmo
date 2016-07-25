package net.pixelstatic.bossdash.entities;

import net.pixelstatic.bossdash.BossDash;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class Enemy3 extends Enemy{
	{
		color = Color.valueOf("ffbc74");
		reload = 15;
		health = 8;
	}
	
	@Override
	public void behavior(){
		
		vector.set(BossDash.i.player.x, BossDash.i.player.y).sub(x, y).nor().setAngle(vector.angle() + MathUtils.random(-20, 20)).scl(1);
		
		x += vector.x;
		y += vector.y;
		
		sprite.setRotation(playerAngle()-90+45 + MathUtils.random(-20, 20));
		
		
		if(tryShoot()){
			shoot(sprite.getRotation()+90-45);
		}
	}

	@Override
	public float hitboxSize(){
		return 8;
	}

	@Override
	void death(){
		BossDash.i.bloomtime = 4;
		for(int i = 0; i < 3; i ++ )
			shoot(i*360/3);
		remove();
	}
}
