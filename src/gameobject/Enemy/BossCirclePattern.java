package gameobject.enemy;

import gamelibrary.*;

import java.awt.*;

/**
 * Created by jiny1 on 6/5/2016.
 */
public class BossCirclePattern extends Weapon {
	public final double _absSpeed = 600;
	private Boss _boss;

	public BossCirclePattern(Boss boss) {
		super();
		_boss = boss;
	}

	@Override
	public void Init() {
		super.Init();
	}

	@Override
	public int getNumOfBullets() {
		return 25;
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
		for (Bullet bullet : _bullets) {
			bullet.Update();
		}
	}

	@Override
	public void Fire() {
		super.Fire();
		for (Bullet bullet : _bullets) {
			bullet.Fire();
		}
	}

	@Override
	protected void CreateBullets() {
		double angle = 2 * Math.PI / _numOfBullets;

		_bullets = new Bullet[_numOfBullets];
		for (int i = 0; i < _numOfBullets; i++) {
			Bullet bullet = getBullet();
			_viewport.children.add(bullet);
			bullet.Init();
			double direction = angle * i;
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
