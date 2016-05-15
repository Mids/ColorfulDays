package game;

import gamelibrary.GameObjectManager;
import gameobject.MyObject;
import gameobject.Origin;
import gameobject.Player;
import loot.GameFrame;
import loot.GameFrameSettings;
import loot.graphics.TextBox;
import loot.graphics.Viewport;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class NewGameFrame extends GameFrame {

	TextBox tb_physics; // TextBox까지 입력하고 Ctrl + Space를 눌러 import 구문 자동 추가
	MyObject myObject;
	Origin origin;
	Viewport viewport;
	Player player;

	public NewGameFrame(GameFrameSettings settings) {
		super(settings);
		// TODO Auto-generated constructor stub

	}

	@Override
	public boolean Initialize() {
		// TODO Auto-generated method stub
		GameObjectManager.setImageResourceManager(images);
		GameObjectManager.setInputManager(inputs);
		GameObjectManager.setGameFrameSettings(settings);

		viewport = new Viewport(0, 0, settings.canvas_width, settings.canvas_height);
		GameObjectManager.setViewport(viewport);

		// Create initial objects
		myObject = new MyObject();
		origin = new Origin();
		tb_physics = new TextBox(10, 10, 200, 70);
		player = new Player();

		viewport.children.add(myObject);
		viewport.children.add(origin);
		viewport.children.add(tb_physics);

		// '카메라' 설정 부분
		viewport.pointOfView_z = 500;
		viewport.view_baseDistance = 500;
		viewport.view_minDistance = 0.1;
		viewport.view_maxDistance = 1200;
		viewport.view_width = settings.canvas_width;
		viewport.view_height = settings.canvas_height;

		// Input settings
		inputs.BindKey(KeyEvent.VK_E, 0);
		inputs.BindKey(KeyEvent.VK_SPACE, 1);
		inputs.BindMouseButton(MouseEvent.BUTTON1, 2);
		inputs.BindKey(KeyEvent.VK_LEFT, 3);
		inputs.BindKey(KeyEvent.VK_RIGHT, 4);
		inputs.BindKey(KeyEvent.VK_UP, 5);
		inputs.BindKey(KeyEvent.VK_DOWN, 6);

		GameObjectManager.Awake();

		return true;
	}

	@Override
	public boolean Update(long timeStamp) {
		// TODO Auto-generated method stub
		inputs.AcceptInputs(); // 이제까지 들어온 입력을 가상 버튼에 반영

		GameObjectManager.Update();

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
