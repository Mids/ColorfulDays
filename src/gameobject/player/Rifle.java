package gameobject.player;

import gamelibrary.*;

import java.awt.*;
import java.util.Random;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class Rifle extends Weapon {
	private final int NUMBEROFBULLETS = 20;
	private final double COOLTIME = 0.15;

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
		GameObjectManager.getImageResourceManager().CreateTempImage(Color.RED, "bullet_r");
		GameObjectManager.getImageResourceManager().CreateTempImage(Color.GREEN, "bullet_g");
		GameObjectManager.getImageResourceManager().CreateTempImage(Color.BLUE, "bullet_b");
		super.Awake();
	}

	@Override
	protected Bullet getBullet() {
		return new RifleBullet();
	}

	@Override
	protected double getCoolTime() {
		return COOLTIME;
	}

	private class RifleBullet extends PlayerBullet {
		public boolean _isActive = false;
		private Vector3 _speed = new Vector3(0, 600, 0);

		private int _canvasHeight;
		private GameObject _player;
		private Random _rand = new Random();

		RifleBullet() {
			_player = GameObjectManager.GetObject("Player");
			_canvasHeight = GameObjectManager.getGameFrameSettings().canvas_height;
			radius_x = 5;
			radius_y = 5;
			Init();
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
			Init();
		}

		@Override
		public void Fire() {
			pos_x = _player.pos_x;
			pos_y = _player.pos_y + _player.radius_y / 2;
			_isActive = true;
		}
		
		@Override
		public void Init() {
			_isActive = false;
			pos_y = _canvasHeight;
			switch (_rand.nextInt(3)) {
				case 0:
					image = GameObjectManager.getImageResourceManager().GetImage("bullet_r");
					break;
				case 1:
					image = GameObjectManager.getImageResourceManager().GetImage("bullet_g");
					break;
				case 2:
					image = GameObjectManager.getImageResourceManager().GetImage("bullet_b");
					break;
			}
		}
	}
}
