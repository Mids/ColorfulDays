package gameobject.player;

import gamelibrary.*;
import loot.GameFrameSettings;
import loot.InputManager.ButtonState;
import loot.graphics.Viewport;

/**
 * Created by jiny1 on 5/12/2016.
 */
public class Player extends GameObject implements Collider {
	private GameFrameSettings _settings;
	private Viewport _viewport;
	private double _speed;
	private ButtonState _space;
	private ButtonState _leftButton;
	private ButtonState _rightButton;
	private ButtonState _upButton;
	private ButtonState _downButton;
	private Weapon _weapon;
	private MonoPlayer _monoPlayer;

	private double _maxLife = 100;
	private double _currentLife = _maxLife;
	private double _hitCount = 0;
	private double _hitTime = 2;
	private double _regen = 20;

	public Weapon getWeapon() {
		return _weapon;
	}

	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "Player");
		_weapon = new Rifle();
	}

	@Override
	public void Awake() {
		_settings = GameObjectManager.getGameFrameSettings();
		GameObjectManager.getImageResourceManager().LoadImage("Images/player.png", "player");
		GameObjectManager.getImageResourceManager().LoadImage("Images/player_m.png", "player_m");
		image = GameObjectManager.getImageResourceManager().GetImage("player");

		_viewport = GameObjectManager.getViewport();
		_viewport.children.add(this);

		_space = GameObjectManager.getInputManager().buttons[1];
		_leftButton = GameObjectManager.getInputManager().buttons[3];
		_rightButton = GameObjectManager.getInputManager().buttons[4];
		_upButton = GameObjectManager.getInputManager().buttons[5];
		_downButton = GameObjectManager.getInputManager().buttons[6];

		Init();
		_monoPlayer = new MonoPlayer(this);
		_viewport.children.add(_monoPlayer);
	}

	void Init() {
		// TODO: Find out how to get image size
		pos_x = 0;
		pos_y = -(_settings.canvas_height / 2 - 50);
		pos_z = 0;
		radius_x = 50;
		radius_y = 50;
		_speed = 300;
	}

	@Override
	public void Update() {
		super.Update();
		if (_hitCount > 0) {
			_hitCount -= Time.getTime().getDeltaTime();
			if (_hitCount <= 0) _hitCount = 0;
		}
		if (_currentLife < _maxLife) {
			_currentLife += _regen * Time.getTime().getDeltaTime();
			if (_currentLife >= _maxLife) {
				_currentLife = _maxLife;
				alpha = 1;
			} else {
				alpha = (float) (_currentLife / _maxLife);
			}
		}

		// Move by arrow buttons
		if (_leftButton.isPressed && pos_x > -(_settings.canvas_width / 2 - radius_x))
			pos_x -= Time.getTime().getDeltaTime() * _speed;
		if (_rightButton.isPressed && pos_x < _settings.canvas_width / 2 - radius_x)
			pos_x += Time.getTime().getDeltaTime() * _speed;
		if (_upButton.isPressed && pos_y < _settings.canvas_height / 2 - radius_y)
			pos_y += Time.getTime().getDeltaTime() * _speed;
		if (_downButton.isPressed && pos_y > -(_settings.canvas_height / 2 - radius_y))
			pos_y -= Time.getTime().getDeltaTime() * _speed;

		// Fire
		if (_space.isPressed && alpha > 0.7) _weapon.Fire();

		_monoPlayer.Update();
	}

	@Override
	public Tag getTag() {
		return Tag.Player;
	}

	public void GotHit() {
		if (_hitCount == 0) {
			_currentLife -= 40;
			if (_currentLife < 0) _currentLife = 0;

			_hitCount = _hitTime;
		}
	}

	@Override
	public boolean IsCollided(Collider other) {
		return other.getTag() == Tag.Enemy || other.getTag() == Tag.EnemyBullet;
	}

	private class MonoPlayer extends GameObject {
		private Player _origin;

		MonoPlayer(Player player) {
			_origin = player;
			pos_x = _origin.pos_x;
			pos_y = _origin.pos_y;
			pos_z = _origin.pos_z;
			radius_x = _origin.radius_x;
			radius_y = _origin.radius_y;
			image = GameObjectManager.getImageResourceManager().GetImage("player_m");
		}

		@Override
		public void Update() {
			pos_x = _origin.pos_x;
			pos_y = _origin.pos_y;
		}
	}
}
