package gameobject.enemy;

import gamelibrary.*;

import java.awt.*;

/**
 * Created by jiny1 on 6/5/2016.
 */
public class BossLaserPattern extends Weapon {
	public final double _absSpeed = 600;
	private final int WAYS = 5;
	private final double STARTANGLE = Math.PI * 3 / 4;
	private final double MINDELAY = 0.02;
	protected double angle;
	private Boss _boss;
	private int _offset = 0;
	private boolean _isFiring = false;
	private double _firingTime;

	public BossLaserPattern(Boss boss) {
		super();
		_boss = boss;
	}

	@Override
	public void Init() {
		angle = (2 * Math.PI) / 4 / (WAYS - 1);
		super.Init();
	}

	@Override
	public int getNumOfBullets() {
		return 50 * WAYS;
	}

	@Override
	protected Bullet getBullet() {
		return new CircleBullet();
	}

	@Override
	protected double getCoolTime() {
		return 0;
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

		for (int i = _offset; i < _offset + WAYS; i++) {
			_bullets[i].Fire();
		}
		_offset += WAYS;
	}

	@Override
	protected void CreateBullets() {
		_bullets = new Bullet[_numOfBullets];
		for (int i = 0; i < _numOfBullets; i++) {
			Bullet bullet = getBullet();
			_viewport.children.add(bullet);
			bullet.Init();
			double direction = angle * (i % WAYS) + STARTANGLE;
			bullet.SetSpeed(new Vector3(Math.sin(direction) * _absSpeed, Math.cos(direction) * _absSpeed, 0));
			_bullets[i] = bullet;
		}
	}

	public class CircleBullet extends EnemyBullet {
		CircleBullet() {
			super();
			pos_y = _canvasHeight;
			radius_x = 5;
			radius_y = 5;
			GameObjectManager.getImageResourceManager().CreateTempImage(Color.lightGray, "bullet_m");
			image = GameObjectManager.getImageResourceManager().GetImage("bullet_m");
		}

		@Override
		public void Init() {
			super.Init();
			pos_y = _canvasHeight;
		}

		@Override
		public void Fire() {
			pos_x = _boss.pos_x;
			pos_y = _boss.pos_y;
			_isActive = true;
		}
	}
}
