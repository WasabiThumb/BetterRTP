package codes.wasabi.betterrtp.api;

import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RTPHandler {

    private final Random random = new Random();

    private boolean rtp(@NotNull World world, @NotNull Player player, double originX, double originZ, double radius, boolean allowFluids) {
        double sX = originX + (radius * random.nextDouble() * (random.nextBoolean() ? -1 : 1));
        double sZ = originZ + (radius * random.nextDouble() * (random.nextBoolean() ? -1 : 1));
        int ix = (int) Math.round(sX);
        int iz = (int) Math.round(sZ);

        Block sampleBlock = world.getHighestBlockAt(ix, iz, HeightMap.WORLD_SURFACE);
        while (sampleBlock.getY() > 0) {
            boolean isLiquid = sampleBlock.isLiquid();
            if (isLiquid && (!allowFluids)) return false;
            if ((!sampleBlock.isPassable()) || isLiquid) break;
            sampleBlock = sampleBlock.getRelative(0, -1, 0);
        }

        if (sampleBlock.getY() <= 0) return false;
        if (sampleBlock.getY() < world.getMaxHeight()) {
            Block blockAbove = sampleBlock.getRelative(0, 1, 0);
            if (!blockAbove.isPassable()) return false;
        }
        if ((sampleBlock.getY() + 1) < world.getMaxHeight()) {
            Block blockAbove = sampleBlock.getRelative(0, 2, 0);
            if (!blockAbove.isPassable()) return false;
        }

        Location spawnLocation = sampleBlock.getLocation().toCenterLocation().add(0d, 0.5d, 0d);
        player.teleport(spawnLocation);
        return true;
    }

    public boolean rtp(@NotNull World world, @NotNull Player player, double originX, double originZ, double radius, boolean allowFluids, int tries) {
        int num = 0;
        while (num < tries) {
            if (rtp(world, player, originX, originZ, radius, allowFluids)) return true;
            num++;
        }
        return false;
    }

}
