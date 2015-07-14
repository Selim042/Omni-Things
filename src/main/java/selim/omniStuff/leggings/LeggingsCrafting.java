package selim.omniStuff.leggings;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import selim.omniStuff.OmniStuff;
import cpw.mods.fml.common.registry.GameRegistry;

public class LeggingsCrafting implements IRecipe {
	
	@Override
	public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
		ItemStack referenceStack = new ItemStack(OmniStuff.omniLeggings);
		boolean valid = false;
		ItemStack stack4 = p_77569_1_.getStackInSlot(4);
		if ((stack4 !=null) && (stack4.getItem() == referenceStack.getItem())) {
			valid = true;
		}
		return valid;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting craftingTable) {
		Item[] upgrades = {OmniStuff.potionsModule,
							OmniStuff.stepModule};
		String[] upgradeID = {"potionsModule", "stepModule"};
		
		boolean valid = true;
		ItemStack stack4 = craftingTable.getStackInSlot(4);
		Item stack4Item = stack4.getItem();
		ItemStack result = new ItemStack(OmniStuff.omniLeggings);
		if (stack4Item.equals(OmniStuff.omniLeggings)) {
			ItemStack[] upgradeSlots = {craftingTable.getStackInSlot(0), craftingTable.getStackInSlot(1), craftingTable.getStackInSlot(2),
					craftingTable.getStackInSlot(3), craftingTable.getStackInSlot(5),
					craftingTable.getStackInSlot(6), craftingTable.getStackInSlot(7), craftingTable.getStackInSlot(8)};
			if (stack4.stackTagCompound != null) {
				NBTTagCompound oldNBT = (NBTTagCompound) stack4.stackTagCompound.copy();
				result.stackTagCompound = oldNBT;
			}
			for (int i = 0; i < upgradeSlots.length; i++) {
				for (int u = 0; u < upgrades.length; u++) {
					if ((upgradeSlots[i] != null) && upgrades[u].equals(upgradeSlots[i].getItem())) {
						result.stackTagCompound.setBoolean(upgradeID[u], true);
						result.stackTagCompound.setInteger("numModules", 1);
					}
					else {
						valid = false;
					}
				}
			}
		}
		else {
			result = null;
		}
		return result;
	}

	@Override
	public int getRecipeSize() {
		return 3;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(OmniStuff.omniLeggings);
	}
}
