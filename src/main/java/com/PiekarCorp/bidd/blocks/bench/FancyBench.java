package com.PiekarCorp.bidd.blocks.bench;

import java.util.List;

import javax.annotation.Nullable;

import com.PiekarCorp.bidd.Main;
import com.PiekarCorp.bidd.init.ModBlocks;
import com.PiekarCorp.bidd.init.ModItems;
import com.PiekarCorp.bidd.util.IHasModel;
import com.PiekarCorp.bidd.util.SeatUtil;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FancyBench extends Block implements IHasModel {
	
    public static final PropertyBool LEFT = PropertyBool.create("left"); 
    public static final PropertyBool RIGHT = PropertyBool.create("right");
    public static final PropertyBool FRONT = PropertyBool.create("front");
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    protected static final AxisAlignedBB ALL_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.2D, 1.0D);
    
    public static final AxisAlignedBB SOUTH_AABB =	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.2D, 0.0625D);
    public static final AxisAlignedBB WEST_AABB = 	new AxisAlignedBB(0.9375D, 0.0D, 0.0D, 1.0D, 1.2D, 1.0D);
    public static final AxisAlignedBB NORTH_AABB =	new AxisAlignedBB(0.0D, 0.0D, 0.9375D, 1.0D, 1.2D, 1.0D);
    public static final AxisAlignedBB EAST_AABB = 	new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0625D, 1.2D, 1.0D);
    
    public static final AxisAlignedBB SOUTH_SEAT_AABB =	new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 0.5625D, 0.8125);
    public static final AxisAlignedBB WEST_SEAT_AABB = 	new AxisAlignedBB(0.1875D, 0.5D, 0.0D, 1.0D, 0.5625D, 1.0D);
    public static final AxisAlignedBB NORTH_SEAT_AABB =	new AxisAlignedBB(0.0D, 0.5D, 1.0D, 1.0D, 0.5625D, 0.1875D);
    public static final AxisAlignedBB EAST_SEAT_AABB = 	new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.8125D, 0.5625D, 1.0D);

	
	public FancyBench(String name, Material material)
	{
		super(material); 
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		
		setCreativeTab(Main.BIDDTAB);
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		 if (!isActualState)
	        {
	            state = state.getActualState(worldIn, pos);
	        }

		 
	        if (getFacing(state)==EnumFacing.NORTH)
	        {
	        	addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_SEAT_AABB);
	            addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
	            if ((Boolean)state.getValue(FRONT) && (Boolean)state.getValue(RIGHT)) {addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_SEAT_AABB);}
	            if ((Boolean)state.getValue(FRONT) && (Boolean)state.getValue(LEFT)) {addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_SEAT_AABB);}
	        }

	        if ((getFacing(state)==EnumFacing.EAST))
	        {
	        	addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_SEAT_AABB);
	            addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
	            if ((Boolean)state.getValue(FRONT) && (Boolean)state.getValue(RIGHT)) {addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_SEAT_AABB);}
	            if ((Boolean)state.getValue(FRONT) && (Boolean)state.getValue(LEFT)) {addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_SEAT_AABB);}
	        }

	        if ((getFacing(state)==EnumFacing.SOUTH))
	        {
	        	addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_SEAT_AABB);
	        	addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
	            if ((Boolean)state.getValue(FRONT) && (Boolean)state.getValue(RIGHT)) {addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_SEAT_AABB);}
	            if ((Boolean)state.getValue(FRONT) && (Boolean)state.getValue(LEFT)) {addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_SEAT_AABB);}
	        }

	        if ((getFacing(state)==EnumFacing.WEST))
	        {
	        	addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_SEAT_AABB);
	            addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
	            if ((Boolean)state.getValue(FRONT) && (Boolean)state.getValue(RIGHT)) {addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_SEAT_AABB);}
	            if ((Boolean)state.getValue(FRONT) && (Boolean)state.getValue(LEFT)) {addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_SEAT_AABB);}
	        }
	        
	}
	
	@Nullable
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> list = Lists.<RayTraceResult>newArrayList();

        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, worldIn, pos)))
        {
            list.add(this.rayTrace(pos, start, end, axisalignedbb));
        }

        RayTraceResult raytraceresult1 = null;
        double d1 = 0.0D;

        for (RayTraceResult raytraceresult : list)
        {
            if (raytraceresult != null)
            {
                double d0 = raytraceresult.hitVec.squareDistanceTo(end);

                if (d0 > d1)
                {
                    raytraceresult1 = raytraceresult;
                    d1 = d0;
                }
            }
        }

        return raytraceresult1;
    }
	
    private List<AxisAlignedBB> getCollisionBoxList(IBlockState bstate)
    {
        List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();
       
		 
        if (getFacing(bstate)==EnumFacing.NORTH)
        {
        	list.add(NORTH_SEAT_AABB);
        	list.add( NORTH_AABB);
            if ((Boolean)bstate.getValue(FRONT) && (Boolean)bstate.getValue(RIGHT)) {list.add( EAST_AABB);list.add(EAST_SEAT_AABB);}
            if ((Boolean)bstate.getValue(FRONT) && (Boolean)bstate.getValue(LEFT)) {list.add( WEST_AABB);list.add(WEST_SEAT_AABB);}
        }

        if ((getFacing(bstate)==EnumFacing.EAST))
        {
        	list.add(EAST_SEAT_AABB);
        	list.add(EAST_AABB);
            if ((Boolean)bstate.getValue(FRONT) && (Boolean)bstate.getValue(RIGHT)) {list.add( SOUTH_AABB);list.add(SOUTH_SEAT_AABB);}
            if ((Boolean)bstate.getValue(FRONT) && (Boolean)bstate.getValue(LEFT)) {list.add( NORTH_AABB);list.add(NORTH_SEAT_AABB);}
        }

        if ((getFacing(bstate)==EnumFacing.SOUTH))
        {
        	list.add(SOUTH_AABB);
        	list.add(SOUTH_SEAT_AABB);
            if ((Boolean)bstate.getValue(FRONT) && (Boolean)bstate.getValue(RIGHT)) {list.add(WEST_AABB);list.add(WEST_SEAT_AABB);}
            if ((Boolean)bstate.getValue(FRONT) && (Boolean)bstate.getValue(LEFT)) {list.add(EAST_AABB);list.add(EAST_SEAT_AABB);}
        }

        if ((getFacing(bstate)==EnumFacing.WEST))
        {
        	list.add(WEST_AABB);
        	list.add(WEST_SEAT_AABB);
            if ((Boolean)bstate.getValue(FRONT) && (Boolean)bstate.getValue(RIGHT)) {list.add(NORTH_AABB);list.add(NORTH_SEAT_AABB);}
            if ((Boolean)bstate.getValue(FRONT) && (Boolean)bstate.getValue(LEFT)) {list.add( SOUTH_AABB);list.add(SOUTH_SEAT_AABB);}
        }

        return list;
    }
	
		
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return ALL_AABB;
    }
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL ;
    }
	
	   @Override
	    @SideOnly(Side.CLIENT)
	    public BlockRenderLayer getBlockLayer()
	    {
	        return BlockRenderLayer.CUTOUT_MIPPED;
	    }

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");	
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(LEFT, canConnectTo( worldIn, pos, state, "left"))
					.withProperty(RIGHT, canConnectTo( worldIn, pos, state, "right"))
					.withProperty(FRONT, canConnectTo( worldIn, pos, state, "front"));

	}
	      
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
	
	@Override   
	protected BlockStateContainer createBlockState()
	    {
		 return new BlockStateContainer(this, FACING, LEFT, RIGHT, FRONT) ;
	    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}

	
	public EnumFacing getFacing(IBlockState state)
	{
	return (EnumFacing)state.getValue(FACING);
	}
	
		
    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, IBlockState state, String flag)
    {
    	EnumFacing facing = getFacing(state);

    	BlockPos pos2 = pos.offset(getDirectionFromFacing(facing, flag));
    	Block block = worldIn.getBlockState(pos2).getBlock();	
    	if (block instanceof FancyBench) 
    	{   		
    		IBlockState state2=worldIn.getBlockState(pos2);
    		EnumFacing facing2 = getFacing(state2);
    		if (flag == "front")
    		{  	
    			if( facing == facing2 || facing == facing2.getOpposite())return false;
    			if( facing2 == getDirectionFromFacing(facing ,"right" ) && canConnectTo(worldIn, pos, state, "left"))return false; 
    			if( facing2 == getDirectionFromFacing(facing ,"left" ) && canConnectTo(worldIn, pos, state, "right"))return false;
    			if( (!canConnectTo(worldIn, pos, state, "left") && !canConnectTo(worldIn, pos, state, "right")))return false;
    		}
    		if (flag == "left")
    		{       	
    			if( facing == getDirectionFromFacing(facing2 ,"right"  ) || facing == facing2.getOpposite())return false;
    			if( facing2 == getDirectionFromFacing(facing ,"right"))return canConnectTo(worldIn, pos2, state2, "front");
    		
    			
    		}
    		if (flag == "right")
    		{
    			if( facing == getDirectionFromFacing(facing2 ,"left" ) || facing == facing2.getOpposite())return false;
    			if( facing2 == getDirectionFromFacing(facing ,"left"))return canConnectTo(worldIn, pos2, state2, "front");
    		
    		} 
    		return true;
    	}
		return false;     
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
    		EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	  if(!playerIn.isSneaking())
          {
              if(SeatUtil.sitOnBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 6 * 0.0625))
              {
                  worldIn.updateComparatorOutputLevel(pos, this);
                  return true;
              }
          }
          return false;
    }
    
    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return SeatUtil.isSomeoneSitting(worldIn, pos.getX(), pos.getY(), pos.getZ()) ? 1 : 0;
    }
    
    public EnumFacing getDirectionFromFacing(EnumFacing facing,String flag)
    {
    	switch(facing) 
    	{
    	case NORTH:
    	switch(flag) 
    	{   	
    		case "left":
    		return EnumFacing.WEST; 	
    		case "right":
    		return EnumFacing.EAST;	
    		case "front":
    		return EnumFacing.NORTH;	
    		default:
    			return facing;
    	}
    	case SOUTH:
        	switch(flag) 
        	{   	
        		case "left":
        		return EnumFacing.EAST;		
        		case "right":
        		return EnumFacing.WEST; 	
        		case "front":
        		return EnumFacing.SOUTH;	
        		default:
        			return facing;
        	}
    	case WEST:
        	switch(flag) 
        	{   	
        		case "left":
        		return EnumFacing.SOUTH;	
        		case "right":
        		return EnumFacing.NORTH;	
        		case "front":
        		return EnumFacing.WEST;	
        		default:
        		return facing;
        	}	
    	case EAST:
        	switch(flag) 
        	{   	
        		case "left":
        		return EnumFacing.NORTH;	
        		case "right":
        		return EnumFacing.SOUTH;	
        		case "front":
        		return EnumFacing.EAST;
        		default:
        			return facing;
        	}
    	default: return facing;
    	}
    	   	
    }
}
