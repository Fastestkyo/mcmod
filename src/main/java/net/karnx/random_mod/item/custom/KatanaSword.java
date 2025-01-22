package net.karnx.random_mod.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class KatanaSword extends SwordItem {
    public KatanaSword(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    private static final int COMBO_TIME = 60;
    private int comboCount = 0;
    private long lasthit = 0;

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        long time = world.getTime();
        if (time - lasthit > COMBO_TIME) {
            comboCount = 0;
        }
        if (comboCount < 3) {
            comboCount++;
        }
        lasthit = time;
        applyCombo(user);
        return super.use(world, user, hand);
    }

    private void applyCombo(PlayerEntity user) {
        switch (comboCount) {
            case 1:
                break;
            case 2:
                user.getMainHandStack().setDamage(user.getMainHandStack().getDamage() -1);
                break;
            case 3:
                user.getMainHandStack().setDamage(user.getMainHandStack().getDamage() -3);
                break;
            default:
                comboCount = 0;
                break;
        }
    }
}
