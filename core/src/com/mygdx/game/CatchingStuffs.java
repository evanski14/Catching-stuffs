package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class CatchingStuffs extends ApplicationAdapter {
	SpriteBatch batch;
	private Texture pepeImg;
	private Texture bucketImg;
	private Music bgMusic;
	private Rectangle bucket;
	
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
		
		//actually create stuffs
		bucket = new Rectangle();
		bucket.x = 800/2 - 64/2;
		bucket.y = 15;
		bucket.width = 64;
		bucket.height = 64;
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0.1f, 10);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(bucketImg, bucket.x, bucket.y);
		batch.end();
		
		//bucket movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			bucket.x -= 350 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			bucket.x += 350 * Gdx.graphics.getDeltaTime();
		}
	}
}
