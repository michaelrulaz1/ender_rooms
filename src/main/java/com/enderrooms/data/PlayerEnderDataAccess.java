package com.enderrooms.data;

import net.minecraft.nbt.NbtCompound;

public interface PlayerEnderDataAccess {
    int enderRooms$getVisitCount();
    void enderRooms$setVisitCount(int count);
    long enderRooms$getLastDoorActivation();
    void enderRooms$setLastDoorActivation(long worldTime);
    NbtCompound enderRooms$toNbt();
    void enderRooms$fromNbt(NbtCompound tag);
}
