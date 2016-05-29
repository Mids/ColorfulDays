package gameobject.enemy;

import gamelibrary.*;

import java.util.Random;

import static java.lang.Math.PI;

/**
 * Created by Jin on 2016-05-16.
 */
public class AnimalManager extends EnemyManager {
	private final float REGENTIME = 0.6f;
	private final int NUMOFENEMIES = 20;

	private Random rand;

	@Override
	public void Awake() {
		rand = new Random();
		super.Awake();
	}

	@Override
	protected void InitEnemyImages() {
		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/flower_blue.png", "flower_blue");
		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/flower_blue_m.png", "flower_blue_m");
	}

	@Override
	protected Enemy getEnemyInstance() {
		return new Animal();
	}

	@Override
	public float getRegenTime() {
		return REGENTIME;
	}

	@Override
	public int getNumOfEnemies() {
		return NUMOFENEMIES;
	}

	private class Animal extends Enemy {
		private Vector3 _speed = new Vector3(300, 0, 0);
		private double randStart;

		@Override
		public void Init() {
			super.Init();
			image = GameObjectManager.getImageResourceManager().GetImage("flower_blue_m");
			randStart = rand.nextDouble() * 2 * PI;
		}

		@Override
		public void Move() {
			super.Move();
			pos_x += Time.getTime().getDeltaTime() * _speed.x * Math.sin(randStart + 10 * pos_y / GameObjectManager.getGameFrameSettings().canvas_height);
		}

		@Override
		public void Colorize() {
			super.Colorize();
			image = GameObjectManager.getImageResourceManager().GetImage("flower_blue");
		}
	}
}
