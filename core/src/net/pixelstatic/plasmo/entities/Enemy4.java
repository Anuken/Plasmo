package net.pixelstatic.plasmo.entities;

import net.pixelstatic.plasmo.Plasmo;

import com.badlogic.gdx.graphics.Color;

public class Enemy4 extends Enemy{
	{
		color = Color.valueOf("f7ff7e");
		health = 5;
	}
	
	@Override
	public void behavior(){
		
		vector.set(Plasmo.i.player.x, Plasmo.i.player.y).sub(x, y).nor().setAngle(vector.angle()).scl(1.7f);
		
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
		Plasmo.i.bloomtime = 10;
		Plasmo.i.shaketime = 2;
		bulletwave(16);
		remove();
	}

}
