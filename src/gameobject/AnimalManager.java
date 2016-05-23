package gameobject;

import gamelibrary.*;
import loot.GameFrameSettings;
import loot.graphics.Viewport;

import java.awt.*;
import java.util.Random;

/**
 * Created by Jin on 2016-05-16.
 */
public class AnimalManager extends EnemyManager {
    private final int _regenTime = 20;
    private final int _numOfEnemys = 20;
    private GameFrameSettings _settings;
    private Viewport _viewport;
    private Player _player;
    private Animal[] _animals;
    private Random _random;
    private int _timeLeft;
    private int offset;

    @Override
    public void Start() {
        GameObjectManager.PutObject(this, "FlowerManager");
    }

    @Override
    public void Awake() {
        _settings = GameObjectManager.getGameFrameSettings();
        _viewport = GameObjectManager.getViewport();
        _random = new Random();
        GameObjectManager.getImageResourceManager().LoadImage("Images/flower.png", "flower");
        GameObjectManager.getImageResourceManager().LoadImage("Images/monoflower.png", "monoflower");
        Image animalImage = GameObjectManager.getImageResourceManager().GetImage("monoflower");

        // Init flowers
        _animals = new Animal[_numOfEnemys];
        for (int i = 0; i < _numOfEnemys; i++) {
            Animal animal = new Animal();
            animal.Init();
            animal.pos_y = _settings.canvas_height;
            animal.radius_x = 50;
            animal.radius_y = 50;
            animal.image = animalImage;
            _viewport.children.add(animal);
            _animals[i] = animal;
        }

        _player = (Player) GameObjectManager.GetObject("Player");
    }

    @Override
    public void Update() {
        if (_timeLeft > 0)
            --_timeLeft;
        else {
            RegenerateEnemy();
        }

        for (Animal animal : _animals) {
            if (animal._isActive) {
                if (animal.pos_y > -_settings.canvas_height / 2 - 50)
                    animal.Move();
                else
                    animal.Destroy();
            }
        }
    }

    @Override
    public void RegenerateEnemy() {
        _animals[offset].pos_x = _random.nextInt(_settings.canvas_width - (int) _animals[offset].radius_x * 2) - _settings.canvas_width / 2 + _animals[offset].radius_x;
        _animals[offset].pos_y = _settings.canvas_height / 2 + _animals[offset].radius_y;
        _animals[offset]._isActive = true;

        if (++offset >= _numOfEnemys) offset = 0;

        _timeLeft = _regenTime;
    }

    private class Animal extends Enemy {
        boolean _isActive;
        private Vector3 _speed = new Vector3(300, -300, 0);

        void Init() {
            _isColored = false;
            _isActive = false;
            pos_y = _settings.canvas_height;
            image = GameObjectManager.getImageResourceManager().GetImage("monoflower");

        }

        void Move() {
            pos_y += Time.getTime().getDeltaTime() * _speed.y;
            pos_x += Time.getTime().getDeltaTime() * _speed.x * Math.sin(10 * pos_y / _settings.canvas_height);
            CheckCollision();
        }

        @Override
        public void Destroy() {
            Init();
        }

        @Override
        public void CheckCollision() {
            if (!_isColored) {
                PlayerBullet[] playerBullets = _player.getWeapon().GetBullets();
                for (PlayerBullet playerBullet : playerBullets) {
                    if (playerBullet.HitTest3D(this)) {
                        playerBullet.Destroy();
                        Colorized();
                        return;
                    }
                }
            }
        }

        @Override
        public void Colorized() {
            _isColored = true;
            image = GameObjectManager.getImageResourceManager().GetImage("flower");
            GameObjectManager.getScoreBoard()._score++;

        }
    }
}
