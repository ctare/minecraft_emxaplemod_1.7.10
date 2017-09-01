package com.example.examplemod.entities.particles;

/**
 * Created by ctare on 2017/08/21.
 */
import com.example.examplemod.utils.Calc;
import cpw.mods.fml.client.FMLClientHandler;
import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import static com.example.examplemod.utils.Calc.randint;

public class FXSwarm extends EntityFX {
    public static final ResourceLocation particleTexture = new ResourceLocation("swarms", "textures/misc/swarm_part2.png");

    public FXSwarm(World par1World, double x, double y, double z, Entity target, float r, float g, float b) {
        super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
        turnSpeed = 10F;
        speed = 0.2F;
        deathtimer = 0;
        particle = 40;
        particleRed = r;
        particleGreen = g;
        particleBlue = b;
        particleScale = rand.nextFloat() * 0.5F + 1.0F;
        this.target = target;
        float f3 = 0.2F;
        motionX = (rand.nextFloat() - rand.nextFloat()) * f3;
        motionY = (rand.nextFloat() - rand.nextFloat()) * f3;
        motionZ = (rand.nextFloat() - rand.nextFloat()) * f3;
        particleGravity = 0.1F;
        noClip = false;
        EntityLivingBase renderentity = FMLClientHandler.instance().getClient().renderViewEntity;
        int visibleDistance = 64;
        if(!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics)
            visibleDistance = 32;
        if(renderentity.getDistance(posX, posY, posZ) > (double)visibleDistance)
            particleMaxAge = 0;
    }

    public FXSwarm(World par1World, double x, double y, double z, Entity target, float r, float g, float b, float sp, float ts, float pg) {
        this(par1World, x, y, z, target, r, g, b);
        speed = sp;
        turnSpeed = ts;
        particleGravity = pg;
    }

    private Calc.Counter counter = new Calc.IterCounter(randint(11), 0.5, 11);
    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        tessellator.draw();
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(particleTexture);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glAlphaFunc(516, 0.003921569F);
//        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
//        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
//        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        tessellator.startDrawingQuads();

        float bob = MathHelper.sin((float)particleAge / 3F) * 0.25F + 1.0F;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
        float var12 = 0.1F * particleScale * bob;

        int i = counter.getFloor();
        float startX = i * 16 / 192f;
        float endX = (i * 16 + 16) / 192f;
        float startY = 0;
        float endY = 1;

