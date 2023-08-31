package com.art.shooter;

import com.art.shooter.chars.ADrawablePerson;
import com.art.shooter.chars.MainCharacter;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class ShooterGame extends ApplicationAdapter {
	PolygonSpriteBatch batch;
	Texture img;
	MainCharacter mainCharacter;
	
	@Override
	public void create () {
		batch = new PolygonSpriteBatch();
		img = new Texture("shooter.png");
		mainCharacter = new MainCharacter();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 0.4f);
		batch.begin();
		mainCharacter.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	// OVERRIDES

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
