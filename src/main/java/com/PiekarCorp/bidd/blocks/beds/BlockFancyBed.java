package com.PiekarCorp.bidd.blocks.beds;


import java.util.Random;


import javax.annotation.Nullable;

import com.PiekarCorp.bidd.init.ModBlocks;
import com.PiekarCorp.bidd.init.ModItems;
import com.PiekarCorp.bidd.items.ItemFancyBed;
import com.PiekarCorp.bidd.tileentity.TileEntityFancyBed;

import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockColored;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.properties.PropertyEnum;


public class BlockFancyBed extends BlockBed{
	
	public static final IProperty<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);
	
	public BlockFancyBed(String name) 
	{
		setUnlocalizedName(name);
		this.setHardness(1);
		setRegistryName(name);
		ModBlocks.BLOCKS.add(this);	
	} 
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	
	@Override
	 public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity player)
	    {
	        return true;
	    }
	
		@Override
	   public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	    {
	        if (state.getValue(PART) == BlockFancyBed.EnumPartType.FOOT)
	        {
	            TileEntity tileentity = worldIn.getTileEntity(pos);

	            if (tileentity instanceof TileEntityFancyBed)
	            {
	                EnumDyeColor enumdyecolor = ((TileEntityFancyBed)tileentity).getColor();
	                return MapColor.getBlockColor(enumdyecolor);
	            }
	        }
	        return MapColor.CLOTH;
	    }
		
	
	
	@Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityFancyBed();
    }
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL ;
    }
	 
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {      	
            return true;           
        }
        else
        {
            if (state.getValue(PART) != BlockFancyBed.EnumPartType.HEAD)
            {
                pos = pos.offset((EnumFacing)state.getValue(FACING));
                state = worldIn.getBlockState(pos);

                if (state.getBlock() != this)
                {
                    return true;
                }
            }

            net.minecraft.world.WorldProvider.WorldSleepResult sleepResult = worldIn.provider.canSleepAt(playerIn, pos);
            if (sleepResult != net.minecraft.world.WorldProvider.WorldSleepResult.BED_EXPLODES)
            {
                if (sleepResult == net.minecraft.world.WorldProvider.WorldSleepResult.DENY) return true;
                if (((Boolean)state.getValue(OCCUPIED)).booleanValue())
                {
                    EntityPlayer entityplayer = this.getPlayerInBed(worldIn, pos);

                    if (entityplayer != null)
                    {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.occupied", new Object[0]), true);
                        return true;
                    }

                    state = state.withProperty(OCCUPIED, Boolean.valueOf(false));
                    worldIn.setBlockState(pos, state, 4);
                }

                EntityPlayer.SleepResult entityplayer$sleepresult = playerIn.trySleep(pos);

                if (entityplayer$sleepresult == EntityPlayer.SleepResult.OK)
                {
                	playerIn.heal(20);
                    state = state.withProperty(OCCUPIED, Boolean.valueOf(true));
                    worldIn.setBlockState(pos, state, 4);
                    return true;
                }
                else
                {
                    if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW)
                    {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.noSleep", new Object[0]), true);
                    }
                    else if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_SAFE)
                    {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.notSafe", new Object[0]), true);
                    }
                    else if (entityplayer$sleepresult == EntityPlayer.SleepResult.TOO_FAR_AWAY)
                    {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.tooFarAway", new Object[0]), true);
                    }

                    return true;
                }
            }
            else
            {
                worldIn.setBlockToAir(pos);
                BlockPos blockpos = pos.offset(((EnumFacing)state.getValue(FACING)).getOpposite());

                if (worldIn.getBlockState(blockpos).getBlock() == this)
                {
                    worldIn.setBlockToAir(blockpos);
                }

                worldIn.newExplosion((Entity)null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0F, true, true);
                return true;
            }
        }
    }
	
    @Nullable
    private EntityPlayer getPlayerInBed(World worldIn, BlockPos pos)
    {
        for (EntityPlayer entityplayer : worldIn.playerEntities)
        {
            if (entityplayer.isPlayerSleeping() && entityplayer.bedLocation.equals(pos))
            {
                return entityplayer;
            }
        }
        
        

        return null;
       
    }
   

    	
    	@Override
        protected BlockStateContainer createBlockState()
        {
           // return new BlockStateContainer(this, new IProperty[] {FACING, PART, OCCUPIED});
    		 return new BlockStateContainer(this, FACING, PART, OCCUPIED, COLOR) ;

        }
    	

        public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
        {
            super.breakBlock(worldIn, pos, state);
            worldIn.removeTileEntity(pos);
        }
        
        public Item getItemDropped(IBlockState state, Random rand, int fortune)
        {
            return state.getValue(PART) == BlockFancyBed.EnumPartType.FOOT ? Items.AIR : ModItems.ITEM_FANCY_BED ;
        }
        
        @Override
        public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
        {
            if (state.getValue(PART) == BlockFancyBed.EnumPartType.HEAD && te instanceof TileEntityFancyBed)
            {
                TileEntityFancyBed tileentitybed = (TileEntityFancyBed)te;
                ItemStack itemstack = tileentitybed.getItemStack();
                spawnAsEntity(worldIn, pos, itemstack);
            }
            else
            {
                super.harvestBlock(worldIn, player, pos, state, (TileEntity)null, stack);
            }
        }
        
        public EnumDyeColor loadColor(IBlockAccess worldIn, BlockPos pos)
        {
        	TileEntity tileentity = worldIn.getTileEntity(pos);
        	if(tileentity instanceof TileEntityFancyBed) {
        		TileEntityFancyBed bed = (TileEntityFancyBed) tileentity;
        	
        		return bed.getColor();
        	}     		
        	return EnumDyeColor.RED;
        }
        
           @SuppressWarnings("deprecation")
    	   @Override
    	    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    	    {
    	            state = state.withProperty(COLOR , loadColor(worldIn, pos));
    	            return state;
    	    }
    	   
    	   @Override
    	   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    	    {
    	        if (state.getValue(PART) == BlockFancyBed.EnumPartType.HEAD)
    	        {
    	        	 TileEntity tileentity = worldIn.getTileEntity(pos);
    	    	     TileEntityFancyBed te = (TileEntityFancyBed)tileentity;
    	    	     EnumDyeColor enumdyecolor = te.getColor();
    	            spawnAsEntity(worldIn, pos, new ItemStack(ModItems.ITEM_FANCY_BED, 1, enumdyecolor.getMetadata()));
    	        }
    	    }
    	   @Override
    	   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    	    {
    	        BlockPos blockpos = pos;

    	        if (state.getValue(PART) == BlockFancyBed.EnumPartType.FOOT)
    	        {
    	            blockpos = pos.offset((EnumFacing)state.getValue(FACING));
    	        }

    	        TileEntity tileentity = worldIn.getTileEntity(blockpos);
    	        TileEntityFancyBed te = (TileEntityFancyBed)tileentity;
    	        EnumDyeColor enumdyecolor = te.getColor();
    	        return new ItemStack(ModItems.ITEM_FANCY_BED, 1, enumdyecolor.getMetadata());
    	    }
    	      	   
    	   @SideOnly(Side.CLIENT)
    	    public static boolean isHeadPiece(int metadata)
    	    {
    	        return false;
    	    }
    	   
    	   @Override
    	    @SideOnly(Side.CLIENT)
    	    public BlockRenderLayer getBlockLayer()
    	    {
    	        return BlockRenderLayer.CUTOUT;
    	    }
    	   
    	   
    	   
}
