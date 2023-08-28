package rozmir;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;




public class SetKeepInventoryTrue {
	@SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		
		GameRules gameRules = MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
    	
		if (gameRules.getGameRuleBooleanValue("keepInventory") == false) {
			
			gameRules.setOrCreateGameRule("keepInventory", "true");
		}
	}
}
