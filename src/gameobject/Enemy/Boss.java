package gameobject.enemy;

import gamelibrary.*;
import gameobject.player.Player;
import gameobject.ui.BackGround;

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
	private boolean _isGreen = false;
	private boolean _startAttack = true;
	private boolean _isBerserk = false;

	private double _coolTime = 2;
	private double _coolDown = _coolTime;
	private Weapon[] _weapons = {new BossCirclePattern(this), new BossAtPattern(this), new BossLaserPattern(this), new BossAccelPattern(this)};

	@Override
	public void Init() {
		_player = (Player) GameObjectManager.GetObject("Player");
		pos_y = GameObjectManager.getGameFrameSettings().canvas_height;
		radius_x = 250;
		radius_y = 179;
		_isColored = false;
		_isActive = true;
		_backgroundSpeed = BackGround.SPEED;
		for (Weapon weapon : _weapons) {
			weapon.Awake();
		}
		GameObjectManager.getImageResourceManager().LoadImage("Images/boss/Stage3_Frog1.png", "boss_m");
		GameObjectManager.getImageResourceManager().LoadImage("Images/boss/Stage3_Frog2.png", "boss_g");
		image = GameObjectManager.getImageResourceManager().GetImage("boss_m");
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
			if (_currentLife < 50 && !_isBerserk) {
				GameObjectManager.getAudioManager().Play("danger");
				_isBerserk = true;
			}
		} else {
			_isColored = true;
			GameObjectManager.getAudioManager().Play("boss_die");
			GameObjectManager.GetObject("EnemyManager").Destroy();
		}
	}

	@Override
	public void Update() {
		if (_entranceTime > 0) {
			Move();
			_entranceTime -= Time.getTime().getDeltaTime();
			if (_entranceTime < 1 && _startAttack) {
				GameObjectManager.getAudioManager().Play("boss");
				_startAttack = false;
			}
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

		switch (_random.nextInt(2)) {
			case 0:
				GameObjectManager.getAudioManager().Play("boss_attack");
				break;
			case 1:
				GameObjectManager.getAudioManager().Play("boss_attack2");
				break;
		}

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

	public void MakeGreen(float alpha) {
		if (!_isGreen) {
			image = GameObjectManager.getImageResourceManager().GetImage("boss_g");
			_isGreen = true;
		}

		this.alpha = alpha;
	}

	class ColorBoss extends GameObject {
		private Boss _origin;

		ColorBoss(Boss origin) {
			_origin = origin;
			GameObjectManager.getImageResourceManager().LoadImage("Images/boss/Stage3_Frog1_color.png", "boss");
			image = GameObjectManager.getImageResourceManager().GetImage("boss");
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
