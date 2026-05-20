package com.enderrooms.registry;

import com.enderrooms.EnderRoomsMod;
import com.enderrooms.block.EnderDoorBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {
    public static final Block ENDER_DOOR = registerBlock("ender_door",
            key -> new EnderDoorBlock(
                    AbstractBlock.Settings.copy(Blocks.IRON_DOOR)
                            .registryKey(key)
                            .sounds(BlockSoundGroup.STONE)
                            .pistonBehavior(PistonBehavior.IGNORE)));

    private static Block registerBlock(String name, Function<RegistryKey<Block>, Block> factory) {
        Identifier id = Identifier.of(EnderRoomsMod.MOD_ID, name);
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, id);
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);

        Block block = factory.apply(blockKey);
        Registry.register(Registries.BLOCK, blockKey, block);
        Registry.register(Registries.ITEM, itemKey,
                new BlockItem(block, new Item.Settings().registryKey(itemKey)));
        return block;
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries ->
                entries.add(ENDER_DOOR));
    }
}
