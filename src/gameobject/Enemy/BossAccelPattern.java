package gameobject.enemy;

import gamelibrary.*;

import java.awt.*;

/**
 * Created by jiny1 on 6/5/2016.
 */
public class BossAccelPattern extends Weapon {
	private final double STARTSPEED = 600;
	private final int WAYS = 8;
	private final int WAVES = 8;
	private final double XSPEED = 300;
	private final double MINDELAY = 0.1;
	protected double angle;
	private Boss _boss;
	private int _offset = 0;
	private boolean _isFiring = false;
	private double _firingTime;

	public BossAccelPattern(Boss boss) {
		super();
		_boss = boss;
	}

	@Override
	public void Init() {
		angle = 2 * Math.PI / 4 / (WAYS - 1);
		super.Init();
	}

	@Override
	public int getNumOfBullets() {
		return WAVES * WAYS;
	}

	@Override
	protected Bullet getBullet() {
		return new AccelBullet();
	}

	@Override
	protected double getCoolTime() {
		return 0;
	}

	@Override
	public void Update() {
		//noinspection Duplicates
		if (_isFiring) {
			if (_firingTime >= MINDELAY) {
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
		InitBullets();
		_isFiring = true;
		_firingTime = MINDELAY;
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
			double xSpeed = 2 * XSPEED * (i % WAYS) / (WAYS - 1) - XSPEED;
			bullet.SetSpeed(new Vector3(xSpeed, STARTSPEED, 0));
			_bullets[i] = bullet;
		}
	}

	@Override
	protected void InitBullets() {
		for (int i = 0; i < _numOfBullets; i++) {
			_bullets[i].Init();
			double xSpeed = 2 * XSPEED * (i % WAYS) / (WAYS - 1) - XSPEED;
			_bullets[i].SetSpeed(new Vector3(xSpeed, STARTSPEED, 0));
		}
	}

	public class AccelBullet extends EnemyBullet {
		private final double _startAccel = 600;
		private double _accel;

		AccelBullet() {
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
			_accel = _startAccel;
		}

		@Override
		protected void Move() {
			_speed.y -= _accel * Time.getTime().getDeltaTime();
			_accel += 5 * _startAccel * Time.getTime().getDeltaTime();
			super.Move();
		}

		@Override
		public void Fire() {
			pos_x = _boss.pos_x;
			pos_y = _boss.pos_y;
			_isActive = true;
		}

		@Override
		public void SetSpeed(Vector3 speed) {
			super.SetSpeed(speed);
		}
	}
}
