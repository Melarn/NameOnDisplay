package me.melarn.nameondisplay;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class NameOnDisplay extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("ExamplePlugin has been enabled.");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("ExamplePlugin has been disabled.");
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) {
            return;
        }

        Player player = event.getPlayer();
        Player target = getTargetPlayer(player);
        if (target != null) {
            sendActionBarMessage(player, ChatColor.GREEN + target.getName() + " was targeted!");
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Player target = getTargetPlayer(player);
        if (target != null) {
            sendActionBarMessage(player, ChatColor.DARK_AQUA + target.getName());
        }
    }

    private Player getTargetPlayer(Player player) {
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (player == target) {
                continue;
            }

            if (player.isSneaking() && player.isSneaking() && player.getLocation().distance(target.getLocation()) <= 5) {
                return target;
            }
        }

        return null;
    }

    private void sendActionBarMessage(Player player, String message) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendActionBar(message);
            }
        }.runTask(this);
    }
}
