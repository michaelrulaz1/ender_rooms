package com.enderrooms.registry;

import com.enderrooms.EnderRoomsMod;
import com.enderrooms.item.EnhancedEyeOfEnderItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static final Item ENHANCED_EYE_OF_ENDER = registerItem("enhanced_eye_of_ender",
            key -> new EnhancedEyeOfEnderItem(new Item.Settings().maxCount(16).registryKey(key)));

    private static Item registerItem(String name, Function<RegistryKey<Item>, Item> factory) {
        Identifier id = Identifier.of(EnderRoomsMod.MOD_ID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        return Registry.register(Registries.ITEM, key, factory.apply(key));
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries ->
                entries.add(ENHANCED_EYE_OF_ENDER));
    }
}
