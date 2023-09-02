package com.art.shooter;

import com.art.shooter.chars.MainCharacter;
import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.CharacterManager;
import com.art.shooter.logic.CustomInputProcessor;
import com.art.shooter.ui.ColorLibrary;
import com.art.shooter.ui.GameUI;
import com.art.shooter.utils.MiscUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ShooterGame extends ApplicationAdapter {
	PolygonSpriteBatch batch;
	EntitySystem entitySystem;
	CharacterManager charManager;
	Viewport mainViewPort;
	float tmpTimer = 5f;
	GameUI gameUI;
	
	@Override
	public void create () {
		batch = new PolygonSpriteBatch();


		entitySystem = EntitySystem.getInstance();
		charManager = CharacterManager.getInstance();
		charManager.createCharacter(MainCharacter.class);

		//viewport and camera stuff
		mainViewPort = new ExtendViewport(800, 800);
		mainViewPort.getCamera().position.set(800, 400, 0);
		mainViewPort.getCamera().position.set(charManager.getMainCharacter().getPos().x, charManager.getMainCharacter().getPos().y, 0);
		MiscUtils.mainViewport = mainViewPort;

		Gdx.input.setInputProcessor(new CustomInputProcessor());
		gameUI = new GameUI(new ScreenViewport(), batch);

	}

	@Override
	public void render () {
		ScreenUtils.clear(ColorLibrary.CHARCOAL_GRAY.getColor());

		float deltaTime = Gdx.graphics.getDeltaTime();
		tmpTimer += deltaTime;

		mainViewPort.apply();
		batch.setProjectionMatrix(mainViewPort.getCamera().combined);
//		mainViewPort.getCamera().position.set(charManager.getMainCharacter().getPos().x, charManager.getMainCharacter().getPos().y, 0);

		batch.begin();
//		gameUI.act();
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
	}

	// OVERRIDES

	@Override
	public void resize(int width, int height) {
		mainViewPort.update(width, height);
	}
}
