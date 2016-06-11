package gameobject.player;

import gamelibrary.*;

import java.awt.*;
import java.util.Random;

import static gamelibrary.GameObjectManager.*;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class Rifle extends Weapon {
	private final int NUMBEROFBULLETS = 20;
	private final double COOLTIME = 0.15;
	private Image[] _bulletImages = new Image[5];

	@Override
	public void Start() {
		PutObject(this, "Rifle");
	}

	@Override
	public int getNumOfBullets() {
		return NUMBEROFBULLETS;
	}

	@Override
	public void Awake() {
		getImageResourceManager().LoadImage("Images/player/bullet_r.png", "bullet_r");
		getImageResourceManager().LoadImage("Images/player/bullet_g.png", "bullet_g");
		getImageResourceManager().LoadImage("Images/player/bullet_b.png", "bullet_b");
		getImageResourceManager().LoadImage("Images/player/bullet_p.png", "bullet_p");
		getImageResourceManager().LoadImage("Images/player/bullet_y.png", "bullet_y");
		_bulletImages[0] = getImageResourceManager().GetImage("bullet_r");
		_bulletImages[1] = getImageResourceManager().GetImage("bullet_g");
		_bulletImages[2] = getImageResourceManager().GetImage("bullet_b");
		_bulletImages[3] = getImageResourceManager().GetImage("bullet_p");
		_bulletImages[4] = getImageResourceManager().GetImage("bullet_y");
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
		private int _canvasHeight;
		private GameObject _player;
		private Random _rand = new Random();

		RifleBullet() {
			_player = GetObject("Player");
			_canvasHeight = getGameFrameSettings().canvas_height;
			radius_x = 10;
			radius_y = 13;
			_speed = new Vector3(0, 600, 0);
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

			((Player) (GetObject("Player"))).PlayerShotImage();
		}

		@Override
		public void Init() {
			_isActive = false;
			pos_y = _canvasHeight;
			image = _bulletImages[_rand.nextInt(5)];
		}
	}
}
