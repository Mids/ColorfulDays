package gamelibrary;

/**
 * Created by Jin on 2016-05-23.
 */
public class Time {
    private static Time _instance;
    private double _previousTime;
    private double _deltaTime;

    public double getDeltaTime() {
        return _deltaTime;
    }

    public void UpdateTime(){
        double currentTime = getSystemTime();
        _deltaTime = currentTime - _previousTime;
        _previousTime = currentTime;
    }

    private Time(){
        _previousTime = getSystemTime();
    }

    private double getSystemTime(){
        return System.nanoTime() / 1000000000.0;
    }

    public static Time getTime(){
        if(_instance == null) {
            _instance = new Time();
        }
        return _instance;
    }
}
