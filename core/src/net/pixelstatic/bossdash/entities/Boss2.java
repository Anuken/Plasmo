package net.pixelstatic.bossdash.entities;

import net.pixelstatic.bossdash.BossDash;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class Boss2 extends Enemy{

	{
		color = Color.valueOf("f691ff");
		reload = 2;
		xp = 3;
		health = 30;
	}
	
	@Override
	public void behavior(){
		
		sprite.rotate(1f);
		
		x += Math.sin(lifetime()/60f + Math.PI/2);
		y += Math.sin(lifetime()/30f)/2f;
		
		if(tryShoot()){
			shoot(playerAngle() + (float)Math.sin(lifetime()/30)*360f);
		}
	}

	@Override
	public float hitboxSize(){
		return 16;
	}
	
	@Override
	public void collided(Entity other){
		BossDash.i.bloomtime = 2;
		super.collided(other);
	}

	@Override
	void death(){
		for(int i = 0; i < 4; i ++)
			new Enemy1().set(x + MathUtils.random(-8, 8),  y + MathUtils.random(-8, 8)).add();
		BossDash.i.bloomtime = 10;
		BossDash.i.shaketime = 10;
		for(int i = 0; i < 9; i ++ )
			shoot(i*40);
		remove();
	}

}