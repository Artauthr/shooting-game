package com.art.shooter;

import com.art.shooter.chars.MainCharacter;
import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.API;
import com.art.shooter.logic.CharacterManager;
import com.art.shooter.logic.CustomInputProcessor;
import com.art.shooter.logic.GameLogic;
import com.art.shooter.ui.ColorLibrary;
import com.art.shooter.ui.GameUI;
import com.art.shooter.utils.Resources;
import com.art.shooter.utils.screenUtils.DebugLineRenderer;
import com.art.shooter.utils.Utils;
import com.art.shooter.utils.screenUtils.Grid;
import com.art.shooter.utils.screenUtils.MovementGrid;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ShooterGame extends ApplicationAdapter {
	private PolygonSpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private GameUI gameUI;
	private OrthographicCamera camera;
	
	@Override
	public void create () {
		batch = new PolygonSpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

		camera = new OrthographicCamera(1280,720);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		Utils.camera = camera;

		API.getInstance().init();
		gameUI = new GameUI(new ScreenViewport(), batch);
		API.register(gameUI);
		Gdx.input.setInputProcessor(new CustomInputProcessor());


		CharacterManager charManager = API.get(CharacterManager.class);
		charManager.createCharacter(MainCharacter.class);
		charManager.spawnEnemyAtRandom();


		//set up cursor
		Pixmap pixmap = new Pixmap(Gdx.files.internal("crosshair.png"));
		int xHotspot = pixmap.getWidth() / 2;
		int yHotspot = pixmap.getHeight() / 2;
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot));
		pixmap.dispose();

		//resources
		API.get(Resources.class).queueLoading();
	}

	private void actUI () {
		gameUI.act();
	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();

		final Resources resources = API.get(Resources.class);
		if (!resources.isLoaded()) {
			resources.updateLoading();
			return;
		}

		ScreenUtils.clear(ColorLibrary.CHARCOAL_GRAY.getColor());

		batch.setProjectionMatrix(camera.combined);

		batch.begin(); //main systems batch
		API.get(EntitySystem.class).drawEntities(batch);
		API.get(CharacterManager.class).drawCharacters(batch);

		if (!API.get(GameLogic.class).isPaused()) {
			API.get(CharacterManager.class).updateCharacters(deltaTime);
			API.get(EntitySystem.class).updateEntities(deltaTime);
		}
		batch.end();

		//ui related
//		actUI();
//		gameUI.draw();

		API.get(DebugLineRenderer.class).draw(shapeRenderer);
		API.get(Grid.class).renderDebug(shapeRenderer);
//		API.get(MovementGrid.class).renderDebug(shapeRenderer);
	}

	@Override
	public void resize(int width, int height) {
		API.get(GameUI.class).onResize();
	}

	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		API.dispose();
	}
}
