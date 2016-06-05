package gameobject.enemy;

import gamelibrary.Enemy;
import gamelibrary.EnemyManager;
import gamelibrary.GameObjectManager;

import java.awt.*;
import java.util.Random;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class FlowerManager extends EnemyManager {
	private final float REGENTIME = 0.5f;
	private final int NUMOFENEMIES = 20;

	@Override
	protected void InitEnemyImages() {
		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/lotus1.png", "lotus1");
		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/lotus1_m.png", "lotus1_m");
		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/lotus2.png", "lotus2");
		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/lotus2_m.png", "lotus2_m");
		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/wflower.png", "wflower");
		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/wflower_m.png", "wflower_m");
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
		private Image colorImage;
		private Random _random = new Random();

		@Override
		public void Init() {
			super.Init();
			switch (_random.nextInt(3)) {
				case 0:
					image = GameObjectManager.getImageResourceManager().GetImage("lotus1_m");
					colorImage = GameObjectManager.getImageResourceManager().GetImage("lotus1");
					radius_x = 50;
					radius_y = 27;
					break;
				case 1:
					image = GameObjectManager.getImageResourceManager().GetImage("lotus2_m");
					colorImage = GameObjectManager.getImageResourceManager().GetImage("lotus2");
					radius_x = 50;
					radius_y = 30;
					break;
				case 2:
					image = GameObjectManager.getImageResourceManager().GetImage("wflower_m");
					colorImage = GameObjectManager.getImageResourceManager().GetImage("wflower");
					radius_x = 50;
					radius_y = 42.5;
					break;
			}
		}

		@Override
		public void Colorize() {
			super.Colorize();
			image = colorImage;
		}
	}
}
