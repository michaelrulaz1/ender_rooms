package com.enderrooms.mixin;

import com.enderrooms.data.PlayerEnderDataAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements PlayerEnderDataAccess {

    @Unique private int enderRooms$visitCount = 0;
    @Unique private long enderRooms$lastDoorActivation = 0L;

    @Override public int enderRooms$getVisitCount() { return enderRooms$visitCount; }
    @Override public void enderRooms$setVisitCount(int count) { enderRooms$visitCount = count; }
    @Override public long enderRooms$getLastDoorActivation() { return enderRooms$lastDoorActivation; }
    @Override public void enderRooms$setLastDoorActivation(long time) { enderRooms$lastDoorActivation = time; }

    @Override
    public NbtCompound enderRooms$toNbt() {
        NbtCompound tag = new NbtCompound();
        tag.putInt("visitCount", enderRooms$visitCount);
        tag.putLong("lastDoorActivation", enderRooms$lastDoorActivation);
        return tag;
    }

    @Override
    public void enderRooms$fromNbt(NbtCompound tag) {
        enderRooms$visitCount = tag.getInt("visitCount");
        enderRooms$lastDoorActivation = tag.getLong("lastDoorActivation");
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void enderRooms$saveData(NbtCompound nbt, CallbackInfo ci) {
        nbt.put("EnderRoomsData", enderRooms$toNbt());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void enderRooms$loadData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("EnderRoomsData")) {
            enderRooms$fromNbt(nbt.getCompound("EnderRoomsData"));
        }
    }
}
