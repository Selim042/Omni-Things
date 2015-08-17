package selim.omniStuff.charger.gui;

import cofh.lib.util.helpers.StringHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import selim.omniStuff.ModInfo;
import selim.omniStuff.charger.ChargerContainer;

public class ContainerGui extends GuiContainer {

	protected int xSize = 176;
	protected int ySize = 166;
	
	private static final ResourceLocation guiTexture = new ResourceLocation(ModInfo.ID + ":textures/gui/charger.png");
	
	private final InventoryPlayer inventoryPlayer;
    private final TileEntity tileGrinder;
	
	public ContainerGui(InventoryPlayer parInventoryPlayer, 
			TileEntity inventoryCharger) {
		super(new ChargerContainer(parInventoryPlayer, 
				(IInventory) inventoryCharger));
		inventoryPlayer = parInventoryPlayer;
		tileGrinder = inventoryCharger;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(StatCollector.translateToLocal("tile.charger.name"), 35, 6, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
		//GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(guiTexture);
		int marginHorizontal = (width - xSize) / 2;
		int marginVertical = (height - ySize) / 2;
		drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, xSize, ySize);
	}

}
