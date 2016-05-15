package gameobject;

import gamelibrary.GameObjectManager;
import loot.graphics.TextBox;

import java.awt.*;

/**
 * Created by jiny1 on 5/16/2016.
 */
public class ScoreBoard extends TextBox {
	public int _score = 0;

	public ScoreBoard() {
		super(10, 10, 200, 70);
		background_color = new Color(0, 0, 0, 0);
		GameObjectManager.setScoreBoard(this);
	}

	public void Update() {
		text = String.format("Colorized Enemies : %d", _score);
	}
}
