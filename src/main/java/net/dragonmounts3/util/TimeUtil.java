package net.dragonmounts3.util;

public class TimeUtil {
    public static final int RATE = 72;
    public static final int SECOND_PRE_MINUTE = 60;
    public static final int MINUTE_PRE_HOUR = 60;
    public static final int SECOND_PRE_HOUR = SECOND_PRE_MINUTE * MINUTE_PRE_HOUR;
    public static final int TICKS_PER_REAL_SECOND = 20;
    public static final int TICKS_PER_GAME_HOUR = TICKS_PER_REAL_SECOND * SECOND_PRE_HOUR / RATE;
}
