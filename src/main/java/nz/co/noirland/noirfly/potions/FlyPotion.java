package nz.co.noirland.noirfly.potions;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class FlyPotion extends ItemStack {

    public FlyPotion() {
        super(Material.POTION, 1);

        ItemMeta im = getItemMeta();
        im.setDisplayName(ChatColor.BOLD + "" + ChatColor.GOLD + "Fly Potion");
        im.setLore(lore());
        setItemMeta(im);
    }

    protected ArrayList<String> lore() {
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.ITALIC + "Drink, then double tap");
        lore.add(ChatColor.ITALIC + "space to fly!");
        return lore;
    }
}
