package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class CatchingStuffs extends ApplicationAdapter {
	SpriteBatch batch;
	private Texture pepeImg;
	private Texture bucketImg;
	private Music bgMusic;
	private Rectangle bucket;
	private Array<Rectangle> pepes;
	private long lastDropTime;
	
	//randome spawn pepe
	private void pepeSpawn() {
		Rectangle pepe = new Rectangle();
		pepe.x = MathUtils.random(0, 800-64);
		pepe.y = 600;
		pepe.width = 64;
		pepe.height = 64;
		pepes.add(pepe);
		lastDropTime = TimeUtils.nanoTime();
	}
	
	
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
			//bucket
		bucket = new Rectangle();
		bucket.x = 800/2 - 64/2;
		bucket.y = 15;
		bucket.width = 64;
		bucket.height = 64;
			//pepes
		pepes = new Array<Rectangle>();
		pepeSpawn();
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0.1f, 10);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//draw bucket
		batch.draw(bucketImg, bucket.x, bucket.y);
		//draw pepe
		for(Rectangle pepe: pepes) {
			batch.draw(pepeImg, pepe.x, pepe.y);
		}
		batch.end();
		
		//bucket movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			bucket.x -= 350 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			bucket.x += 350 * Gdx.graphics.getDeltaTime();
		}
		
		//bucket hits the screen
		if(bucket.x < 0) {
			bucket.x = 0;
		}
		if(bucket.x > 800-64) {
			bucket.x = 800-64;
		}
		
		//pepe spawn timer check
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000 ) {
			pepeSpawn();
		}
		
		//pepe movement
		Iterator<Rectangle> iter = pepes.iterator();
		while(iter.hasNext()) {
			Rectangle pepe = iter.next();
			pepe.y -= 200 * Gdx.graphics.getDeltaTime();
			if(pepe.y + 64 < 0) {
				iter.remove();
			}
			if(pepe.overlaps(bucket)) {
				iter.remove();
			}
		}
	}
}
