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
	public static final int SPEED = -200;
	protected GameFrameSettings _settings;
	protected Viewport _viewport;
	private GameObject _monoBackground;

	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "Background");
	}

	@Override
	public void Awake() {
		_settings = GameObjectManager.getGameFrameSettings();
		GameObjectManager.getImageResourceManager().LoadImage("Images/background.jpg", "background");
		GameObjectManager.getImageResourceManager().LoadImage("Images/background_m.jpg", "background_m");
		image = GameObjectManager.getImageResourceManager().GetImage("background");

		_viewport = GameObjectManager.getViewport();
		_monoBackground = new MonoBackground();
		_viewport.children.add(_monoBackground);
		_viewport.children.add(this);

		Init();
	}

	@Override
	public void Update() {
		_monoBackground.Update();
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

	public void Colorize(float alpha) {
		_monoBackground.alpha = alpha;
	}

	private class MonoBackground extends BackGround {
		@Override
		public void Start() {
			this._viewport = GameObjectManager.getViewport();
			this._settings = GameObjectManager.getGameFrameSettings();
			this.Init();
			this.image = GameObjectManager.getImageResourceManager().GetImage("background_m");
		}

		@Override
		public void Update() {
			pos_y += SPEED * 2 * Time.getTime().getDeltaTime();
			if (pos_y < _settings.canvas_height - radius_y)
				pos_y = radius_y - _settings.canvas_height;
		}
	}
}
