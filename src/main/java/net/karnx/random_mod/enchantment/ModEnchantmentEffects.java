package net.karnx.random_mod.enchantment;

import com.mojang.serialization.MapCodec;
import net.karnx.random_mod.RandomMod;
import net.karnx.random_mod.enchantment.custom.LightningStrikerEnchantmentEffect;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantmentEffects {

    public static final MapCodec<? extends EnchantmentEntityEffect> LIGHTNING_STRIKER =
            registerEntityEffect("lightning_striker", LightningStrikerEnchantmentEffect.CODEC);

    private  static MapCodec <? extends EnchantmentEntityEffect> registerEntityEffect(String name, MapCodec<? extends EnchantmentEntityEffect> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(RandomMod.MOD_ID, name), codec);
    }


    public static void registerEnchantmentEffects() {


        RandomMod.LOGGER.info("Registering Enchantment Effects For " + RandomMod.MOD_ID);
    }
}