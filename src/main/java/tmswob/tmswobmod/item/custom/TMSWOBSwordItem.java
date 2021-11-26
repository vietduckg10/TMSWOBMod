package tmswob.tmswobmod.item.custom;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;

import javax.annotation.Nullable;
import java.util.List;

public class TMSWOBSwordItem extends SwordItem {
    public TMSWOBSwordItem(IItemTier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
        super(p_i48460_1_, p_i48460_2_, p_i48460_3_, p_i48460_4_);
        this.attackSpeed = p_i48460_3_;
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
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity p_77644_2_, LivingEntity p_77644_3_) {
        World world = p_77644_2_.level;
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
        return super.hurtEnemy(stack, p_77644_2_, p_77644_3_);
    }

    public long setMaxExp(ItemStack stack){
        CompoundNBT nbt = stack.getTag();
        long maxEXP = nbt.getLong("MaxExp");
        if (maxEXP == 0){
            maxEXP = this.getMaxDamage(stack) / 8;
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
        double bonusDamage = level * this.bonusStats;
        double bonusSpeed = (Math.floor(level / 3) * 0.1 * this.bonusStats);
        double bonusLuck = Math.floor(level / 10) * 0.1;
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
        attackPoints.put("Amount", DoubleNBT.valueOf(this.getDamage() + bonusDamage));
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
        //bonus luck
        CompoundNBT luckPoints = new CompoundNBT();
        luckPoints.put("AttributeName", StringNBT.valueOf("generic.luck"));
        luckPoints.put("Name", StringNBT.valueOf("generic.luck"));
        luckPoints.put("Amount", DoubleNBT.valueOf(bonusLuck));
        luckPoints.put("Operation", IntNBT.valueOf(0));
        UUID = new IntArrayNBT(new int[] {
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE),
                random.nextInt(Integer.MAX_VALUE)
        });
        luckPoints.put("UUID", UUID);
        luckPoints.put("Slot", StringNBT.valueOf("mainhand"));
        modifiers.add(luckPoints);
        nbt.put("AttributeModifiers", modifiers);
        stack.setTag(nbt);
    }
}
