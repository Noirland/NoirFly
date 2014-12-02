package nz.co.noirland.noirfly.xpbar;

import nz.co.noirland.noirfly.NoirFly;
import nz.co.noirland.zephcore.Util;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class XPTimer extends BukkitRunnable {

    XPHandler xpHandler = XPHandler.inst();
    UUID player;
	int count = 18;

	public XPTimer(UUID player, long ticks) {
		this.player = player;
        runTaskTimer(NoirFly.inst(), 0, ticks);
	}

	public void run() {
        OfflinePlayer offline = Util.player(player);
        if (!offline.isOnline()) {
            this.cancel();
            return;
        }

        Player player = offline.getPlayer();

        if(count >= 0) {
            xpHandler.setPlayerXP(player, count--);
        }else{
            xpHandler.resetPlayerXP(player);
            this.cancel();
        }
    }

    @Override
    public void cancel() {
        super.cancel();
        OfflinePlayer offline = Util.player(player);
        if(offline.isOnline()) {
            xpHandler.resetPlayerXP(offline.getPlayer());
        }
    }
}
