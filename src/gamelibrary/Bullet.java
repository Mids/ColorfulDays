package gamelibrary;

/**
 * Created by Jin on 2016-05-23.
 */
public abstract class Bullet extends GameObject implements Collider {
	protected Vector3 _speed;

	public abstract void Fire();

	public void Init() {

	}

	public void SetSpeed(Vector3 speed) {
		_speed = speed;
	}
}
