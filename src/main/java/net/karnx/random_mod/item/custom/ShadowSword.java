package net.karnx.random_mod.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ShadowSword extends SwordItem {
    private static final int COOLDOWN_TICKS = 30; // Cooldown in ticks (1.5 seconds)
    private static final double DASH_DISTANCE = 5.0; // Dash distance
    private static final float DAMAGE = 6.0F; // Damage dealt to entities

    public ShadowSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            // Get direction player is facing
            Vec3d direction = user.getRotationVector().normalize();
            Vec3d startPos = user.getPos();
            Vec3d dashEnd = startPos.add(direction.multiply(DASH_DISTANCE));

            // Create a bounding box for entities in the dash path
            Box dashBox = new Box(startPos, dashEnd).expand(1.0); // Widen the path slightly
            world.getOtherEntities(user, dashBox).forEach(entity -> {
                if (entity instanceof LivingEntity livingEntity) {
                    // Directly apply damage without DamageSource
                    livingEntity.damage(livingEntity.getDamageSources().generic(), DAMAGE);
                }
            });

            // Teleport player to the end position
            user.requestTeleport(dashEnd.x, dashEnd.y, dashEnd.z);

            // Spawn particles along the dash path
            for (double i = 0; i < DASH_DISTANCE; i += 0.5) {
                Vec3d particlePos = startPos.add(direction.multiply(i));
                world.addParticle(ParticleTypes.SMOKE, particlePos.x, particlePos.y, particlePos.z, 0, 0, 0);
            }

            // Apply cooldown to the item
            user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
