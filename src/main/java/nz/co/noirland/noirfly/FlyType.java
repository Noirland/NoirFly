package nz.co.noirland.noirfly;

public class FlyType {
    public static FlyType POTION = null;
    public static FlyType SPLASH_POTION = null;
    public static FlyType COMMAND = null;
    public static FlyType PERMANENT = null;


    private final int cooldown;
    private final int time;

    protected FlyType(int time, int cooldown) {
        this.time = time;
        this.cooldown = cooldown;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getTime() {
        return time;
    }

    public long getTicks() {
        return time * 20L;
    }

    public boolean isPermanent() {
        return time == -1;
    }
}
