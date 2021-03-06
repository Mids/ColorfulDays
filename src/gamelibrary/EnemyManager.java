package gamelibrary;

import loot.GameFrameSettings;
import loot.graphics.Viewport;

import java.util.Random;

/**
 * Created by jiny1 on 5/16/2016.
 */
public abstract class EnemyManager extends GameObject {
	protected Random _random;
	protected GameFrameSettings _settings;
	protected Viewport _viewport;
	protected float _waitingTime = 4;
	protected float _destroyCount = -1;
	protected boolean _isStageRevealed = false;
	private float _regenTime;
	private int _numOfEnemies;
	private Enemy[] _enemies;
	private float _timeLeft;
	private int offset;

	public void RegenerateEnemy() {
		_enemies[offset].pos_x = _random.nextInt(_settings.canvas_width - (int) _enemies[offset].radius_x * 4) - _settings.canvas_width / 2 + _enemies[offset].radius_x;
		_enemies[offset].pos_y = _settings.canvas_height / 2 + _enemies[offset].radius_y;
		_enemies[offset]._isActive = true;

		if (++offset >= _numOfEnemies) offset = 0;
	}

	protected abstract void InitEnemyImages();

	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "EnemyManager");
	}

	@Override
	public void Awake() {
		InitEnemyImages();

		_regenTime = getRegenTime();
		_numOfEnemies = getNumOfEnemies();
		_settings = GameObjectManager.getGameFrameSettings();
		_viewport = GameObjectManager.getViewport();
		_random = new Random();
		_isActive = false;

		// Init flowers
		_enemies = new Enemy[_numOfEnemies];
		for (int i = 0; i < _numOfEnemies; i++) {
			Enemy enemy = getEnemyInstance();
			enemy.Init();
			_viewport.children.add(enemy);
			_enemies[i] = enemy;
		}

		GameObjectManager.getScoreBoard().ResetPoint();
	}

	@Override
	public void Update() {
		if (_waitingTime > 0) {
			_waitingTime -= Time.getTime().getDeltaTime();
			return;
		}

		if (_destroyCount > 0) {
			_destroyCount -= Time.getTime().getDeltaTime();

			if (!_isStageRevealed && _destroyCount < 3) {
				GameObjectManager.getStageNumber().Reveal(getNextStage());
				_isStageRevealed = true;
			}

			if (_destroyCount <= 0) {
				GameObjectManager.DeleteObject("EnemyManager");
				for (int i = 0; i < _numOfEnemies; i++) {
					_viewport.children.remove(_enemies[i]);
					_enemies[i] = null;
				}
				_enemies = null;

				GameObjectManager.NextStage();
				return;
			}
		} else if (_timeLeft > 0)
			_timeLeft -= Time.getTime().getDeltaTime();
		else {
			RegenerateEnemy();
			_timeLeft = _regenTime;
		}

		// Move Enemies
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

	public abstract float getRegenTime();

	public abstract int getNumOfEnemies();

	public abstract String getNextStage();

	@Override
	public void Destroy() {
		_destroyCount = 8;
	}
}
