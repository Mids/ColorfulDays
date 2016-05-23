package gamelibrary;

import loot.graphics.Viewport;

/**
 * Created by jiny1 on 5/16/2016.
 */
public abstract class Weapon extends GameObject {
	private final double _coolTime = 0.15;
	private int _numOfBullets;
	private Viewport _viewport;
	private Bullet[] _bullets;
	private double _coolDown;
	private int offset = 0;

	public Bullet[] GetBullets() {
		return _bullets;
	}

	@Override
	public void Awake() {
		_viewport = GameObjectManager.getViewport();
		_numOfBullets = getNumOfBullets();
		InitBullets();

	}

	protected void InitBullets() {
		_bullets = new Bullet[_numOfBullets];
		for (int i = 0; i < _numOfBullets; i++) {
			Bullet bullet = getBullet();
			_viewport.children.add(bullet);
			_bullets[i] = bullet;
		}
	}

	@Override
	public void Update() {
		if (_coolDown > 0)
			_coolDown -= Time.getTime().getDeltaTime();

		for (Bullet bullet : _bullets) {
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

	public abstract int getNumOfBullets();

	protected abstract Bullet getBullet();
}
