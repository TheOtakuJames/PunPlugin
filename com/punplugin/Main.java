/*     */ package com.punplugin;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.ConsoleCommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class Main extends JavaPlugin
/*     */ {
/*     */   public void onEnable()
/*     */   {
/*  19 */     getLogger().log(Level.INFO, "PunPlugin enabled!");
/*  20 */     saveDefaultConfig();
/*     */   }
/*     */ 
/*     */   public String color(String t) {
/*  24 */     return ChatColor.translateAlternateColorCodes('&', t);
/*     */   }
/*     */ 
/*     */   public boolean onCommand(CommandSender sender, Command command, String string, String[] args)
/*     */   {
/*  29 */     if (command.getName().equalsIgnoreCase("puns")) {
/*  30 */       if ((sender instanceof ConsoleCommandSender)) {
/*  31 */         getLogger().log(Level.WARNING, "Please use this command in-game!");
/*  32 */         return true;
/*     */       }
/*  34 */       Player p = (Player)sender;
/*  35 */       if (getConfig().getString("players." + p.getName()) == null) {
/*  36 */         getConfig().set("players." + p.getName(), "0");
/*  37 */         saveConfig();
/*  38 */         reloadConfig();
/*     */       }
/*  40 */       String puns = getConfig().getString("players." + p.getName());
/*  41 */       p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPuns: &6" + puns));
/*  42 */       return true;
/*     */     }
/*  44 */     if (command.getName().equalsIgnoreCase("pun")) {
/*  45 */       if ((sender instanceof ConsoleCommandSender)) {
/*  46 */         getLogger().log(Level.WARNING, "Please use this command in-game!");
/*  47 */         return true;
/*     */       }
/*  49 */       Player p = (Player)sender;
/*  50 */       if (args.length < 1) {
/*  51 */         p.sendMessage(color("&cUsage: /pun <add|remove> <player>"));
/*  52 */         return true;
/*     */       }
/*  54 */       if (!p.hasPermission("punpoints.admin")) {
/*  55 */         p.sendMessage("Unknown command. Type \"help\" for help.");
/*  56 */         return true;
/*     */       }
/*  58 */       if (args[0].equalsIgnoreCase("add")) {
/*  59 */         if (args.length > 1) {
/*  60 */           Player target = Bukkit.getPlayer(args[1]);
/*  61 */           if (target != null) {
/*  62 */             int amount = 0;
/*  63 */             if (getConfig().getString("players." + target.getName()) != null) {
/*  64 */               amount = Integer.valueOf(getConfig().getString("players." + target.getName())).intValue() + 1;
/*     */             }
/*  66 */             if (amount == 0) amount = 1;
/*  67 */             getConfig().set("players." + target.getName(), Integer.valueOf(amount));
/*  68 */             Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&3&lPun Points: &a" + target.getName() + " &brecieved a pun point!"));
/*  69 */             saveConfig();
/*  70 */             reloadConfig();
/*  71 */             return true;
/*     */           }
/*  73 */           String player = args[1];
/*  74 */           int amount = 0;
/*  75 */           if (getConfig().getString("players." + player) != null) {
/*  76 */             amount = Integer.valueOf(getConfig().getString("players." + player)).intValue() + 1;
/*     */           }
/*  78 */           if (amount == 0) amount = 1;
/*  79 */           getConfig().set("players." + target.getName(), Integer.valueOf(amount));
/*  80 */           Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&3&lPun Points: &a" + target.getName() + " &brecieved a pun point!"));
/*  81 */           saveConfig();
/*  82 */           reloadConfig();
/*     */         }
/*     */         else {
/*  85 */           p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /pun add <player>"));
/*  86 */           return true;
/*     */         }
/*  88 */         return true;
/*     */       }
/*     */ 
/*  91 */       if (args[0].equalsIgnoreCase("remove"))
/*     */       {
/*  93 */         if (args.length > 1) {
/*  94 */           Player target = Bukkit.getPlayer(args[1]);
/*  95 */           if (target != null) {
/*  96 */             int amount = 0;
/*  97 */             if (getConfig().getString("players." + target.getName()) != null) {
/*  98 */               amount = Integer.valueOf(getConfig().getString("players." + target.getName())).intValue();
/*  99 */               if (amount == 0) {
/* 100 */                 p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player has no pun points!"));
/* 101 */                 return true;
/*     */               }
/*     */             } else {
/* 104 */               p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThatP player has no pun points!"));
/* 105 */               getConfig().set("players." + target.getName(), "0");
/* 106 */               return true;
/*     */             }
/* 108 */             amount--;
/* 109 */             if (amount == 0) {
/* 110 */               Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c" + target.getName() + " lost all their pun points!"));
/* 111 */               getConfig().set("players." + target.getName(), "0");
/* 112 */               saveConfig();
/* 113 */               reloadConfig();
/* 114 */               return true;
/*     */             }
/* 116 */             getConfig().set("players." + target.getName(), Integer.valueOf(amount));
/* 117 */             saveConfig();
/* 118 */             reloadConfig();
/* 119 */             Bukkit.broadcastMessage(color("&3&lPun Points: &a" + target.getName() + " &blost a pun point!"));
/*     */           } else {
/* 121 */             String player = args[1];
/* 122 */             int amount = 0;
/* 123 */             if (getConfig().getString("players." + player) != null) {
/* 124 */               amount = Integer.valueOf(getConfig().getString("players." + player)).intValue();
/* 125 */               if (amount == 0) {
/* 126 */                 p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player has no pun points!"));
/* 127 */                 return true;
/*     */               }
/*     */             } else {
/* 130 */               p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player has no pun points!"));
/* 131 */               getConfig().set("players." + player, "0");
/* 132 */               return true;
/*     */             }
/* 134 */             amount--;
/* 135 */             if (amount == 0) {
/* 136 */               Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player + " lost all their pun points!"));
/* 137 */               getConfig().set("players." + player, "0");
/* 138 */               saveConfig();
/* 139 */               reloadConfig();
/* 140 */               return true;
/*     */             }
/* 142 */             getConfig().set("players." + player, Integer.valueOf(amount));
/* 143 */             saveConfig();
/* 144 */             reloadConfig();
/* 145 */             Bukkit.broadcastMessage(color("&3&lPun Points: &a" + player + " &blost a pun point!"));
/*     */           }
/*     */         } else {
/* 148 */           p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /pun remove [player]"));
/* 149 */           return true;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 154 */       return true;
/*     */     }
/* 156 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\james\Downloads\PunPlugin.jar
 * Qualified Name:     com.punplugin.Main
 * JD-Core Version:    0.6.2
 */