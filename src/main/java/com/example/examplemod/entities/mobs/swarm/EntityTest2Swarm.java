package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.particles.SwarmParticleManager;
import com.example.examplemod.utils.Calc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

import static com.example.examplemod.utils.Calc.randint;

/**
 * Created by ctare on 2017/08/23.
 */
public class EntityTest2Swarm extends EntityMob {
    SwarmParticleManager particleManager = new SwarmParticleManager().changeSpeedAndTurn(2);

    private ChunkCoordinates currentFlightTarget;
    public int damBonus = 0;

    public EntityTest2Swarm(World p_i1738_1_) {
        super(p_i1738_1_);
        setSize(1.0f, 1.0f);
        this.isImmuneToFire = true;
    }

    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5d);
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50d);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2 + 4);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        motionY *= 0.60000002384185791D;
        int rdm = randint(150, 255);
        particleManager
                .setColor(rdm, rdm - randint(20), randint(70, 120))
                .addParticle(this);
    }


//    @SideOnly(Side.CLIENT)
//    public int getBrightnessForRender(float par1) {
//        return 15728880;
//    }
//
//    public float getBrightness(float par1) {
//        return 1.0F;
//    }
//
//    protected float getSoundVolume() {
//        return 0.1F;
//    }
//
//    protected float getSoundPitch() {
//        return super.getSoundPitch() * 0.95F;
//    }
//
//    protected String getLivingSound() {
//        return this.getIsBatHanging() && this.rand.nextInt(4) != 0?null:"mob.bat.idle";
//    }
//
//    protected String getHurtSound() {
//        return "mob.bat.hurt";
//    }
//
//    protected String getDeathSound() {
//        return "mob.bat.death";
//    }

    public boolean canBePushed() {
        return false;
    }

    protected boolean isAIEnabled() {
        return false;
    }

    public void onLivingUpdate() {
        if(this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0F);
        }

        super.onLivingUpdate();
    }

    protected void updateEntityActionState() {
        super.updateEntityActionState();
        double var1;
        double var3;
        double var5;
        float var7;
        float var8;
        if(this.entityToAttack == null) {
            if(this.currentFlightTarget != null && (!this.worldObj.isAirBlock(this.currentFlightTarget.posX, this.currentFlightTarget.posY, this.currentFlightTarget.posZ) || this.currentFlightTarget.posY < 1)) {
                this.currentFlightTarget = null;
            }

            if(this.currentFlightTarget == null || this.rand.nextInt(30) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0F) {
                this.currentFlightTarget = new ChunkCoordinates((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
            }

            var1 = (double)this.currentFlightTarget.posX + 0.5 - this.posX;
            var3 = (double)this.currentFlightTarget.posY + 0.1 - this.posY;
            var5 = (double)this.currentFlightTarget.posZ + 0.5 - this.posZ;
        } else {
            var1 = this.entityToAttack.posX - this.posX;
            var3 = this.entityToAttack.posY + (double)(this.entityToAttack.getEyeHeight() * 0.66F) - this.posY;
            var5 = this.entityToAttack.posZ - this.posZ;
        }
        this.motionX += (Math.signum(var1) * 0.5 - this.motionX) * 0.1;
        this.motionY += (Math.signum(var3) * 0.7 - this.motionY) * 0.1;
        this.motionZ += (Math.signum(var5) * 0.5 - this.motionZ) * 0.1;
        var7 = (float) Math.toDegrees(Math.atan2(this.motionZ, this.motionX)) - 90.0F;
        var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
        this.moveForward = 0.5F;
        this.rotationYaw += var8;

//        if(this.entityToAttack instanceof EntityPlayer && ((EntityPlayer)this.entityToAttack).capabilities.disableDamage) {
//            this.entityToAttack = null;
//        }
    }

    protected void updateAITasks() {
        super.updateAITasks();
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        return !this.isEntityInvulnerable() && !par1DamageSource.isFireDamage() && !par1DamageSource.isExplosion() && super.attackEntityFrom(par1DamageSource, par2);
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

    protected Entity findPlayerToAttack() {
        double var1 = 12.0D;
        AxisAlignedBB box = this.boundingBox.expand(50, 50, 50);

        List list = worldObj.getEntitiesWithinAABB(IMob.class, box);
        Entity target = null;
        double d0 = Double.MAX_VALUE;

        for (Object entity : list) {
            Entity entity2 = (Entity) entity;

            if (!(entity2 instanceof EntityTest2Swarm)) {
                double d1 = this.getDistanceSqToEntity(entity2);

                if (d1 <= d0) {
                    target = entity2;
                    d0 = d1;
                }
            }
        }

        return target;
//        return this.worldObj.findNearestEntityWithinAABB(IMob.class, , this);
//        return this.worldObj.getClosestVulnerablePlayerToEntity(this, var1);
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
        int var1 = MathHelper.floor_double(this.boundingBox.minY);
        int var2 = MathHelper.floor_double(this.posX);
        int var3 = MathHelper.floor_double(this.posZ);
        int var4 = this.worldObj.getBlockLightValue(var2, var1, var3);
        byte var5 = 7;
        return var4 <= this.rand.nextInt(var5) && super.getCanSpawnHere();
    }

    protected boolean isValidLightLevel() {
        return true;
    }
}
