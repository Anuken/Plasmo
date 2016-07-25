package net.pixelstatic.bossdash.entities;

import net.pixelstatic.bossdash.BossDash;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class Enemy1 extends Enemy{
	{
		color = Color.valueOf("f691ff");
		reload = 10;
		health = 5;
		xp = 2;
	}
	
	@Override
	public void behavior(){
		
		x += MathUtils.random(-1, 1);
		y += MathUtils.random(-1, 1);
		
		if(tryShoot()){
			shoot(playerAngle() + MathUtils.random(-10, 10));
		}
	}

	@Override
	public float hitboxSize(){
		return 8;
	}
	
	@Override
	public void collided(Entity other){
		BossDash.i.bloomtime = 2;
		super.collided(other);
	}

	@Override
	void death(){
		BossDash.i.bloomtime = 5;
		BossDash.i.shaketime = 2;
		for(int i = 0; i < 4; i ++ )
			shoot(i*90);
		remove();
	}
}
