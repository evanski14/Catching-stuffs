package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.CatchingStuffs;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		//config window size
		config.title = "Catching Stuffs";
		config.width = 800;
		config.height = 600;
		
		new LwjglApplication(new CatchingStuffs(), config);
	}
}
