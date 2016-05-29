package gameobject.enemy;

import gamelibrary.Enemy;
import gamelibrary.EnemyManager;
import gamelibrary.GameObjectManager;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class FlowerManager extends EnemyManager {
	private final float REGENTIME = 0.5f;
	private final int NUMOFENEMIES = 20;

	@Override
	protected void InitEnemyImages() {
		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/flower_red.png", "flower_red");
		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/flower_red_m.png", "flower_red_m");
	}

	@Override
	protected Enemy getEnemyInstance() {
		return new Flower();
	}

	@Override
	public float getRegenTime() {
		return REGENTIME;
	}

	@Override
	public int getNumOfEnemies() {
		return NUMOFENEMIES;
	}

	@Override
	public String getNextStage() {
		return "Stage 2";
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
