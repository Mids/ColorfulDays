package gameobject.enemy;

import gamelibrary.*;

import java.awt.*;

/**
 * Created by Jin on 2016-05-23.
 */
public class EnemyRifle extends Weapon {
	private final int NUMBEROFBULLETS = 20;
	private final double COOLTIME = 0.5;
	protected GameObject _enemy;

	public EnemyRifle(Enemy enemy) {
		_enemy = enemy;
	}

	@Override
	public int getNumOfBullets() {
		return NUMBEROFBULLETS;
	}

	@Override
	protected Bullet getBullet() {
		return new EnemyRifleBullet();
	}

	@Override
	protected double getCoolTime() {
		return COOLTIME;
	}

	public class EnemyRifleBullet extends EnemyBullet {

		EnemyRifleBullet() {
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
			pos_x = _enemy.pos_x;
			pos_y = _enemy.pos_y - _enemy.radius_y / 2;
			_isActive = true;
		}
	}
}
