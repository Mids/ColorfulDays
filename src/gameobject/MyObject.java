package gameobject;

import gamelibrary.GameObject;
import gamelibrary.GameObjectManager;
import gamelibrary.Vector3;

public class MyObject extends GameObject {
	final double coef_tension = 0.001f;
	public Vector3 _velocity;
	public Vector3 _accel;
	private GameObject _origin;

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
	}

	public void Init() {
		_velocity = new Vector3(6.4, 6.4, 0);
		_accel = new Vector3(0, 0, 0);
	}

}
