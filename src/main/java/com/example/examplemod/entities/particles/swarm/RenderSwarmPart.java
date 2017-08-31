package com.example.examplemod.entities.particles.swarm;

import com.example.examplemod.entities.particles.SwarmParticleManager;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static com.example.examplemod.utils.Calc.randint;
import static net.minecraft.client.particle.EntityFX.interpPosX;
import static net.minecraft.client.particle.EntityFX.interpPosY;
import static net.minecraft.client.particle.EntityFX.interpPosZ;

/**
 * Created by ctare on 2017/08/31.
 */

@SideOnly(Side.CLIENT)
public class RenderSwarmPart extends Render{
    public static final ResourceLocation particleTexture = new ResourceLocation("swarms", "textures/misc/swarm_part.png");

    @Override
    public void doRender(Entity orgEnt, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        EntitySwarmPart p_76986_1_ = (EntitySwarmPart) orgEnt;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);

        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glAlphaFunc(516, 0.003921569F);

        this.bindEntityTexture(p_76986_1_);
//        int i = p_76986_1_.getTextureByXP();
        int i = p_76986_1_.counter.getFloor();
        p_76986_1_.counter.nextCount();
        float startX = i * 16 / 192f;
        float endX = (i * 16 + 16) / 192f;
        float startY = 0;
        float endY = 1;
//        float startX = (float)(i % 4 * 16) / 64.0F;
//        float endX = (float)(i % 4 * 16 + 16) / 64.0F;
//        float startY = (float)(i / 4 * 16) / 64.0F;
//        float endY = (float)(i / 4 * 16 + 16) / 64.0F;
        float f6 = 1.0F;
        float f7 = 0.5F;
        float f8 = 0.25F;
        int j = p_76986_1_.getBrightnessForRender(p_76986_9_);
        int k = j % 65536;
        int l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k / 1.0F, (float)l / 1.0F);
        GL11.glColor4f(255, 255, 255, 255);
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        float f9 = 0.3F;
        GL11.glScalef(f9, f9, f9);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(p_76986_1_.color.getRed(), p_76986_1_.color.getGreen(), p_76986_1_.color.getBlue(), p_76986_1_.color.getAlpha());
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV((double)(0.0F - f7), (double)(0.0F - f8), 0.0D, (double)startX, (double)endY);
        tessellator.addVertexWithUV((double)(f6 - f7), (double)(0.0F - f8), 0.0D, (double)endX, (double)endY);
        tessellator.addVertexWithUV((double)(f6 - f7), (double)(1.0F - f8), 0.0D, (double)endX, (double)startY);
        tessellator.addVertexWithUV((double)(0.0F - f7), (double)(1.0F - f8), 0.0D, (double)startX, (double)startY);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glAlphaFunc(516, 0.1F);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return particleTexture;
    }
}
