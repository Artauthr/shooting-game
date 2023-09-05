package com.art.shooter;

import com.art.shooter.chars.ADrawablePerson;
import com.art.shooter.chars.CommonShooterEnemy;
import com.art.shooter.chars.MainCharacter;
import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.CharacterManager;
import com.art.shooter.logic.CustomInputProcessor;
import com.art.shooter.logic.GameLogic;
import com.art.shooter.ui.ColorLibrary;
import com.art.shooter.ui.GameUI;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ShooterGame extends ApplicationAdapter {
	PolygonSpriteBatch batch;
	EntitySystem entitySystem;
	CharacterManager charManager;
	Viewport mainViewPort;
	ShapeRenderer shapeRenderer;
	float tmpTimer = 5f;
	int enemyCount = 1;
	int enemyCounter = 0;
	GameUI gameUI;
	GameLogic gameLogic;
	
	@Override
	public void create () {
		batch = new PolygonSpriteBatch();
		shapeRenderer = new ShapeRenderer();

		entitySystem = EntitySystem.getInstance();
		charManager = CharacterManager.getInstance();
		charManager.createCharacter(MainCharacter.class);
		gameLogic = GameLogic.getInstance();

		//viewport and camera stuff
		mainViewPort = new ExtendViewport(18, 10);
		OrthographicCamera camera = (OrthographicCamera) mainViewPort.getCamera();
		camera.position.set(charManager.getMainCharacter().getPos().x, charManager.getMainCharacter().getPos().y, 0);
		camera.zoom = 15f;
		Utils.mainViewport = mainViewPort;

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
		entitySystem.drawEntities(batch);
		charManager.drawCharacters(batch);
		if (!gameLogic.isPaused()) {
			charManager.updateCharacters(deltaTime);
			entitySystem.updateEntities(deltaTime);
		}

//		gameUI.act();
		if (tmpTimer > 5f && enemyCounter < enemyCount)  {
			charManager.spawnEnemyAtRandom();
			tmpTimer = 0f;
			enemyCounter++;
		}


		batch.end();


		// SHAPE RENDERER ONLY FOR DEBUG
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // Line type for wireframe
		shapeRenderer.setColor(Color.RED); // Set the color, for example to red

		// Draw each bounding box; assuming `boundingBox` is your Rectangle object
		if (charManager.getCharacters().size > 2) {
			Array<ADrawablePerson> characters = charManager.getCharacters();
			CommonShooterEnemy enemy = (CommonShooterEnemy) characters.get(1);
			Rectangle bBox = enemy.getBoundingBox();
			shapeRenderer.rect(bBox.x, bBox.y, bBox.width, bBox.height);
		}

		if (charManager.getCharacters().size > 2) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
				ADrawablePerson e = charManager.getCharacters().get(1);
				float rotation = e.getCharacterSprite().getRotation();
				System.out.println("rotation = " + rotation);
				{

				}
			}
		}

		shapeRenderer.end();

	}

	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		entitySystem.dispose();
	}

	@Override
	public void resize(int width, int height) {
		mainViewPort.update(width, height);
	}
}
