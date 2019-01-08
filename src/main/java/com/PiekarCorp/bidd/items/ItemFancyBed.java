package com.PiekarCorp.bidd.items;

import com.PiekarCorp.bidd.Main;
import com.PiekarCorp.bidd.blocks.beds.BlockFancyBed;
import com.PiekarCorp.bidd.init.ModBlocks;
import com.PiekarCorp.bidd.init.ModItems;
import com.PiekarCorp.bidd.proxy.CommonProxy;
import com.PiekarCorp.bidd.tileentity.TileEntityFancyBed;
import com.PiekarCorp.bidd.util.IHasModel;
import com.PiekarCorp.bidd.util.Reference;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ItemFancyBed extends ItemBed implements IHasModel {

	public ItemFancyBed(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MISC);
		ModItems.ITEMS.add(this);
	}
	 
	@Override
	  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	    {
	        if (worldIn.isRemote)
	        {
	            return EnumActionResult.SUCCESS;
	        }
	        else if (facing != EnumFacing.UP)
	        {
	            return EnumActionResult.FAIL;
	        }
	        else
	        {
	            IBlockState iblockstate = worldIn.getBlockState(pos);
	            Block block = iblockstate.getBlock();
	            boolean flag = block.isReplaceable(worldIn, pos);

	            if (!flag)
	            {
	                pos = pos.up();
	            }

	            int i = MathHelper.floor((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
	            EnumFacing enumfacing = EnumFacing.getHorizontal(i);
	            BlockPos blockpos = pos.offset(enumfacing);
	            ItemStack itemstack = player.getHeldItem(hand);

	            if (player.canPlayerEdit(pos, facing, itemstack) && player.canPlayerEdit(blockpos, facing, itemstack))
	            {
	                IBlockState iblockstate1 = worldIn.getBlockState(blockpos);
	                boolean flag1 = iblockstate1.getBlock().isReplaceable(worldIn, blockpos);
	                boolean flag2 = flag || worldIn.isAirBlock(pos);
	                boolean flag3 = flag1 || worldIn.isAirBlock(blockpos);

	                if (flag2 && flag3 && worldIn.getBlockState(pos.down()).isTopSolid() && worldIn.getBlockState(blockpos.down()).isTopSolid())
	                {	
	                    IBlockState iblockstate2 = ModBlocks.BLOCK_FANCY_BED.getDefaultState().withProperty(BlockFancyBed.OCCUPIED, Boolean.valueOf(false)).withProperty(BlockFancyBed.FACING, enumfacing).withProperty(BlockFancyBed.PART, BlockFancyBed.EnumPartType.FOOT).withProperty(BlockFancyBed.COLOR,EnumDyeColor.byMetadata(itemstack.getMetadata()));	                    
	                    worldIn.setBlockState(pos, iblockstate2, 11);
	                    worldIn.setBlockState(blockpos, iblockstate2.withProperty(BlockFancyBed.PART, BlockFancyBed.EnumPartType.HEAD) , 11);
	                    SoundType soundtype = iblockstate2.getBlock().getSoundType(iblockstate2, worldIn, pos, player);
	                    worldIn.playSound((EntityPlayer)null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
	                    TileEntity tileentity = worldIn.getTileEntity(blockpos);

	                    if (tileentity instanceof TileEntityFancyBed)
	                    {
	                        TileEntityFancyBed te = (TileEntityFancyBed)tileentity;
	                        te.setItemValues(itemstack);
	                    }

	                    TileEntity tileentity1 = worldIn.getTileEntity(pos);

	                    if (tileentity1 instanceof TileEntityFancyBed)
	                    {
	                    	 TileEntityFancyBed te1 = (TileEntityFancyBed)tileentity1;
                     		te1.setItemValues(itemstack);
	                    }


	                    worldIn.notifyNeighborsRespectDebug(pos, block, false);
	                    worldIn.notifyNeighborsRespectDebug(blockpos, iblockstate1.getBlock(), false);

	                    if (player instanceof EntityPlayerMP)
	                    {
	                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
	                    }

	                    itemstack.shrink(1);
	                    return EnumActionResult.SUCCESS;
	                }
	                else
	                {
	                    return EnumActionResult.FAIL;
	                }
	            }
	            else
	            {
	                return EnumActionResult.FAIL;
	            }
	        }
	    }

	@Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "_" + EnumDyeColor.byMetadata(stack.getMetadata()).getUnlocalizedName();
    }
	
	
	
	private static void registerMetaRender(Item item, int meta, String file){
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MOD_ID + ":" + file));
	}
	
	@Override
	public void registerModels() {
		registerMetaRender(this, 0, "item_fancy_bed_white");
		registerMetaRender(this, 1, "item_fancy_bed_orange");
		registerMetaRender(this, 2, "item_fancy_bed_magenta");
		registerMetaRender(this, 3, "item_fancy_bed_light_blue");
		registerMetaRender(this, 4, "item_fancy_bed_yellow");
		registerMetaRender(this, 5, "item_fancy_bed_lime");
		registerMetaRender(this, 6, "item_fancy_bed_pink");
		registerMetaRender(this, 7, "item_fancy_bed_gray");
		registerMetaRender(this, 8, "item_fancy_bed_silver");
		registerMetaRender(this, 9, "item_fancy_bed_cyan");
		registerMetaRender(this, 10, "item_fancy_bed_purple");
		registerMetaRender(this, 11, "item_fancy_bed_blue");
		registerMetaRender(this, 12, "item_fancy_bed_brown");
		registerMetaRender(this, 13, "item_fancy_bed_green");
		registerMetaRender(this, 14, "item_fancy_bed_red");
		registerMetaRender(this, 15, "item_fancy_bed_black");
	} 
	
}
