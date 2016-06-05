package gameobject.enemy;

import gamelibrary.Bullet;
import gamelibrary.Enemy;

/**
 * Created by jiny1 on 5/30/2016.
 */
public class EnemyGuidedRifle extends EnemyRifle {

	public EnemyGuidedRifle(Enemy enemy) {
		super(enemy);
	}

	@Override
	protected Bullet getBullet() {
		return new EnemyGuidedRifleBullet();
	}

	@Override
	protected double getCoolTime() {
		return 0.8;
	}

	private class EnemyGuidedRifleBullet extends EnemyRifleBullet {
		private final double _absSpeed = 600;

		@Override
		public void Fire() {
			super.Fire();

			double angle = Math.atan2(pos_x - _player.pos_x, pos_y - _player.pos_y);

			_speed.x = -Math.sin(angle) * _absSpeed;
			_speed.y = -Math.cos(angle) * _absSpeed;
		}
	}
}
