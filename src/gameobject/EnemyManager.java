package gameobject;

import gamelibrary.GameObject;
import gamelibrary.GameObjectManager;
import gamelibrary.Vector3;
import loot.GameFrameSettings;
import loot.graphics.Viewport;

import java.awt.*;
import java.util.Random;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class EnemyManager extends GameObject {
	private final int _regenTime = 20;
	private final int _numOfEnemys = 20;
	private GameFrameSettings _settings;
	private Viewport _viewport;
	private GameObject _player;
	private Enemy[] _enemys;
	private Random _random;
	private int _timeLeft;
	private int offset;

	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "EnemyManager");
	}

	@Override
	public void Awake() {
		_settings = GameObjectManager.getGameFrameSettings();
		_viewport = GameObjectManager.getViewport();
		_random = new Random();
		GameObjectManager.getImageResourceManager().LoadImage("Images/flower.png", "flower");
		Image flowerImage = GameObjectManager.getImageResourceManager().GetImage("flower");

		_enemys = new Enemy[_numOfEnemys];
		for (int i = 0; i < _numOfEnemys; i++) {
			Enemy enemy = new Enemy();
			enemy.pos_y = _settings.canvas_height;
			enemy.radius_x = 50;
			enemy.radius_y = 50;
			enemy.image = flowerImage;
			_viewport.children.add(enemy);
			_enemys[i] = enemy;
		}

		_player = GameObjectManager.GetObject("Player");
	}

	@Override
	public void Update() {
		if (_timeLeft > 0)
			--_timeLeft;
		else {
			RegenerateEnemy();
		}

		for (Enemy enemy : _enemys) {
			if (enemy.pos_y > -_settings.canvas_height / 2 - 50)
				enemy.Move();
		}
	}

	private void RegenerateEnemy() {
		// Consider enemy's radius (50)
		_enemys[offset].pos_x = _random.nextInt(_settings.canvas_width - 100) - _settings.canvas_width / 2 + 50;
		_enemys[offset].pos_y = _settings.canvas_height / 2 + 50;

		if (++offset >= _numOfEnemys) offset = 0;

		_timeLeft = _regenTime;
	}

	private class Enemy extends GameObject {
		private Vector3 _speed = new Vector3(0, -5, 0);

		void Move() {
			pos_y += _speed.y;
		}
	}
}
