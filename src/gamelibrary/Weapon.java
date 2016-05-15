package gamelibrary;

/**
 * Created by jiny1 on 5/16/2016.
 */
public abstract class Weapon extends GameObject {
	public abstract void Fire();

	public abstract PlayerBullet[] GetBullets();
}
