package selim.omniStuff.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import selim.omniStuff.OmniStuff;
import selim.omniStuff.chestplate.ChestplateCrafting;
import selim.omniStuff.helmet.GoggleCrafting;
import selim.omniStuff.tool.ToolCrafting;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class MyRecipies{
	public static void addRecipies() {

		GameRegistry.addRecipe(new ItemStack(OmniStuff.omniGoggles),
				"lll",
				"lbl",
				"pip",
				'l', new ItemStack(Items.leather),
				'p', new ItemStack(Blocks.glass_pane),
				'i', new ItemStack(Items.iron_ingot),
				'b', new ItemStack(OmniStuff.bindingCatalyst)
		);
		
		GameRegistry.addRecipe(new ItemStack(OmniStuff.omniTool),
				"dpd",
				"btb",
				"dsd",
				'd', new ItemStack(Items.diamond),
				'p', new ItemStack(Items.diamond_pickaxe),
				'b', new ItemStack(OmniStuff.bindingCatalyst),
				's', new ItemStack(Items.diamond_sword),
				't', new ItemStack(Items.stick)
		);
		
		GameRegistry.addRecipe(new ItemStack(OmniStuff.potionsModule),
				" g ",
				"sbm",
				"tcf",
				'g', new ItemStack(Items.golden_carrot),
				's', new ItemStack(Items.sugar),
				'b', new ItemStack(OmniStuff.bindingCatalyst),
				'm', new ItemStack(Items.speckled_melon),
				't', new ItemStack(Items.ghast_tear),
				'c', new ItemStack(Items.magma_cream),
				'f', new ItemStack(Items.fermented_spider_eye)
		);
		
		GameRegistry.addRecipe(new ItemStack(OmniStuff.paxelModule),
				"pbs",
				"bhb",
				"wba",
				'p', new ItemStack(Items.diamond_pickaxe),
				's', new ItemStack(Items.diamond_shovel),
				'b', new ItemStack(OmniStuff.bindingCatalyst),
				'a', new ItemStack(Items.diamond_axe),
				'h', new ItemStack(Items.diamond_hoe),
				'w', new ItemStack(Items.diamond_sword)
		);
		
		if (Loader.isModLoaded("Thaumcraft")) {
			GameRegistry.addRecipe(new ItemStack(OmniStuff.revealingModule),
					"bbb",
					"bgb",
					"bbb",
					'g', new ItemStack(GameRegistry.findItem("Thaumcraft", "ItemGoggles")),
					'b', new ItemStack(OmniStuff.bindingCatalyst)
			);
		}
		
		if (Loader.isModLoaded("OpenPeripheral")) {
			GameRegistry.addRecipe(new ItemStack(OmniStuff.terminalModule),
					"bbb",
					"btb",
					"bbb",
					'b', new ItemStack(OmniStuff.bindingCatalyst),
					't', new ItemStack(GameRegistry.findItem("OpenPeripheral", "glasses"))
			);
		}
		
		if (Loader.isModLoaded("ThermalExpansion") || Loader.isModLoaded("PneumaticCraft")) {
			GameRegistry.addRecipe(new ItemStack(OmniStuff.wrenchModule),
					"   ",
					"tbp",
					"   ",
					't', new ItemStack(GameRegistry.findItem("ThermalExpansion", "wrench")),
					'p', new ItemStack(GameRegistry.findItem("PneumaticCraft", "pneumaticWrench")),
					'b', new ItemStack(OmniStuff.bindingCatalyst)
			);
		}
		else if (Loader.isModLoaded("BuildCraft")) {
			GameRegistry.addRecipe(new ItemStack(OmniStuff.wrenchModule),
					"did",
					"ibi",
					"did",
					'd', new ItemStack(Items.diamond),
					'i', new ItemStack(Items.iron_ingot),
					'b', new ItemStack(OmniStuff.bindingCatalyst)
			);
		}
		
		GameRegistry.addShapelessRecipe(new ItemStack(OmniStuff.bindingCatalyst, 2),
				new ItemStack(Items.diamond), new ItemStack(Items.slime_ball),
				new ItemStack(Items.redstone), new ItemStack(Items.redstone)
		);
	}
	
	public static void addCustomHandlers() {
		GameRegistry.addRecipe(new GoggleCrafting());
		GameRegistry.addRecipe(new ToolCrafting());
		//GameRegistry.addRecipe(new ChestplateCrafting());
	}
}
