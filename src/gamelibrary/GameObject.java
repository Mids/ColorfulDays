package gamelibrary;

import loot.graphics.DrawableObject3D;

import java.awt.*;

public abstract class GameObject extends DrawableObject3D {
	public GameObject() {
		super();
		// TODO Auto-generated constructor stub
		Start();
	}

	public GameObject(boolean trigger_coordination) {
		super(trigger_coordination);
		// TODO Auto-generated constructor stub
		Start();
	}

	public GameObject(double pos_x, double pos_y, double pos_z, double radius_x, double radius_y, Image image) {
		super(pos_x, pos_y, pos_z, radius_x, radius_y, image);
		// TODO Auto-generated constructor stub
		Start();
	}

	public GameObject(double pos_x, double pos_y, double pos_z, double radius_x, double radius_y) {
		super(pos_x, pos_y, pos_z, radius_x, radius_y);
		// TODO Auto-generated constructor stub
		Start();
	}

	public GameObject(double radius_x, double radius_y, Image image) {
		super(radius_x, radius_y, image);
		// TODO Auto-generated constructor stub
		Start();
	}

	public GameObject(double radius_x, double radius_y) {
		super(radius_x, radius_y);
		// TODO Auto-generated constructor stub
		Start();
	}

	public GameObject(DrawableObject3D other) {
		super(other);
		// TODO Auto-generated constructor stub
		Start();
	}

	public GameObject(Image image) {
		super(image);
		// TODO Auto-generated constructor stub
		Start();
	}

	public GameObject(int width, int height, Image image) {
		super(width, height, image);
		// TODO Auto-generated constructor stub
		Start();
	}

	public GameObject(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
		Start();
	}

	/**
	 * Called when the constructor is called.
	 */
	public void Start() {

	}

	/**
	 * Called when the GameObjectManager initialize.
	 */
	public void Awake() {

	}

	/**
	 * Called when a frame starts
	 */
	public void Update() {

	}

	/**
	 * Called when the object destroyed
	 */
	public void Destroy() {

	}

	public boolean HitTest3D(GameObject other) {
		return pos_x - radius_x <= other.pos_x + other.radius_x && pos_x + radius_x >= other.pos_x - other.radius_x &&
				pos_y - radius_y <= other.pos_y + other.radius_y && pos_y + radius_y >= other.pos_y - other.radius_y &&
				Double.valueOf(pos_z).equals(other.pos_z);
	}
}
