package game;

import loot.GameFrameSettings;

public class Main {

	public static void main(String args[]) {
		GameFrameSettings settings = new GameFrameSettings();
		/* ���⼭ settings. �� �Է��Ͽ� ���� ȭ�� ���� ���� ���� */
		settings.canvas_height = 800;
		settings.canvas_width = 600;

		NewGameFrame window = new NewGameFrame(settings); // SampleFrame ��� �������� ���� Ŭ���� �̸� �ֱ�

		window.setVisible(true);
	}
}
