package tmswob.tmswobmod.item.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;
import tmswob.tmswobmod.item.TMSWOBItems;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TMSWOBHoeItem extends ToolItem {
    private static final Set<Block> DIGGABLES = ImmutableSet.of(Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK, Blocks.HAY_BLOCK, Blocks.DRIED_KELP_BLOCK, Blocks.TARGET, Blocks.SHROOMLIGHT, Blocks.SPONGE, Blocks.WET_SPONGE, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES);

    public TMSWOBHoeItem(IItemTier p_i231595_1_, int p_i231595_2_, float p_i231595_3_, Item.Properties p_i231595_4_) {
        super((float)p_i231595_2_, p_i231595_3_, p_i231595_1_, DIGGABLES, p_i231595_4_.addToolType(net.minecraftforge.common.ToolType.HOE, p_i231595_1_.getLevel()));
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
                        "\u00A763x3 Tilling: " + nbt.getString("ToggleTilling") + "\u00A7r"));
            }
            if (nbt.getInt("Level") > 1){
                tooltip.add(new TranslationTextComponent(
                        "\u00A76Bone Digger " + ((int) (Math.floor((nbt.getInt("Level") + 1) / 3))) + "\u00A7r"));
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
        }
        return super.mineBlock(stack, world, p_179218_3_, pos, livingEntity);
    }

    public long setMaxExp(ItemStack stack){
        CompoundNBT nbt = stack.getTag();
        long maxEXP = nbt.getLong("MaxExp");
        if (maxEXP == 0){
            maxEXP = this.getMaxDamage(stack) / 10;
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
        if (level > 0){
            if (nbt.get("ToggleTilling") == null){
                nbt.putString("ToggleTilling", "On");
                System.out.println("level up: " + nbt.getString("ToggleTilling"));
            }
        }
        stack.setTag(nbt);
    }

    public ActionResultType useOn(ItemUseContext p_195939_1_) {
        World world = p_195939_1_.getLevel();
        BlockPos blockpos = p_195939_1_.getClickedPos();
        ItemStack stack = p_195939_1_.getItemInHand();
        int level = stack.getTag().getInt("Level");
        CompoundNBT nbt = stack.getTag();
        if (nbt.get("MaxExp") == null) {
            nbt.putLong("MaxExp", setMaxExp(stack));
            nbt.putLong("CurrentExp", 0);
            nbt.putInt("Level", 0);
            nbt.putInt("Progress", 0);
            nbt.putString("ExpBar", setExpBar(nbt));
        }
        List<BlockPos> validPos = new ArrayList<BlockPos>();
        if (level > 0 && nbt.getString("ToggleTilling").equals("On")) {
            BlockPos surroundPos1, surroundPos2, surroundPos3, surroundPos4,
                    surroundPos5, surroundPos6, surroundPos7, surroundPos8;
            surroundPos1 = blockpos.offset(new Vector3i(-1, 0, -1));
            surroundPos2 = blockpos.offset(new Vector3i(1, 0, -1));
            surroundPos3 = blockpos.offset(new Vector3i(-1, 0, 1));
            surroundPos4 = blockpos.offset(new Vector3i(1, 0, 1));
            surroundPos5 = blockpos.offset(new Vector3i(0, 0, -1));
            surroundPos6 = blockpos.offset(new Vector3i(0, 0, 1));
            surroundPos7 = blockpos.offset(new Vector3i(-1, 0, 0));
            surroundPos8 = blockpos.offset(new Vector3i(1, 0, 0));
            BlockPos[] surroundPos = new BlockPos[]{
                    surroundPos1, surroundPos2, surroundPos3, surroundPos4,
                    surroundPos5, surroundPos6, surroundPos7, surroundPos8
            };
            for (int i = 0; i < 8; i++) {
                if (world.isEmptyBlock(surroundPos[i].above())) {
                    if (world.getBlockState(surroundPos[i]).getToolModifiedState(world, blockpos, p_195939_1_.getPlayer(), p_195939_1_.getItemInHand(), net.minecraftforge.common.ToolType.HOE)
                            == world.getBlockState(blockpos).getToolModifiedState(world, blockpos, p_195939_1_.getPlayer(), p_195939_1_.getItemInHand(), net.minecraftforge.common.ToolType.HOE)){
                        validPos.add(surroundPos[i]);
                    }
                }
            }
        }
        int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(p_195939_1_);
        if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
        if (p_195939_1_.getPlayer().isShiftKeyDown()){
            if (level > 0){
                if (nbt.getString("ToggleTilling").equals("On")) {
                    nbt.putString("ToggleTilling", "Off");
                    System.out.println(nbt.getString("ToggleTilling"));
                }
                else{
                    if (nbt.getString("ToggleTilling").equals("Off")) {
                        nbt.putString("ToggleTilling", "On");
                        System.out.println(nbt.getString("ToggleTilling"));
                    }
                }
            }
        }
        if (!p_195939_1_.getPlayer().isShiftKeyDown()){
            if (p_195939_1_.getClickedFace() != Direction.DOWN && world.isEmptyBlock(blockpos.above())) {
                BlockState blockstate = world.getBlockState(blockpos).getToolModifiedState(world, blockpos, p_195939_1_.getPlayer(), p_195939_1_.getItemInHand(), net.minecraftforge.common.ToolType.HOE);
                if (blockstate != null) {
                    PlayerEntity playerentity = p_195939_1_.getPlayer();
                    world.playSound(playerentity, blockpos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    if (!world.isClientSide) {
                        int randomNum = random.nextInt(10);
                        world.setBlock(blockpos, blockstate, 11);
                        if (level > 1) {
                            for (int j = 0; j < Math.floor((level + 1) / 3 ); j++) {
                                randomNum = random.nextInt(10);
                                if (randomNum < 1){
                                    boneDigger(world, blockpos);
                                }
                            }
                        }
                        if (nbt.getString("ToggleTilling").equals("On")) {
                            if (level > 0) {
                                for (int i = 0; i < validPos.size(); i++) {
                                    world.setBlock(validPos.get(i), blockstate, 11);
                                    if (level > 1) {
                                        for (int j = 0; j < Math.floor((level + 1) / 3 ); j++) {
                                            randomNum = random.nextInt(10);
                                            if (randomNum < 1){
                                                boneDigger(world, blockpos);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (playerentity != null) {
                            p_195939_1_.getItemInHand().hurtAndBreak(1, playerentity, (p_220043_1_) -> {
                                p_220043_1_.broadcastBreakEvent(p_195939_1_.getHand());
                            });
                        }
                    }
                    if (!world.isClientSide) {
                        long currentEXP = nbt.getInt("CurrentExp");
                        int progress = nbt.getInt("Progress");
                        long maxEXP = nbt.getLong("MaxExp");
                        currentEXP++;
                        nbt.putLong("CurrentExp", currentEXP);
                        if (Math.round((float) 10 * currentEXP / maxEXP) > progress) {
                            nbt.putInt("Progress", Math.round((float) 10 * currentEXP / maxEXP));
                            nbt.putString("ExpBar", setExpBar(nbt));
                        }
                        if (currentEXP >= maxEXP) {
                            nbt.putLong("CurrentExp", 0);
                            nbt.putInt("Progress", 0);
                            nbt.putString("ExpBar", setExpBar(nbt));
                            nbt.putLong("MaxExp", setMaxExp(stack));
                            level++;
                            nbt.putInt("Level", level);
                            upgradeStats(stack);
                        }
                    }

                    return ActionResultType.sidedSuccess(world.isClientSide);
                }
            }
        }

        return ActionResultType.PASS;
    }
    public void boneDigger(World world, BlockPos pos){
        final ItemStack[] bonePool = new ItemStack[]{
                Items.BONE.getDefaultInstance(),
                Items.BONE_MEAL.getDefaultInstance(),
        };
        int randomPool = random.nextInt(2);
        pos = pos.above();
        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), bonePool[randomPool]);
        itemEntity.spawnAtLocation(itemEntity.getItem());
    }
}
