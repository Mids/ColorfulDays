package gameobject;

import gamelibrary.*;

/**
 * Created by Jin on 2016-05-16.
 */
public class AnimalManager extends EnemyManager {

	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "AnimalManager");
	}

	@Override
	public void Awake() {
		GameObjectManager.getImageResourceManager().LoadImage("Images/flower.png", "flower");
		GameObjectManager.getImageResourceManager().LoadImage("Images/monoflower.png", "monoflower");

		super.Awake();
	}

	@Override
	protected Enemy getEnemyInstance() {
		return new Animal();
	}

	private class Animal extends Enemy {
		private Vector3 _speed = new Vector3(300, 0, 0);

		@Override
		public void Init() {
			super.Init();
			image = GameObjectManager.getImageResourceManager().GetImage("monoflower");
		}

		@Override
		public void Move() {
			super.Move();
			pos_x += Time.getTime().getDeltaTime() * _speed.x * Math.sin(10 * pos_y / GameObjectManager.getGameFrameSettings().canvas_height);
		}

		@Override
		public void Colorize() {
			super.Colorize();
			image = GameObjectManager.getImageResourceManager().GetImage("flower");
		}
	}
}
