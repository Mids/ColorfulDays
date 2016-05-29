package gameobject.ui;

import gamelibrary.GameObjectManager;
import gamelibrary.Time;
import loot.graphics.TextBox;

import java.awt.*;

/**
 * Created by jiny1 on 5/30/2016.
 */
public class StageNumber extends TextBox {
	private float _timeLeft = 4;
	private float alpha = 1;

	public StageNumber() {
		super(0, 0, 600, 800);
		font = new Font(Font.SANS_SERIF, Font.PLAIN, 80);
		foreground_color = Color.white;
		background_color = Color.black;
		margin_left = 300 - 276 / 2;
		margin_top = 400 - 100;
		GameObjectManager.setStageNumber(this);
		text = String.format("Stage 1");
	}

	public void Update() {
		if (_timeLeft > 0) {
			_timeLeft -= Time.getTime().getDeltaTime();

			if (_timeLeft > 4) {
				alpha = (2 - (_timeLeft % 2)) / 2;
			} else if (_timeLeft > 2) {
				alpha = 1;
			} else if (_timeLeft > 0) {
				alpha = _timeLeft % 2 / 2;
			} else {
				alpha = 0;
			}
		}
	}

	public void Reveal(String revealText) {
		text = revealText;
		_timeLeft = 6;
	}

	@Override
	public void Draw(Graphics2D g) {
		System.out.println(alpha);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		super.Draw(g);
	}
}
