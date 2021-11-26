package tmswob.tmswobmod.item;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class TMSWOBFoodItems {
    public static final Food ORIGIN_INFUSE_APPLE = new Food.Builder()
            .nutrition(4)
            .saturationMod(1.2f)
            .effect(() -> new EffectInstance(Effects.REGENERATION,200,1),1.0f)
            .effect(() -> new EffectInstance(Effects.DAMAGE_RESISTANCE,400,0),1.0f)
            .alwaysEat()
            .build();

}
