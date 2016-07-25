package net.pixelstatic.bossdash.entities;

import net.pixelstatic.bossdash.BossDash;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class Boss4 extends Enemy{
	
	{
		color = Color.valueOf("ff8a7e");
		reload = 7;
		xp = 15;
		health = 100;
	}
	
	@Override
	public void behavior(){
		
		sprite.rotate(5);
		
		if(tryShoot()){
			//shoot(playerAngle() + (float)Math.sin(lifetime()/20)*40);
			//shoot(playerAngle() - (float)Math.sin(lifetime()/20)*40);
			
			for(int i = 0; i < 8; i ++)
				shoot(sprite.getRotation() + i*45);
		}
		
		if(Math.random() < 0.006)
			new Enemy5().set(x + MathUtils.random(-32, 32), y + MathUtils.random(-32, 32)).add();
	}

	@Override
	public float hitboxSize(){
		return 32;
	}
	
	@Override
	public void collided(Entity other){
		BossDash.i.bloomtime = 3;
		super.collided(other);
	}

	@Override
	void death(){
		BossDash.i.bloomtime = 30;
		BossDash.i.shaketime = 15;
		for(int i = 0; i < 18; i ++ )
			shoot(i*20);
		remove();
	}
}
