package gamelibrary;

import gameobject.player.Player;

/**
 * Created by Jin on 2016-05-23.
 */
public abstract class EnemyBullet extends Bullet {
	protected Vector3 _speed = new Vector3(0, -600, 0);
	protected Player _player;

	protected EnemyBullet() {
		_player = (Player) GameObjectManager.GetObject("Player");
	}

	@Override
	public Tag getTag() {
		return Tag.EnemyBullet;
	}

	@Override
	public boolean IsCollided(Collider other) {
		return other.getTag() == Tag.Player;
	}

	protected void CheckCollision() {
		if (_isActive) {
			// Hit by bullet
			Bullet[] playerBullets = _player.getWeapon().GetBullets();
			for (Bullet bullet : playerBullets) {
				if (bullet._isActive && bullet.HitTest3D(this)) {
					bullet.Destroy();
					Destroy();
					return;
				}
			}

			// TODO: Hit player
			if (_player.HitTest3D(this)) {
//				System.out.println("Game Over!!");
			}
		}
	}

	public void Init() {
		_isActive = false;
	}

	@Override
	public void Destroy() {
		Init();
	}

	protected void Move() {
		pos_x += Time.getTime().getDeltaTime() * _speed.x;
		pos_y += Time.getTime().getDeltaTime() * _speed.y;
	}
}
