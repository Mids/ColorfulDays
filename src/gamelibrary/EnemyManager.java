package gamelibrary;

import loot.GameFrameSettings;
import loot.graphics.Viewport;

import java.util.Random;

/**
 * Created by jiny1 on 5/16/2016.
 */
public abstract class EnemyManager extends GameObject {
	private int _regenTime;
	private int _numOfEnemies;

	private GameFrameSettings _settings;
	private Viewport _viewport;
	private Enemy[] _enemies;
	private Random _random;
	private int _timeLeft;
	private int offset;

	public void RegenerateEnemy() {
		_enemies[offset].pos_x = _random.nextInt(_settings.canvas_width - (int) _enemies[offset].radius_x * 2) - _settings.canvas_width / 2 + _enemies[offset].radius_x;
		_enemies[offset].pos_y = _settings.canvas_height / 2 + _enemies[offset].radius_y;
		_enemies[offset]._isActive = true;

		if (++offset >= _numOfEnemies) offset = 0;

		_timeLeft = _regenTime;
	}

	protected abstract void InitEnemyImages();

	@Override
	public void Awake() {
		InitEnemyImages();

		_regenTime = getRegenTime();
		_numOfEnemies = getNumOfEnemies();
		_settings = GameObjectManager.getGameFrameSettings();
		_viewport = GameObjectManager.getViewport();
		_random = new Random();

		// Init flowers
		_enemies = new Enemy[_numOfEnemies];
		for (int i = 0; i < _numOfEnemies; i++) {
			Enemy enemy = getEnemyInstance();
			enemy.Init();
			_viewport.children.add(enemy);
			_enemies[i] = enemy;
		}

	}

	@Override
	public void Update() {
		if (_timeLeft > 0)
			--_timeLeft;
		else {
			RegenerateEnemy();
		}

		for (Enemy enemy : _enemies) {
			if (enemy._isActive) {
				if (enemy.pos_y > -_settings.canvas_height / 2 - 50)
					enemy.Update();
				else
					enemy.Destroy();
			}
		}
	}

	protected abstract Enemy getEnemyInstance();

	public abstract int getRegenTime();

	public abstract int getNumOfEnemies();

	@Override
	public abstract void Destroy();
}
