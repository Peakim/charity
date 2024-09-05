package org.peakimo.charity.Events;

import java.util.Iterator;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.peakimo.charity.Charity;

public class clickGUI implements Listener {
   private static Economy econ = null;
   private Charity instance = Charity.getInstance();

   @EventHandler
   public void onInventoryClick(InventoryClickEvent event) {
      Player bazikon = (Player)event.getWhoClicked();
      String playerName = bazikon.getName();
      int givetocharity = this.instance.getConfig().getInt("give-charity");
      ItemStack BLUE_CONCRETE = new ItemStack(Material.BLUE_CONCRETE);
      ItemMeta BLUE_CONCRETEMeta = BLUE_CONCRETE.getItemMeta();
      BLUE_CONCRETEMeta.setDisplayName(this.color("&aSadaghe dadan " + givetocharity + "$"));
      ItemStack clickedItem = event.getCurrentItem();
      if (event.getView().getTitle().equals("Charity") && clickedItem != null && clickedItem.getType() == Material.BLUE_CONCRETE & clickedItem.getItemMeta().getDisplayName().equals(this.color("&aSadaghe dadan " + givetocharity + "$"))) {
         Economy economy = Charity.getEconomy();
         String PlayerName = event.getWhoClicked().getName();
         double balance = economy.getBalance(PlayerName);
         if (balance >= (double)givetocharity) {
            economy.withdrawPlayer(bazikon, (double)givetocharity);
            bazikon.sendMessage(ChatColor.GREEN + "You give " + givetocharity + "$ to charity!");
            bazikon.performCommand("playsound minecraft:khayer player " + playerName + " ~ ~ ~ 1");
            Iterator var13 = Bukkit.getOnlinePlayers().iterator();

            while(var13.hasNext()) {
               Player onlineplayers = (Player)var13.next();
               onlineplayers.sendMessage(this.color("&7[&a&lCharity&r&7] &a" + PlayerName + " &aDonated " + givetocharity + "$ to charity"));
               onlineplayers.playSound(onlineplayers.getLocation(), Sound.ENTITY_PIGLIN_JEALOUS, 1.0F, 1.0F);
            }
         }
      }

   }

   private String color(String str) {
      return ChatColor.translateAlternateColorCodes('&', str);
   }
}
