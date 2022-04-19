package codes.wasabi.betterrtp;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    private final FileConfiguration cfg;

    protected Config(FileConfiguration cfg) {
        this.cfg = cfg;
    }

    public boolean isEnabled() {
        return cfg.getBoolean("enabled", true);
    }

    public boolean isEnabledOverworld() {
        return cfg.getBoolean("enabled-overworld", true);
    }

    public boolean isEnabledNether() {
        return cfg.getBoolean("enabled-nether", false);
    }

    public boolean isEnabledEnd() {
        return cfg.getBoolean("enabled-end", false);
    }

    public double getRadius() {
        return cfg.getDouble("rtp-radius", 10000d);
    }

    public double getOriginX() {
        return cfg.getDouble("rtp-origin.x", 0);
    }

    public double getOriginZ() {
        return cfg.getDouble("rtp-origin.z", 0);
    }

    public boolean getSpawnOverFluids() {
        return cfg.getBoolean("spawn-over-fluids", false);
    }

    public int getMaxRetries() {
        return cfg.getInt("max-retries", 5);
    }

    public double getInvincibilityDuration() {
        return cfg.getDouble("invincibility-duration", 2d);
    }

}
