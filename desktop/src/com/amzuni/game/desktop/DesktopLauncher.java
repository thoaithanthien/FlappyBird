package com.amzuni.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.amzuni.game.FlappyBirdMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FlappyBirdMain.WIDTH;
		config.height = FlappyBirdMain.HEIGHT;
		config.title   =  FlappyBirdMain.TITLE;
		new LwjglApplication(new FlappyBirdMain(), config);
	}
}

