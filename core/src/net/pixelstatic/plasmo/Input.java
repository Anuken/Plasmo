package net.pixelstatic.plasmo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class Input extends InputAdapter{
	public static final int ABILITY_KEY = Keys.SPACE;
	public static final int START_KEY = Keys.ENTER;
	
	
	{
		Gdx.input.setInputProcessor(this);
		
	}
	
	public void update(){
		//if(Gdx.input.isKeyJustPressed(Keys.Q))
		//	PixmapIO.writePNG(Gdx.files.local("screenshot-" + MathUtils.random(9999999) + ".png"), ScreenUtils.getFrameBufferPixmap(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
	}
}
