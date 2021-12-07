package tmswob.tmswobmod.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tmswob.tmswobmod.TMSWOBMod;

import java.util.List;

@Mod.EventBusSubscriber(modid = TMSWOBMod.MODID)
public class BeanEntity extends CreatureEntity {

    public BeanEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
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

    @SubscribeEvent
    public static void givePlayerRegeneration(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        World world = event.player.level;
        if (!world.isClientSide){
            List<BeanEntity> BeanNumber = world.getEntitiesOfClass(BeanEntity.class, player.getBoundingBox().inflate(5), beanEntity -> beanEntity.isAlive());
            if (BeanNumber.size() >= 3){
                player.addEffect(new EffectInstance(Effects.REGENERATION));
            }
            if (BeanNumber.size() >= 6){
                player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST));
            }
        }
    }
}
