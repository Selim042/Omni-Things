package selim.omniStuff.charger.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import selim.omniStuff.OmniStuff;

public class ModuleInv implements IInventory {

	ItemStack omniThing;
	
	ItemStack[] modules;
	
	Item[] allModules = OmniStuff.allModules;
	
	public ModuleInv(ItemStack thing) {
		omniThing = thing;
	}
	
	void determineSlots() {
		if ((omniThing != null) && (omniThing.stackTagCompound != null)) {
			for (int i = 0; i < this.allModules.length; i++) {
				if (omniThing.stackTagCompound.getBoolean(this.allModules[i].getUnlocalizedName())) {
					this.modules[this.modules.length] = new ItemStack(this.allModules[i]);
				}
			}
		}
	}
	
	@Override
	public int getSizeInventory() {
		return 7;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		determineSlots();
		if (modules != null && modules[slot] != null) {
			return modules[slot];
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return false;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return false;
	}

}
