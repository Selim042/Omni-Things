package selim.omniStuff.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import selim.omniStuff.charger.ChargerEntity;
import selim.omniStuff.charger.GuiBasic;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;


public class CommonProxy implements IGuiHandler {
	
	public void registerRenderers() {
		
	}
	
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(ChargerEntity.class, "charger");
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == GuiBasic.GUI_ID)
			return new GuiBasic();
		// TODO Auto-generated method stub
		return null;
	}
}
