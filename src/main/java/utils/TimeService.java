package utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by пользователь on 07.02.2015.
 */
public class TimeService {
    private static TimeService timeService;
    private Timer timer = null;

    public static TimeService instance() {
        if (timeService == null) {
            timeService = new TimeService();
        }
        return timeService;
    }

    public void start() {
        timer = new Timer();
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public boolean schedulePeriodicTask(TimerTask task, long timeMs) {
        if (timer != null) {
            timer.schedule(task, 0, timeMs);
            return true;
        }
        return false;
    }

    private TimeService() {

    }
}
