package First.neoforge_creeper_super;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;

import java.util.EnumSet;
import java.util.Objects;

import static First.neoforge_creeper_super.Config.FindExplodableBlock;

public class FindExplodableBlockGoal extends Goal {
    private final Creeper creeper;
    private final int radius;
    private BlockPos targetPos;
    /**
     * 苦力怕主动搜寻目标方块并爆破
     */
    public FindExplodableBlockGoal(Creeper creeper, int radius) {
        this.creeper = creeper;
        this.radius = radius;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        BlockPos creeperPos = creeper.blockPosition();
        Level level = creeper.level();
        ChunkPos chunkPos = level.getChunk(creeperPos).getPos();
        double minDistance = Double.MAX_VALUE;
        BlockPos nearestPos = null;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkAccess chunk = level.getChunk(chunkPos.x + dx, chunkPos.z + dz);
                for (BlockPos pos : chunk.getBlockEntitiesPos()) {
                    if (isValidTarget(pos)) {
                        double distance = pos.distSqr(creeperPos);
                        if (distance < minDistance) {
                            minDistance = distance;
                            nearestPos = pos;
                        }
                    }
                }
            }
        }

        if (nearestPos != null) {
            this.targetPos = nearestPos;
            return true;
        }
        return false;
    }

    private boolean isValidTarget(BlockPos pos) {
        return pos.distSqr(creeper.blockPosition()) <= radius * radius &&
                creeper.level().getBlockState(pos).getExplosionResistance(creeper.level(), pos, null) < 30;
    }

    @Override
    public boolean canContinueToUse() {
        return targetPos != null &&
                creeper.getNavigation().isInProgress() &&
                creeper.level().getBlockEntity(targetPos) != null && !Objects.requireNonNull(creeper.level().getBlockEntity(targetPos)).isRemoved();
    }

    @Override
    public void start() {
        creeper.getNavigation().moveTo(
                targetPos.getX() + 0.5,
                targetPos.getY(),
                targetPos.getZ() + 0.5,
                1.3
        );
    }

    @Override
    public void tick() {
        if (creeper.getVehicle() instanceof Spider spider) {
            spider.getNavigation().setSpeedModifier(1.3);
        }
        if (targetPos != null) {
            creeper.getLookControl().setLookAt(
                    targetPos.getX() + 0.5,
                    targetPos.getY() + 0.5,
                    targetPos.getZ() + 0.5,
                    10.0F,
                    creeper.getMaxHeadXRot()
            );
            double distance = creeper.distanceToSqr(
                    targetPos.getX(),
                    targetPos.getY(),
                    targetPos.getZ()
            );
            creeper.setSwellDir((int) (10 / distance));
        }
    }

    @Override
    public void stop() {
        targetPos = null;
        creeper.getNavigation().stop();
    }
}