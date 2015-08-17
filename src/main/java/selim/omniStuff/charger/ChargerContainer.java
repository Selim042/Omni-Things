package selim.omniStuff.charger;

import selim.omniStuff.charger.gui.ModuleInv;
import selim.omniStuff.charger.gui.ThingSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.slot.SlotEnergy;

public class ChargerContainer extends Container {

	protected ChargerEntity tileEntity;
	
	public ChargerContainer(InventoryPlayer invPlayer, 
          IInventory inventoryCharger) {
		ModuleInv moduleInv = new ModuleInv(inventoryCharger.getStackInSlot(0));
		
		this.addSlotToContainer(new ThingSlot(inventoryCharger, 0, 147, 13));
		this.addSlotToContainer(new SlotEnergy(inventoryCharger, 1, 8, 52));
		
		// add module slots
		for (int i = 0; i < 7; i++) {
			this.addSlotToContainer(new Slot(moduleInv, i + 2, 44 + (i * 18), 52));
		}
		
		bindPlayerInventory(invPlayer);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
        
        for (int i = 0; i < 9; i++) {
        	addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		return null;
	}
}
