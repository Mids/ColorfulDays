package gameobject.enemy;

import gamelibrary.*;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.PI;

/**
 * Created by jiny1 on 5/30/2016.
 */
public class MachinManager extends EnemyManager {
	private final float REGENTIME = 0.7f;
	private final int NUMOFENEMIES = 20;

	private Random rand;

	@Override
	public void Awake() {
		rand = new Random();
		super.Awake();
	}

	@Override
	protected void InitEnemyImages() {
//		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/flower_blue.png", "flower_blue");
//		GameObjectManager.getImageResourceManager().LoadImage("Images/flowers/flower_blue_m.png", "flower_blue_m");
		GameObjectManager.getImageResourceManager().CreateTempImage(Color.red, "machine");
		GameObjectManager.getImageResourceManager().CreateTempImage(Color.black, "machine_m");
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
		return "TheEnd";
	}

	private class Machine extends Enemy {
		private Vector3 _speed = new Vector3(300, 0, 0);
		private double randStart;

		@Override
		public void Init() {
			super.Init();
			image = GameObjectManager.getImageResourceManager().GetImage("machine_m");
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
			image = GameObjectManager.getImageResourceManager().GetImage("machine");
		}

		protected EnemyRifle getRifle() {
			return new EnemyGuidedRifle(this);
		}
	}
}
