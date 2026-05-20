package com.enderrooms.item;

import com.enderrooms.EnderRoomsMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class EnhancedEyeOfEnderItem extends EnderEyeItem {

    public EnhancedEyeOfEnderItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack saved = user.getStackInHand(hand).copy();

        ActionResult result = super.use(world, user, hand);

        if (result.isAccepted()) {
            if (!world.isClient) {
                // Tag every EyeOfEnderEntity spawned within 2 blocks as enhanced.
                Box nearby = user.getBoundingBox().expand(2.0, 2.0, 2.0);
                world.getEntitiesByType(EntityType.EYE_OF_ENDER, nearby, e -> true)
                     .forEach(e -> EnderRoomsMod.ENHANCED_EYE_IDS.add(e.getUuid()));
            }
            // Enhanced version is never consumed — restore the stack.
            if (!user.isCreative()) {
                user.setStackInHand(hand, saved);
            }
        }
        return result;
    }
}
