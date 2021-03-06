package com.PiekarCorp.bidd;

import com.PiekarCorp.bidd.entity.EntitySeat;
import com.PiekarCorp.bidd.proxy.ClientProxy;
import com.PiekarCorp.bidd.proxy.CommonProxy;
import com.PiekarCorp.bidd.tileentity.TileEntityFancyBed;
import com.PiekarCorp.bidd.util.Reference;
import com.PiekarCorp.bidd.util.TabCreative;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid=Reference.MOD_ID, name=Reference.NAME, version=Reference.VERSION)
public class Main {

	public static final CreativeTabs BIDDTAB = new TabCreative("biddtab");
	
	@Instance
	public static Main instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static ClientProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		GameRegistry.registerTileEntity(TileEntityFancyBed.class, Reference.MOD_ID + "TileEntityFancyBed");
		EntityRegistry.registerModEntity(new ResourceLocation(""), EntitySeat.class, "MountableBlock", 0, Main.instance , 80, 1, false);
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		
	}
}
