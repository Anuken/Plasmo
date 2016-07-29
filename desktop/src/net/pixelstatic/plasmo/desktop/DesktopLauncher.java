package net.pixelstatic.plasmo.desktop;

import java.awt.Toolkit;

import net.pixelstatic.plasmo.Plasmo;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Toolkit.getDefaultToolkit().getScreenSize().width;
		config.height = Toolkit.getDefaultToolkit().getScreenSize().height;
		//config.vSyncEnabled = false;
		//config.backgroundFPS = 0;
		//config.foregroundFPS = 0;
		new LwjglApplication(new Plasmo(), config);
	}
}
