package selim.omniStuff.items;

import java.util.List;

import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import selim.omniStuff.ModInfo;
import selim.omniStuff.OmniStuff;

public class RevealingModule extends Item {
	
	public RevealingModule() {
		setUnlocalizedName("revealingModule");
		setTextureName(ModInfo.ID + ":RevealingModule");
		setCreativeTab(OmniStuff.omniStuffTab);
	}
	
	String[] neiLore = {"Used on the 'Omni-Goggles'.", "Makes the 'Omni-Goggles' act as",
			"Thaumcraft's Goggles of Revealing."};
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool) {
		if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
			list.add(StringHelper.shiftForDetails());
		}
		if (!StringHelper.isShiftKeyDown()) {
			return;
		}
		for (int i = 0; i < neiLore.length; i++) {
			list.add(neiLore[i]);
		}
	}
}
