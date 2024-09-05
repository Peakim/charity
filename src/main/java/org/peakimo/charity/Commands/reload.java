package org.peakimo.charity.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.peakimo.charity.Charity;

public class reload implements CommandExecutor {
   private Charity instance = Charity.getInstance();

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      Player bazikon = (Player)sender;
      if (sender.hasPermission("Charity.reload")) {
         this.instance.reloadConfig();
         sender.sendMessage("Charity plugin reloaded.");
      }

      return true;
   }
}
