package selim.omniStuff.helmet;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import openmods.utils.ItemUtils;
import openperipheral.addons.api.ITerminalItem;
import openperipheral.addons.api.TerminalRegisterEvent;
import openperipheral.addons.glasses.TerminalUtils;
import selim.omniStuff.ModInfo;
import selim.omniStuff.OmniStuff;
import selim.omniStuff.config.LoadConfig;
import selim.omniStuff.keys.KeyInputHandler;
import selim.omniStuff.things.OmniThing;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;

public class OmniGoggles extends OmniThing implements
	IRevealer, IGoggles, IVisDiscountGear, ITerminalItem {

	private static final String OPENP_TAG = "openp";
	
	public OmniGoggles(int armorPart, String[] modules) {
		super(armorPart, modules);
		
		setUnlocalizedName("omniGoggles");
		setCreativeTab(CreativeTabs.tabCombat);
		setCreativeTab(OmniStuff.omniStuffTab);
		setTextureName(ModInfo.ID + ":" + "goggles/basic");
	}
	
	@Override
	public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type) {
        return ModInfo.ID + ":textures/models/armor/OmniGoggles.png";
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(itemStack, player, list, bool);
		if (super.displayToolip) {
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
            }
		}
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
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
		}
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
}
