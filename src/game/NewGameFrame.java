package game;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import gamelibrary.GameObject;
import gamelibrary.GameObjectManager;
import gameobject.MyObject;
import gameobject.Origin;
import loot.GameFrame;
import loot.GameFrameSettings;
import loot.graphics.DrawableObject3D;
import loot.graphics.TextBox;
import loot.graphics.Viewport;

@SuppressWarnings("serial")
public class NewGameFrame extends GameFrame {

	TextBox tb_physics; // TextBox���� �Է��ϰ� Ctrl + Space�� ���� import ���� �ڵ� �߰�
	MyObject myObject;
	Origin origin;
	Viewport viewport;

	public NewGameFrame(GameFrameSettings settings) {
		super(settings);
		// TODO Auto-generated constructor stub

	}

	@Override
	public boolean Initialize() {
		// TODO Auto-generated method stub
		images.LoadImage("Images/image2.jpg", "origin");

		GameObjectManager.setImageResourceManager(images);
		GameObjectManager.setInputManager(inputs);

		viewport = new Viewport(0, 0, settings.canvas_width, settings.canvas_height);
		GameObjectManager.setViewport(viewport);
		myObject = new MyObject();
		origin = new Origin();
		tb_physics = new TextBox(10, 10, 200, 70);

		GameObjectManager.PutObject(origin, "Origin");
		myObject.Start();
		origin.Start();

		viewport.children.add(myObject);
		viewport.children.add(origin);
		viewport.children.add(tb_physics);

		// 'ī�޶�' ���� �κ�
		viewport.pointOfView_z = 300;
		viewport.view_baseDistance = 500;
		viewport.view_minDistance = 0.1;
		viewport.view_maxDistance = 1200;
		viewport.view_width = settings.canvas_width;
		viewport.view_height = settings.canvas_height;

		inputs.BindKey(KeyEvent.VK_E, 0);
		inputs.BindKey(KeyEvent.VK_SPACE, 1);
		inputs.BindMouseButton(MouseEvent.BUTTON1, 2);

		return true;
	}

	@Override
	public boolean Update(long timeStamp) {
		// TODO Auto-generated method stub
		inputs.AcceptInputs(); // �������� ���� �Է��� ���� ��ư�� �ݿ�

		myObject.Update();
		origin.Update();

		tb_physics.text = String.format(
				"pos: (%+.2f, %+.2f, %+.2f)\n" + "vel: (%+.2f, %+.2f, %+.2f)\n" + "acc: (%+.2f, %+.2f, %+.2f)",
				myObject.pos_x, myObject.pos_y, myObject.pos_z, myObject._velocity.x, myObject._velocity.y, myObject._velocity.z,
				myObject._accel.x, myObject._accel.y, myObject._accel.z);


		return true;

	}

	@Override
	public void Draw(long timeStamp) {
		// TODO Auto-generated method stub
		BeginDraw();
		ClearScreen();
		viewport.Draw(g);
		EndDraw();

	}
}
