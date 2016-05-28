package gameobject.enemy;

import gamelibrary.Enemy;
import gamelibrary.EnemyManager;
import gamelibrary.GameObjectManager;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class FlowerManager extends EnemyManager {
	private static final String KEY = "FlowerManager";
	private final int REGENTIME = 20;
	private final int NUMOFENEMIES = 20;

	@Override
	public void Start() {
		GameObjectManager.PutObject(this, KEY);
	}

	@Override
	protected void InitEnemyImages() {
		GameObjectManager.getImageResourceManager().LoadImage("Images/bullet.png", "bullet");
		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/flower_red.png", "flower_red");
		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/flower_red_m.png", "flower_red_m");
	}

	@Override
	public void Awake() {
		super.Awake();
	}

	@Override
	protected Enemy getEnemyInstance() {
		return new Flower();
	}

	@Override
	public int getRegenTime() {
		return REGENTIME;
	}

	@Override
	public int getNumOfEnemies() {
		return NUMOFENEMIES;
	}

	@Override
	public void Destroy() {
		GameObjectManager.DeleteObject(KEY);
	}

	// Flower
	private class Flower extends Enemy {
		@Override
		public void Init() {
			super.Init();
			image = GameObjectManager.getImageResourceManager().GetImage("flower_red_m");
		}

		@Override
		public void Colorize() {
			super.Colorize();
			image = GameObjectManager.getImageResourceManager().GetImage("flower_red");
		}
	}
}
