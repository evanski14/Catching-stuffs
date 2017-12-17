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
	private Texture angrypepeImg;
	private Texture bucketImg;
	private Music bgMusic;
	private Sound roblox;
	private Sound WOW;
	private Sound Triple;
	private Sound GetTheCam;
	private Sound Dota;
	private Sound faker;
	private Rectangle bucket;
	private Array<Rectangle> pepes;
	private long lastDropTime;
	private long lastDropTimeA;
	private Array<Rectangle> angrypepes;
	private int score;
	private String ScoreName;
	BitmapFont ScoreBitmapFontName;
	private int streak;
	private String StreakName;
	BitmapFont StreakBitmapFontName;
	private int health;
	private String HealthName;
	BitmapFont HealthBitmapFontName;
	private int highS;
	private String HighSName;
	BitmapFont HighSBitmapFontName;
	
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
	
	//random spawn angrypepe
	private void angrypepeSpawn() {
		Rectangle angrypepe = new Rectangle();
		angrypepe.x = MathUtils.random(0, 800-64);
		angrypepe.y = 600;
		angrypepe.width = 64;
		angrypepe.height = 64;
		angrypepes.add(angrypepe);
		lastDropTimeA = TimeUtils.nanoTime();
	}
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		//load textures
		pepeImg = new Texture(Gdx.files.internal("pepe.png"));
		bucketImg = new Texture("bucket.png");
		angrypepeImg = new Texture("AngryPepe.png");
		
		//load sounds
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Gold_Rush.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
		bgMusic.setVolume(0.1f);
		
		roblox = Gdx.audio.newSound(Gdx.files.internal("roblox.mp3"));
		WOW = Gdx.audio.newSound(Gdx.files.internal("WOW.mp3"));
		Triple = Gdx.audio.newSound(Gdx.files.internal("Triple.wav"));
		GetTheCam = Gdx.audio.newSound(Gdx.files.internal("GetTheCam.mp3"));
		Dota = Gdx.audio.newSound(Gdx.files.internal("Dota.mp3"));
		faker = Gdx.audio.newSound(Gdx.files.internal("faker.mp3"));
		
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
		angrypepes = new Array<Rectangle>();
		angrypepeSpawn();
		
		
		//ScoreBoard
		score = 0;
		ScoreName = "score: 0";
		ScoreBitmapFontName = new BitmapFont();
		
		//Streak
		streak = 0;
		StreakName = "streak: 0";
		StreakBitmapFontName = new BitmapFont();
		
		//Health
		health = 3;
		HealthName = "health: 3";
		HealthBitmapFontName = new BitmapFont();
		
		//Highest Streak
		highS = 0;
		HighSName = "highest streak: 0";
		HighSBitmapFontName = new BitmapFont();
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
		//draw health
		HealthBitmapFontName.setColor(Color.RED);
		HealthBitmapFontName.draw(batch, HealthName, 675, 525);
		//draw highest streak
		HighSBitmapFontName.setColor(Color.BLACK);
		HighSBitmapFontName.draw(batch, HighSName, 675, 500);
		//draw bucket
		batch.draw(bucketImg, bucket.x, bucket.y);
		//draw pepe
		for(Rectangle pepe: pepes) {
			batch.draw(pepeImg, pepe.x, pepe.y);
		}
		for(Rectangle angrypepe: angrypepes) {
			batch.draw(angrypepeImg, angrypepe.x, angrypepe.y);
		}
		batch.end();
		
		//bucket movement
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			bucket.x -= 450 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			bucket.x += 450 * Gdx.graphics.getDeltaTime();
		}
		
		//bucket hits the screen
		if(bucket.x < 0) {
			bucket.x = 0;
		}
		if(bucket.x > 800-64) {
			bucket.x = 800-64;
		}
		
		//pepe spawn timer
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000 ) {
			pepeSpawn();
		}
		
		//angry pepe spawn timer
		if(TimeUtils.nanoTime() - lastDropTimeA > 1700000000 ) {
			angrypepeSpawn();
		}
		
		//pepe movement
		Iterator<Rectangle> iter = pepes.iterator();
		while(iter.hasNext()) {
			Rectangle pepe = iter.next();
			pepe.y -= (200 + score*5) * Gdx.graphics.getDeltaTime();
			if(health <= 0) {
				health = 0;
				HealthName = "health: " + health;
				bgMusic.pause();
				break;
			}
			if(pepe.y + 64 < 0) {
				streak = 0;
				StreakName = "streak: " + streak;
				health -= 1;
				HealthName = "health: " + health;
				roblox.play();
				iter.remove();
			}
			if(pepe.overlaps(bucket)) {
				score++;
				ScoreName = "score: " + score;
				streak++;
				if(streak == 3) {
					Triple.play(0.1f);
				}
				if(streak == 20) {
					GetTheCam.play(0.5f);
				}
				if(streak == 10) {
					Dota.play();
				}
				if(score == 30 || score == 60 || score == 90) {
					faker.play(0.5f);
				}
				StreakName = "streak: " + streak;
				if(streak >= highS) {
					highS = streak;
					HighSName = "highest streak: " + highS;
				}
				iter.remove();
				WOW.play(0.8f);
			}
		}
		
		//angry pepe movement
		Iterator<Rectangle> iterr = angrypepes.iterator();
		while(iterr.hasNext()) {
			if(health <= 0 ) {
				health = 0;
				bgMusic.pause();
				break;
			}
			Rectangle angrypepe = iterr.next();
			angrypepe.y -=350 * Gdx.graphics.getDeltaTime();
			if(angrypepe.y + 64 < 0) {
				iterr.remove();
			}
			if(angrypepe.overlaps(bucket)) {
				iterr.remove();
				roblox.play();
				streak = 0;
				StreakName = "streak: " + streak;
			}
		}
		
	}
	
	@Override
	public void dispose() {
		bucketImg.dispose();
		pepeImg.dispose();
		angrypepeImg.dispose();
		bgMusic.dispose();
		roblox.dispose();
		WOW.dispose();
		Triple.dispose();
		GetTheCam.dispose();
		Dota.dispose();
		faker.dispose();
	}
}
