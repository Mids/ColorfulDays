package gamelibrary;

import gameobject.player.Player;
import gameobject.ui.BackGround;

/**
 * Created by jiny1 on 5/16/2016.
 */
public abstract class Enemy extends GameObject implements Collider {
	public boolean _isColored;
	public boolean _isActive;
	private double _backgroundSpeed;
	private Player _player;

	public void Init() {
		_player = (Player) GameObjectManager.GetObject("Player");
		pos_y = GameObjectManager.getGameFrameSettings().canvas_height;
		radius_x = 50;
		radius_y = 50;
		_isColored = false;
		_isActive = false;
		_backgroundSpeed = BackGround.SPEED;
	}

	protected void Move() {
		pos_y += Time.getTime().getDeltaTime() * _backgroundSpeed;
	}

	@Override
	public Tag getTag() {
		return Tag.Enemy;
	}

	@Override
	public boolean IsCollided(Collider other) {
		return other.getTag() == Tag.PlayerBullet;
	}

	@Override
	public void Destroy() {
		Init();
	}

	protected void CheckCollision() {
		if (!_isColored) {
			// Hit by bullet
			Bullet[] playerBullets = _player.getWeapon().GetBullets();
			for (Bullet bullet : playerBullets) {
				if (bullet.HitTest3D(this)) {
					bullet.Destroy();
					Colorize();
					return;
				}
			}

			// TODO: Hit player
			if (_player.HitTest3D(this)) {
//				System.out.println("Game Over");
			}
		}
	}

	protected void Colorize() {
		_isColored = true;
		GameObjectManager.getScoreBoard()._score++;
	}

	@Override
	public void Update() {
		Move();
		CheckCollision();
		Fire();
	}

	/**
	 * Not necessary
	 */
	protected void Fire() {

	}
}
