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

		// '카메라' 설정 부분
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
		inputs.AcceptInputs(); // 이제까지 들어온 입력을 가상 버튼에 반영

		// 현재 마우스를 누르고 있다면 원점의 위치를 현재 마우스 포인터의 위치로 설정
		if (inputs.buttons[2].isPressed == true) {
			Point pos_new_origin = viewport.GetRelativePositionFromCenter(inputs.pos_mouseCursor);
			origin.pos_x = pos_new_origin.x;
			origin.pos_y = -pos_new_origin.y;
		}

		// 현재 E 키를 누르고 있다면 원점의 위치를 화면 중앙으로 초기화
		if (inputs.buttons[0].isPressed == true) {
			origin.pos_x = 0;
			origin.pos_y = 0;
		}

		// 이번 프레임에 space bar를 누르기 시작했다면
		// 공전 운동을 하는 요소의 물리값을 현재 원점을 기준으로 하는 초기 상태로 재설정
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
		/* 여기서 settings. 을 입력하여 게임 화면 관련 설정 가능 */

		NewGameFrame window = new NewGameFrame(settings); // SampleFrame 대신 여러분이
															// 만든 클래스 이름 넣기
		window.setVisible(true);
	}

	final double coef_tension = 0.001f;
	TextBox tb_physics; // TextBox까지 입력하고 Ctrl + Space를 눌러 import 구문 자동 추가

	class MyObject extends DrawableObject3D // 여기까지 적고 Ctrl + Space 한 번 입력(자동
											// import)
	{
		double vel_x, vel_y, vel_z; // 원래 있던 pos_x, pos_y는 지웁니다.
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
