package gameobject.enemy;

import gamelibrary.*;
import gameobject.player.Player;
import gameobject.ui.BackGround;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.PI;

/**
 * Created by jiny1 on 6/4/2016.
 */
public class Boss extends Enemy {
	public ColorBoss _colorBoss;
	protected Random _random;
	private Vector3 _speed = new Vector3(300, 0, 0);
	private double randStart;
	private int _canvasHeight;
	private double _entranceTime = 3;
	private int _maxLife = 10;
	private int _currentLife = _maxLife;

	private double _coolTime = 2;
	private double _coolDown = _coolTime;
	private Weapon[] _weapons = {new BossCirclePattern(this)};

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
		randStart = _random.nextDouble() * 2 * PI;
		_canvasHeight = GameObjectManager.getGameFrameSettings().canvas_height;

		_colorBoss = new ColorBoss(this);
	}

	@Override
	protected void Colorize() {
		if (_currentLife > 0) {
			_currentLife--;
			alpha = (float) _currentLife / _maxLife;
		} else {
			super.Colorize();
		}
	}

	@Override
	public void Update() {
		if (_entranceTime > 0) {
			Move();
			_entranceTime -= Time.getTime().getDeltaTime();
		}

		if (!_isColored) {
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
		_weapons[_random.nextInt(_weapons.length)].Fire();
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
