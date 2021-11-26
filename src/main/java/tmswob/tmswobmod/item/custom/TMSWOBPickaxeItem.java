package tmswob.tmswobmod.item.custom;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tmswob.tmswobmod.TMSWOBMod;
import tmswob.tmswobmod.item.TMSWOBItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = TMSWOBMod.MODID)
public class TMSWOBPickaxeItem extends ToolItem {
    private static final Set<Block> DIGGABLES = ImmutableSet.of(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.NETHER_GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.PISTON, Blocks.STICKY_PISTON, Blocks.PISTON_HEAD);

    public TMSWOBPickaxeItem(IItemTier p_i48478_1_, int p_i48478_2_, float p_i48478_3_, Item.Properties p_i48478_4_) {
        super((float)p_i48478_2_, p_i48478_3_, p_i48478_1_, DIGGABLES, p_i48478_4_.addToolType(net.minecraftforge.common.ToolType.PICKAXE, p_i48478_1_.getLevel()));
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
                        "\u00A76Bonus Mining Speed: " + Math.round(nbt.getFloat("BonusMineSpeed") / speed * 100) + "%" + "\u00A7r"));
            }
            if (nbt.getInt("Level") >= 10){
                tooltip.add(new TranslationTextComponent(
                        "\u00A76Place Torch: " + nbt.getString("ToggleTorch") + "\u00A7r"));
            }
            if (nbt.getInt("Level") >= 20){
                tooltip.add(new TranslationTextComponent(
                        "\u00A76Mining Fatigue Immunity" + "\u00A7r"));
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
                nbt.putFloat("BonusMineSpeed", 0);
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
            maxEXP = this.getMaxDamage(stack) / 2;
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
        float bonusMineSpeed = level * speed * 0.05f;
        nbt.putFloat("BonusMineSpeed", bonusMineSpeed);
        double bonusLuck = Math.floor(level / 2) * 0.1;
        if (level >= 10){
            if (nbt.get("ToggleTorch") == null){
                nbt.putString("ToggleTorch", "On");
            }
        }
        IntArrayNBT UUID = new IntArrayNBT(new int[] {
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE)
        });
        ListNBT modifiers = new ListNBT();
        //bonus luck
        CompoundNBT luckPoints = new CompoundNBT();
        luckPoints.put("AttributeName", StringNBT.valueOf("generic.luck"));
        luckPoints.put("Name", StringNBT.valueOf("generic.luck"));
        luckPoints.put("Amount", DoubleNBT.valueOf(bonusLuck));
        luckPoints.put("Operation", IntNBT.valueOf(0));
        luckPoints.put("UUID", UUID);
        luckPoints.put("Slot", StringNBT.valueOf("mainhand"));
        modifiers.add(luckPoints);
        nbt.put("AttributeModifiers", modifiers);
        stack.setTag(nbt);
    }

    public boolean isCorrectToolForDrops(BlockState p_150897_1_) {
        int i = this.getTier().getLevel();
        if (p_150897_1_.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
            return i >= p_150897_1_.getHarvestLevel();
        }
        Material material = p_150897_1_.getMaterial();
        return material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL;
    }

    public float getDestroySpeed(ItemStack stack, BlockState p_150893_2_) {
        Material material = p_150893_2_.getMaterial();
        CompoundNBT nbt = stack.getTag();
        float bonusMineSpeed = nbt.getFloat("BonusMineSpeed");
        return material != Material.METAL && material != Material.HEAVY_METAL && material != Material.STONE ? super.getDestroySpeed(stack, p_150893_2_) : this.speed + bonusMineSpeed;
    }

    @SubscribeEvent
    public static void onHoldOriginPickaxeEffect(TickEvent.PlayerTickEvent event){
        World world = event.player.level;
        PlayerEntity player = event.player;
        ItemStack stack = player.getItemInHand(Hand.MAIN_HAND);
        if (!world.isClientSide){
            if (stack.getItem() == TMSWOBItems.ORIGIN_INFUSE_NETHERITE_PICKAXE.get()){
                CompoundNBT nbt = stack.getTag();
                int level = nbt.getInt("Level");
                if (level >= 20 && player.getEffect(Effects.DIG_SLOWDOWN) != null){
                    player.removeEffect(Effects.DIG_SLOWDOWN);
                }
            }
        }
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getLevel();
        if (!world.isClientSide){
            CompoundNBT nbt = stack.getTag();
            int level = nbt.getInt("Level");
            if (level >= 10){
                String toggle = nbt.getString("ToggleTorch");
                Direction clickedFace = context.getClickedFace();
                BlockPos blockPos = context.getClickedPos();
                PlayerEntity playerEntity = context.getPlayer();
                if (!playerEntity.isShiftKeyDown()){
                    if (toggle.equals("On")){
                        if (clickedFace != Direction.DOWN) {
                            BlockState torchState = Blocks.TORCH.defaultBlockState();
                            switch (clickedFace) {
                                case UP:
                                    blockPos = blockPos.offset(0, 1, 0);
                                    break;
                                case EAST:
                                    blockPos = blockPos.offset(1, 0, 0);
                                    torchState = Blocks.WALL_TORCH.getStateDefinition().getPossibleStates().get(3);
                                    break;
                                case WEST:
                                    blockPos = blockPos.offset(-1, 0, 0);
                                    torchState = Blocks.WALL_TORCH.getStateDefinition().getPossibleStates().get(2);
                                    break;
                                case SOUTH:
                                    blockPos = blockPos.offset(0, 0, 1);
                                    torchState = Blocks.WALL_TORCH.getStateDefinition().getPossibleStates().get(1);
                                    break;
                                case NORTH:
                                    blockPos = blockPos.offset(0, 0, -1);
                                    torchState = Blocks.WALL_TORCH.defaultBlockState();
                                    break;
                                default:
                                    break;
                            }
                            world.setBlock(blockPos, torchState, 50);
                            stack.setDamageValue(stack.getDamageValue() + 1);
                        }
                    }
                }
                else{
                    if (toggle.equals("On")){
                        nbt.putString("ToggleTorch", "Off");
                    }
                    else{
                        nbt.putString("ToggleTorch", "On");
                    }
                }
            }
        }
        return super.onItemUseFirst(stack, context);
    }
}
