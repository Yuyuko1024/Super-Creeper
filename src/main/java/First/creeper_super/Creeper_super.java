package First.creeper_super;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(Creeper_super.MODID)
public class Creeper_super {

    public static final String MODID = "creeper_super";

    public Creeper_super() {
        MinecraftForge.EVENT_BUS.register(CreeperDeath.class);
    }
}
