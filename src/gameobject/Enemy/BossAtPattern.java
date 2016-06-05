package gameobject.enemy;

import gamelibrary.Bullet;
import gamelibrary.Time;

/**
 * Created by jiny1 on 6/5/2016.
 */
public class BossAtPattern extends BossCirclePattern {
	private final double ROUND = 2.8;
	private final double MINDELAY = 0.02;
	private int _offset = 0;
	private boolean _isFiring = false;
	private double _firingTime;

	public BossAtPattern(Boss boss) {
		super(boss);
	}

	@Override
	public void Init() {
		angle = 2 * Math.PI / _numOfBullets;
		angle *= ROUND;
		if (_bullets == null) {
			CreateBullets();
		} else {
			InitBullets();
		}
	}

	@Override
	public int getNumOfBullets() {
		return 60;
	}

	@Override
	public void Update() {
		//noinspection Duplicates
		if (_isFiring) {
			if (_firingTime > MINDELAY) {
				DelayedFire();
				_firingTime = 0;
			} else {
				_firingTime += Time.getTime().getDeltaTime();
			}
		}

		for (Bullet bullet : _bullets) {
			bullet.Update();
		}
	}

	@Override
	public void Fire() {
		_isFiring = true;
		_firingTime = 0;
	}

	private void DelayedFire() {
		if (_offset >= _bullets.length) {
			_isFiring = false;
			_offset = 0;
			return;
		}

		_bullets[_offset++].Fire();
	}
}
