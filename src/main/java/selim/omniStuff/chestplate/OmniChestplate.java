package selim.omniStuff.chestplate;

import java.util.List;

import openperipheral.addons.glasses.TerminalUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import selim.omniStuff.ModInfo;
import selim.omniStuff.OmniStuff;
import selim.omniStuff.config.LoadConfig;

public class OmniChestplate extends ItemArmor {

	//flight, flux pack, glider, backpack
	
	String[] moduleID = {"potionsModule", "flight"};
	
	public OmniChestplate() {
		super(OmniStuff.omniArmorMaterial, 0, 1);
		setUnlocalizedName("omniChestplate");
		setCreativeTab(CreativeTabs.tabCombat);
		setCreativeTab(OmniStuff.omniStuffTab);
		setTextureName(ModInfo.ID + ":" + "OmniChestplate");
	}
	
	String[] neiLore = {"Chestplate"};
	String[] craftingHelp = {"Put chestplate in middle", "slot of crafting table,",
							"then put any compatible", "module anywhere else."};
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool) {
		if (itemStack.stackTagCompound != null) {
			list.add("Modules:");
			boolean flight = itemStack.stackTagCompound.getBoolean("flight");
            if ((flight == true) && (LoadConfig.enableGogglesOfRevealing)) {
            	list.add(" - Flight");
            }
            int numModules = itemStack.stackTagCompound.getInteger("numModules");
            if (numModules == 0) {
            	list.add(" - No Modules");
            	for (int i = 0; i < craftingHelp.length; i++) {
            		list.add(craftingHelp[i]);
            	}
            }
/*			int energyStored = itemStack.stackTagCompound.getInteger("energy");
            if (Loader.isModLoaded("ThermalExpansion")) {
            	list.add("Charge: " + energyStored + "/" + maxRfStored + " RF");
            	list.add("Uses " + (numModules * rfPerModule) + " RF/t");
            } */
		}
		else {
			for (int i = 0; i < neiLore.length; i++) {
				list.add(neiLore[i]);
			}
		}
    }
	
	public int getMaxItemUseDuration() {return -1;}
	
	public int getMaxDamage() {return -1;}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if (player.getCurrentArmor(2).getItem().equals(OmniStuff.omniChestplate)) {
			
		}
		else {
			
		}
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity,
			int p_77663_4_, boolean p_77663_5_) {
		entity.fallDistance = 0;
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
		    for (int i = 0; i < moduleID.length; i++) {
		    	itemStack.stackTagCompound.setBoolean(moduleID[i], false);
		    }
		}
	}
}
