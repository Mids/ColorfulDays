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
import loot.InputManager.ButtonState;
import loot.graphics.Viewport;

import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class NewGameFrame extends GameFrame {
	ScoreBoard scoreBoard; // TextBox까지 입력하고 Ctrl + Space를 눌러 import 구문 자동 추가
	Viewport viewport;
	Player player;
	EnemyManager enemyManager;
	BackGround backGround;
	StageNumber stageNumber;
	ButtonState _esc;

	boolean _pause = false;

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
		GameObjectManager.setAudioManager(audios);

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

		// '카메라' 설정 부분
		viewport.pointOfView_z = 500;
		viewport.view_baseDistance = 500;
		viewport.view_minDistance = 100;
		viewport.view_maxDistance = 1200;
		viewport.view_width = settings.canvas_width;
		viewport.view_height = settings.canvas_height;

		// Input settings
		inputs.BindKey(KeyEvent.VK_E, 0);
		inputs.BindKey(KeyEvent.VK_SPACE, 1);
		inputs.BindKey(KeyEvent.VK_ESCAPE, 2);
		inputs.BindKey(KeyEvent.VK_LEFT, 3);
		inputs.BindKey(KeyEvent.VK_RIGHT, 4);
		inputs.BindKey(KeyEvent.VK_UP, 5);
		inputs.BindKey(KeyEvent.VK_DOWN, 6);

		audios.LoadAudio("Sounds/bgm.wav", "bgm", 1);
		audios.LoadAudio("Sounds/fire.wav", "fire", 5);
		audios.LoadAudio("Sounds/enemy_die.wav", "enemy_die", 5);
		audios.LoadAudio("Sounds/boss.wav", "boss", 1);
		audios.LoadAudio("Sounds/boss_die.wav", "boss_die", 1);
		audios.LoadAudio("Sounds/danger.wav", "danger", 1);
		audios.LoadAudio("Sounds/boss_attack.wav", "boss_attack", 2);
		audios.LoadAudio("Sounds/boss_attack2.wav", "boss_attack2", 2);
		audios.LoadAudio("Sounds/clear.wav", "clear", 1);
		audios.Play("bgm");

		_esc = inputs.buttons[2];

		GameObjectManager.Awake();

		return true;
	}

	@Override
	public boolean Update(long timeStamp) {
		// TODO Auto-generated method stub
		inputs.AcceptInputs(); // 이제까지 들어온 입력을 가상 버튼에 반영

		Time.getTime().UpdateTime();

		if (_esc.IsPressedNow()) {
			_pause = !_pause;
		}

		if (!_pause) {
			GameObjectManager.Update();
		}

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
