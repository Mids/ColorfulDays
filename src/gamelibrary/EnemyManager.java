package gamelibrary;

import gameobject.player.Player;
import loot.GameFrameSettings;
import loot.graphics.Viewport;

import java.util.Random;

/**
 * Created by jiny1 on 5/16/2016.
 */
public abstract class EnemyManager extends GameObject {
	private final int _regenTime = 20;
	private final int _numOfEnemys = 20;

	private GameFrameSettings _settings;
	private Viewport _viewport;
	private Player _player;
	private Enemy[] _enemys;
	private Random _random;
	private int _timeLeft;
	private int offset;

	public void RegenerateEnemy() {
		_enemys[offset].pos_x = _random.nextInt(_settings.canvas_width - (int) _enemys[offset].radius_x * 2) - _settings.canvas_width / 2 + _enemys[offset].radius_x;
		_enemys[offset].pos_y = _settings.canvas_height / 2 + _enemys[offset].radius_y;
		_enemys[offset]._isActive = true;

		if (++offset >= _numOfEnemys) offset = 0;

		_timeLeft = _regenTime;
	}

	@Override
	public void Awake() {

		_settings = GameObjectManager.getGameFrameSettings();
		_viewport = GameObjectManager.getViewport();
		_random = new Random();

		_player = (Player) GameObjectManager.GetObject("Player");

		// Init flowers
		_enemys = new Enemy[_numOfEnemys];
		for (int i = 0; i < _numOfEnemys; i++) {
			Enemy enemy = getEnemyInstance();
			enemy.Init();
			_viewport.children.add(enemy);
			_enemys[i] = enemy;
		}

	}

	@Override
	public void Update() {
		if (_timeLeft > 0)
			--_timeLeft;
		else {
			RegenerateEnemy();
		}

		for (Enemy enemy : _enemys) {
			if (enemy._isActive) {
				if (enemy.pos_y > -_settings.canvas_height / 2 - 50)
					enemy.Update();
				else
					enemy.Destroy();
			}
		}
	}

	protected abstract Enemy getEnemyInstance();
}
