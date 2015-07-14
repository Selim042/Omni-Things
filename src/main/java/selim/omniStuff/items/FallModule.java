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

public class FallModule extends Item {
	
	public FallModule() {
		setUnlocalizedName("fallModule");
		setTextureName(ModInfo.ID + ":FallModule");
		setCreativeTab(OmniStuff.omniStuffTab);
	}
	
	String[] neiLore = {"Used on the 'Omni-Boots'.", "Gives the user to fall",
			"further with less damage."};
	
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
