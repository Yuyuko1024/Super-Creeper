package First.neoforge_creeper_super;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Neoforge_creeper_super.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {


    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.BooleanValue Irreversible_Explosion = BUILDER
            .comment("如果已经准备爆炸，那么苦力怕死亡时产生爆炸")
            .translation("不可挽回")
            .define("Irreversible_Explosion", true);
    public static final ModConfigSpec.BooleanValue Snap_Pop = BUILDER
            .comment("苦力怕摔落致死时产生爆炸")
            .translation("摔炮")
            .define("Snap_Pop", true);
    public static final ModConfigSpec.BooleanValue Chain_Explosion = BUILDER
            .comment("苦力怕受到苦力怕爆炸伤害时，根据受到伤害量产生不同延迟的爆炸")
            .translation("链式反应")
            .define("Chain_Explosion", true);
    public static final ModConfigSpec.BooleanValue Mount_Spider = BUILDER
            .comment("苦力主动骑乘蜘蛛")
            .translation("蜘蛛骑士")
            .define("Mount_Spider", true);
    public static final ModConfigSpec.BooleanValue Find_ExplodableBlock = BUILDER
            .comment("苦力怕寻找工作方块并爆破")
            .translation("反科技")
            .define("Find_ExplodableBlock", true);
    public static final ModConfigSpec.BooleanValue Fire = BUILDER
            .comment("苦力怕燃烧时爆炸")
            .translation("引线")
            .define("Fire", true);




    public static boolean IrreversibleExplosion = true;
    public static boolean SnapPop = true;
    public static boolean ChainExplosion = true;
    public static boolean MountSpider = true;
    public static boolean FindExplodableBlock = true;
    public static boolean FIRE = true;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        IrreversibleExplosion = Irreversible_Explosion.get();
        SnapPop = Snap_Pop.get();
        ChainExplosion = Chain_Explosion.get();
        MountSpider = Mount_Spider.get();
        FindExplodableBlock = Find_ExplodableBlock.get();
        FIRE = Fire.get();
    }
}
