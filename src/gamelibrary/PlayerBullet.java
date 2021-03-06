package gamelibrary;

/**
 * Created by jiny1 on 5/16/2016.
 */
public abstract class PlayerBullet extends Bullet {
	@Override
	public Tag getTag() {
		return Tag.PlayerBullet;
	}

	@Override
	public boolean IsCollided(Collider other) {
		return other.getTag() == Tag.Enemy;
	}
}
