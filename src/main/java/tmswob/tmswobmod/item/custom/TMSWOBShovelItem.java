package tmswob.tmswobmod.item.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.IPacket;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import tmswob.tmswobmod.item.TMSWOBItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TMSWOBShovelItem extends ToolItem {
    private static final Set<Block> DIGGABLES = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.SOUL_SOIL);
    protected static final Map<Block, BlockState> FLATTENABLES = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.defaultBlockState()));

    public TMSWOBShovelItem(IItemTier p_i48469_1_, float p_i48469_2_, float p_i48469_3_, Item.Properties p_i48469_4_) {
        super(p_i48469_2_, p_i48469_3_, p_i48469_1_, DIGGABLES, p_i48469_4_.addToolType(net.minecraftforge.common.ToolType.SHOVEL, p_i48469_1_.getLevel()));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag p_77624_4_) {
        super.appendHoverText(stack, world, tooltip, p_77624_4_);
        CompoundNBT nbt = stack.getTag();
        tooltip.add(new TranslationTextComponent(
                        "\u00A7eThis item will grow overtime.\u00A7r"
                )
        );
        if (nbt.get("MaxExp") != null){
            tooltip.add(new TranslationTextComponent(
                    "\u00A7bLevel: " + nbt.getInt("Level") + "\u00A7r"
            ));
            tooltip.add(new TranslationTextComponent(
                    "\u00A7bExp: " + nbt.getLong("CurrentExp") + "/" + nbt.getLong("MaxExp") + "\u00A7r"));
            tooltip.add(new TranslationTextComponent(
                    "\u00A7a" + nbt.getString("ExpBar") + "\u00A7r"));
            if (nbt.getInt("Level") > 0){
                tooltip.add(new TranslationTextComponent(
                        "\u00A76Bonus Digging Speed: " + Math.round(nbt.getFloat("BonusDigSpeed") / speed * 100) + "%" + "\u00A7r"));
            }
            if (nbt.getInt("Level") >= 5){
                tooltip.add(new TranslationTextComponent(
                        "\u00A76Treasure Digger " + ((int) (Math.floor(nbt.getInt("Level") / 5))) + "\u00A7r"));
            }
        }
    }

    @Override
    public boolean mineBlock(ItemStack stack, World world, BlockState p_179218_3_, BlockPos pos, LivingEntity livingEntity) {
        if (!world.isClientSide){
            CompoundNBT nbt = stack.getTag();
            if (nbt.get("MaxExp") == null) {
                nbt.putLong("MaxExp", setMaxExp(stack));
                nbt.putLong("CurrentExp", 0);
                nbt.putInt("Level", 0);
                nbt.putInt("Progress", 0);
                nbt.putString("ExpBar", setExpBar(nbt));
                nbt.putFloat("BonusDigSpeed", 0);
            }
            long currentEXP = nbt.getInt("CurrentExp");
            int progress = nbt.getInt("Progress");
            long maxEXP = nbt.getLong("MaxExp");
            int level = nbt.getInt("Level");
            currentEXP++;
            nbt.putLong("CurrentExp", currentEXP);
            if (Math.round((float) 10 * currentEXP / maxEXP) > progress){
                nbt.putInt("Progress", Math.round((float) 10 * currentEXP / maxEXP));
                nbt.putString("ExpBar", setExpBar(nbt));
            }
            if (currentEXP >= maxEXP){
                nbt.putLong("CurrentExp", 0);
                nbt.putInt("Progress", 0);
                nbt.putString("ExpBar", setExpBar(nbt));
                nbt.putLong("MaxExp", setMaxExp(stack));
                level++;
                nbt.putInt("Level", level);
                upgradeStats(stack);
            }
            if (level >= 5){
                for (int j = 0; j < Math.floor(level / 5); j++) {
                    if (random.nextInt(100) < 5){
                        treasureDigger(world, pos);
                    }
                }
            }
        }
        return super.mineBlock(stack, world, p_179218_3_, pos, livingEntity);
    }

    public long setMaxExp(ItemStack stack){
        CompoundNBT nbt = stack.getTag();
        long maxEXP = nbt.getLong("MaxExp");
        if (maxEXP == 0){
            maxEXP = this.getMaxDamage(stack) / 3;
        }
        else {
            maxEXP = (long) Math.ceil(maxEXP * 1.1);
        }
        return maxEXP;
    }

    public String setExpBar(CompoundNBT nbt){
        String expBar = "";
        int progress = nbt.getInt("Progress");
        if (progress > 0){
            for (int i = 0; i < progress; i++){
                expBar = expBar + "\u2588";
            }
            for (int i = 10; i > progress; i--){
                expBar = expBar + "\u2591";
            }
        }
        else{
            for (int i = 0; i < 10; i++){
                expBar = expBar + "\u2591";
            }
        }
        return expBar;
    }

    public void upgradeStats(ItemStack stack){
        CompoundNBT nbt = stack.getTag();
        int level = nbt.getInt("Level");
        float bonusDigSpeed = level * speed * 0.05f;
        nbt.putFloat("BonusDigSpeed", bonusDigSpeed);
        stack.setTag(nbt);
    }

    public void treasureDigger(World world, BlockPos pos){
        final ItemStack[] commonTreasure = new ItemStack[]{
                Items.IRON_NUGGET.getDefaultInstance(),
                Items.GOLD_NUGGET.getDefaultInstance(),
                Items.FLINT.getDefaultInstance()
        };
        final ItemStack[] uncommonTreasure = new ItemStack[]{
                Items.IRON_INGOT.getDefaultInstance(),
                Items.GOLD_INGOT.getDefaultInstance(),
                Items.ENDER_PEARL.getDefaultInstance(),
                Items.GHAST_TEAR.getDefaultInstance()
        };
        final ItemStack[] rareTreasure = new ItemStack[]{
                Items.DIAMOND.getDefaultInstance(),
                Items.EMERALD.getDefaultInstance(),
                TMSWOBItems.ORIGIN_SHARD.get().getDefaultInstance()
        };
        int randomPool = random.nextInt(10);
        ItemStack treasure;
        if (randomPool < 1){
            treasure = rareTreasure[random.nextInt(rareTreasure.length)];
        }
        else{
            if (randomPool > 3){
                treasure = commonTreasure[random.nextInt(commonTreasure.length)];
            }
            else{
                treasure = uncommonTreasure[random.nextInt(uncommonTreasure.length)];
            }
        }
        if (world.getBlockEntity(pos.above()) == null){
            pos = pos.above();
        }
        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), treasure);
        itemEntity.spawnAtLocation(itemEntity.getItem());
    }

    public boolean isCorrectToolForDrops(BlockState p_150897_1_) {
        return p_150897_1_.is(Blocks.SNOW) || p_150897_1_.is(Blocks.SNOW_BLOCK);
    }

    public ActionResultType useOn(ItemUseContext p_195939_1_) {
        World world = p_195939_1_.getLevel();
        BlockPos blockpos = p_195939_1_.getClickedPos();
        BlockState blockstate = world.getBlockState(blockpos);
        if (p_195939_1_.getClickedFace() == Direction.DOWN) {
            return ActionResultType.PASS;
        } else {
            PlayerEntity playerentity = p_195939_1_.getPlayer();
            BlockState blockstate1 = blockstate.getToolModifiedState(world, blockpos, playerentity, p_195939_1_.getItemInHand(), net.minecraftforge.common.ToolType.SHOVEL);
            BlockState blockstate2 = null;
            if (blockstate1 != null && world.isEmptyBlock(blockpos.above())) {
                world.playSound(playerentity, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                blockstate2 = blockstate1;
                int level = p_195939_1_.getItemInHand().getTag().getInt("Level");
                if (level >= 5){
                    for (int j = 0; j < Math.floor(level / 5); j++) {
                        if (random.nextInt(100) < 5){
                            treasureDigger(world, blockpos);
                        }
                    }
                }
            } else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.getValue(CampfireBlock.LIT)) {
                if (!world.isClientSide()) {
                    world.levelEvent((PlayerEntity)null, 1009, blockpos, 0);
                }

                CampfireBlock.dowse(world, blockpos, blockstate);
                blockstate2 = blockstate.setValue(CampfireBlock.LIT, Boolean.valueOf(false));
            }

            if (blockstate2 != null) {
                if (!world.isClientSide) {
                    world.setBlock(blockpos, blockstate2, 11);
                    if (playerentity != null) {
                        p_195939_1_.getItemInHand().hurtAndBreak(1, playerentity, (p_220041_1_) -> {
                            p_220041_1_.broadcastBreakEvent(p_195939_1_.getHand());
                        });
                    }
                }

                return ActionResultType.sidedSuccess(world.isClientSide);
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    public float getDestroySpeed(ItemStack stack, BlockState p_150893_2_) {
        Material material = p_150893_2_.getMaterial();
        CompoundNBT nbt = stack.getTag();
        float bonusDigSpeed = nbt.getFloat("BonusDigSpeed");
        return material != Material.DIRT && material != Material.GRASS && material != Material.SAND && material != Material.CLAY && material != Material.SNOW ? super.getDestroySpeed(stack, p_150893_2_) : this.speed + bonusDigSpeed ;
    }
}
