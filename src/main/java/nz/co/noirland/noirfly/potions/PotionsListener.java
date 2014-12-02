package nz.co.noirland.noirfly.potions;

import nz.co.noirland.noirfly.FlyType;
import nz.co.noirland.noirfly.NoirFly;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class PotionsListener implements Listener {

    private NoirFly plugin = NoirFly.inst();

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPotionSplash(PotionSplashEvent event) {
        ItemStack thrownPotion = event.getPotion().getItem(); // Get the splash potion
        Collection<LivingEntity> affectedList = event.getAffectedEntities(); // Get entities affected
        if (thrownPotion.isSimilar(new SplashFlyPotion())) { // Check if potion was a fly potion
            for (LivingEntity affected : affectedList) { // Allow flight for all affected players
                if (affected instanceof Player) {
                    Player player = (Player) affected;
                    plugin.startFly(player, FlyType.SPLASH_POTION);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        ItemStack consumed = event.getItem();
        Player consumer = event.getPlayer();
        if (consumed.isSimilar(new FlyPotion())) {
            if (!plugin.startFly(consumer, FlyType.POTION)) {
                event.setCancelled(true);
            }
        }
    }
}
