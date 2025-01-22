package net.karnx.random_mod.item.custom;

import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class VolcanicSword extends SwordItem {

    private static final int COOLDOWN_TIME = 40; // Cooldown time in ticks (40 ticks = 2 seconds)
    private long lastused = 0; // Time in ticks when the sword was last used

    public VolcanicSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) return super.use(world, user, hand);
        long ticks = world.getTime();

        if (ticks - lastused < COOLDOWN_TIME) {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
        if (world instanceof ServerWorld) {
            launchFireball(world, user);
        }
        lastused = ticks;
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    private void launchFireball(World world, PlayerEntity user) {
        Vec3d lookDirection = user.getRotationVector();
        Vec3d fireballVelocity = new Vec3d(lookDirection.x, lookDirection.y, lookDirection.z);

        FireballEntity fireball = new FireballEntity(world, user, fireballVelocity, 1); // 1 is the explosion power
        fireball.setPos(user.getX() + fireballVelocity.x * 2, user.getY() + user.getHeight() * 0.5, user.getZ() + fireballVelocity.z * 2);

        world.spawnEntity(fireball);
    }
}
