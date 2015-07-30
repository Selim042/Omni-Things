package selim.omniStuff.helmet;

import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import cofh.lib.util.helpers.StringHelper;
import openmods.utils.ItemUtils;
import openperipheral.addons.api.ITerminalItem;
import openperipheral.addons.api.TerminalRegisterEvent;
import openperipheral.addons.glasses.TerminalUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import selim.omniStuff.ModInfo;
import selim.omniStuff.OmniStuff;
import selim.omniStuff.config.LoadConfig;
import selim.omniStuff.keys.KeyInputHandler;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class OldOmniGoggles extends ItemArmor implements
	IRevealer, IGoggles, IVisDiscountGear, ITerminalItem,
	IEnergyContainerItem {
	
	String[] neiLore = {"One helmet with many", "HUDs from many mods."};
	String[] craftingHelp = {"Put goggles in middle", "slot of crafting table,",
							"then put any compatible", "module anywhere else."};
	
	String[] moduleID = {"gogglesOfRevealing", "potionsModule", "terminalGlasses"};
	String[] standardRfModule = {/*"gogglesOfRevealing", */"potionsModule", "terminalGlasses"};
	
	int rfIn = 1280;
	int rfOut = 640;
	int rfPerModule = 5;
	int maxRfStored = 4000000;
	
	public OldOmniGoggles() {
		super(OmniStuff.omniArmorMaterial, 0, 0);
		setUnlocalizedName("omniGoggles");
		setCreativeTab(CreativeTabs.tabCombat);
		setCreativeTab(OmniStuff.omniStuffTab);
		setTextureName(ModInfo.ID + ":" + "goggles/basic");
	}
	
	private static final String OPENP_TAG = "openp";
	
	@Override
	public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type) {
        return ModInfo.ID + ":textures/models/armor/OmniGoggles.png";
    }

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
			boolean gogglesOfRevealing = itemStack.stackTagCompound.getBoolean("gogglesOfRevealing");
            if ((gogglesOfRevealing == true) && (LoadConfig.enableGogglesOfRevealing)) {
            	list.add(" - Goggles of Revealing");
            	list.add("     " + EnumChatFormatting.DARK_PURPLE + "Vis discount: 5%");
            }
            boolean terminalGlasses = itemStack.stackTagCompound.getBoolean("terminalGlasses");
            if ((terminalGlasses == true) && (LoadConfig.enableTerminalGlasses)) {
            	list.add(" - Terminal Glasses");
            	Long guid = extractGuid(itemStack);
            	if (guid != null) list.add("     " + StatCollector.translateToLocalFormatted("openperipheral.misc.key", TerminalUtils.formatTerminalId(guid)));
            }
            boolean nightVision = itemStack.stackTagCompound.getBoolean("potionsModule");
            if (nightVision == true) {
              	list.add(" - Potions Module");
              	list.add("     - Night Vision");
              	list.add("     - Water Breathing");
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
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		return false;
	}
	
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		if (itemStack.stackTagCompound == null) {
			itemStack.stackTagCompound = new NBTTagCompound();
		}
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		int currentEnergy = this.getEnergyStored(itemStack);
		if (currentEnergy > 0) {
			if (player instanceof EntityPlayerMP && itemStack.stackTagCompound.getBoolean("terminalGlasses")) {
				Long guid = extractGuid(itemStack);
				if (guid != null) MinecraftForge.EVENT_BUS.post(new TerminalRegisterEvent((EntityPlayerMP)player, guid));
			}
			if (itemStack.stackTagCompound.getBoolean("potionsModule") && KeyInputHandler.nightVisionToggle) {
				player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 320, 0, true));
			}
			if (itemStack.stackTagCompound.getBoolean("potionsModule")) {
				player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 320, 0, true));
			}
			if (!player.capabilities.isCreativeMode) {
				itemStack.stackTagCompound.setInteger("Energy", currentEnergy - itemStack.stackTagCompound.getInteger("numModules") * rfPerModule);
			}
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
		    itemStack.stackTagCompound.setInteger("Energy", 0);
		    for (int i = 0; i < moduleID.length; i++) {
		    	itemStack.stackTagCompound.setBoolean(moduleID[i], false);
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
	
	public int getDamageReductionAmount(int p_78044_1_) {
		return p_78044_1_;
    }
	
    /* Thaumcraft */
	@Override
	public int getVisDiscount(ItemStack stack, EntityPlayer player,
			Aspect aspect) {
		int currentEnergy = this.getEnergyStored(stack);
		if (currentEnergy > 0) {
			if (stack.stackTagCompound != null && stack.stackTagCompound.getBoolean("gogglesOfRevealing")) {
				return 5;
			}
		}
		return 0;
	}

	@Override
	public boolean showIngamePopups(ItemStack stack, EntityLivingBase player) {
		int currentEnergy = this.getEnergyStored(stack);
		if (currentEnergy > 0) {
			if (stack.stackTagCompound != null && stack.stackTagCompound.getBoolean("gogglesOfRevealing")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean showNodes(ItemStack stack, EntityLivingBase player) {
		int currentEnergy = this.getEnergyStored(stack);
		if (currentEnergy > 0) {
			if (stack.stackTagCompound != null && stack.stackTagCompound.getBoolean("gogglesOfRevealing")) {
				return true;
			}
		}
		return false;
	}

	/* OpenPeripherals */
	@Override
	public Long getTerminalGuid(ItemStack stack) {
		return extractGuid(stack);
	}

	@Override
	public void bindToTerminal(ItemStack stack, long guid) {
		NBTTagCompound tag = ItemUtils.getItemTag(stack);

		NBTTagCompound openPTag = (NBTTagCompound)tag.getTag(OPENP_TAG);
		if (openPTag == null) {
			openPTag = new NBTTagCompound();
			tag.setTag(OPENP_TAG, openPTag);
		}

		openPTag.setLong("guid", guid);
	}
	
	private static Long extractGuid(ItemStack stack) {
		NBTTagCompound tag = ItemUtils.getItemTag(stack);
		if (!tag.hasKey(OPENP_TAG)) return null;

		NBTTagCompound openp = tag.getCompoundTag(OPENP_TAG);
		return TerminalUtils.extractGuid(openp);
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
