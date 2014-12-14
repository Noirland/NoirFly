package nz.co.noirland.noirfly;

import nz.co.noirland.noirfly.potions.FlyPotionsCommand;
import nz.co.noirland.noirfly.potions.PotionsListener;
import nz.co.noirland.noirfly.xpbar.XPHandler;
import nz.co.noirland.noirfly.xpbar.XPTimer;
import nz.co.noirland.zephcore.CheckFallingTask;
import nz.co.noirland.zephcore.Debug;
import nz.co.noirland.zephcore.Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class NoirFly extends JavaPlugin {

    public static final String FLY_PERM = "noirfly.fly";
    public static final String PERMAFLY_PERM = "noirfly.permfly";

    private static NoirFly inst;
    private static Debug debug;
    XPHandler xpHandler;

    private final HashMap<UUID, FlyData> flying = new HashMap<UUID, FlyData>();
    private final HashMap<UUID, CooldownData> cooldowns = new HashMap<UUID, CooldownData>();

    public static NoirFly inst() {
        return inst;
    }

    public static Debug debug() {
        return debug;
    }

    @Override
    public void onEnable() {
        inst = this;
        debug = new Debug(this);
        FlyConfig.inst();

        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("flypotion").setExecutor(new FlyPotionsCommand());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PotionsListener(), this);
        xpHandler = XPHandler.inst();
    }

    @Override
    public void onDisable() {
        for (UUID player : flying.keySet()) {
            Player p = Util.player(player).getPlayer();
            if (p == null) continue;
            p.setAllowFlight(false);
            flying.remove(p.getUniqueId());
            xpHandler.resetPlayerXP(p);
            p.sendMessage(ChatColor.GOLD + "You can no longer fly.");
        }
    }

    public boolean startFly(Player player, FlyType type) {
        UUID uuid = player.getUniqueId();

        if (isFlying(uuid) || player.getAllowFlight()) { // player is already flying
            player.sendMessage(ChatColor.GOLD + "You're already flying!");
            return false;
        }

        if (cooldowns.containsKey(player.getUniqueId())) {
            long now = System.currentTimeMillis();
            CooldownData data = cooldowns.get(uuid);
            long diff = now - data.time - TimeUnit.SECONDS.toMillis(data.type.getCooldown());
            if (diff < 0) { // Still has time remaining
                player.sendMessage(ChatColor.GOLD + "You must wait " + TimeUnit.MILLISECONDS.toSeconds(Math.abs(diff)) + " seconds before flying.");
                return false;
            }
            cooldowns.remove(uuid);
        }

        XPTimer timer = null;
        StopFlyTask task = null;

        if (type.isPermanent()) {
            player.sendMessage(ChatColor.GOLD + "You can now fly permanently!");
        } else {
            player.sendMessage(ChatColor.GOLD + "You can now fly for " + type.getTime() + " seconds!");
            timer = xpHandler.xpTimer(player, type.getTicks());
            task = new StopFlyTask(uuid, type.getTicks());
        }

        player.setAllowFlight(true);
        flying.put(uuid, new FlyData(type, timer, task));
        return true;
    }

    public void stopFly(UUID player) {
        if (!isFlying(player)) return;
        if (flying.get(player).type.getCooldown() > 0) {
            cooldowns.put(player, new CooldownData(System.currentTimeMillis(), flying.get(player).type));
        }

        FlyData data = flying.get(player);
        if (data.timer != null) data.timer.cancel();
        if (data.stopTask != null) data.stopTask.cancel();
        flying.remove(player);

        if (Util.player(player).isOnline()) {
            Player p = Util.player(player).getPlayer();
            p.setAllowFlight(false);
            p.sendMessage(ChatColor.GOLD + "You can no longer fly.");
            Util.cancelFall(p);
        }
    }

    public boolean isFlying(UUID player) {
        return flying.containsKey(player);
    }

    public FlyType getFlyType(UUID player) {
        return flying.get(player).type;
    }

    private static class CooldownData {
        long time;
        FlyType type;

        public CooldownData(long time, FlyType type) {
            this.time = time;
            this.type = type;
        }
    }

    private static class FlyData {
        FlyType type;
        XPTimer timer;
        StopFlyTask stopTask;

        public FlyData(FlyType type, XPTimer timer, StopFlyTask stopTask) {
            this.type = type;
            this.timer = timer;
            this.stopTask = stopTask;
        }
    }
}
