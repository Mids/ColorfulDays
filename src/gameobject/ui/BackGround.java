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
	private Bridge _bridge;
	private Bridge _monoBridge;

	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "BackGround");
	}

	@Override
	public void Awake() {
		_settings = GameObjectManager.getGameFrameSettings();
		GameObjectManager.getImageResourceManager().LoadImage("Images/background/Stage1_background.png", "bridge");
		GameObjectManager.getImageResourceManager().LoadImage("Images/background/Stage1_background_m.png", "bridge_m");
		GameObjectManager.getImageResourceManager().LoadImage("Images/background/Stage1_back_water.png", "backwater");
		GameObjectManager.getImageResourceManager().LoadImage("Images/background/Stage1_back_water_m.png", "backwater_m");
		GameObjectManager.getImageResourceManager().LoadImage("Images/background_m.jpg", "background_m");
		image = GameObjectManager.getImageResourceManager().GetImage("backwater");
		_bridge = new Bridge(this);
		_bridge.image = GameObjectManager.getImageResourceManager().GetImage("bridge");
		_monoBridge = new Bridge(this);
		_monoBridge.image = GameObjectManager.getImageResourceManager().GetImage("bridge_m");

		_viewport = GameObjectManager.getViewport();
		_monoBackground = new MonoBackground();

		_viewport.children.add(_monoBackground);
		_viewport.children.add(this);
		_viewport.children.add(_monoBridge);
		_viewport.children.add(_bridge);

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
		radius_y = 2396;
		pos_y = radius_y - _settings.canvas_height;
	}

	public void Colorize(float alpha) {
		_monoBackground.alpha = alpha;
		_monoBridge.SetAlpha(alpha);
	}

	private class MonoBackground extends BackGround {
		@Override
		public void Start() {
			this._viewport = GameObjectManager.getViewport();
			this._settings = GameObjectManager.getGameFrameSettings();
			this.Init();
			this.image = GameObjectManager.getImageResourceManager().GetImage("backwater_m");
		}

		@Override
		public void Update() {
			pos_y += SPEED * 2 * Time.getTime().getDeltaTime();
			if (pos_y < _settings.canvas_height - radius_y)
				pos_y = radius_y - _settings.canvas_height;
		}
	}

	private class Bridge extends GameObject {
		Bridge(BackGround origin) {
			radius_x = 300;
			radius_y = 400;
			pos_x = 0;
			pos_y = 0;
			pos_z = 0;
		}

		public void SetAlpha(float alpha) {
			this.alpha = alpha;
		}
	}
}
