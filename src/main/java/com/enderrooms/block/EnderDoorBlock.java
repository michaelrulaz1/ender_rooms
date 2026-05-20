package com.enderrooms.block;

import com.enderrooms.data.PlayerEnderDataAccess;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnderDoorBlock extends DoorBlock {

    public EnderDoorBlock(AbstractBlock.Settings settings) {
        super(BlockSetType.IRON, settings);
    }

    /**
     * Fires every tick an entity occupies the door's bounding box.
     * A 60-tick (3 s) per-player cooldown prevents repeated triggers.
     */
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient || !(entity instanceof ServerPlayerEntity player)) return;
        if (!state.get(OPEN)) return;

        PlayerEnderDataAccess data = (PlayerEnderDataAccess) player;
        long now = world.getTime();
        if (now - data.enderRooms$getLastDoorActivation() < 60L) return;

        data.enderRooms$setLastDoorActivation(now);
        data.enderRooms$setVisitCount(data.enderRooms$getVisitCount() + 1);

        player.sendMessage(Text.translatable("chat.ender_rooms.ender_door_activated"), true);
        world.playSound(null, pos,
                SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                SoundCategory.BLOCKS, 0.6f, 0.8f);
    }
}
