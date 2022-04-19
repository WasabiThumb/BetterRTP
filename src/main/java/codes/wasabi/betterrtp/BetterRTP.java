package codes.wasabi.betterrtp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class BetterRTP extends JavaPlugin {

    public static Logger logger;
    public static CommandManager commands;
    public static Config config;

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.log(Level.INFO, "Loading config");
        saveDefaultConfig();
        config = new Config(getConfig());
        logger.log(Level.INFO, "Registering commands");
        commands = new CommandManager();
        logger.log(Level.INFO, "Loaded");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.log(Level.INFO, "Disabling");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return commands.onCommand(sender, command, label, args);
    }
}
