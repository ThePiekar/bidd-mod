package com.PiekarCorp.bidd.util;

import com.PiekarCorp.bidd.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabCreative extends CreativeTabs{

	public TabCreative(String label) {
	super(label);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.ITEM_FANCY_BED);
	}
}
