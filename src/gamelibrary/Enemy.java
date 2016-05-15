package gamelibrary;

/**
 * Created by jiny1 on 5/16/2016.
 */
public abstract class Enemy extends GameObject implements Collider {
	@Override
	public Tag getTag() {
		return Tag.Enemy;
	}

	@Override
	public boolean IsCollided(Collider other) {
		return other.getTag() == Tag.PlayerBullet;
	}
}
