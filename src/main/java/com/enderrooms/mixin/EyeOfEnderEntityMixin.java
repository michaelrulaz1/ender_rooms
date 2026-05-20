package com.enderrooms.mixin;

import com.enderrooms.EnderRoomsMod;
import net.minecraft.entity.EyeOfEnderEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(EyeOfEnderEntity.class)
public abstract class EyeOfEnderEntityMixin {

    // Private fields made accessible via ender_rooms.accesswidener
    @Shadow private int lifespan;
    @Shadow private boolean dropsItem;

    /**
     * For enhanced eyes: reset lifespan every tick so the entity never
     * naturally expires. Also prunes the UUID from the tracking set once
     * the entity has been removed by any other means (e.g. player pickup),
     * preventing unbounded set growth within a session.
     */
    @Inject(method = "tick", at = @At("RETURN"))
    private void enderRooms$tickHook(CallbackInfo ci) {
        EyeOfEnderEntity self = (EyeOfEnderEntity)(Object)this;
        UUID id = self.getUuid();
        if (!EnderRoomsMod.ENHANCED_EYE_IDS.contains(id)) return;

        if (self.isRemoved()) {
            EnderRoomsMod.ENHANCED_EYE_IDS.remove(id);
        } else {
            lifespan = 0;
            dropsItem = true;
        }
    }
}
