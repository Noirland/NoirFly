package nz.co.noirland.noirfly.potions;

import nz.co.noirland.zephcore.Util;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyPotionsCommand implements CommandExecutor {

    /*
     /flypotion [player]
     /flypotion [player] 5
     /flypotion [player] splash
     /flypotion [player] splash 5
    */
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("flypotion.give")) {
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use that command.");
                return true;
            }
        }
        if (args.length < 1) return false;

        OfflinePlayer offline = Util.player(args[0]);
        if (!offline.isOnline()) return false;
        Player p = offline.getPlayer();

        FlyPotion potion = new FlyPotion();

        if (args.length <= 0) {
            return false;
        }
        if (args.length >= 2) { // add number of normal, or 1 or more splash
            if (args[1].equals("splash")) { // give 1 or more splash
                potion = new SplashFlyPotion();
                if (args.length == 3) { // give multiple splash
                    potion.setAmount(Integer.parseInt((args[2])));
                }
            } else { // give multiple normal
                potion = new FlyPotion();
                potion.setAmount(Integer.parseInt((args[1])));
            }
        }
        p.getInventory().addItem(potion);
        return true;
    }

}
