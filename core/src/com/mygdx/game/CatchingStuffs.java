package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	private Sound roblox;
	private Sound WOW;
	private Sound Triple;
	private Sound GetTheCam;
	private Sound Dota;
	private Rectangle bucket;
	private Array<Rectangle> pepes;
	private long lastDropTime;
	private int score;
	private String ScoreName;
	BitmapFont ScoreBitmapFontName;
	private int streak;
	private String StreakName;
	BitmapFont StreakBitmapFontName;
	
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
		bgMusic.setVolume(0.1f);
		
		roblox = Gdx.audio.newSound(Gdx.files.internal("roblox.mp3"));
		WOW = Gdx.audio.newSound(Gdx.files.internal("WOW.mp3"));
		Triple = Gdx.audio.newSound(Gdx.files.internal("Triple.wav"));
		GetTheCam = Gdx.audio.newSound(Gdx.files.internal("GetTheCam.wav"));
		Dota = Gdx.audio.newSound(Gdx.files.internal("Dota.mp3"));
		
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
		
		//ScoreBoard
		score = 0;
		ScoreName = "score: 0";
		ScoreBitmapFontName = new BitmapFont();
		
		//Streak
		streak = 0;
		StreakName = "streak: 0";
		StreakBitmapFontName = new BitmapFont();
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0.1f, 10);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//draw scoreboard
		ScoreBitmapFontName.setColor(Color.BLACK);
		ScoreBitmapFontName.draw(batch, ScoreName, 675, 575);
		//draw streakboard
		StreakBitmapFontName.setColor(Color.BLACK);
		StreakBitmapFontName.draw(batch, StreakName, 675, 550);
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
				score++;
				ScoreName = "score: " + score;
				streak++;
				if(streak == 3) {
					Triple.play(0.2f);
				}
				if(score == 20) {
					GetTheCam.play(0.2f);
				}
				if(streak == 10) {
					Dota.play();
				}
				StreakName = "streak: " + streak;
				iter.remove();
				WOW.play();
			}
			if(pepe.y < 0) {
				streak = 0;
				StreakName = "streal: " + streak;
				iter.remove();
				roblox.play();
			}
		}
	}
}
