package com.PiekarCorp.bidd.init;

import java.util.ArrayList;
import java.util.List;

import com.PiekarCorp.bidd.blocks.BlockBase;
import com.PiekarCorp.bidd.blocks.beds.BlockFancyBed;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
//	public static final Block BLOCK_NAME = new BlockBase("block_name", Material.WOOD);
	
	public static final Block BLOCK_FANCY_BED = new BlockFancyBed("block_fancy_bed");	
}
