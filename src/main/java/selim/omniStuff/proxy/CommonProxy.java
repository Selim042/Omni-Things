package selim.omniStuff.proxy;

import selim.omniStuff.charger.ChargerEntity;
import cpw.mods.fml.common.registry.GameRegistry;


public class CommonProxy {
	
	public void registerRenderers() {
		
	}
	
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(ChargerEntity.class, "charger");
	}
}
