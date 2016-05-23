package gameobject.enemy;

import gamelibrary.Enemy;
import gamelibrary.EnemyManager;
import gamelibrary.GameObjectManager;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class FlowerManager extends EnemyManager {
	private final int REGENTIME = 20;
	private final int NUMOFENEMIES = 20;

	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "FlowerManager");
	}

	@Override
	public void Awake() {
		GameObjectManager.getImageResourceManager().LoadImage("Images/flower.png", "flower");
		GameObjectManager.getImageResourceManager().LoadImage("Images/monoflower.png", "monoflower");

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

	// Flower
	private class Flower extends Enemy {
		@Override
		public void Init() {
			super.Init();
			image = GameObjectManager.getImageResourceManager().GetImage("monoflower");
		}

		@Override
		public void Colorize() {
			super.Colorize();
			image = GameObjectManager.getImageResourceManager().GetImage("flower");
		}
	}
}
