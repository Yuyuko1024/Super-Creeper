package First.neoforge_creeper_super;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;


@Mod(Neoforge_creeper_super.MODID)
public class Neoforge_creeper_super {
    public static final String MODID = "neoforge_creeper_super";

    public Neoforge_creeper_super(ModContainer modContainer) {

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.BUILDER.build());
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }

    }
}
