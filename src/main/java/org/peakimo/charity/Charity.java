package org.peakimo.charity;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.peakimo.charity.Commands.reload;
import org.peakimo.charity.Events.FreezeGUI;
import org.peakimo.charity.Events.clickGUI;
import org.peakimo.charity.Events.givecharity;

public final class Charity extends JavaPlugin {
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    public static Charity instance;

    public static Charity getInstance() {
        return instance;
    }

    public void onDisable() {
        this.getLogger().info(String.format("[%s] Disabled Version %s", this.getDescription().getName(), this.getDescription().getVersion()));
    }

    public void onEnable() {
        this.saveDefaultConfig();
        instance = this;
        if (!this.setupEconomy()) {
            this.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", this.getDescription().getName()));
            this.getServer().getPluginManager().disablePlugin(this);
        } else {
            this.setupPermissions();
            this.setupChat();

            // Events
            this.getServer().getPluginManager().registerEvents(new givecharity(), this);
            this.getServer().getPluginManager().registerEvents(new clickGUI(), this);
            this.getServer().getPluginManager().registerEvents(new FreezeGUI(), this);
            //Commands
            this.getCommand("charity-reload").setExecutor(new reload());
        }
    }

    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            } else {
                econ = (Economy)rsp.getProvider();
                return econ != null;
            }
        }
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = this.getServer().getServicesManager().getRegistration(Chat.class);
        chat = (Chat)rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = this.getServer().getServicesManager().getRegistration(Permission.class);
        perms = (Permission)rsp.getProvider();
        return perms != null;
    }

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            this.getLogger().info("Only players are supported for this Example Plugin, but you should not do this!!!");
            return true;
        } else {
            Player player = (Player)sender;
            if (command.getLabel().equals("test-economy")) {
                sender.sendMessage(String.format("You have %s", econ.format(econ.getBalance(player.getName()))));
                EconomyResponse r = econ.depositPlayer(player, 1.05D);
                if (r.transactionSuccess()) {
                    sender.sendMessage(String.format("You were given %s and now have %s", econ.format(r.amount), econ.format(r.balance)));
                } else {
                    sender.sendMessage(String.format("An error occured: %s", r.errorMessage));
                }

                return true;
            } else if (command.getLabel().equals("test-permission")) {
                if (perms.has(player, "example.plugin.awesome")) {
                    sender.sendMessage("You are awesome!");
                } else {
                    sender.sendMessage("You suck!");
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public static Economy getEconomy() {
        return econ;
    }
}
