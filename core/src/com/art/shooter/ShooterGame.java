package com.art.shooter;

import com.art.shooter.chars.MainCharacter;
import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.CharacterManager;
import com.art.shooter.logic.CustomInputProcessor;
import com.art.shooter.logic.GameLogic;
import com.art.shooter.ui.ColorLibrary;
import com.art.shooter.ui.GameUI;
import com.art.shooter.utils.screenUtils.DebugLineRenderer;
import com.art.shooter.utils.Utils;
import com.art.shooter.utils.screenUtils.Grid;
import com.art.shooter.utils.screenUtils.GridCell;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ShooterGame extends ApplicationAdapter {
	private PolygonSpriteBatch batch;
	private EntitySystem entitySystem;
	private CharacterManager charManager;
	private Viewport mainViewPort;
	private ShapeRenderer shapeRenderer;
	private float tmpTimer = 5f;
	private float tmpTimer2 = 2f;
	private int enemyCount = 1;
	private int enemyCounter = 0;
	private GameUI gameUI;
	private GameLogic gameLogic;
	private int gridSize = 32;
	private OrthographicCamera camera;
	private DebugLineRenderer debugRenderer;
	private Grid grid;
	
	@Override
	public void create () {
		batch = new PolygonSpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

		entitySystem = EntitySystem.getInstance();

		gameLogic = GameLogic.getInstance();

		camera = new OrthographicCamera(1280,720);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		Utils.camera = camera;

		grid = Grid.getInstance();

		debugRenderer = new DebugLineRenderer();


		Gdx.input.setInputProcessor(new CustomInputProcessor());
		gameUI = new GameUI(new ScreenViewport(), batch);

		charManager = CharacterManager.getInstance();
		charManager.createCharacter(MainCharacter.class);
//		charManager.spawnEnemyAtRandom();
	}

	@Override
	public void render () {
		ScreenUtils.clear(ColorLibrary.CHARCOAL_GRAY.getColor());

		float deltaTime = Gdx.graphics.getDeltaTime();
		tmpTimer += deltaTime;
		tmpTimer2 += deltaTime;


		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		entitySystem.drawEntities(batch);
		charManager.drawCharacters(batch);
		if (!gameLogic.isPaused()) {
			charManager.updateCharacters(deltaTime);
			entitySystem.updateEntities(deltaTime);
		}

//		if (tmpTimer > 5f && enemyCounter < enemyCount)  {
//			charManager.spawnEnemyAtRandom();
//			tmpTimer = 0f;
//			enemyCounter++;
//		}

		batch.end();

		debugRenderer.draw(shapeRenderer);

		grid.debug(shapeRenderer);

	}

	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		entitySystem.dispose();
		charManager.dispose();
	}


}
