package com.example.examplemod.entities.mobs.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import static com.example.examplemod.utils.Calc.isMatch;

/**
 * Created by ctare on 2017/08/27.
 */
public abstract class EntityLiquidMob extends EntityMob{
    private ChunkCoordinates currentFlightTarget;
    public int damBonus = 0;

    public EntityLiquidMob(World p_i1738_1_) {
        super(p_i1738_1_);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        motionY *= 0.60000002384185791D;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5d);
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50d);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2 + damBonus);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
    }

    @Override
    protected void updateEntityActionState() {
        if (this.entityToAttack == null) {
            this.entityToAttack = this.findPlayerToAttack();

            if (this.entityToAttack != null) {
                float f4 = 16.0F;
                this.setPathToEntity(this.worldObj.getPathEntityToEntity(this, this.entityToAttack, f4, true, false, false, true));
            }
        } else if (this.entityToAttack.isEntityAlive()) {
            float f = this.entityToAttack.getDistanceToEntity(this);

            if (this.canEntityBeSeen(this.entityToAttack)) {
                this.attackEntity(this.entityToAttack, f);
            }
        } else {
            this.entityToAttack = null;
        }

        double var1;
        double var3;
        double var5;
        float var7;
        float var8;

        float speedCoefficient = 0.2f;
        if(!this.isInWater()) {
            speedCoefficient = 0.1f;
        }
        if(this.entityToAttack == null || !(this.entityToAttack.isInWater() || this.getDistanceSqToEntity(this.entityToAttack) < 4.0)) {
            if(this.currentFlightTarget != null && (!isMatch(worldObj, this.currentFlightTarget.posX, this.currentFlightTarget.posY, this.currentFlightTarget.posZ, Material.water) || this.currentFlightTarget.posY < 1)) {
                this.currentFlightTarget = null;
            }

            if(this.currentFlightTarget == null || this.rand.nextInt(30) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0F) {
                this.currentFlightTarget = new ChunkCoordinates(
                        (int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7),
                        (int)this.posY + this.rand.nextInt(6) - 2,
                        (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
            }

            var1 = (double)this.currentFlightTarget.posX + 0.5 - this.posX;
            var3 = (double)this.currentFlightTarget.posY + 0.1 - this.posY;
            var5 = (double)this.currentFlightTarget.posZ + 0.5 - this.posZ;
            this.entityToAttack = null;
        } else {
            speedCoefficient = 0.3f;
            var1 = this.entityToAttack.posX - this.posX;
            var3 = this.entityToAttack.posY + (double)(this.entityToAttack.getEyeHeight() * 0.66F) - this.posY;
            var5 = this.entityToAttack.posZ - this.posZ;
        }

        this.motionX += (Math.signum(var1) * speedCoefficient - this.motionX) * 0.1;
        this.motionY += (Math.signum(var3) * (speedCoefficient * 1.4) - this.motionY) * 0.1;
        this.motionZ += (Math.signum(var5) * speedCoefficient - this.motionZ) * 0.1;
        var7 = (float) Math.toDegrees(Math.atan2(this.motionZ, this.motionX)) - 90.0F;
        var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
        this.rotationYaw += var8;

//        if(this.entityToAttack instanceof EntityPlayer && ((EntityPlayer)this.entityToAttack).capabilities.disableDamage) {
//            this.entityToAttack = null;
//        }
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.dataWatcher.updateObject(16, par1NBTTagCompound.getByte("BatFlags"));
        this.damBonus = par1NBTTagCompound.getByte("damBonus");
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(16));
        par1NBTTagCompound.setByte("damBonus", (byte)this.damBonus);
    }

    public boolean getCanSpawnHere() {
        return this.posY > 45.0D && this.posY < 63.0D && super.getCanSpawnHere();
    }

    protected boolean isValidLightLevel() {
        return true;
    }

    protected boolean isAIEnabled() {
        return false;
    }

    protected void updateAITasks() {
        super.updateAITasks();
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

//    @Override
//    public boolean isInWater() {
//        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
//    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        return !this.isEntityInvulnerable() && super.attackEntityFrom(par1DamageSource, par2);
    }

    protected void attackEntity(Entity par1Entity, float par2) {
        if(this.attackTime <= 0 && par2 < Math.max(2.5F, par1Entity.width * 1.1F) && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY) {

            this.attackTime = 20;
            double mx = par1Entity.motionX;
            double my = par1Entity.motionY;
            double mz = par1Entity.motionZ;
            this.attackEntityAsMob(par1Entity);
            par1Entity.isAirBorne = false;
            par1Entity.motionX = mx;
            par1Entity.motionY = my;
            par1Entity.motionZ = mz;
        }
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1) {
        return 15728880;
    }

    @Override
    public float getBrightness(float par1) {
        return 1.0F;
    }

    @Override
    public void onEntityUpdate() {
        int i = this.getAir();
        super.onEntityUpdate();

        if (this.isEntityAlive() && !this.isInWater()) {
            i -= 2;
            this.setAir(i);

            if (this.getAir() == -40) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.drown, 5.0F);
            }
        } else {
            this.setAir(300);
        }
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }
}
