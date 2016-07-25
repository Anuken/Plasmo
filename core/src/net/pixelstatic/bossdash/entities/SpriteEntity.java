package net.pixelstatic.bossdash.entities;

import net.pixelstatic.utils.graphics.Textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class SpriteEntity extends Entity{
	Sprite sprite;
	
	{
		sprite = new Sprite(Textures.get(getClass().getSimpleName().toLowerCase()));
	}
	
	@Override
	public void draw(SpriteBatch batch){
		Texture texture = sprite.getTexture();	
		sprite.setPosition(x - texture.getWidth()/2, y - texture.getHeight()/2);
		sprite.draw(batch);
	}
	
	public SpriteEntity setRotation(float r){
		sprite.setRotation(r);
		return this;
	}
}
