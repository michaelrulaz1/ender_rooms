package com.enderrooms;

import com.enderrooms.registry.ModBlocks;
import com.enderrooms.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EnderRoomsMod implements ModInitializer {
    public static final String MOD_ID = "ender_rooms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /** UUIDs of in-flight EyeOfEnderEntity instances spawned by the enhanced item. */
    public static final Set<UUID> ENHANCED_EYE_IDS =
            Collections.synchronizedSet(new HashSet<>());

    @Override
    public void onInitialize() {
        ModBlocks.initialize();
        ModItems.initialize();
        LOGGER.info("Ender Rooms initialized.");
    }
}
