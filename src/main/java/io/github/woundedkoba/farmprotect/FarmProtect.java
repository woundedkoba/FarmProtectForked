package io.github.woundedkoba.farmprotect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public class FarmProtect extends JavaPlugin implements Listener {
  public static FarmProtect plugin;
  
  public Logger log;
  
  public String chatPluginName = ChatColor.translateAlternateColorCodes('&', "&c[&6FarmProtect&c] ");
  
  List<String> worlds = new ArrayList<>();
  
  List<Material> cropsList = new ArrayList<>(
      
      Arrays.asList(new Material[] { Material.WHEAT, Material.MELON_STEM, Material.PUMPKIN_STEM, Material.CARROT, Material.POTATO }));
  
  public void onEnable() {
    setLogger();
    createConfig();
    getConfig().getStringList("Worlds").stream()
      .filter(world -> (Bukkit.getWorld(world) != null))
      .forEach(world -> {
        
        });
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(this, (Plugin)this);
    this.log.info(String.format("Version %s by WoundedKoba has been Enabled!", new Object[] { getDescription().getVersion() }));
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
      sendHelp(sender, 1);
    } else if (args.length == 1) {
      if (args[0].equalsIgnoreCase("reload")) {
        if (sender.hasPermission("farmprotect.reload")) {
          reloadConfig();
          sender.sendMessage(String.valueOf(this.chatPluginName) + NamedTextColor.GREEN + "Plugin has successfully reloaded.");
          return true;
        } 
        sender.sendMessage(NamedTextColor.RED + "You do not have the permissions to use this command!");
        return true;
      } 
      if (args[0].equalsIgnoreCase("toggle")) {
        sendHelp(sender, 2);
      } else {
        sendHelp(sender, 1);
      } 
    } else if (args.length > 1) {
      if (args[0].equalsIgnoreCase("add")) {
        if (sender.hasPermission("farmprotect.addworld")) {
          if (Bukkit.getWorld(args[1]) == null) {
            sender.sendMessage(String.valueOf(this.chatPluginName) + NamedTextColor.RED + "not a valid world.");
          } else if (this.worlds.contains(args[1].toLowerCase())) {
            sender.sendMessage(String.valueOf(this.chatPluginName) + NamedTextColor.RED + "This world is already protected!");
          } else {
            this.worlds.add(args[1]);
            getConfig().set("Worlds", this.worlds);
            try {
              getConfig().save(new File(getDataFolder(), "config.yml"));
            } catch (IOException e) {
              e.printStackTrace();
            } 
            sender.sendMessage(String.valueOf(this.chatPluginName) + NamedTextColor.GOLD + args[1] + NamedTextColor.GREEN + "has been added.");
          } 
        } else {
          sender.sendMessage(NamedTextColor.RED + "You do not have the permissions to use this command!");
          return true;
        } 
      } else if (args[0].equalsIgnoreCase("remove")) {
        if (sender.hasPermission("farmprotect.removeworld")) {
          if (!this.worlds.contains(args[1].toLowerCase())) {
            sender.sendMessage(String.valueOf(this.chatPluginName) + NamedTextColor.RED + "This world isn't being protected!");
          } else {
            this.worlds.remove(args[1]);
            getConfig().set("Worlds", this.worlds);
            try {
              getConfig().save(new File(getDataFolder(), "config.yml"));
            } catch (IOException e) {
              e.printStackTrace();
            } 
            sender.sendMessage(String.valueOf(this.chatPluginName) + NamedTextColor.YELLOW + args[1] + NamedTextColor.GREEN + " has been removed.");
          } 
        } else {
          sender.sendMessage(NamedTextColor.RED + "You do not have the permissions to use this command!");
          return true;
        } 
      } else if (args[0].equalsIgnoreCase("toggle")) {
        if (args[1].equalsIgnoreCase("multiworld")) {
          toggleConfig(sender, "MultiWorld", "farmprotect.multiworld.toggle");
        } else if (args[1].equalsIgnoreCase("explosionprotect")) {
          toggleConfig(sender, "ExplosionProtect", "farmprotect.explosionprotect.toggle");
        } else {
          sendHelp(sender, 2);
        } 
      } else {
        sendHelp(sender, 1);
      } 
    } 
    return false;
  }
  
  public void sendHelp(CommandSender sender, int page) {
    sender.sendMessage(formatColor("&6o0=======&c[&eFarmProtect&c]&6========0o"));
    sender.sendMessage(formatColor("&bNOTE &f: &eAll farmprotect commands have an alias of /fp"));
    if (page == 2) {
      sender.sendMessage(formatColor("&b/fp toggle multiworld &f- &eTurns on/off multiworld protection support"));
      sender.sendMessage(formatColor("&b/fp toggle explosionprotect &f- &eTurns on/off explosion protection support"));
    } else {
      sender.sendMessage(formatColor("&b/fp toggle <option> &f- &eToggles certain config values /fp toggle for help"));
      sender.sendMessage(formatColor("&b/fp add <world> &f- &eAdds a world into protection"));
      sender.sendMessage(formatColor("&b/fp remove <world> &f- &eRemoves protection for a world"));
      sender.sendMessage(formatColor("&b/fp reload &f- &eReloads the config"));
    } 
  }
  
  public String formatColor(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }
  
  @EventHandler
  public void soilChangePlayer(PlayerInteractEvent event) {
    if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.FARMLAND)
      if (getConfig().getBoolean("MultiWorld")) {
        if (this.worlds.contains(event.getClickedBlock().getWorld().getName().toLowerCase()))
          event.setCancelled(true); 
      } else {
        event.setCancelled(true);
      }  
  }
  
  @EventHandler
  public void soilChangeEntity(EntityInteractEvent event) {
    if (event.getEntityType() != EntityType.PLAYER && event.getBlock().getType() == Material.FARMLAND)
      if (getConfig().getBoolean("MultiWorld")) {
        if (this.worlds.contains(event.getBlock().getWorld().getName().toLowerCase()))
          event.setCancelled(true); 
      } else {
        event.setCancelled(true);
      }  
  }
  
  @EventHandler
  public void onBlockExplode(BlockExplodeEvent event) {
    if (getConfig().getBoolean("ExplosionProtect")) {
      if (getConfig().getBoolean("MultiWorld") && 
        !this.worlds.contains(event.getBlock().getWorld().getName().toLowerCase()))
        return; 
      Iterator<Block> blockList = event.blockList().iterator();
      while (blockList.hasNext()) {
        Block block = blockList.next();
        if (block.getType() == Material.FARMLAND || this.cropsList.contains(block.getType()))
          blockList.remove(); 
      } 
    } 
  }
  
  @EventHandler
  public void onEntityExplode(EntityExplodeEvent event) {
    if (getConfig().getBoolean("ExplosionProtect")) {
      if (getConfig().getBoolean("MultiWorld") && 
        !this.worlds.contains(event.getEntity().getWorld().getName().toLowerCase()))
        return; 
      Iterator<Block> blockList = event.blockList().iterator();
      while (blockList.hasNext()) {
        Block block = blockList.next();
        if (block.getType() == Material.FARMLAND || this.cropsList.contains(block.getType()))
          blockList.remove(); 
      } 
    } 
  }
  
  public void toggleConfig(CommandSender sender, String configKey, String permission) {
    if (sender.hasPermission(permission)) {
      if (getConfig().getBoolean(configKey)) {
        getConfig().set(configKey, Boolean.valueOf(false));
        sender.sendMessage(String.valueOf(this.chatPluginName) + NamedTextColor.RED + configKey + " has been turned off.");
      } else {
        getConfig().set(configKey, Boolean.valueOf(true));
        sender.sendMessage(String.valueOf(this.chatPluginName) + NamedTextColor.GREEN + configKey + " has been turned on.");
      } 
      try {
        getConfig().save(new File(getDataFolder(), "config.yml"));
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } else {
      sender.sendMessage(NamedTextColor.RED + "You do not have the permissions to use this command!");
    } 
  }
  
  private void createConfig() {
    File file = new File(getDataFolder() + File.separator + "config.yml");
    if (!file.exists()) {
      this.log.info("Cannot find config.yml, Generating now....");
      saveDefaultConfig();
      this.log.info("Config generated !");
    } 
  }
  
  private void setLogger() {
    this.log = getLogger();
  }
  
}
