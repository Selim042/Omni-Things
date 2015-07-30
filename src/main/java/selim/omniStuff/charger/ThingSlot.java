package selim.omniStuff.charger;

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
		return true;
	}
}
