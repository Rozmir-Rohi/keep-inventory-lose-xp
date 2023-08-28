package rozmir;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class DeathEvent {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	  public void onDeathEvent(LivingDeathEvent event) {
	    if (event.entityLiving instanceof EntityPlayer)
	    {
	    	EntityPlayer player = (EntityPlayer)event.entityLiving;
	    
		    float xp_to_remove = player.experienceLevel * 7;
		    
		    
		    if (player.experienceTotal - xp_to_remove <= 0) // If not enough levels or will be negative
	        {
	            player.experienceLevel = 0;
	            player.experience = 0;
	            player.experienceTotal = 0;
	            return;
	        }
	        
	        player.experienceTotal -= xp_to_remove;
	
	        if (player.experience * (float)player.xpBarCap() < xp_to_remove) // Removing experience within current level to floor it to player.experience == 0.0f
	        {
	        	xp_to_remove -= player.experience * (float)player.xpBarCap();
	        	player.experience = 1.0f;
	        	player.experienceLevel--;
	        	
			    int xp_to_drop = EntityXPOrb.getXPSplit((int) xp_to_remove);
			    MinecraftServer.getServer().worldServerForDimension(0).spawnEntityInWorld(new EntityXPOrb(MinecraftServer.getServer().worldServerForDimension(0), player.posX, player.posY, player.posZ, xp_to_drop));
	        }
	
	        while (player.xpBarCap() < xp_to_remove) // Removing whole levels
	        {
	            xp_to_remove -= player.xpBarCap();
	            player.experienceLevel--;
	        }
	        
	        player.experience -= xp_to_remove / (float)player.xpBarCap(); // Removing experience from remaining level
		    
	    }
	}
}
