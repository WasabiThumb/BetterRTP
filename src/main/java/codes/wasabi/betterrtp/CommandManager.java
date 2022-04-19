package codes.wasabi.betterrtp;

import codes.wasabi.betterrtp.api.RTPHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class CommandManager implements CommandExecutor {

    private final RTPHandler rtp = new RTPHandler();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Config config = BetterRTP.config;
        if (command.getName().equalsIgnoreCase("rtp")) {
            if (sender instanceof Player ply) {
                if (!config.isEnabled()) {
                    sender.sendMessage(Component.text("RTP is currently disabled by server administrators.").color(NamedTextColor.RED));
                    return true;
                }
                World world = ply.getWorld();
                boolean allowed = false;
                switch (world.getEnvironment()) {
                    case NORMAL -> allowed = config.isEnabledOverworld();
                    case NETHER -> allowed = config.isEnabledNether();
                    case THE_END -> allowed = config.isEnabledEnd();
                }
                if (!allowed) {
                    sender.sendMessage(Component.text("RTP is not allowed in this dimension!").color(NamedTextColor.RED));
                    return true;
                }
                ply.sendActionBar(Component.text("Finding a random location...").color(NamedTextColor.DARK_AQUA));
                boolean success = rtp.rtp(world, ply, config.getOriginX(), config.getOriginZ(), config.getRadius(), config.getSpawnOverFluids(), config.getMaxRetries());
                if (success) {
                    sender.sendMessage(Component.text("Teleported!").color(NamedTextColor.GOLD));
                    Location loc = ply.getLocation();
                    world.spawnParticle(Particle.PORTAL, loc, 10, 0.02d, 0.02d, 0.02d);
                    world.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                    //
                    double invincibility = config.getInvincibilityDuration();
                    if (invincibility > 0d) {
                        int ticks = (int) Math.ceil(invincibility * 20d);
                        PotionEffect pe;
                        pe = new PotionEffect(PotionEffectType.HEAL, ticks, 255);
                        ply.addPotionEffect(pe);
                    }
                } else {
                    sender.sendMessage(Component.text("Could not find a suitable place to teleport you to.").color(NamedTextColor.RED));
                }
            } else {
                sender.sendMessage(Component.text("You must be a player to run this command!").color(NamedTextColor.RED));
            }
            return true;
        }
        return false;
    }

}
