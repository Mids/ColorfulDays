package gamelibrary;

/**
 * Created by jiny1 on 5/16/2016.
 */
public interface Collider {
	Tag getTag();

	boolean IsCollided(Collider other);

	enum Tag {
		Player,
		Enemy,
		PlayerBullet,
		EnemyBullet
	}
}
