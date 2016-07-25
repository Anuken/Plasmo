package net.pixelstatic.plasmo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;

public class Input extends InputAdapter{
	
	{
		Gdx.input.setInputProcessor(this);
	}
	
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		if(button == Buttons.RIGHT) Plasmo.i.player.rightclick();
		return false;
	}
	
	public void update(){
		
	}
}
