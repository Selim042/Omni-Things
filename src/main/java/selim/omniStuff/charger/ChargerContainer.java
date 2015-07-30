package selim.omniStuff.charger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import cofh.lib.gui.slot.SlotEnergy;

public class ChargerContainer extends Container {

	public ChargerContainer(InventoryPlayer parInventoryPlayer, 
          IInventory inv) {
		this.addSlotToContainer(new ThingSlot(inv, 0, 147, 13));
		this.addSlotToContainer(new SlotEnergy(inv, 1, 8, 52));
		
		// add module slots
		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(inv, i + 2, 44 + (i * 18), 52));
		}
		
		// add player inventory slots
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(parInventoryPlayer, j+i*9+9, 
                8+j*18, 84+i*18));
            }
        }

        // add hotbar slots
        for (i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(parInventoryPlayer, i, 8 + i * 18, 
            142));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
}
