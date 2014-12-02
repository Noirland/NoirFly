package nz.co.noirland.noirfly;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

class StopFlyTask extends BukkitRunnable {

    private UUID player;

    public StopFlyTask(UUID player, long ticks) {
        this.player = player;
        runTaskLater(NoirFly.inst(), ticks);
    }

    public void run() {
        NoirFly.inst().stopFly(player);
    }

}
