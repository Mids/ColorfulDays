package gamelibrary;

import loot.graphics.Viewport;

/**
 * Created by jiny1 on 5/16/2016.
 */
public abstract class Weapon extends GameObject {
	protected int _numOfBullets;
	protected Viewport _viewport;
	protected Bullet[] _bullets;
	private double _coolTime;
	private double _coolDown;
	private int offset = 0;

	public Bullet[] GetBullets() {
		return _bullets;
	}

	@Override
	public void Awake() {
		_viewport = GameObjectManager.getViewport();
		_coolTime = getCoolTime();
		_numOfBullets = getNumOfBullets();
		_isActive = false;
		Init();
	}

	protected void CreateBullets() {
		_bullets = new Bullet[_numOfBullets];
		for (int i = 0; i < _numOfBullets; i++) {
			Bullet bullet = getBullet();
			_viewport.children.add(bullet);
			bullet.Init();
			_bullets[i] = bullet;
		}
	}

	public void Init() {
		if (_bullets == null) {
			CreateBullets();
		} else {
			InitBullets();
		}
	}

	protected void InitBullets() {
		for (int i = 0; i < _numOfBullets; i++) {
			_bullets[i].Init();
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

	protected abstract double getCoolTime();
}
