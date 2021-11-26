package tmswob.tmswobmod.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tmswob.tmswobmod.TMSWOBMod;
import tmswob.tmswobmod.item.TMSWOBItems;

@Mod.EventBusSubscriber(modid = TMSWOBMod.MODID)
public class OriginInfuseApple extends Item {

    public OriginInfuseApple(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }
    @SubscribeEvent
    public static void onUseApple(PlayerInteractEvent.EntityInteract event) throws InterruptedException {
        Entity entity = event.getTarget();
        World world = event.getWorld();
        PlayerEntity playerEntity = event.getPlayer();
        if(!world.isClientSide){
            if (playerEntity.getItemInHand(playerEntity.getUsedItemHand()).getItem() == TMSWOBItems.ORIGIN_INFUSE_APPLE.get() && entity.isAlive())
            {
                if (entity instanceof ZombieVillagerEntity) {
                    if (!((ZombieVillagerEntity) entity).isConverting()) {
                        if (((ZombieVillagerEntity) entity).mobInteract(playerEntity, playerEntity.getUsedItemHand()) == ActionResultType.PASS) {
                            ((ZombieVillagerEntity) entity).startConverting(playerEntity.getUUID(), random.nextInt(2401) + 360);
                            if (!playerEntity.isCreative()) {
                                playerEntity.getItemInHand(playerEntity.getUsedItemHand()).shrink(1);
                            }
                        }
                    }
                }
                if (entity instanceof ZombifiedPiglinEntity) {
                    //int convertTime = 200;
                    //((ZombifiedPiglinEntity) entity).addEffect(new EffectInstance(Effects.DAMAGE_BOOST, convertTime));
                    entity.remove();
                    if (((ZombifiedPiglinEntity) entity).isBaby()) {
                        PiglinEntity newEntity = (PiglinEntity) EntityType.PIGLIN.spawn((ServerWorld) world, null, null, entity.blockPosition(), SpawnReason.EVENT, false, false);
                        newEntity.setBaby(true);
                    } else {
                        PiglinEntity newEntity = (PiglinEntity) EntityType.PIGLIN.spawn((ServerWorld) world, null, null, entity.blockPosition(), SpawnReason.EVENT, false, false);
                        newEntity.setBaby(false);
                    }
                    if (!playerEntity.isCreative()) {
                        playerEntity.getItemInHand(playerEntity.getUsedItemHand()).shrink(1);
                    }
                }
                if (entity instanceof WitchEntity) {
                    entity.remove();
                    VillagerEntity newEntity = (VillagerEntity) EntityType.VILLAGER.spawn((ServerWorld) world,null, null, entity.blockPosition(), SpawnReason.EVENT, false, false);
                    newEntity.setBaby(false);
                    if (!playerEntity.isCreative()){
                        playerEntity.getItemInHand(playerEntity.getUsedItemHand()).shrink(1);
                    }
                }
            }
        }
    }
}