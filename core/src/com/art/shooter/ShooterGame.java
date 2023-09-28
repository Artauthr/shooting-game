package com.art.shooter;

import com.art.shooter.chars.CommonShooterEnemy;
import com.art.shooter.chars.MainCharacter;
import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.API;
import com.art.shooter.logic.CharacterManager;
import com.art.shooter.logic.CustomInputProcessor;
import com.art.shooter.logic.GameLogic;
import com.art.shooter.ui.ColorLibrary;
import com.art.shooter.ui.GameUI;
import com.art.shooter.utils.screenUtils.DebugLineRenderer;
import com.art.shooter.utils.Utils;
import com.art.shooter.utils.screenUtils.Grid;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ShooterGame extends ApplicationAdapter {
	private PolygonSpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private float tmpTimer = 5f;
	private float tmpTimer2 = 2f;
	private int enemyCount = 1;
	private int enemyCounter = 0;
	private GameUI gameUI;
	private OrthographicCamera camera;
	private DebugLineRenderer debugRenderer;
	
	@Override
	public void create () {
		batch = new PolygonSpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

		camera = new OrthographicCamera(1280,720);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		Utils.camera = camera;

		debugRenderer = new DebugLineRenderer();

		API.getInstance().init();
		gameUI = new GameUI(new ScreenViewport(), batch);
		API.register(gameUI);

		Gdx.input.setInputProcessor(new CustomInputProcessor());


		CharacterManager charManager = API.get(CharacterManager.class);
		charManager.createCharacter(MainCharacter.class);
		charManager.spawnEnemyAtRandom();

		Pixmap pixmap = new Pixmap(Gdx.files.internal("crosshair.png"));
		int xHotspot = pixmap.getWidth() / 2;
		int yHotspot = pixmap.getHeight() / 2;
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot));
		pixmap.dispose();
	}

	private void act (float delta) {
		gameUI.act();
	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();

		ScreenUtils.clear(ColorLibrary.CHARCOAL_GRAY.getColor());

		tmpTimer += deltaTime;
		tmpTimer2 += deltaTime;

		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		API.get(EntitySystem.class).drawEntities(batch);
		API.get(CharacterManager.class).drawCharacters(batch);

		if (!API.get(GameLogic.class).isPaused()) {
			API.get(CharacterManager.class).updateCharacters(deltaTime);
			API.get(EntitySystem.class).updateEntities(deltaTime);
		}

		batch.end();

		act(deltaTime);
		gameUI.draw();

		debugRenderer.draw(shapeRenderer);

		API.get(Grid.class).debug(shapeRenderer);

	}

	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		API.dispose();
	}


}
