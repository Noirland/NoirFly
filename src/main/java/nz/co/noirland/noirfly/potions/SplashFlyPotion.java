package nz.co.noirland.noirfly.potions;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public class SplashFlyPotion extends FlyPotion {

    public SplashFlyPotion() {
        super();
        setDurability((short) 16384);
    }

    @Override
    protected ArrayList<String> lore() {
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.ITALIC + "Throw, then double tap");
        lore.add(ChatColor.ITALIC + "space to fly!");
        return lore;
    }
}
