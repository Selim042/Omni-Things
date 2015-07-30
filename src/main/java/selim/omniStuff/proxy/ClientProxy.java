package selim.omniStuff.proxy;

import cpw.mods.fml.common.network.NetworkRegistry;


public class ClientProxy extends CommonProxy {
        
        @Override
        public void registerRenderers() {
                // This is for rendering entities and so forth later on
        	//MinecraftForgeClient.registerItemRenderer(OmniStuff.omniGoggles, new GoggleRender());

        }
}