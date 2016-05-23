package gameobject;

import gamelibrary.*;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class Rifle extends Weapon {
	private final int NUMBEROFBULLETS = 20;
	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "Rifle");
	}

	@Override
	public int getNumOfBullets() {
		return NUMBEROFBULLETS;
	}

	@Override
	public void Awake() {
		GameObjectManager.getImageResourceManager().LoadImage("Images/bullet.png", "bullet");
		super.Awake();
	}

	@Override
	protected Bullet getBullet() {
		return new RifleBullet();
	}

	private class RifleBullet extends PlayerBullet implements Collider {
		public boolean _isActive = false;
		private Vector3 _speed = new Vector3(0, 600, 0);

		private int _canvasHeight;
		private GameObject _player;

		RifleBullet() {
			_player =  GameObjectManager.GetObject("Player");
			_canvasHeight =GameObjectManager.getGameFrameSettings().canvas_height;
			pos_y = _canvasHeight;
			radius_x = 5;
			radius_y = 5;
			image = GameObjectManager.getImageResourceManager().GetImage("bullet");
		}

		void Move() {
			pos_y += Time.getTime().getDeltaTime() * _speed.y;
		}

		@Override
		public void Update() {
			if (_isActive) {
				if (pos_y < _canvasHeight / 2 + 5)
					Move();
				else
					Destroy();
			}
		}

		@Override
		public void Destroy() {
			_isActive = false;
			pos_y = _canvasHeight;
		}

		@Override
		public void Fire() {
			pos_x = _player.pos_x;
			pos_y = _player.pos_y + _player.radius_y / 2;
			_isActive = true;
		}
	}
}
