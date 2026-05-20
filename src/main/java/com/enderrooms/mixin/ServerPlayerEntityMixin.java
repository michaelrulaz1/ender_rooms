package com.enderrooms.mixin;

import com.enderrooms.data.PlayerEnderDataAccess;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    /**
     * Copies Ender Rooms player data on death/respawn so it survives across lives.
     * keepInventory does not affect our data — we always copy it.
     */
    @Inject(method = "copyFrom", at = @At("TAIL"))
    private void enderRooms$copyData(ServerPlayerEntity oldPlayer, boolean keepInventory, CallbackInfo ci) {
        PlayerEnderDataAccess newAccess = (PlayerEnderDataAccess)(Object)this;
        PlayerEnderDataAccess oldAccess = (PlayerEnderDataAccess) oldPlayer;
        newAccess.enderRooms$fromNbt(oldAccess.enderRooms$toNbt());
    }
}
