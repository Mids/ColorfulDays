package gameobject.enemy;

import gamelibrary.*;

import java.awt.*;
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
		GameObjectManager.getImageResourceManager().LoadImage("Images/animals/butterfly.png", "butterfly");
		GameObjectManager.getImageResourceManager().LoadImage("Images/animals/butterfly_m.png", "butterfly_m");
		GameObjectManager.getImageResourceManager().LoadImage("Images/animals/parrot.png", "parrot");
		GameObjectManager.getImageResourceManager().LoadImage("Images/animals/parrot_m.png", "parrot_m");
		GameObjectManager.getImageResourceManager().LoadImage("Images/animals/rabbit.png", "rabbit");
		GameObjectManager.getImageResourceManager().LoadImage("Images/animals/rabbit_m.png", "rabbit_m");
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

	@Override
	public String getNextStage() {
		return "Stage 3";
	}

	private class Animal extends Enemy {
		private Image colorImage;
		private Vector3 _speed = new Vector3(300, 0, 0);
		private double randStart;
		private int _canvasHeight;

		@Override
		public void Init() {
			super.Init();
			image = GameObjectManager.getImageResourceManager().GetImage("flower_blue_m");
			radius_y = 50;
			radius_x = 50;
			randStart = rand.nextDouble() * 2 * PI;
			_canvasHeight = GameObjectManager.getGameFrameSettings().canvas_height;

			switch (_random.nextInt(3)) {
				case 0:
					image = GameObjectManager.getImageResourceManager().GetImage("butterfly_m");
					colorImage = GameObjectManager.getImageResourceManager().GetImage("butterfly");
					radius_x = 50;
					radius_y = 28.5;
					break;
				case 1:
					image = GameObjectManager.getImageResourceManager().GetImage("parrot_m");
					colorImage = GameObjectManager.getImageResourceManager().GetImage("parrot");
					radius_x = 50;
					radius_y = 50.5;
					break;
				case 2:
					image = GameObjectManager.getImageResourceManager().GetImage("rabbit_m");
					colorImage = GameObjectManager.getImageResourceManager().GetImage("rabbit");
					radius_x = 50;
					radius_y = 73;
					break;
			}
		}

		@Override
		public void Move() {
			super.Move();
			pos_x += Time.getTime().getDeltaTime() * _speed.x * Math.sin(randStart + 10 * pos_y / _canvasHeight);
		}

		@Override
		public void Colorize() {
			super.Colorize();
			image = colorImage;
		}
	}
}
