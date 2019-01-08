package com.PiekarCorp.bidd.tileentity;

import javax.annotation.Nullable;

import com.PiekarCorp.bidd.blocks.beds.BlockFancyBed;
import com.PiekarCorp.bidd.init.ModBlocks;
import com.PiekarCorp.bidd.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.ITickable;
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

	private EnumDyeColor bcolor= EnumDyeColor.RED;
	
	public TileEntityFancyBed()
	{
	}	
	 
    public void setItemValues(ItemStack p_193051_1_)
    {
        this.setColor(EnumDyeColor.byMetadata(p_193051_1_.getMetadata()));
    }
	 
    
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
    	super.writeToNBT(compound);
        compound.setInteger("bcolor", bcolor.getMetadata());
        return compound;
    }
    
    public void setColor(EnumDyeColor color)
    {
        this.bcolor = color;
        markDirty();
    }

	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        bcolor = EnumDyeColor.byMetadata(compound.getInteger("bcolor"));
    }
    

    public EnumDyeColor getColor()
    {
        return bcolor;
    }
		
	public ItemStack getItemStack() {
		return new ItemStack(ModItems.ITEM_FANCY_BED, 1, bcolor.getMetadata());
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

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 1, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
		notifyBlockUpdate();
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
		
}
