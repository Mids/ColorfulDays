import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import loot.GameFrame;
import loot.GameFrameSettings;
import loot.graphics.DrawableObject3D;
import loot.graphics.TextBox;
import loot.graphics.Viewport;

public class NewGameFrame extends GameFrame {

	public NewGameFrame(GameFrameSettings settings) {
		super(settings);
		// TODO Auto-generated constructor stub

	}

	class Origin extends DrawableObject3D {
		public Origin() {
			super(0, 0, 0, 50, 50, images.GetImage("origin"));
		}
	}

	@Override
	public boolean Initialize() {
		// TODO Auto-generated method stub
		images.LoadImage("Images/image.jpg", "img");
		images.LoadImage("Images/image2.jpg", "origin");

		myObject = new MyObject();
		origin = new Origin();
		tb_physics = new TextBox(10, 10, 200, 70);
		viewport = new Viewport(0, 0, settings.canvas_width, settings.canvas_height);

		viewport.children.add(myObject);
		viewport.children.add(origin);
		viewport.children.add(tb_physics);

		// 'ī�޶�' ���� �κ�
		viewport.pointOfView_z = 1000;
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

		// ���� ���콺�� ������ �ִٸ� ������ ��ġ�� ���� ���콺 �������� ��ġ�� ����
		if (inputs.buttons[2].isPressed == true) {
			Point pos_new_origin = viewport.GetRelativePositionFromCenter(inputs.pos_mouseCursor);
			origin.pos_x = pos_new_origin.x;
			origin.pos_y = -pos_new_origin.y;
		}

		// ���� E Ű�� ������ �ִٸ� ������ ��ġ�� ȭ�� �߾����� �ʱ�ȭ
		if (inputs.buttons[0].isPressed == true) {
			origin.pos_x = 0;
			origin.pos_y = 0;
		}

		// �̹� �����ӿ� space bar�� ������ �����ߴٸ�
		// ���� ��� �ϴ� ����� �������� ���� ������ �������� �ϴ� �ʱ� ���·� �缳��
		if (inputs.buttons[1].IsPressedNow() == true) {
			myObject.pos_x = origin.pos_x;
			myObject.pos_y = origin.pos_y;
			myObject.pos_z = 200 + origin.pos_z;

			myObject.vel_x = 6.4;
			myObject.vel_y = 6.4;
			myObject.vel_z = 0;
		}

		myObject.acc_x = (origin.pos_x - myObject.pos_x) * coef_tension;
		myObject.acc_y = (origin.pos_y - myObject.pos_y) * coef_tension;
		myObject.acc_z = (origin.pos_z - myObject.pos_z) * coef_tension;

		myObject.vel_x += myObject.acc_x;
		myObject.vel_y += myObject.acc_y;
		myObject.vel_z += myObject.acc_z;

		myObject.pos_x += myObject.vel_x;
		myObject.pos_y += myObject.vel_y;
		myObject.pos_z += myObject.vel_z;

		tb_physics.text = String.format(
				"pos: (%+.2f, %+.2f, %+.2f)\n" + "vel: (%+.2f, %+.2f, %+.2f)\n" + "acc: (%+.2f, %+.2f, %+.2f)\n" + "off: %d",
				myObject.pos_x, myObject.pos_y, myObject.pos_z, myObject.vel_x, myObject.vel_y, myObject.vel_z,
				myObject.acc_x, myObject.acc_y, myObject.acc_z, offset);

		offset++;
		if (viewport.pointOfView_z <= 200 || viewport.pointOfView_z > 1000)
			offset *= -1;
		
		viewport.pointOfView_z -= offset;
		
		return true;

	}
	int offset = 1;

	@Override
	public void Draw(long timeStamp) {
		// TODO Auto-generated method stub
		BeginDraw();
		ClearScreen();
		viewport.Draw(g);
		EndDraw();

	}

	public static void main(String args[]) {
		GameFrameSettings settings = new GameFrameSettings();
		/* ���⼭ settings. �� �Է��Ͽ� ���� ȭ�� ���� ���� ���� */

		NewGameFrame window = new NewGameFrame(settings); // SampleFrame ��� ��������
															// ���� Ŭ���� �̸� �ֱ�
		window.setVisible(true);
	}

	final double coef_tension = 0.001f;
	TextBox tb_physics; // TextBox���� �Է��ϰ� Ctrl + Space�� ���� import ���� �ڵ� �߰�

	class MyObject extends DrawableObject3D // ������� ���� Ctrl + Space �� �� �Է�(�ڵ�
											// import)
	{
		double vel_x, vel_y, vel_z; // ���� �ִ� pos_x, pos_y�� ����ϴ�.
		double acc_x, acc_y, acc_z;

		public MyObject() {
			super(0, 0, 200, 50, 50, images.GetImage("img"));
			vel_x = 6.4;
			vel_y = 6.4;
		}

	}

	MyObject myObject;
	Origin origin;
	Viewport viewport;
}
