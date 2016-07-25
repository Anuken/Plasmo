package net.pixelstatic.bossdash.entities;

import net.pixelstatic.bossdash.BossDash;

import com.badlogic.gdx.graphics.Color;

public class Boss1 extends Enemy{
	
	{
		color = Color.valueOf("99fda2");
		reload = 3;
		xp = 3;
		health = 30;
	}
	
	@Override
	public void behavior(){
		
		x += Math.sin(lifetime()/60f + Math.PI/2);
		y += Math.sin(lifetime()/30f)/2f;
		
		if(tryShoot()){
			shoot(playerAngle() + (float)Math.sin(lifetime()/20)*40);
			shoot(playerAngle() - (float)Math.sin(lifetime()/20)*40);
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
		BossDash.i.bloomtime = 20;
		BossDash.i.shaketime = 10;
		for(int i = 0; i < 18; i ++ )
			shoot(i*20);
		remove();
	}
}