        float var13 = (float)((prevPosX + (posX - prevPosX) * (double)f) - interpPosX);
        float var14 = (float)((prevPosY + (posY - prevPosY) * (double)f) - interpPosY);
        float var15 = (float)((prevPosZ + (posZ - prevPosZ) * (double)f) - interpPosZ);
        float trans = (50F - (float)deathtimer) / 50F;
        tessellator.setBrightness(240);
        if((target instanceof EntityLivingBase) && ((EntityLivingBase)target).hurtTime <= 0)
            tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, trans);
        else
            tessellator.setColorRGBA_F(particleRed, (particleGreen) / 2.0F, (particleBlue) / 2.0F, trans);

        tessellator.addVertexWithUV(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12, endX, endY);
        tessellator.addVertexWithUV((var13 - f1 * var12) + f4 * var12, var14 + f2 * var12, (var15 - f3 * var12) + f5 * var12, endX, startY);
        tessellator.addVertexWithUV(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12, startX, startY);
        tessellator.addVertexWithUV((var13 + f1 * var12) - f4 * var12, var14 - f2 * var12, (var15 + f3 * var12) - f5 * var12, startX, endY);

        tessellator.draw();
        GL11.glPopMatrix();
        try{
            Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation) ReflectionHelper.getPrivateValue(EffectRenderer.class, null, "particleTextures", "b", "field_110737_b"));
        }catch (Exception ignored){
            Minecraft.getMinecraft().renderEngine.bindTexture(null);
        }

        tessellator.startDrawingQuads();
        tessellator.setBrightness(0);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glAlphaFunc(516, 0.1F);
    }

    public int getFXLayer() {
        return 1;
    }

    public void onUpdate() {
        counter.nextCount();
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        particleAge++;
        if(target == null || target.isDead || (target instanceof EntityLivingBase) && ((EntityLivingBase)target).deathTime > 0) {
            deathtimer++;
            motionY -= particleGravity / 2.0F;
            if(deathtimer > 50)
                setDead();
        } else {
            motionY += particleGravity;
        }
        pushOutOfBlocks(posX, posY, posZ);
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.985;
        motionY *= 0.985;
        motionZ *= 0.985;
        if(target != null && !target.isDead && (!(target instanceof EntityLivingBase) || ((EntityLivingBase)target).deathTime <= 0)) {
            boolean hurt = false;
            if(target instanceof EntityLivingBase)
                hurt = ((EntityLivingBase)target).hurtTime > 0;
            if(getDistanceSqToEntity(target) > (double)target.width && !hurt)
                faceEntity(target, turnSpeed / 2.0F + (float)rand.nextInt((int)(turnSpeed / 2.0F)), turnSpeed / 2.0F + (float)rand.nextInt((int)(turnSpeed / 2.0F)));
            else
                faceEntity(target, -(turnSpeed / 2.0F + (float)rand.nextInt((int)(turnSpeed / 2.0F))), -(turnSpeed / 2.0F + (float)rand.nextInt((int)(turnSpeed / 2.0F))));
            motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
            motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
            motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
            setHeading(motionX, motionY, motionZ, speed, 15F);
        }
        if(buzzcount.size() < 3 && rand.nextInt(50) == 0 && worldObj.getClosestPlayerToEntity(this, 8D) != null) {
//            worldObj.playSound(posX, posY, posZ, "thaumcraft:fly", 0.03F, 0.5F + rand.nextFloat() * 0.4F, false);
            buzzcount.add(Long.valueOf(System.nanoTime() + 0x16e360L));
        }
        if(buzzcount.size() >= 3 && ((Long)buzzcount.get(0)).longValue() < System.nanoTime())
            buzzcount.remove(0);
    }

    public void faceEntity(Entity par1Entity, float par2, float par3) {
        double d0 = par1Entity.posX - posX;
        double d1 = par1Entity.posZ - posZ;
        double d2 = (par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY) / 2D - (boundingBox.minY + boundingBox.maxY) / 2D;
        double d3 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
        float f2 = (float)((Math.atan2(d1, d0) * 180D) / 3.1415926535897931D) - 90F;
        float f3 = (float)(-((Math.atan2(d2, d3) * 180D) / 3.1415926535897931D));
        rotationPitch = updateRotation(rotationPitch, f3, par3);
        rotationYaw = updateRotation(rotationYaw, f2, par2);
    }

    private float updateRotation(float par1, float par2, float par3) {
        float f3 = MathHelper.wrapAngleTo180_float(par2 - par1);
        if(f3 > par3)
            f3 = par3;
        if(f3 < -par3)
            f3 = -par3;
        return par1 + f3;
    }

    public void setHeading(double par1, double par3, double par5, float par7,
                           float par8)
    {
        float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= f2;
        par3 /= f2;
        par5 /= f2;
        par1 += rand.nextGaussian() * (double)(rand.nextBoolean() ? -1 : 1) * 0.0074999998323619366D * (double)par8;
        par3 += rand.nextGaussian() * (double)(rand.nextBoolean() ? -1 : 1) * 0.0074999998323619366D * (double)par8;
        par5 += rand.nextGaussian() * (double)(rand.nextBoolean() ? -1 : 1) * 0.0074999998323619366D * (double)par8;
        par1 *= par7;
        par3 *= par7;
        par5 *= par7;
        motionX = par1;
        motionY = par3;
        motionZ = par5;
    }

    protected boolean pushOutOfBlocks(double par1, double par3, double par5) {
        int var7 = MathHelper.floor_double(par1);
        int var8 = MathHelper.floor_double(par3);
        int var9 = MathHelper.floor_double(par5);
        double var10 = par1 - (double)var7;
        double var12 = par3 - (double)var8;
        double var14 = par5 - (double)var9;
        if(!worldObj.isAirBlock(var7, var8, var9) && !worldObj.isAnyLiquid(boundingBox)) {
            boolean var16 = !worldObj.isBlockNormalCubeDefault(var7 - 1, var8, var9, true);
            boolean var17 = !worldObj.isBlockNormalCubeDefault(var7 + 1, var8, var9, true);
            boolean var18 = !worldObj.isBlockNormalCubeDefault(var7, var8 - 1, var9, true);
            boolean var19 = !worldObj.isBlockNormalCubeDefault(var7, var8 + 1, var9, true);
            boolean var20 = !worldObj.isBlockNormalCubeDefault(var7, var8, var9 - 1, true);
            boolean var21 = !worldObj.isBlockNormalCubeDefault(var7, var8, var9 + 1, true);
            byte var22 = -1;
            double var23 = 9999D;
            if(var16 && var10 < var23) {
                var23 = var10;
                var22 = 0;
            }
            if(var17 && 1.0D - var10 < var23) {
                var23 = 1.0D - var10;
                var22 = 1;
            }
            if(var18 && var12 < var23) {
                var23 = var12;
                var22 = 2;
            }
            if(var19 && 1.0D - var12 < var23) {
                var23 = 1.0D - var12;
                var22 = 3;
            }
            if(var20 && var14 < var23) {
                var23 = var14;
                var22 = 4;
            }
            if(var21 && 1.0D - var14 < var23) {
                var23 = 1.0D - var14;
                var22 = 5;
            }
            float var25 = rand.nextFloat() * 0.05F + 0.025F;
            float var26 = (rand.nextFloat() - rand.nextFloat()) * 0.1F;
            if(var22 == 0) {
                motionX = -var25;
                motionY = motionZ = var26;
            }
            if(var22 == 1) {
                motionX = var25;
                motionY = motionZ = var26;
            }
            if(var22 == 2) {
                motionY = -var25;
                motionX = motionZ = var26;
            }
            if(var22 == 3) {
                motionY = var25;
                motionX = motionZ = var26;
            }
            if(var22 == 4) {
                motionZ = -var25;
                motionY = motionX = var26;
            }
            if(var22 == 5) {
                motionZ = var25;
                motionY = motionX = var26;
            }
            return true;
        } else {
            return false;
        }
    }

    private Entity target;
    private float turnSpeed;
    private float speed;
    int deathtimer;
    private static ArrayList buzzcount = new ArrayList();
    public int particle;

}
