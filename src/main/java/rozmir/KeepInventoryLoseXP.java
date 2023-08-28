package rozmir;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = KeepInventoryLoseXP.MODID, version = KeepInventoryLoseXP.VERSION)
public class KeepInventoryLoseXP
{
    public static final String MODID = "keepinventorylosexp";
    public static final String VERSION = "1.0";
    
    @EventHandler
	public void preInit(FMLPreInitializationEvent event) {
	MinecraftForge.EVENT_BUS.register(new DeathEvent());
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		
		FMLCommonHandler.instance().bus().register(new ConnectionHandler());
	}
}
