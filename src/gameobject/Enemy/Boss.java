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
public class Boss extends Enemy{
	protected Random _random;
	private Vector3 _speed = new Vector3(300, 0, 0);
	private double randStart;
	private int _canvasHeight;
	public ColorBoss _colorBoss;

	private double _entranceTime = 3;
	private int _maxLife = 10;
	private int _currentLife = _maxLife;

	@Override
	public void Init() {
		_player = (Player) GameObjectManager.GetObject("Player");
		pos_y = GameObjectManager.getGameFrameSettings().canvas_height;
		radius_x = 250;
		radius_y = 100;
		_isColored = false;
		_isActive = true;
		_backgroundSpeed = BackGround.SPEED;
		if (_weapon == null) _weapon = getRifle();
		_weapon.Awake();
		GameObjectManager.getImageResourceManager().CreateTempImage(Color.lightGray, "boss");
		image = GameObjectManager.getImageResourceManager().GetImage("boss");
		_random = new Random();
		randStart = _random.nextDouble() * 2 * PI;
		_canvasHeight = GameObjectManager.getGameFrameSettings().canvas_height;

		_colorBoss = new ColorBoss(this);
	}

	@Override
	protected void Move() {
		System.out.println("move");
		if (_entranceTime > 0) {
			pos_y += Time.getTime().getDeltaTime() * _backgroundSpeed;
			_entranceTime -= Time.getTime().getDeltaTime();
		}
	}

	@Override
	protected void Colorize() {
		if(_currentLife > 0) {
			_currentLife--;
			alpha = (float)_currentLife / _maxLife;
		} else {
			super.Colorize();
		}
	}

	@Override
	protected EnemyRifle getRifle() {
		return super.getRifle();
	}

	@Override
	public void Update() {
		super.Update();
		_colorBoss.Follow();
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
