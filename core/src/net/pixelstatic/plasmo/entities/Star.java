package net.pixelstatic.plasmo.entities;

import com.badlogic.gdx.graphics.Color;

public class Star extends SpriteEntity{
	
	{
		color = new Color(0.2f,0.4f,0.6f,0.2f);
		sprite.setColor(color);
	}
	
	@Override
	public void update(){
		sprite.rotate(0.1f);
	}

}
