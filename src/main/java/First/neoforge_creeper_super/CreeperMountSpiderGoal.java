package First.neoforge_creeper_super;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;

import java.util.EnumSet;
import java.util.List;

import static First.neoforge_creeper_super.Config.MountSpider;

public class CreeperMountSpiderGoal extends Goal {
    private final Creeper creeper;
    private Spider targetSpider;
    private final double speedModifier;
    private int timeToRecalcPath;

    public CreeperMountSpiderGoal(Creeper creeper, double speed) {
        this.creeper = creeper;
        this.speedModifier = speed;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (creeper.getVehicle() instanceof Spider) {
            return false;
        }
        List<Spider> entityList = creeper.level().getEntitiesOfClass(Spider.class,
                creeper.getBoundingBox().inflate(16.0D),
                spider -> !spider.isVehicle() && spider.isAlive());

        if (entityList.isEmpty()) {
            return false;
        }
        this.targetSpider = entityList.get(0);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return !creeper.isVehicle() &&
                targetSpider != null &&
                targetSpider.isAlive() &&
                creeper.distanceToSqr(targetSpider) < 100.0D;
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.targetSpider = null;
    }

    @Override
    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            creeper.getNavigation().moveTo(targetSpider, speedModifier);
        }

        if (creeper.distanceToSqr(targetSpider) < 2.25D) {
            creeper.startRiding(targetSpider, true);
            stop();
        }
    }
}