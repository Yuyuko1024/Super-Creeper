package First.creeper_super.mixin;


import First.creeper_super.CreeperMountSpiderGoal;
import First.creeper_super.FindExplodableBlockGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public abstract class CreeperMixin extends Mob {
    protected CreeperMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void onRegisterGoals(CallbackInfo ci) {
        Creeper creeper = (Creeper) (Object) this;
        this.goalSelector.addGoal(1, new CreeperMountSpiderGoal(creeper, 1.1));
        this.goalSelector.addGoal(1, new OpenDoorGoal(creeper, true));
        this.goalSelector.addGoal(2, new FindExplodableBlockGoal(creeper, 24));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        Creeper creeper = (Creeper) (Object) this;
        if (creeper.getPersistentData().getBoolean("Boom")) {
            creeper.setSwellDir(3);
        }
        if (creeper.isOnFire()) {
            creeper.setSwellDir(1);
        }
    }

}