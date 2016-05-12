package gamelibrary;

import java.awt.Image;

import loot.graphics.DrawableObject3D;

public class GameObject extends DrawableObject3D{	
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
	
	public void Start(){
		
	}
	
	public void Update(){
		
	};
}
