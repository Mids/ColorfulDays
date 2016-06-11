package gameobject.enemy;

import gamelibrary.Enemy;
import gamelibrary.EnemyManager;
import gamelibrary.GameObjectManager;
import gamelibrary.Time;

import java.util.Random;

/**
 * Created by jiny1 on 6/4/2016.
 */
public class BossManager extends EnemyManager {
	private Boss _boss;
	private boolean _clearSound = true;

	@Override
	protected void InitEnemyImages() {

	}

	@Override
	protected Enemy getEnemyInstance() {
		return null;
	}

	@Override
	public float getRegenTime() {
		return -1;
	}

	@Override
	public int getNumOfEnemies() {
		return 1;
	}

	@Override
	public String getNextStage() {
		return "TheEnd";
	}

	@Override
	public void Awake() {
		InitEnemyImages();
		_settings = GameObjectManager.getGameFrameSettings();
		_viewport = GameObjectManager.getViewport();
		_random = new Random();
		_isActive = false;

		// Create Boss
		_boss = new Boss();
		_boss.Init();
		_viewport.children.add(_boss);
		_viewport.children.add(_boss._colorBoss);

		GameObjectManager.getScoreBoard().ResetPoint();
	}

	@Override
	public void Update() {
		if (_waitingTime > 0) {
			_waitingTime -= Time.getTime().getDeltaTime();
			return;
		}

		// TODO: If destroyed, Reveal The end
		if (_destroyCount > 0) {
			_destroyCount -= Time.getTime().getDeltaTime();

			if (_destroyCount < 7 && _destroyCount > 6) {
				_boss.MakeGreen(7 - _destroyCount);
				if (_clearSound) {
					GameObjectManager.getAudioManager().Stop("bgm");
					GameObjectManager.getAudioManager().Play("clear");
					_clearSound = false;
				}
			}

			if (!_isStageRevealed && _destroyCount < 3) {
				GameObjectManager.getStageNumber().Reveal(getNextStage());
				GameObjectManager.getStageNumber().End();
				_isStageRevealed = true;
			}
		}

		// TODO: Move Boss if Boss is Active
		if (_boss._isActive)
			_boss.Update();
	}

	@Override
	public void Destroy() {
		_destroyCount = 9;
	}
}
