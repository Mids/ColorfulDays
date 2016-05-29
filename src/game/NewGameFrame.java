package game;

import gamelibrary.EnemyManager;
import gamelibrary.GameObjectManager;
import gamelibrary.Time;
import gameobject.enemy.FlowerManager;
import gameobject.player.Player;
import gameobject.ui.BackGround;
import gameobject.ui.ScoreBoard;
import gameobject.ui.StageNumber;
import loot.GameFrame;
import loot.GameFrameSettings;
import loot.graphics.Viewport;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class NewGameFrame extends GameFrame {
	ScoreBoard scoreBoard; // TextBox���� �Է��ϰ� Ctrl + Space�� ���� import ���� �ڵ� �߰�
	Viewport viewport;
	Player player;
	EnemyManager enemyManager;
	BackGround backGround;
	StageNumber stageNumber;

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
		backGround = new BackGround();
		scoreBoard = new ScoreBoard();
		stageNumber = new StageNumber();
		player = new Player();
		enemyManager = new FlowerManager();

		viewport.children.add(stageNumber);
		viewport.children.add(scoreBoard);

		// 'ī�޶�' ���� �κ�
		viewport.pointOfView_z = 500;
		viewport.view_baseDistance = 500;
		viewport.view_minDistance = 100;
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
		inputs.AcceptInputs(); // �������� ���� �Է��� ���� ��ư�� �ݿ�

		Time.getTime().UpdateTime();
		GameObjectManager.Update();

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
