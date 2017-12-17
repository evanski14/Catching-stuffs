package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CatchingStuffs extends ApplicationAdapter {
	SpriteBatch batch;
	private Texture pepeImg;
	private Texture bucketImg;
	private Music bgMusic;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		//load textures
		pepeImg = new Texture(Gdx.files.internal("pepe.png"));
		bucketImg = new Texture("bucket.png");
		
		//load sounds
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Gold_Rush.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(pepeImg, 0, 0);
		batch.end();
	}
}
