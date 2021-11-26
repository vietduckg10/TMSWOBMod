package tmswob.tmswobmod.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TMSWOBArmorItem extends ArmorItem {

    public TMSWOBArmorItem(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
    }

    public long durabilityLoss = 0;
    public final double bonusStats = 0.5;

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
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClientSide){
            CompoundNBT nbt = stack.getTag();
            if (nbt.get("MaxExp") == null){
                nbt.putLong("MaxExp", setMaxExp(stack));
                nbt.putLong("CurrentExp", 0);
                nbt.putInt("Level", 0);
                nbt.putInt("Progress", 0);
                nbt.putString("ExpBar", setExpBar(nbt));
            }
            if (this.getDamage(stack) != durabilityLoss) {
                durabilityLoss = this.getDamage(stack);
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
        }
        super.onArmorTick(stack, world, player);
    }

    public String getItemType(){
        return this.getSlot().getName();
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
        double bonusArmor = level * this.bonusStats;
        double bonusToughness = Math.floor((double) level / 3) * this.bonusStats;
        double bonusKnockBackRes = Math.floor((double) level / 5) * this.bonusStats * 0.01;
        double bonusHealth = Math.floor((double) level / 10) * 2;

        IntArrayNBT UUID = new IntArrayNBT(new int[] {
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE)
        });
        ListNBT modifiers = new ListNBT();
        //bonus armor
        CompoundNBT armorPoints = new CompoundNBT();
        armorPoints.put("AttributeName", StringNBT.valueOf("generic.armor"));
        armorPoints.put("Name", StringNBT.valueOf("generic.armor"));
        armorPoints.put("Amount", DoubleNBT.valueOf(this.getDefense() + bonusArmor));
        armorPoints.put("Operation", IntNBT.valueOf(0));
        armorPoints.put("UUID", UUID);
        armorPoints.put("Slot", StringNBT.valueOf(getItemType()));
        modifiers.add(armorPoints);
        //bonus toughness
        CompoundNBT armorToughnessPoints = new CompoundNBT();
        armorToughnessPoints.put("AttributeName", StringNBT.valueOf("generic.armor_toughness"));
        armorToughnessPoints.put("Name", StringNBT.valueOf("generic.armor_toughness"));
        armorToughnessPoints.put("Amount", DoubleNBT.valueOf(this.getToughness() + bonusToughness));
        armorToughnessPoints.put("Operation", IntNBT.valueOf(0));
        UUID = new IntArrayNBT(new int[] {
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE)
        });
        armorToughnessPoints.put("UUID", UUID);
        armorToughnessPoints.put("Slot", StringNBT.valueOf(getItemType()));
        modifiers.add(armorToughnessPoints);
        //bonus knockbackRes
        CompoundNBT knockbackResPoints = new CompoundNBT();
        knockbackResPoints.put("AttributeName", StringNBT.valueOf("generic.knockback_resistance"));
        knockbackResPoints.put("Name", StringNBT.valueOf("generic.knockback_resistance"));
        knockbackResPoints.put("Amount", DoubleNBT.valueOf(this.knockbackResistance + bonusKnockBackRes));
        knockbackResPoints.put("Operation", IntNBT.valueOf(0));
        UUID = new IntArrayNBT(new int[] {
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE)
        });
        knockbackResPoints.put("UUID", UUID);
        knockbackResPoints.put("Slot", StringNBT.valueOf(getItemType()));
        modifiers.add(knockbackResPoints);
        //bonus health
        CompoundNBT healthPoints = new CompoundNBT();
        healthPoints.put("AttributeName", StringNBT.valueOf("generic.max_health"));
        healthPoints.put("Name", StringNBT.valueOf("generic.max_health"));
        healthPoints.put("Amount", DoubleNBT.valueOf(bonusHealth));
        healthPoints.put("Operation", IntNBT.valueOf(0));
        UUID = new IntArrayNBT(new int[] {
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE)
        });
        healthPoints.put("UUID", UUID);
        healthPoints.put("Slot", StringNBT.valueOf(getItemType()));
        modifiers.add(healthPoints);
        nbt.put("AttributeModifiers", modifiers);
        //stack.setTag(nbt);
    }
}
