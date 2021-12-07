package tmswob.tmswobmod.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BeanWithHandEntity extends BeanEntity{
    public BeanWithHandEntity(EntityType<? extends BeanEntity> type, World world) {
        super(type, world);
    }
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new TMSWOBFollowPlayerGoal(this));
        this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 0.4f));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.BAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BAT_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(SoundEvents.SHEEP_STEP, 0.1f, 0.1f);
        super.playStepSound(p_180429_1_, p_180429_2_);
    }
}
