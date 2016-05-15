package gameobject;

import gamelibrary.GameObject;
import gamelibrary.GameObjectManager;
import loot.GameFrameSettings;
import loot.InputManager.ButtonState;
import loot.graphics.Viewport;

/**
 * Created by jiny1 on 5/12/2016.
 */
public class Player extends GameObject {
	private GameFrameSettings _settings;
	private Viewport _viewport;
	private double _speed;
	private ButtonState _space;
	private ButtonState _leftButton;
	private ButtonState _rightButton;
	private ButtonState _upButton;
	private ButtonState _downButton;

	private Weapon _weapon;

	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "Player");
		_weapon = new Rifle();
	}

	@Override
	public void Awake() {
		super.Awake();
		_settings = GameObjectManager.getGameFrameSettings();
		GameObjectManager.getImageResourceManager().LoadImage("Images/player.png", "player");
		image = GameObjectManager.getImageResourceManager().GetImage("player");

		_viewport = GameObjectManager.getViewport();
		_viewport.children.add(this);

		_space = GameObjectManager.getInputManager().buttons[1];
		_leftButton = GameObjectManager.getInputManager().buttons[3];
		_rightButton = GameObjectManager.getInputManager().buttons[4];
		_upButton = GameObjectManager.getInputManager().buttons[5];
		_downButton = GameObjectManager.getInputManager().buttons[6];

		Init();
	}

	void Init() {
		// TODO: Find out how to get image size
		pos_x = 0;
		pos_y = -(_settings.canvas_height / 2 - 50);
		pos_z = 0;
		radius_x = 50;
		radius_y = 50;
		_speed = 30;
	}

	@Override
	public void Update() {
		super.Update();

		// Move by arrow buttons
		if (_leftButton.isPressed && pos_x > -(_settings.canvas_width / 2 - radius_x))
			pos_x -= GameObjectManager.DELTATIME * _speed;
		if (_rightButton.isPressed && pos_x < _settings.canvas_width / 2 - radius_x)
			pos_x += GameObjectManager.DELTATIME * _speed;
		if (_upButton.isPressed && pos_y < _settings.canvas_height / 2 - radius_y)
			pos_y += GameObjectManager.DELTATIME * _speed;
		if (_downButton.isPressed && pos_y > -(_settings.canvas_height / 2 - radius_y))
			pos_y -= GameObjectManager.DELTATIME * _speed;

		// Fire
		if (_space.isPressed) _weapon.Fire();
	}
}
