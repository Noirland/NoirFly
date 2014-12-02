package nz.co.noirland.noirfly;

import nz.co.noirland.zephcore.Config;
import nz.co.noirland.zephcore.Debug;
import org.bukkit.plugin.Plugin;

public class FlyConfig extends Config {

    private static FlyConfig inst;

    public static FlyConfig inst() {
        if(inst == null) {
            inst = new FlyConfig();
        }
        return inst;
    }

    private FlyConfig() {
        super("config.yml");

        initFlyTypes();
    }

    private void initFlyTypes() {
        FlyType.POTION = new FlyType(config.getInt("potion.fly-time"), config.getInt("potion.cooldown"));
        FlyType.SPLASH_POTION = new FlyType(config.getInt("splash-potion.fly-time"), config.getInt("splash-potion.cooldown"));
        FlyType.COMMAND = new FlyType(config.getInt("command.fly-time"), config.getInt("command.cooldown"));
        FlyType.PERMANENT = new FlyType(-1, config.getInt("permanent.cooldown"));
    }

    @Override
    protected Plugin getPlugin() {
        return NoirFly.inst();
    }

    @Override
    protected Debug getDebug() {
        return NoirFly.debug();
    }
}
