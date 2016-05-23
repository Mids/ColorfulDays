package gamelibrary;

import loot.GameFrameSettings;
import loot.graphics.Viewport;

/**
 * Created by jiny1 on 5/16/2016.
 */
public abstract class Weapon extends GameObject {
	private final double _coolTime = 0.15;
	private int _numOfBullets;
	private GameFrameSettings _settings;
	private Viewport _viewport;
	private GameObject _player;
	private PlayerBullet[] _bullets;
	private double _coolDown;
	private int offset = 0;

	public PlayerBullet[] GetBullets() {
		return _bullets;
	}
	public abstract int getNumOfBullets();

	@Override
	public void Awake() {
		_settings = GameObjectManager.getGameFrameSettings();
		_viewport = GameObjectManager.getViewport();
		_player = GameObjectManager.GetObject("Player");
		_numOfBullets = getNumOfBullets();
		InitBullets();

	}

	protected void InitBullets() {
		_bullets = new PlayerBullet[_numOfBullets];
		for (int i = 0; i < _numOfBullets; i++) {
			PlayerBullet bullet = getBullet();
			_viewport.children.add(bullet);
			_bullets[i] = bullet;
		}
	}

	@Override
	public void Update() {
		if (_coolDown > 0)
			_coolDown -= Time.getTime().getDeltaTime();

		for (PlayerBullet bullet : _bullets) {
			bullet.Update();
		}
	}

	public void Fire() {
		if (_coolDown > 0)
			return;
		_bullets[offset].Fire();

		if (++offset >= _numOfBullets) offset = 0;
		_coolDown = _coolTime;
	}

	protected abstract PlayerBullet getBullet();
}
