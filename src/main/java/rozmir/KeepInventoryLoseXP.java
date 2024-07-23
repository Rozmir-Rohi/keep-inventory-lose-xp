package rozmir;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;


@Mod(modid = "KeepInventoryLoseXP", version = "1.1")
public class KeepInventoryLoseXP
{
    public static Configuration configFile;
    
    public static int experienceLossMultiplier;
    
    @EventHandler
	public void preInit(FMLPreInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(new DeathEvent());
    	configFile = new Configuration(event.getSuggestedConfigurationFile());
    	syncConfigSettings();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		
		FMLCommonHandler.instance().bus().register(new SetKeepInventoryTrue());
	}
	
	public static void syncConfigSettings() {
		experienceLossMultiplier = configFile.getInt("experienceLossMultiplier", "general", 7, 0, 2147483647, "Vanilla MC: 7 (higher values mean more experience is lost on death)");
		
		if (configFile.hasChanged()) {configFile.save();}
	}
}
