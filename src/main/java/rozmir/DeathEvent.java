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
	    
		    float experienceToRemove = player.experienceLevel * KeepInventoryLoseXP.experienceLossMultiplier;
		    
		    
		    if (player.experienceTotal - experienceToRemove <= 0) // If not enough levels or will be negative
	        {
	            player.experienceLevel = 0;
	            player.experience = 0;
	            player.experienceTotal = 0;
	            return;
	        }
	        
	        player.experienceTotal -= experienceToRemove;
	
	        if (player.experience * (float)player.xpBarCap() < experienceToRemove) // Removing experience within current level to floor it to player.experience == 0.0f
	        {
	        	experienceToRemove -= player.experience * (float)player.xpBarCap();
	        	player.experience = 1.0f;
	        	player.experienceLevel--;
	        	
			    int xp_to_drop = EntityXPOrb.getXPSplit((int) experienceToRemove);
			    MinecraftServer.getServer().worldServerForDimension(0).spawnEntityInWorld(new EntityXPOrb(MinecraftServer.getServer().worldServerForDimension(0), player.posX, player.posY, player.posZ, xp_to_drop));
	        }
	
	        while (player.xpBarCap() < experienceToRemove) // Removing whole levels
	        {
	            experienceToRemove -= player.xpBarCap();
	            player.experienceLevel--;
	        }
	        
	        player.experience -= experienceToRemove / (float)player.xpBarCap(); // Removing experience from remaining level
		    
	    }
	}
}
