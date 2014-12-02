package nz.co.noirland.noirfly.xpbar;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import nz.co.noirland.noirfly.NoirFly;
import nz.co.noirland.zephcore.Util;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class XPHandler {
	private ProtocolManager protocolManager;
	private NoirFly plugin = NoirFly.inst();
    private boolean enabled = true;
    private static XPHandler inst;

    public static XPHandler inst() {
        if(inst == null) {
            inst = new XPHandler();
        }
        return inst;
    }

	private XPHandler() {
        Plugin lib = plugin.getServer().getPluginManager().getPlugin("ProtocolLib");
        if(lib == null || !lib.isEnabled()) {
            enabled = false;
            return;
        }
		protocolManager = ProtocolLibrary.getProtocolManager();
	}
	
	public XPTimer xpTimer(Player player, long time) {
        if(!enabled) return null;
		return new XPTimer(player.getUniqueId(), time / 18);
	}
	
	public void setPlayerXP(Player player, int bars) {
        if(!enabled) return;
		float exp = bars * ((float) 1/ 18);
		PacketContainer setXP = protocolManager.createPacket(PacketType.Play.Server.EXPERIENCE);

		setXP.getFloat().write(0, exp);
		setXP.getIntegers().write(1, 0);
		try {
			protocolManager.sendServerPacket(player, setXP);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public void resetPlayerXP(Player player) {
        if(!enabled) return;
        if(player != null){
            PacketContainer setXP = protocolManager.createPacket(PacketType.Play.Server.EXPERIENCE);
            setXP.getFloat().write(0, player.getExp());
            setXP.getIntegers().write(1, player.getLevel()).write(0, player.getTotalExperience());
            try {
                protocolManager.sendServerPacket(player, setXP);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
		
	}
	
}

