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
	GameFrameSettings _settings;
	Viewport viewport;
	double speed;
	ButtonState _leftButton;
	ButtonState _rightButton;
	ButtonState _upButton;
	ButtonState _downButton;

	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "Player");
	}

	@Override
	public void Awake() {
		super.Awake();
		_settings = GameObjectManager.getGameFrameSettings();
		GameObjectManager.getImageResourceManager().LoadImage("Images/player.png", "player");
		image = GameObjectManager.getImageResourceManager().GetImage("player");

		viewport = GameObjectManager.getViewport();
		viewport.children.add(this);

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
		speed = 30;
	}

	@Override
	public void Update() {
		super.Update();

		// Move by arrow buttons
		if (_leftButton.isPressed == true && pos_x > -(_settings.canvas_width / 2 - radius_x)) {
			pos_x -= GameObjectManager.DELTATIME * speed;
		}
		if (_rightButton.isPressed == true && pos_x < _settings.canvas_width / 2 - radius_x) {
			pos_x += GameObjectManager.DELTATIME * speed;
		}
		if (_upButton.isPressed == true && pos_y < _settings.canvas_height / 2 - radius_y) {
			pos_y += GameObjectManager.DELTATIME * speed;
		}
		if (_downButton.isPressed == true && pos_y > -(_settings.canvas_height / 2 - radius_y)) {
			pos_y -= GameObjectManager.DELTATIME * speed;
		}
	}
}
