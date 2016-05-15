package gameobject;

import gamelibrary.GameObject;
import gamelibrary.GameObjectManager;
import gamelibrary.Vector3;
import loot.GameFrameSettings;
import loot.graphics.Viewport;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class BackGround extends GameObject {
	private GameFrameSettings _settings;
	private Viewport _viewport;
	private Vector3 _speed;

	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "Background");
	}

	@Override
	public void Awake() {
		_settings = GameObjectManager.getGameFrameSettings();
		GameObjectManager.getImageResourceManager().LoadImage("Images/background.jpg", "background");
		image = GameObjectManager.getImageResourceManager().GetImage("background");

		_viewport = GameObjectManager.getViewport();
		_viewport.children.add(this);

		Init();
	}

	@Override
	public void Update() {
		pos_y += _speed.y;
		if (pos_y < _settings.canvas_height / 2 - radius_y)
			pos_y = radius_y - _settings.canvas_height / 2;
	}

	void Init() {
		pos_z = 0;
		radius_x = 300;
		radius_y = 800;
		pos_y = radius_y - _settings.canvas_height / 2;
		_speed = new Vector3(0, -5, 0);
	}
}
