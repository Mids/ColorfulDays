package gameobject.ui;

import gamelibrary.GameObjectManager;
import gamelibrary.Time;
import loot.graphics.TextBox;

import java.awt.*;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class ScoreBoard extends TextBox {
	private int _score = 0;
	private int _scoreToGoNext = 10;

	public ScoreBoard() {
		super(10, 10, 200, 70);
		font = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
		foreground_color = Color.white;
		background_color = new Color(0, 0, 0, 0);
		GameObjectManager.setScoreBoard(this);
	}

	public void Update() {
		text = String.format("Colorized Enemies : %d\nFPS : %.2f", _score, 1 / Time.getTime().getDeltaTime());
	}

	public void GainPoint() {
		if (++_score == _scoreToGoNext) {
			// Change Stage
			GameObjectManager.GetObject("EnemyManager").Destroy();
		}
		float backgroundAlpha = (_scoreToGoNext - (float) _score % _scoreToGoNext) / _scoreToGoNext;
		if (_score >= _scoreToGoNext) {
			backgroundAlpha = 0;
		}

		// Colorize background
		((BackGround) GameObjectManager.GetObject("BackGround")).Colorize(backgroundAlpha);
	}

	public void ResetPoint() {
		_score = 0;
		// Colorize background
		((BackGround) GameObjectManager.GetObject("BackGround")).Colorize(1);
	}
}
