package riskyken.cosmeticWings.common.wings.types;

import riskyken.cosmeticWings.client.render.wings.IWingRenderer;
import riskyken.cosmeticWings.client.render.wings.RenderWingsMech;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WingsMech extends AbstractWings {

    public WingsMech() {
        super("mech");
    }
    
    @Override
    public int getNumberOfRenderLayers() {
        return 2;
    }
    
    @Override
    public boolean canRecolour(int layer) {
        if (layer == 1) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean isNomalRender(int layer) {
        if (layer == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean isGlowing(int layer) {
        if (layer == 1) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Class<? extends IWingRenderer> getRendererClass() {
        return RenderWingsMech.class;
    }
}
