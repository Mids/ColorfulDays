package gamelibrary;

import java.util.HashMap;
import java.util.Map;

import loot.ImageResourceManager;
import loot.InputManager;

public class GameObjectManager {
	private static Map<String, GameObject> GameObjectMap = new HashMap<String, GameObject>();
	private static ImageResourceManager imageResourceManager;
	private static InputManager inputManager;

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

	public static InputManager getInputManager() {
		return inputManager;
	}

	public static void setInputManager(InputManager inputManager) {
		GameObjectManager.inputManager = inputManager;
	}

}
