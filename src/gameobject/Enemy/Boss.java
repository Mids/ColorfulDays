package gameobject.enemy;

import gamelibrary.*;
import gameobject.player.Player;
import gameobject.ui.BackGround;

import java.awt.*;
import java.util.Random;

/**
 * Created by jiny1 on 6/4/2016.
 */
public class Boss extends Enemy {
	public ColorBoss _colorBoss;
	protected Random _random;
	private double _entranceTime = 3;
	private int _maxLife = 100;
	private int _currentLife = _maxLife;
	private BackGround _backGround;

	private double _coolTime = 2;
	private double _coolDown = _coolTime;
	private Weapon[] _weapons = {new BossCirclePattern(this), new BossAtPattern(this), new BossLaserPattern(this), new BossAccelPattern(this)};

	@Override
	public void Init() {
		_player = (Player) GameObjectManager.GetObject("Player");
		pos_y = GameObjectManager.getGameFrameSettings().canvas_height;
		radius_x = 250;
		radius_y = 100;
		_isColored = false;
		_isActive = true;
		_backgroundSpeed = BackGround.SPEED;
		for (Weapon weapon : _weapons) {
			weapon.Awake();
		}
		GameObjectManager.getImageResourceManager().CreateTempImage(Color.lightGray, "boss");
		image = GameObjectManager.getImageResourceManager().GetImage("boss");
		_random = new Random();

		_colorBoss = new ColorBoss(this);
	}

	@Override
	protected void Colorize() {
		if (_currentLife > 0) {
			_currentLife--;
			alpha = (float) _currentLife / _maxLife;
			if (_backGround == null) _backGround = (BackGround) GameObjectManager.GetObject("BackGround");
			_backGround.Colorize(alpha);
		} else {
			_isColored = true;
			GameObjectManager.GetObject("EnemyManager").Destroy();
		}
	}

	@Override
	public void Update() {
		if (_entranceTime > 0) {
			Move();
			_entranceTime -= Time.getTime().getDeltaTime();
		} else if (!_isColored) {
			CheckCollision();
			Fire();
		}

		for (Weapon weapon : _weapons) {
			weapon.Update();
		}

		_colorBoss.Follow();
	}

	@Override
	protected void Fire() {
		if (_coolDown > 0) {
			_coolDown -= Time.getTime().getDeltaTime();
			return;
		}
		_coolDown = _coolTime;

		if ((double) _currentLife / _maxLife < 0.5) {
			int a = _random.nextInt(_weapons.length);
			int b = a;
			while (a == b) {
				b = _random.nextInt(_weapons.length);
			}
			_weapons[a].Fire();
			_weapons[b].Fire();
		} else {
			_weapons[_random.nextInt(_weapons.length)].Fire();
		}
	}

	class ColorBoss extends GameObject {
		private Boss _origin;

		ColorBoss(Boss origin) {
			_origin = origin;
			GameObjectManager.getImageResourceManager().CreateTempImage(Color.RED, "color_boss");
			image = GameObjectManager.getImageResourceManager().GetImage("color_boss");
			radius_x = _origin.radius_x;
			radius_y = _origin.radius_y;
			pos_y = _origin.pos_y;
			_isActive = true;
		}

		public void Follow() {
			pos_y = _origin.pos_y;
		}
	}
}
