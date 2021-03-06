package riskyken.cosmeticWings.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import riskyken.cosmeticWings.CosmeticWings;
import riskyken.cosmeticWings.client.settings.Keybindings;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyboardHandler {

    public KeyboardHandler() {
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onKeyInputEvent(InputEvent.KeyInputEvent event) {
        if (Keybindings.openWingsGui.isPressed()) {
            EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
            player.openGui(CosmeticWings.instance, 0, player.worldObj, 0, 0, 0);
        }
    }
}
