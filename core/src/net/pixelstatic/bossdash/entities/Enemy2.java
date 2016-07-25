package net.pixelstatic.bossdash.entities;

import net.pixelstatic.bossdash.BossDash;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class Enemy2 extends Enemy{
	{
		color = Color.valueOf("80fffe");
		reload = 15;
		health = 2;
	}
	
	@Override
	public void behavior(){
		
		vector.set(BossDash.i.player.x, BossDash.i.player.y).sub(x, y).nor().setAngle(vector.angle() + MathUtils.random(-5, 5)).scl(1);
		
		x += vector.x;
		y += vector.y;
		
		sprite.rotate(3);
		
		if(tryShoot()){
			for(int i = 0; i < 3; i ++)
				if(Math.random() < 0.5)
			shoot(playerAngle() + MathUtils.random(-4, 4));
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
