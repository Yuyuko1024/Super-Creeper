package First.neoforge_creeper_super;


import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static First.neoforge_creeper_super.Config.*;

@EventBusSubscriber(modid = Neoforge_creeper_super.MODID, bus = EventBusSubscriber.Bus.GAME)
public class CreeperEvent {
    @SubscribeEvent
    public static void livingHurtEvent(LivingIncomingDamageEvent livingHurtEvent) {
        LivingEntity livingEntity = livingHurtEvent.getEntity();
        double damage = livingHurtEvent.getAmount();
        DamageSource damageSource = livingHurtEvent.getSource();
        Level level = livingEntity.level();
        if (!level.isClientSide() && livingEntity instanceof Creeper creeper) {
            if ((damage > creeper.getHealth() && creeper.getSwellDir() > 0 && IrreversibleExplosion)) {
                creeper.heal(1);
                creeper.setSwellDir(30);
                livingHurtEvent.setCanceled(true);
                return;
            }
            if (damage > creeper.getHealth() && damageSource.is(DamageTypes.FALL) && SnapPop) {
                creeper.heal(1);
                creeper.setSwellDir(30);
                livingHurtEvent.setCanceled(true);
                return;
            }
            if (damageSource.getEntity() instanceof Creeper && ChainExplosion) {
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
