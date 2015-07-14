package selim.omniStuff.leggings;

import java.util.List;

import openperipheral.addons.glasses.TerminalUtils;
import cofh.api.energy.IEnergyContainerItem;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import selim.omniStuff.ModInfo;
import selim.omniStuff.OmniStuff;
import selim.omniStuff.config.LoadConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class OmniLeggings extends ItemArmor implements IEnergyContainerItem {

	String[] neiLore = {"Leggings, that help you", "move better? Right?"};
	String[] craftingHelp = {"Put leggings in middle", "slot of crafting table,",
							"then put any compatible", "module anywhere else."};
	
	String[] moduleID = {"potionsModule"};
	
	int rfIn = 1280;
	int rfOut = 640;
	int rfPerModule = 5;
	int maxRfStored = 4000000;
	
	public OmniLeggings() {
		super(OmniStuff.omniArmorMaterial, 0, 2);
		setUnlocalizedName("omniLeggings");
		setCreativeTab(CreativeTabs.tabCombat);
		setCreativeTab(OmniStuff.omniStuffTab);
		setTextureName(ModInfo.ID + ":" + "leggings/basic");
	}

	public int getMaxItemUseDuration() {return -1;}
	
	public int getMaxDamage() {return -1;}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool) {
		if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
			list.add(StringHelper.shiftForDetails());
		}
		if (!StringHelper.isShiftKeyDown()) {
			return;
		}
		if (itemStack.stackTagCompound != null) {
			list.add("Modules:");
            boolean potionsModule = itemStack.stackTagCompound.getBoolean("potionsModule");
            if (potionsModule == true) {
              	list.add(" - Potions Module");
              	list.add("     - Jump Boost");
            }
            boolean step = itemStack.stackTagCompound.getBoolean("stepModule");
            if (step == true) {
            	list.add(" - Step Assist Module");
            }
            int numModules = itemStack.stackTagCompound.getInteger("numModules");
            if (numModules == 0) {
            	list.add(" - No Modules");
            	for (int i = 0; i < craftingHelp.length; i++) {
            		list.add(craftingHelp[i]);
            	}
            }
			int energyStored = this.getEnergyStored(itemStack);
            if (Loader.isModLoaded("ThermalExpansion")) {
            	list.add("Charge: " + energyStored + " / " + maxRfStored + " RF");
            	list.add("Uses " + (numModules * rfPerModule) + " RF/t");
            }
		}
		else {
			for (int i = 0; i < neiLore.length; i++) {
				list.add(neiLore[i]);
			}
		}
    }
	
	@Override
	public int getDisplayDamage(ItemStack stack) {return this.getMaxEnergyStored(stack) - this.getEnergyStored(stack);}
	
	@Override
	public int getMaxDamage(ItemStack stack) {return this.getMaxEnergyStored(stack);}
	
	@Override
	public boolean isDamaged(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		int currentEnergy = this.getEnergyStored(itemStack);
		if (currentEnergy > 0) {
			if (itemStack.stackTagCompound.getBoolean("potionsModule")) {
				player.addPotionEffect(new PotionEffect(Potion.jump.id, 320, 2, true));
			}
			itemStack.stackTagCompound.setInteger("Energy", currentEnergy - 15);
		}
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity p_77663_3_,
			int p_77663_4_, boolean p_77663_5_) {
		if (itemStack.stackTagCompound != null) {
			int numModules = 0;
			for (int i = 0; i < moduleID.length; i++) {
				if (itemStack.stackTagCompound.getBoolean(moduleID[i])) numModules++;
			}
			itemStack.stackTagCompound.setInteger("numModules", numModules);
		}
		else {
			itemStack.stackTagCompound = new NBTTagCompound();
		    itemStack.stackTagCompound.setInteger("numModules", 0);
//		    itemStack.stackTagCompound.setInteger("energy", 0);
		    for (int i = 0; i < moduleID.length; i++) {
		    	itemStack.stackTagCompound.setBoolean(moduleID[i], false);
		    }
		}
	}
	
	/* Thermal Expansion */
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {

		if (container.stackTagCompound == null) {
			container.stackTagCompound = new NBTTagCompound();
		}
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyReceived = Math.min(maxRfStored - energy, Math.min(this.rfIn, maxReceive));

		if (!simulate) {
			energy += energyReceived;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Energy")) {
			return 0;
		}
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyExtracted = Math.min(energy, Math.min(this.rfOut, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		if (container.stackTagCompound != null) {
			int storedEnergy = container.stackTagCompound.getInteger("Energy");
			int numModules = container.stackTagCompound.getInteger("numModules");
		}
		return container.stackTagCompound.getInteger("Energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		// TODO Auto-generated method stub
		return maxRfStored;
	}
}
