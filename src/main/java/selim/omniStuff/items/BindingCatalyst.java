package selim.omniStuff.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import selim.omniStuff.ModInfo;
import selim.omniStuff.OmniStuff;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BindingCatalyst extends Item {
	public BindingCatalyst() {
		setUnlocalizedName("bindingCatalyst");
		setTextureName(ModInfo.ID + ":BindingCatalyst");
		setCreativeTab(OmniStuff.omniStuffTab);
	}
	
	String[] neiLore = {"Used to craft 'Omni-Stuff'",
			"and various modules.", "You may want a lot of these"};
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool) {
		list.add("Crafting ingredient.");
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
