package com.art.shooter;

import com.art.shooter.chars.ADrawablePerson;
import com.art.shooter.chars.MainCharacter;
import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.CharacterManager;
import com.art.shooter.ui.ColorLibrary;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class ShooterGame extends ApplicationAdapter {
	PolygonSpriteBatch batch;
	Texture img;
	MainCharacter mainCharacter;
	EntitySystem entitySystem;
	CharacterManager charManager;
	float tmpTimer = 5f;
	
	@Override
	public void create () {
		batch = new PolygonSpriteBatch();
		img = new Texture("shooter.png");
		mainCharacter = new MainCharacter();
		entitySystem = EntitySystem.getInstance();
		charManager = CharacterManager.getInstance();
		charManager.createCharacter(MainCharacter.class);

	}

	@Override
	public void render () {
		ScreenUtils.clear(ColorLibrary.CHARCOAL_GRAY.getColor());

		float deltaTime = Gdx.graphics.getDeltaTime();
		tmpTimer += deltaTime;

		batch.begin();
		if (tmpTimer > 5f) {
			charManager.spawnEnemyAtRandom();
			tmpTimer = 0f;
		}

		charManager.updateCharacters(batch, deltaTime);
		entitySystem.updateEntities(batch, deltaTime);
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
