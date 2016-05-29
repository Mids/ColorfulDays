package gamelibrary;

import gameobject.enemy.AnimalManager;
import gameobject.enemy.FlowerManager;
import gameobject.ui.ScoreBoard;
import gameobject.ui.StageNumber;
import loot.GameFrameSettings;
import loot.ImageResourceManager;
import loot.InputManager;
import loot.graphics.Viewport;

import java.util.HashMap;
import java.util.Map;

public final class GameObjectManager {
	public static String _deleteKey;
	private static Map<String, GameObject> GameObjectMap = new HashMap<>();
	private static ImageResourceManager imageResourceManager;
	private static InputManager inputManager;
	private static Viewport viewport;
	private static GameFrameSettings gameFrameSettings;
	private static ScoreBoard scoreBoard;
	private static StageNumber _stageNumber;
	private static boolean _next = false;
	private static int _stage = 0;
	private static Class<? extends EnemyManager>[] _managers = new Class[]{AnimalManager.class, FlowerManager.class};

	public static ImageResourceManager getImageResourceManager() {
		return imageResourceManager;
	}

	public static void setImageResourceManager(ImageResourceManager imageResourceManager) {
		GameObjectManager.imageResourceManager = imageResourceManager;
	}

	public static void PutObject(GameObject gameObject, String key) {
		GameObjectMap.put(key, gameObject);
	}

	public static GameObject GetObject(String key) {
		return GameObjectMap.get(key);
	}

	public static void DeleteObject(String key) {
		_deleteKey = key;
	}

	public static InputManager getInputManager() {
		return inputManager;
	}

	public static void setInputManager(InputManager inputManager) {
		GameObjectManager.inputManager = inputManager;
	}

	public static Viewport getViewport() {
		return viewport;
	}

	public static void setViewport(Viewport viewport) {
		GameObjectManager.viewport = viewport;
	}

	public static void Update() {
		if (_next && _stage < 2) {
			try {
				_managers[_stage++].newInstance().Awake();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			_next = false;
		}
		for (Map.Entry<String, GameObject> entry : GameObjectMap.entrySet()) {
			entry.getValue().Update();
		}
		scoreBoard.Update();
		_stageNumber.Update();
	}

	public static void Awake() {
		for (Map.Entry<String, GameObject> entry : GameObjectMap.entrySet()) {
			entry.getValue().Awake();
		}
	}

	public static GameFrameSettings getGameFrameSettings() {
		return gameFrameSettings;
	}

	public static void setGameFrameSettings(GameFrameSettings gameFrameSettings) {
		GameObjectManager.gameFrameSettings = gameFrameSettings;
	}

	public static ScoreBoard getScoreBoard() {
		return scoreBoard;
	}

	public static void setScoreBoard(ScoreBoard scoreBoard) {
		GameObjectManager.scoreBoard = scoreBoard;
	}

	public static StageNumber getStageNumber() {
		return _stageNumber;
	}

	public static void setStageNumber(StageNumber stageNumber) {
		_stageNumber = stageNumber;
	}

	public static void NextStage() {
		_next = true;
	}
}
