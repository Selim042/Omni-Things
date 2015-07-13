package selim.omniStuff.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import selim.omniStuff.OmniStuff;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class OmniCrafting implements IRecipe {

	ItemStack omniPart = null;
	ItemStack module = null;
	String nbtTag = null;
	
	public OmniCrafting(ItemStack omniPartF, ItemStack moduleF, String nbtTagF) {
		omniPart = omniPartF;
		module = moduleF;
		nbtTag = nbtTagF;
	}
	
	@Override
	public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
		ItemStack referenceStack = omniPart;
		boolean valid = false;
		ItemStack stack4 = p_77569_1_.getStackInSlot(4);
		if ((stack4 !=null) && (stack4.getItem() == referenceStack.getItem())) {
			valid = true;
		}
		return valid;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting craftingTable) {
		boolean valid = true;
		ItemStack stack4 = craftingTable.getStackInSlot(4);
		Item stack4Item = stack4.getItem();
		ItemStack result = omniPart;
		if (stack4Item.equals(omniPart.getItem())) {
			System.out.println("item matches");
			ItemStack[] upgradeSlots = {craftingTable.getStackInSlot(0), craftingTable.getStackInSlot(1), craftingTable.getStackInSlot(2),
					craftingTable.getStackInSlot(3), craftingTable.getStackInSlot(5),
					craftingTable.getStackInSlot(6), craftingTable.getStackInSlot(7), craftingTable.getStackInSlot(8)};
			if (stack4.stackTagCompound != null) {
				System.out.println("nbt exists");
				NBTTagCompound oldNBT = (NBTTagCompound) stack4.stackTagCompound.copy();
				result.stackTagCompound = oldNBT;
			}
			for (int i = 0; i < upgradeSlots.length; i++) {
				if ((upgradeSlots[i] != null) && module.getItem().equals(upgradeSlots[i].getItem())) {
					System.out.println("module matches");
					result.stackTagCompound.setBoolean(nbtTag, true);
				}
				else {
					valid = false;
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
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return omniPart;
	}
}
