package nz.co.noirland.noirfly;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FlyCommand implements CommandExecutor {

    private final NoirFly plugin = NoirFly.inst();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players may use this command.");
            return true;
        }

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        FlyType type;
        if (player.hasPermission(NoirFly.PERMAFLY_PERM)) {
            type = FlyType.PERMANENT;
        } else if (player.hasPermission(NoirFly.FLY_PERM)) {
            type = FlyType.COMMAND;
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
            return true;
        }
        if (plugin.isFlying(uuid)) {
            if (plugin.getFlyType(uuid) == FlyType.COMMAND || plugin.getFlyType(uuid) == FlyType.PERMANENT) {
                plugin.stopFly(uuid);
            } else {
                player.sendMessage(ChatColor.GOLD + "You cannot disable fly right now.");
            }
        } else {
            plugin.startFly(player, type);
        }

        return true;
    }
}
