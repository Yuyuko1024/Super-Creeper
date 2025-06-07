package First.neoforge_creeper_super.mixin;


import First.neoforge_creeper_super.CreeperMountSpiderGoal;
import First.neoforge_creeper_super.FindExplodableBlockGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static First.neoforge_creeper_super.Config.*;

@Mixin(Creeper.class)
public abstract class CreeperMixin extends Mob {
    protected CreeperMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void onRegisterGoals(CallbackInfo ci) {
        Creeper creeper = (Creeper) (Object) this;
        if (MountSpider) {
            this.goalSelector.addGoal(1, new CreeperMountSpiderGoal(creeper, 1.1));
        }
        if (FindExplodableBlock) {
            this.goalSelector.addGoal(2, new FindExplodableBlockGoal(creeper, 24));
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        Creeper creeper = (Creeper) (Object) this;
        if (creeper.getPersistentData().getBoolean("Boom")) {
            creeper.setSwellDir(3);
        }
        if (creeper.isOnFire() && FIRE) {
            creeper.setSwellDir(1);
        }
    }

}