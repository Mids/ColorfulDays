package gamelibrary;

import loot.ImageResourceManager;
import loot.InputManager;
import loot.graphics.Viewport;

import java.util.HashMap;
import java.util.Map;

public class GameObjectManager {
	private static Map<String, GameObject> GameObjectMap = new HashMap<String, GameObject>();
	private static ImageResourceManager imageResourceManager;
	private static InputManager inputManager;
	private static Viewport viewport;

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
		GameObjectMap.remove("key");
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
		for (Map.Entry<String, GameObject> entry : GameObjectMap.entrySet()) {
			entry.getValue().Update();
		}
	}

	public static void Awake() {
		for (Map.Entry<String, GameObject> entry : GameObjectMap.entrySet()) {
			entry.getValue().Awake();
		}
	}
}
