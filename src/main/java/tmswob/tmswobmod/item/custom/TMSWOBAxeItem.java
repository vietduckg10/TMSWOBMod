package tmswob.tmswobmod.item.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TMSWOBAxeItem extends ToolItem {
    private static final Set<Material> DIGGABLE_MATERIALS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD, Material.PLANT, Material.REPLACEABLE_PLANT, Material.BAMBOO, Material.VEGETABLE);
    private static final Set<Block> OTHER_DIGGABLE_BLOCKS = Sets.newHashSet(Blocks.LADDER, Blocks.SCAFFOLDING, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON, Blocks.CRIMSON_BUTTON, Blocks.WARPED_BUTTON);
    protected static final Map<Block, Block> STRIPABLES = (new ImmutableMap.Builder<Block, Block>()).put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD).put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG).put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD).put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG).put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG).put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD).put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG).put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG).put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG).put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM).put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE).put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM).put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE).build();

    public TMSWOBAxeItem(IItemTier p_i48530_1_, float p_i48530_2_, float p_i48530_3_, Item.Properties p_i48530_4_) {
        super(p_i48530_2_, p_i48530_3_, p_i48530_1_, OTHER_DIGGABLE_BLOCKS, p_i48530_4_.addToolType(net.minecraftforge.common.ToolType.AXE, p_i48530_1_.getLevel()));
        this.attackSpeed = p_i48530_3_;
    }

    public final double bonusStats = 0.5;
    public double attackSpeed;

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
                        "\u00A76Bonus Chopping Speed: " + Math.round(nbt.getFloat("BonusChopSpeed") / speed * 100) + "%" + "\u00A7r"));
            }
        }
    }

    @Override
    public boolean mineBlock(ItemStack stack, World world, BlockState p_179218_3_, BlockPos p_179218_4_, LivingEntity p_179218_5_) {
        if (!world.isClientSide){
            CompoundNBT nbt = stack.getTag();
            if (nbt.get("MaxExp") == null) {
                nbt.putLong("MaxExp", setMaxExp(stack));
                nbt.putLong("CurrentExp", 0);
                nbt.putInt("Level", 0);
                nbt.putInt("Progress", 0);
                nbt.putString("ExpBar", setExpBar(nbt));
                nbt.putFloat("BonusChopSpeed", 0);
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
        return super.mineBlock(stack, world, p_179218_3_, p_179218_4_, p_179218_5_);
    }

    public long setMaxExp(ItemStack stack){
        CompoundNBT nbt = stack.getTag();
        long maxEXP = nbt.getLong("MaxExp");
        if (maxEXP == 0){
            maxEXP = this.getMaxDamage(stack) / 4;
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
        float bonusChopSpeed = level * speed * 0.05f;
        nbt.putFloat("BonusChopSpeed", bonusChopSpeed);
        double bonusDamage = Math.floor(level / 3) * this.bonusStats;
        double bonusSpeed = (Math.floor(level / 5) * 0.1 * this.bonusStats);
        IntArrayNBT UUID = new IntArrayNBT(new int[] {
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE)
        });
        ListNBT modifiers = new ListNBT();
        //bonus damage
        CompoundNBT attackPoints = new CompoundNBT();
        attackPoints.put("AttributeName", StringNBT.valueOf("generic.attack_damage"));
        attackPoints.put("Name", StringNBT.valueOf("generic.attack_damage"));
        attackPoints.put("Amount", DoubleNBT.valueOf(this.getAttackDamage() + bonusDamage));
        attackPoints.put("Operation", IntNBT.valueOf(0));
        attackPoints.put("UUID", UUID);
        attackPoints.put("Slot", StringNBT.valueOf("mainhand"));
        modifiers.add(attackPoints);
        //bonus attack speed
        CompoundNBT attackSpeedPoints = new CompoundNBT();
        attackSpeedPoints.put("AttributeName", StringNBT.valueOf("generic.attack_speed"));
        attackSpeedPoints.put("Name", StringNBT.valueOf("generic.attack_speed"));
        attackSpeedPoints.put("Amount", DoubleNBT.valueOf(attackSpeed + bonusSpeed));
        attackSpeedPoints.put("Operation", IntNBT.valueOf(0));
        UUID = new IntArrayNBT(new int[] {
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE)
        });
        attackSpeedPoints.put("UUID", UUID);
        attackSpeedPoints.put("Slot", StringNBT.valueOf("mainhand"));
        modifiers.add(attackSpeedPoints);
        nbt.put("AttributeModifiers", modifiers);
        stack.setTag(nbt);
    }

    public float getDestroySpeed(ItemStack stack, BlockState p_150893_2_) {
        Material material = p_150893_2_.getMaterial();
        CompoundNBT nbt = stack.getTag();
        float bonusChopSpeed = nbt.getFloat("BonusChopSpeed");
        return DIGGABLE_MATERIALS.contains(material) ? this.speed + bonusChopSpeed : super.getDestroySpeed(stack, p_150893_2_);
    }

    public ActionResultType useOn(ItemUseContext p_195939_1_) {
        World world = p_195939_1_.getLevel();
        BlockPos blockpos = p_195939_1_.getClickedPos();
        BlockState blockstate = world.getBlockState(blockpos);
        BlockState block = blockstate.getToolModifiedState(world, blockpos, p_195939_1_.getPlayer(), p_195939_1_.getItemInHand(), net.minecraftforge.common.ToolType.AXE);
        if (block != null) {
            PlayerEntity playerentity = p_195939_1_.getPlayer();
            world.playSound(playerentity, blockpos, SoundEvents.AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isClientSide) {
                world.setBlock(blockpos, block, 11);
                if (playerentity != null) {
                    p_195939_1_.getItemInHand().hurtAndBreak(1, playerentity, (p_220040_1_) -> {
                        p_220040_1_.broadcastBreakEvent(p_195939_1_.getHand());
                    });
                }
            }

            return ActionResultType.sidedSuccess(world.isClientSide);
        } else {
            return ActionResultType.PASS;
        }
    }

    @javax.annotation.Nullable
    public static BlockState getAxeStrippingState(BlockState originalState) {
        Block block = STRIPABLES.get(originalState.getBlock());
        return block != null ? block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, originalState.getValue(RotatedPillarBlock.AXIS)) : null;
    }
}
