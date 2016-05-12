package game;

import loot.GameFrameSettings;

public class Main {

	public static void main(String args[]) {
		GameFrameSettings settings = new GameFrameSettings();
		/* 여기서 settings. 을 입력하여 게임 화면 관련 설정 가능 */
		settings.canvas_height = 800;
		settings.canvas_width = 600;

		NewGameFrame window = new NewGameFrame(settings); // SampleFrame 대신 여러분이 만든 클래스 이름 넣기

		window.setVisible(true);
	}
}
