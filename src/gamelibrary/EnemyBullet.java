package gamelibrary;

/**
 * Created by Jin on 2016-05-23.
 */
public abstract class EnemyBullet extends Bullet {
	@Override
	public Tag getTag() {
		return Tag.EnemyBullet;
	}

	@Override
	public boolean IsCollided(Collider other) {
		return other.getTag() == Tag.Player;
	}
}
