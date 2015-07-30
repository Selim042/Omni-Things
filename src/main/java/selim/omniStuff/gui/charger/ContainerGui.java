package selim.omniStuff.gui.charger;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import selim.omniStuff.ModInfo;
import selim.omniStuff.charger.ChargerContainer;

public class ContainerGui extends GuiContainer {

	private static final ResourceLocation guiTexture = new ResourceLocation(ModInfo.ID + ":textures/gui/charger.png");
	
	private final InventoryPlayer inventoryPlayer;
    private final IInventory tileGrinder;
	
	public ContainerGui(InventoryPlayer parInventoryPlayer, 
			IInventory parInventoryGrinder) {
		super(new ChargerContainer(parInventoryPlayer, 
				parInventoryGrinder));
		inventoryPlayer = parInventoryPlayer;
		tileGrinder = parInventoryGrinder;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString("Omni-Thing Charger", 35, 5, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
		//GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(guiTexture);
		int marginHorizontal = (width - xSize) / 2;
		int marginVertical = (height - ySize) / 2;
		drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, 
				xSize, ySize);
	}

}
