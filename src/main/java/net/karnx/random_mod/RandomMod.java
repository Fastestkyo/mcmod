package net.karnx.random_mod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.*;
import net.karnx.random_mod.block.ModBlocks;
import net.karnx.random_mod.compenent.ModDataComponentTypes;
import net.karnx.random_mod.effect.ModEffects;
import net.karnx.random_mod.enchantment.ModEnchantmentEffects;
import net.karnx.random_mod.entity.ModEntities;
import net.karnx.random_mod.entity.custom.MantisEntity;
import net.karnx.random_mod.item.ModItemGroups;
import net.karnx.random_mod.item.ModItems;
import net.karnx.random_mod.potion.ModPotions;
import net.karnx.random_mod.sound.ModSounds;
import net.karnx.random_mod.util.HammerUsageEvent;
import net.karnx.random_mod.world.gen.ModWorldGeneration;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomMod implements ModInitializer {
	public static final String MOD_ID = "random-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerModItemGroups();
		ModDataComponentTypes.registerDataComponentTypes();
		ModWorldGeneration.generateModWorldGen();

		ModEntities.registerModEntities();
		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());
		AttackEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
			if(entity instanceof WolfEntity wolfEntity) {
				if(playerEntity.getMainHandStack().getItem() == ModItems.PINK_GARNET_SWORD && !world.isClient) {
					playerEntity.sendMessage(Text.literal( "You just hit a wolf with ur sword. U SICK FUQ!"));
					playerEntity.getMainHandStack().decrement(1);
					wolfEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 6));
				}
			}


			return ActionResult.PASS;
		});
		ModSounds.registerSounds();
		FuelRegistry.INSTANCE.add(ModItems.STAR_ASHES, 600);
		ModEffects.registerEffects();
		ModPotions.registerPotions();
		ModEnchantmentEffects.registerEnchantmentEffects();


		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(Potions.AWKWARD, Items.SLIME_BALL, ModPotions.SLIMEY_POTION);

		});
		CompostingChanceRegistry.INSTANCE.add(ModItems.CAULIFLOWER, 0.5F);
		CompostingChanceRegistry.INSTANCE.add(ModItems.CAULIFLOWER_SEEDS, 0.35F);
		CompostingChanceRegistry.INSTANCE.add(ModItems.HONEY_BERRIES, 0.10F);

		StrippableBlockRegistry.register(ModBlocks.STRIPPED_DRIFTWOOD_LOG, ModBlocks.STRIPPED_DRIFTWOOD_LOG);
		StrippableBlockRegistry.register(ModBlocks.STRIPPED_DRIFTWOOD_WOOD, ModBlocks.STRIPPED_DRIFTWOOD_WOOD);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIFTWOOD_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIFTWOOD_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_DRIFTWOOD_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_DRIFTWOOD_WOOD, 5, 5);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIFTWOOD_PLANKS, 5, 28);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DRIFTWOOD_LEAVES, 30, 65);

		FabricDefaultAttributeRegistry.register(ModEntities.MANTIS, MantisEntity.createAttributes());
	}

}