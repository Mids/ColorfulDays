package gameobject.enemy;

import gamelibrary.*;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.PI;

/**
 * Created by jiny1 on 5/30/2016.
 */
public class MachineManager extends EnemyManager {
	private final float REGENTIME = 0.7f;
	private final int NUMOFENEMIES = 20;

	@Override
	public void Awake() {
		_random = new Random();
		super.Awake();
	}

	@Override
	protected void InitEnemyImages() {
		GameObjectManager.getImageResourceManager().LoadImage("Images/robots/Stage3_robot.png", "robot_m");
		GameObjectManager.getImageResourceManager().LoadImage("Images/robots/Stage3_robot_blue.png", "robot_b");
		GameObjectManager.getImageResourceManager().LoadImage("Images/robots/Stage3_robot_green.png", "robot_g");
		GameObjectManager.getImageResourceManager().LoadImage("Images/robots/Stage3_robot_red.png", "robot_r");
		GameObjectManager.getImageResourceManager().LoadImage("Images/robots/Stage3_robot_yellow.png", "robot_y");
	}

	@Override
	protected Enemy getEnemyInstance() {
		return new Machine();
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
		return " BOSS ";
	}

	private class Machine extends Enemy {
		private Image colorImage;
		private Vector3 _speed = new Vector3(300, 0, 0);
		private double randStart;
		private int _canvasHeight;

		@Override
		public void Init() {
			super.Init();
			image = GameObjectManager.getImageResourceManager().GetImage("robot_m");
			switch (_random.nextInt(4)) {
				case 0:
					colorImage = GameObjectManager.getImageResourceManager().GetImage("robot_b");
					break;
				case 1:
					colorImage = GameObjectManager.getImageResourceManager().GetImage("robot_g");
					break;
				case 2:
					colorImage = GameObjectManager.getImageResourceManager().GetImage("robot_r");
					break;
				case 3:
					colorImage = GameObjectManager.getImageResourceManager().GetImage("robot_y");
					break;
			}
			radius_x = 26;
			radius_y = 50;
			randStart = _random.nextDouble() * 2 * PI;
			_canvasHeight = GameObjectManager.getGameFrameSettings().canvas_height;
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

		@Override
		protected Weapon getRifle() {
			return new EnemyGuidedRifle(this);
		}
	}
}
