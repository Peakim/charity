package org.peakimo.charity.Events;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.peakimo.charity.Charity;

public class givecharity implements Listener {
   private Charity instance = Charity.getInstance();
   private static Economy econ = null;

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.BLUE_GLAZED_TERRACOTTA) {
         int givetocharity = this.instance.getConfig().getInt("give-charity");
         Player bazikon = event.getPlayer();
         Economy economy = Charity.getEconomy();
         Inventory gui = Bukkit.createInventory(bazikon, 9, "Charity");
         ItemStack BLUE_CONCRETE = new ItemStack(Material.BLUE_CONCRETE);
         ItemMeta BLUE_CONCRETEMeta = BLUE_CONCRETE.getItemMeta();
         BLUE_CONCRETEMeta.setDisplayName(this.color("&aSadaghe dadan " + givetocharity + "$"));
         BLUE_CONCRETE.setItemMeta(BLUE_CONCRETEMeta);
         gui.setItem(4, BLUE_CONCRETE);
         bazikon.openInventory(gui);
      }

   }

   private String color(String str) {
      return ChatColor.translateAlternateColorCodes('&', str);
   }
}
