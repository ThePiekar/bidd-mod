package com.PiekarCorp.bidd.tileentity;

import javax.annotation.Nullable;

import com.PiekarCorp.bidd.blocks.beds.BlockFancyBed;
import com.PiekarCorp.bidd.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFancyBed extends TileEntity {

	private EnumDyeColor color = EnumDyeColor.PINK;
	
	public TileEntityFancyBed()
	{
		
	}
	
	@Override
	    public void onLoad()
	{
		
	}
	
	 
    public void setItemValues(ItemStack p_193051_1_)
    {
        this.setColor(EnumDyeColor.byMetadata(p_193051_1_.getMetadata()));
    }
	 
    
	
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
    	super.writeToNBT(compound);
        compound.setInteger("color", this.color.getMetadata());
        return compound;
    }
        
    public void setColor(EnumDyeColor color)
    {
        this.color = color;
        this.markDirty();
    }

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.color = EnumDyeColor.byMetadata(compound.getInteger("color"));
    }

    
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
		notifyBlockUpdate();
	}
    
	public void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState().withProperty(BlockFancyBed.COLOR, color), 2);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
		markDirty();
	}
    
	private IBlockState getState() {
		return world.getBlockState(pos);
	}
   
	private void notifyBlockUpdate() {
		final IBlockState state = getWorld().getBlockState(getPos());
		getWorld().notifyBlockUpdate(getPos(), state, state, 3);
	}
	
	@Override
	public void markDirty() {
		super.markDirty();
		notifyBlockUpdate();
	}
	
    public EnumDyeColor getColor()
    {
        return this.color;
    }
	
    @SideOnly(Side.CLIENT)
    public boolean isHeadPiece()
    {
        return BlockFancyBed.isHeadPiece(this.getBlockMetadata());
    }
	
	public ItemStack getItemStack() {
		return new ItemStack(ModItems.ITEM_FANCY_BED, 1, color.getMetadata());
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
	return (oldState.getBlock() != newState.getBlock());
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
	}
	
	@Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        this.readFromNBT(tag);
    }
		
}
