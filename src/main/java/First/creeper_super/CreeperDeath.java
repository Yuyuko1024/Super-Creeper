package First.creeper_super;


import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CreeperDeath {
    @SubscribeEvent
    public static void livingHurtEvent(LivingHurtEvent livingHurtEvent) {
        LivingEntity livingEntity = livingHurtEvent.getEntity();
        double damage = livingHurtEvent.getAmount();
        DamageSource damageSource = livingHurtEvent.getSource();
        Level level = livingEntity.level();
        if (!level.isClientSide() && livingEntity instanceof Creeper creeper) {
            if ((damage > creeper.getHealth() && creeper.getSwellDir() > 0)) {
                creeper.heal(1);
                creeper.setSwellDir(30);
                livingHurtEvent.setCanceled(true);
                return;
            }
            if (damage > creeper.getHealth() && damageSource.is(DamageTypes.FALL)) {
                creeper.heal(1);
                creeper.setSwellDir(30);
                livingHurtEvent.setCanceled(true);
                return;
            }
            if (damageSource != null && damageSource.getEntity() instanceof Creeper) {
                creeper.setSwellDir((int) (damage * 30 / creeper.getMaxHealth()));
                creeper.getPersistentData().putBoolean("Boom", true);
                if (damage > creeper.getHealth()) {
                    creeper.heal(1);
                    livingHurtEvent.setCanceled(true);
                }
            }
        }
    }

}
