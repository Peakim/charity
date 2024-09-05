package org.peakimo.charity.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class FreezeGUI implements Listener {
   @EventHandler
   public void onInventoryMove(InventoryClickEvent event) {
      if (event.getView().getTitle().equals("Charity")) {
         event.setCancelled(true);
      }

   }
}
