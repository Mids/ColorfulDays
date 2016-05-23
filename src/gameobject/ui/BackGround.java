package gameobject.ui;

import gamelibrary.GameObject;
import gamelibrary.GameObjectManager;
import gamelibrary.Time;
import loot.GameFrameSettings;
import loot.graphics.Viewport;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class BackGround extends GameObject {
	private GameFrameSettings _settings;
	private Viewport _viewport;
	public static final int SPEED = -300;

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
		pos_y += SPEED * 2 * Time.getTime().getDeltaTime();
		if (pos_y < _settings.canvas_height - radius_y)
			pos_y = radius_y - _settings.canvas_height;
	}

	void Init() {
		pos_z = -_viewport.pointOfView_z;
		radius_x = 600;
		radius_y = 1600;
		pos_y = radius_y - _settings.canvas_height;
	}
}
