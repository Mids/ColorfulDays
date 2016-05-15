package gameobject;

import gamelibrary.*;
import loot.GameFrameSettings;
import loot.graphics.Viewport;

import java.awt.*;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class Rifle extends Weapon {
	private final int _coolTime = 10;
	private final int _numOfBullets = 20;
	private GameFrameSettings _settings;
	private Viewport _viewport;
	private GameObject _player;
	private Bullet[] _bullets;
	private int _coolDown;
	private int offset = 0;


	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "Rifle");
	}

	@Override
	public void Awake() {
		_settings = GameObjectManager.getGameFrameSettings();
		_viewport = GameObjectManager.getViewport();
		GameObjectManager.getImageResourceManager().LoadImage("Images/bullet.png", "bullet");
		Image bulletImage = GameObjectManager.getImageResourceManager().GetImage("bullet");

		_bullets = new Bullet[_numOfBullets];
		for (int i = 0; i < _numOfBullets; i++) {
			Bullet bullet = new Bullet();
			bullet.pos_y = _settings.canvas_height;
			bullet.radius_x = 2;
			bullet.radius_y = 2;
			bullet.image = bulletImage;
			_viewport.children.add(bullet);
			_bullets[i] = bullet;
		}

		_player = GameObjectManager.GetObject("Player");
	}

	@Override
	public void Update() {
		if (_coolDown > 0)
			--_coolDown;

		for (Bullet bullet : _bullets) {
			if (bullet.pos_y < _settings.canvas_height / 2 + 5)
				bullet.Move();
		}
	}

	@Override
	public void Fire() {
		if (_coolDown > 0)
			return;
		_bullets[offset].pos_x = _player.pos_x;
		_bullets[offset].pos_y = _player.pos_y + _player.radius_y / 2;
		if (++offset >= _numOfBullets) offset = 0;
		_coolDown = _coolTime;
	}

	private class Bullet extends PlayerBullet implements Collider {
		private Vector3 _speed = new Vector3(0, 10, 0);

		void Move() {
			pos_y += _speed.y;
		}
	}
}
