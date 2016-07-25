package net.pixelstatic.bossdash.entities;

import net.pixelstatic.bossdash.BossDash;

import com.badlogic.gdx.graphics.Color;

public class Enemy4 extends Enemy{
	{
		color = Color.valueOf("f7ff7e");
		health = 5;
	}
	
	@Override
	public void behavior(){
		
		vector.set(BossDash.i.player.x, BossDash.i.player.y).sub(x, y).nor().setAngle(vector.angle()).scl(1.7f);
		
		x += vector.x;
		y += vector.y;
		
		if(playerDistance() < 20)
			death();
	}

	@Override
	public float hitboxSize(){
		return 8;
	}

	@Override
	void death(){
		BossDash.i.bloomtime = 6;
		BossDash.i.shaketime = 1;
		bulletwave(16);
		remove();
	}

}
