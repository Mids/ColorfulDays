package gameobject;

import gamelibrary.GameObject;
import gamelibrary.GameObjectManager;
import loot.InputManager.ButtonState;
import loot.graphics.Viewport;

import java.awt.*;

/**
 * Created by jiny1 on 5/12/2016.
 */
public class Origin extends GameObject {
	ButtonState _mouseLeft;
	ButtonState _eButton;
	Viewport viewport;

	public Origin() {
		super(0, 0, 0, 50, 50);
	}

	@Override
	public void Start() {
		super.Start();
		GameObjectManager.PutObject(this, "Origin");
		GameObjectManager.getImageResourceManager().LoadImage("Images/image2.jpg", "origin");
		image = GameObjectManager.getImageResourceManager().GetImage("origin");

		_mouseLeft = GameObjectManager.getInputManager().buttons[2];
		_eButton = GameObjectManager.getInputManager().buttons[0];
		viewport = GameObjectManager.getViewport();
		Init();
	}

	@Override
	public void Update() {
		super.Update();

		// 현재 마우스를 누르고 있다면 원점의 위치를 현재 마우스 포인터의 위치로 설정
		if (_mouseLeft.isPressed == true) {
			Point pos_new_origin = viewport.GetRelativePositionFromCenter(GameObjectManager.getInputManager().pos_mouseCursor);
			
			pos_x = pos_new_origin.x;
			pos_y = -pos_new_origin.y;
		}

		// 현재 E 키를 누르고 있다면 원점의 위치를 화면 중앙으로 초기화
		if (_eButton.isPressed == true) {
			Init();
		}
	}

	public void Init(){
		pos_x = 0;
		pos_y = 0;
	}
}
