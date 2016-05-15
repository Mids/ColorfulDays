package gameobject;

import gamelibrary.*;
import loot.GameFrameSettings;
import loot.graphics.Viewport;

import java.awt.*;
import java.util.Random;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class FlowerManager extends EnemyManager {
	private final int _regenTime = 20;
	private final int _numOfEnemys = 20;
	private GameFrameSettings _settings;
	private Viewport _viewport;
	private Player _player;
	private Flower[] _flowers;
	private Random _random;
	private int _timeLeft;
	private int offset;

	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "FlowerManager");
	}

	@Override
	public void Awake() {
		_settings = GameObjectManager.getGameFrameSettings();
		_viewport = GameObjectManager.getViewport();
		_random = new Random();
		GameObjectManager.getImageResourceManager().LoadImage("Images/flower.png", "flower");
		Image flowerImage = GameObjectManager.getImageResourceManager().GetImage("flower");

		// Init flowers
		_flowers = new Flower[_numOfEnemys];
		for (int i = 0; i < _numOfEnemys; i++) {
			Flower flower = new Flower();
			flower.Init();
			flower.pos_y = _settings.canvas_height;
			flower.radius_x = 50;
			flower.radius_y = 50;
			flower.image = flowerImage;
			_viewport.children.add(flower);
			_flowers[i] = flower;
		}

		_player = (Player) GameObjectManager.GetObject("Player");
	}

	@Override
	public void Update() {
		if (_timeLeft > 0)
			--_timeLeft;
		else {
			RegenerateEnemy();
		}

		for (Flower flower : _flowers) {
			if (flower._isActive) {
				if (flower.pos_y > -_settings.canvas_height / 2 - 50)
					flower.Move();
				else
					flower.Destroy();
			}
		}
	}

	@Override
	public void RegenerateEnemy() {
		// Consider enemy's radius (50)
		_flowers[offset].pos_x = _random.nextInt(_settings.canvas_width - 100) - _settings.canvas_width / 2 + 50;
		_flowers[offset].pos_y = _settings.canvas_height / 2 + 50;
		_flowers[offset]._isActive = true;

		if (++offset >= _numOfEnemys) offset = 0;

		_timeLeft = _regenTime;
	}

	private class Flower extends Enemy {
		private boolean _isActive;
		private Vector3 _speed = new Vector3(0, -5, 0);

		void Move() {
			pos_y += _speed.y;
			CheckCollision();
		}

		@Override
		public void CheckCollision() {
			if (_isActive) {
				PlayerBullet[] playerBullets = _player.getWeapon().GetBullets();
				for (PlayerBullet playerBullet : playerBullets) {
					if (playerBullet.HitTest3D(this)) {
						playerBullet.Destroy();
						Destroy();
						return;
					}
				}
			}
		}

		@Override
		public void Destroy() {
			Init();
		}

		public void Init() {
			_isActive = false;
			pos_y = _settings.canvas_height;
		}
	}
}
