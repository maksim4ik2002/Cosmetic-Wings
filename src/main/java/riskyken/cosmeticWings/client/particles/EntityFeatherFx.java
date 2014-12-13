package riskyken.cosmeticWings.client.particles;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.client.abstraction.IRenderBuffer;
import riskyken.cosmeticWings.client.abstraction.RenderBridge;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityFeatherFx extends EntityFX {

    private static final ResourceLocation particleTextures;
    private static final ResourceLocation whiteFeather;
    private static final ResourceLocation blackFeather;
    private static final ResourceLocation redFeather;
    
    static {
        particleTextures = new ResourceLocation("textures/particle/particles.png");
        whiteFeather = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/particles/tiny-white-feather.png");
        blackFeather = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/particles/tiny-black-feather.png");
        redFeather = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/particles/tiny-red-feather.png");
    }

    private final int type;
    private final boolean isUnlit;
    private float rotationSpeed;

    public EntityFeatherFx(World world, double x, double y, double z, int type, float wingScale, int colour) {
        super(world, x, y, z);

        this.posX = x;
        this.posY = y;
        this.posZ = z;

        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;

        this.type = type;
        this.isUnlit = type != 0;
        this.particleScale = wingScale;
        
        Color c = new Color(colour);
        float red = (float) c.getRed() / 255;
        float green = (float) c.getGreen() / 255;
        float blue = (float) c.getBlue() / 255;
        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        
        particleMaxAge = 400;

        this.motionX = (rand.nextFloat() - 0.5F) * 0.01F;
        this.motionZ = (rand.nextFloat() - 0.5F) * 0.01F;
        
        this.motionY = -0.03F;
        this.rotationSpeed = 2.0F + rand.nextFloat();
        this.rotationPitch = rand.nextFloat() * 20 - 10;
        if (rand.nextFloat() >= 0.5F) {
            this.rotationSpeed = -this.rotationSpeed;
        }
        this.particleGravity = 0;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.isCollidedVertically) {
            this.rotationSpeed = 0F;
        }

        this.motionY = -0.01F;
        this.rotationPitch += rotationSpeed;
        if (this.rotationPitch > 360F) {
            this.rotationPitch -= 360F;
        }
        if (particleMaxAge - particleAge < 50) {
            particleAlpha = 1 + -((float) (particleAge - particleMaxAge + 50) / 50);
        }
    }

    @Override
    public void renderParticle(Tessellator tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
        IRenderBuffer renderBuffer = RenderBridge.INSTANCE;
        if (isDead) {
            return;
        }

        renderBuffer.draw();

        GL11.glPushMatrix();

        switch (type) {
        case 0:
            Minecraft.getMinecraft().renderEngine.bindTexture(blackFeather);
            break;
        case 1:
            Minecraft.getMinecraft().renderEngine.bindTexture(whiteFeather);
            break;
        case 2:
            Minecraft.getMinecraft().renderEngine.bindTexture(redFeather);
            break;
        }

        float f10 = 0.1F * this.particleScale;

        float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) par2 - interpPosX);
        float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) par2 - interpPosY);
        float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) par2 - interpPosZ);

        renderBuffer.startDrawingQuads();
        if (isUnlit) {
            renderBuffer.setBrightness(15728880);
        } else {
            renderBuffer.setBrightness(getBrightnessForRender(0));
        }

        drawBillboard(f11 - par3 * f10 - par6 * f10, f12 - par4 * f10, f13
                - par5 * f10 - par7 * f10, rotationPitch);
        
        renderBuffer.draw();
        GL11.glPopMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);
        renderBuffer.startDrawingQuads();
    }
    
    private void drawBillboard(double x, double y, double z, float rotation) {
        RenderManager renderManager = RenderManager.instance;
        IRenderBuffer renderBuffer = RenderBridge.INSTANCE;

        float scale = 0.05F;

        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(180, 0, 0, 1);

        GL11.glTranslatef((float) -0.01F, (float) -0.01F, (float) -0.01F);
        GL11.glRotatef(rotation, 0, 0, 1);
        GL11.glTranslatef((float) 0.01F, (float) 0.01F, (float) 0.01F);
        GL11.glScalef(scale, scale, scale);
        GL11.glScalef(particleScale, particleScale, particleScale);

        renderBuffer.setColourRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        renderBuffer.addVertexWithUV(-1, -1, 0, 0, 0);
        renderBuffer.addVertexWithUV(-1, 1, 0, 0, 1);
        renderBuffer.addVertexWithUV(1, 1, 0, 1, 1);
        renderBuffer.addVertexWithUV(1, -1, 0, 1, 0);
    }
}
