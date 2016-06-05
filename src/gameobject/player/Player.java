package gameobject.player;

import gamelibrary.*;
import loot.GameFrameSettings;
import loot.InputManager.ButtonState;
import loot.graphics.Viewport;

import java.awt.*;

/**
 * Created by jiny1 on 5/12/2016.
 */
public class Player extends GameObject implements Collider {
	private final double _shootingDelay = 0.1;
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
	private Image[] _playerImages;
	private int _imageOffset = 0;
	private double _isShooting = 0;

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
		GameObjectManager.getImageResourceManager().LoadImage("Images/player/player1.png", "player1");
		GameObjectManager.getImageResourceManager().LoadImage("Images/player/player2.png", "player2");
		GameObjectManager.getImageResourceManager().LoadImage("Images/player/player3.png", "player3");
		GameObjectManager.getImageResourceManager().LoadImage("Images/player/player4.png", "player4");
		GameObjectManager.getImageResourceManager().LoadImage("Images/player/player1_m.png", "player1_m");
		GameObjectManager.getImageResourceManager().LoadImage("Images/player/player2_m.png", "player2_m");
		GameObjectManager.getImageResourceManager().LoadImage("Images/player/player3_m.png", "player3_m");
		GameObjectManager.getImageResourceManager().LoadImage("Images/player/player4_m.png", "player4_m");
		_playerImages = new Image[8];
		_playerImages[0] = GameObjectManager.getImageResourceManager().GetImage("player1");
		_playerImages[1] = GameObjectManager.getImageResourceManager().GetImage("player2");
		_playerImages[2] = GameObjectManager.getImageResourceManager().GetImage("player3");
		_playerImages[3] = GameObjectManager.getImageResourceManager().GetImage("player4");
		_playerImages[4] = GameObjectManager.getImageResourceManager().GetImage("player1_m");
		_playerImages[5] = GameObjectManager.getImageResourceManager().GetImage("player2_m");
		_playerImages[6] = GameObjectManager.getImageResourceManager().GetImage("player3_m");
		_playerImages[7] = GameObjectManager.getImageResourceManager().GetImage("player4_m");

		image = _playerImages[_imageOffset];

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
		radius_x = 16;
		radius_y = 50;
		pos_x = 0;
		pos_y = -(_settings.canvas_height / 2 - 150 - radius_y);
		pos_z = 0;
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

		if (_isShooting > 0) {
			_isShooting -= Time.getTime().getDeltaTime();
			if (_isShooting <= 0) {
				image = _playerImages[_imageOffset];
				_monoPlayer.image = _playerImages[_imageOffset + 4];
			}
		} else {
			// Move by arrow buttons
			if (_leftButton.isPressed && pos_x > -(_settings.canvas_width / 2 - 2 * radius_x)) {
				pos_x -= Time.getTime().getDeltaTime() * _speed;
				_imageOffset = 2;
				image = _playerImages[_imageOffset];
				_monoPlayer.image = _playerImages[_imageOffset + 4];
			}

			if (_rightButton.isPressed && pos_x < _settings.canvas_width / 2 - 2 * radius_x) {
				pos_x += Time.getTime().getDeltaTime() * _speed;
				_imageOffset = 0;
				image = _playerImages[_imageOffset];
				_monoPlayer.image = _playerImages[_imageOffset + 4];
			}

			if (GameObjectManager.getCurrentStage() != 1) {
				if (_upButton.isPressed && pos_y < _settings.canvas_height / 2 - radius_y)
					pos_y += Time.getTime().getDeltaTime() * _speed;
				if (_downButton.isPressed && pos_y > -(_settings.canvas_height / 2 - radius_y))
					pos_y -= Time.getTime().getDeltaTime() * _speed;
			}

			// Fire
			if (_space.isPressed && alpha > 0.7) {
				_weapon.Fire();
			}
		}

		_monoPlayer.Update();
	}

	@Override
	public Tag getTag() {
		return Tag.Player;
	}

	public void GotHit() {
		if (_hitCount == 0) {
			_currentLife -= 50;
			if (_currentLife < 0) _currentLife = 0;

			_hitCount = _hitTime;
		}
	}

	@Override
	public boolean IsCollided(Collider other) {
		return other.getTag() == Tag.Enemy || other.getTag() == Tag.EnemyBullet;
	}

	public void PlayerShotImage() {
		_isShooting = _shootingDelay;
		image = _playerImages[_imageOffset + 1];
		_monoPlayer.image = _playerImages[_imageOffset + 5];
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
