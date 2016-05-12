package game;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import gamelibrary.GameObject;
import gamelibrary.GameObjectManager;
import gameobject.MyObject;
import loot.GameFrame;
import loot.GameFrameSettings;
import loot.graphics.DrawableObject3D;
import loot.graphics.TextBox;
import loot.graphics.Viewport;

@SuppressWarnings("serial")
public class NewGameFrame extends GameFrame {

	public NewGameFrame(GameFrameSettings settings) {
		super(settings);
		// TODO Auto-generated constructor stub

	}

	class Origin extends GameObject {
		public Origin() {
			super(0, 0, 0, 50, 50, images.GetImage("origin"));
		}
	}

	@Override
	public boolean Initialize() {
		// TODO Auto-generated method stub
		images.LoadImage("Images/image2.jpg", "origin");
		
		GameObjectManager.setImageResourceManager(images);
		GameObjectManager.setInputManager(inputs);

		myObject = new MyObject();
		origin = new Origin();
		tb_physics = new TextBox(10, 10, 200, 70);
		viewport = new Viewport(0, 0, settings.canvas_width, settings.canvas_height);

		GameObjectManager.PutObject(origin, "Origin");
		myObject.Start();
		
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
		
		myObject.Update();

		tb_physics.text = String.format(
				"pos: (%+.2f, %+.2f, %+.2f)\n" + "vel: (%+.2f, %+.2f, %+.2f)\n" + "acc: (%+.2f, %+.2f, %+.2f)\n" + "off: %d",
				myObject.pos_x, myObject.pos_y, myObject.pos_z, myObject._velocity.x, myObject._velocity.y, myObject._velocity.z,
				myObject._accel.x, myObject._accel.y, myObject._accel.z, offset);

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

//	public static void main(String args[]) {
//		GameFrameSettings settings = new GameFrameSettings();
//		/* 여기서 settings. 을 입력하여 게임 화면 관련 설정 가능 */
//
//		NewGameFrame window = new NewGameFrame(settings); // SampleFrame 대신 여러분이
//															// 만든 클래스 이름 넣기
//		window.setVisible(true);
//	}

	TextBox tb_physics; // TextBox까지 입력하고 Ctrl + Space를 눌러 import 구문 자동 추가


	MyObject myObject;
	Origin origin;
	Viewport viewport;
}
