package riskyken.cosmeticWings.proxies;

import java.util.UUID;

import riskyken.cosmeticWings.client.abstraction.RenderBridge;
import riskyken.cosmeticWings.client.handler.KeyboardHandler;
import riskyken.cosmeticWings.client.particles.ParticleManager;
import riskyken.cosmeticWings.client.render.WingRenderManager;
import riskyken.cosmeticWings.client.settings.Keybindings;
import riskyken.cosmeticWings.client.wings.ClientWingsCache;
import riskyken.cosmeticWings.common.wings.WingsData;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        RenderBridge.init();
    }
    
    @Override
    public void init() {
        new KeyboardHandler();
        ClientWingsCache.init();
    }

    @Override
    public void initRenderers() {
        WingRenderManager.init();
        ParticleManager.init();
    }

    @Override
    public void postInit() {
    }

    @Override
    public void registerKeyBindings() {
        ClientRegistry.registerKeyBinding(Keybindings.openWingsGui);
    }

    @Override
    public void receivedWingsData(UUID playerId, WingsData wingsData) {
        ClientWingsCache.INSTANCE.setWingsData(playerId, wingsData);
    }
}
