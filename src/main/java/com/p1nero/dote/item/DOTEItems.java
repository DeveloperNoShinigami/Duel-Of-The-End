package com.p1nero.dote.item;

import com.p1nero.dote.DuelOfTheEndMod;
import com.p1nero.dote.item.custom.SimpleDescriptionFoilItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DOTEItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, DuelOfTheEndMod.MOD_ID);

	public static final RegistryObject<Item> IMMORTALESSENCE = REGISTRY.register("immortalessence", () -> new SimpleDescriptionFoilItem(new Item.Properties().fireResistant().rarity(DOTERarities.SHANG_PIN)));


}
