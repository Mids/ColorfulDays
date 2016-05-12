package gameobject;

import gamelibrary.GameObject;
import gamelibrary.GameObjectManager;
import gamelibrary.Vector3;
import loot.InputManager.ButtonState;

public class MyObject extends GameObject {
	final double coef_tension = 0.001f;
	public Vector3 _velocity;
	public Vector3 _accel;
	private GameObject _origin;
	private ButtonState _space;

	public MyObject() {

		super(0, 0, 200, 50, 50);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Start() {
		GameObjectManager.PutObject(this, "MyObject");
	}

	@Override
	public void Awake() {
		super.Awake();
		GameObjectManager.getImageResourceManager().LoadImage("Images/image.jpg", "img");
		image = GameObjectManager.getImageResourceManager().GetImage("img");
		_origin = GameObjectManager.GetObject("Origin");

		_space = GameObjectManager.getInputManager().buttons[1];
		Init();
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub

		_accel.x = (_origin.pos_x - pos_x) * coef_tension;
		_accel.y = (_origin.pos_y - pos_y) * coef_tension;
		_accel.z = (_origin.pos_z - pos_z) * coef_tension;

		_velocity.x += _accel.x;
		_velocity.y += _accel.y;
		_velocity.z += _accel.z;

		pos_x += _velocity.x;
		pos_y += _velocity.y;
		pos_z += _velocity.z;

		// 이번 프레임에 space bar를 누르기 시작했다면
		// 공전 운동을 하는 요소의 물리값을 현재 원점을 기준으로 하는 초기 상태로 재설정
		if (_space.IsPressedNow() == true) {
			pos_x = _origin.pos_x;
			pos_y = _origin.pos_y;
			pos_z = 200 + _origin.pos_z;

			Init();
		}
	}

	public void Init() {
		_velocity = new Vector3(6.4, 6.4, 0);
		_accel = new Vector3(0, 0, 0);
	}

}
