package First.creeper_super;


import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
            // 检查是否是玩家或者生物造成的伤害
            if (damageSource.getEntity() instanceof LivingEntity attacker) {
                // 判断是否为近战伤害
                if (isDirectDamage(damageSource)) {
                    // 如果是近战伤害，则直接瞬爆
                    creeper.setSwellDir(3);
                    creeper.getPersistentData().putBoolean("Boom", true);
                }
            }


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

    // 检查是否为直接伤害
    private static boolean isDirectDamage(DamageSource damageSource) {
        return damageSource.is(DamageTypes.PLAYER_ATTACK) ||
                damageSource.is(DamageTypes.MOB_ATTACK) ||
                (damageSource.getDirectEntity() == damageSource.getEntity());
    }

}
