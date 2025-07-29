package com.p1nero.dote.block;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.block.custom.BetterStructureBlock;
import com.p1nero.dote.item.DOTEItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DOTEBlocks {
    public static final DeferredRegister<Block> REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCKS, DuelOfTheEndMod.MOD_ID);

    public static final RegistryObject<Block> BETTER_STRUCTURE_BLOCK = registerBlock("better_structure_block",
            () -> new BetterStructureBlock(BlockBehaviour.Properties.copy(Blocks.STRUCTURE_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> M_BLOCK = registerBlock("m_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> P_BLOCK = registerBlock("p_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> U_BLOCK = registerBlock("u_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return DOTEItems.REGISTRY.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register() {
    }

}
