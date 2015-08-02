package selim.omniStuff.charger.gui;

import selim.omniStuff.things.OmniThing;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ThingSlot extends Slot {
	
	public ThingSlot(IInventory inv, int slotIndex, int xDispPos,
			int yDispPos) {
		super(inv, slotIndex, xDispPos, yDispPos);
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		if (itemStack.getItem() instanceof OmniThing) {
			return true;
		}
		return false;
	}
}
