package selim.omniStuff;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;
import selim.omniStuff.charger.ChargerBlock;
import selim.omniStuff.chestplate.OmniChestplate;
import selim.omniStuff.crafting.MyRecipies;
import selim.omniStuff.helmet.OmniGoggles;
import selim.omniStuff.items.BindingCatalyst;
import selim.omniStuff.items.PaxelModule;
import selim.omniStuff.items.PotionsModule;
import selim.omniStuff.items.RevealingModule;
import selim.omniStuff.items.TerminalModule;
import selim.omniStuff.items.VanillaUtilsModule;
import selim.omniStuff.items.WrenchModule;
import selim.omniStuff.keys.KeyBindings;
import selim.omniStuff.keys.KeyInputHandler;
import selim.omniStuff.proxy.CommonProxy;
import selim.omniStuff.tool.OmniTool;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid=ModInfo.ID, name=ModInfo.NAME, version=ModInfo.VERSION, dependencies=ModInfo.DEPENDENCIES)
public class OmniStuff {

        @Instance(value = ModInfo.ID)
        public static OmniStuff instance;
        @SidedProxy(clientSide=ModInfo.PROXY_CLIENT, serverSide=ModInfo.PROXY_SERVER)
        public static CommonProxy proxy;
        
        public static final ArmorMaterial omniArmorMaterial = EnumHelper.addArmorMaterial(
        		"omniArmorMaterial", -1, new int[]{2, 6, 5, 2}, 5);
        public static final ArmorMaterial protectiveOmniArmorMaterial = EnumHelper.addArmorMaterial(
        		"protectiveOmniArmorMaterial", -1, new int[]{4, 8, 7, 4}, 10);
        
        public static final ToolMaterial omniToolMaterial = EnumHelper.addToolMaterial(
        		"omniToolMaterial", 10, -1, 15.0F, 10.0F, 15);
        
        public static CreativeTabs omniStuffTab = new CreativeTabs("omniStuffTab") {
        	@Override
        	@SideOnly(Side.CLIENT)
        	public Item getTabIconItem() {
        		return omniGoggles;
        	}
        };
        
        private static int modGuiIndex = 0;
        public static final int ItemInventoryGuiIndex = modGuiIndex++;
        
    	// Register Items
    	public static Item omniGoggles = new OmniGoggles();
    	public static Item omniChestplate = new OmniChestplate();
    	
    	public static Item omniTool = new OmniTool();
    	
    	public static Item bindingCatalyst = new BindingCatalyst();
    	public static Item paxelModule = new PaxelModule();
    	public static Item wrenchModule = new WrenchModule();
    	public static Item potionsModule = new PotionsModule();
    	public static Item revealingModule = new RevealingModule();
    	public static Item terminalModule = new TerminalModule();
    	public static Item vanillaUtilsModule = new VanillaUtilsModule();
    	
    	// Register Blocks
    	public static Block charger = new ChargerBlock();
    	
        @EventHandler
        public void preInit(FMLPreInitializationEvent event) {
        	
        	// not currently funtional
        	//Config.init(event.getSuggestedConfigurationFile());
        	
        	// Register Keybinds
        	FMLCommonHandler.instance().bus().register(new KeyInputHandler());
        	if (FMLCommonHandler.instance().getSide().isClient()) {
        		KeyBindings.init();
        	}
        }
        
        @EventHandler
        public void load(FMLInitializationEvent event) {
        	// Version Checker
        	NBTTagCompound modData = new NBTTagCompound();
        	modData.setString("curseProjectName", "231468-omni-things");
        	modData.setString("curseFilenameParser", "OmniThings-[].jar");
        	modData.setString("modDisplayName", "Omni-Things");
        	FMLInterModComms.sendRuntimeMessage(ModInfo.ID, "VersionChecker", "addCurseCheck", modData);
        	
        	
                proxy.registerRenderers();
                
                // Register Items
                GameRegistry.registerItem(omniGoggles, "omniGoggles");
                //GameRegistry.registerItem(omniChestplate, "omniChestplate");
                
                GameRegistry.registerItem(omniTool, "omniTool");
                
                GameRegistry.registerItem(bindingCatalyst, "bindingCatalyst");
                GameRegistry.registerItem(paxelModule, "paxelModule");
                GameRegistry.registerItem(potionsModule, "potionsModule");
                if (Loader.isModLoaded("ThermalExpansion") || Loader.isModLoaded("BuildCraft:Core") || Loader.isModLoaded("PneumaticCraft")) {
                	GameRegistry.registerItem(wrenchModule, "wrenchModule");
                }
                if (Loader.isModLoaded("Thaumcraft")) {
                	GameRegistry.registerItem(revealingModule, "revealingModule");
                }
                if (Loader.isModLoaded("OpenPeripheral")) {
                	GameRegistry.registerItem(terminalModule, "terminalModule");
                }
//                GameRegistry.registerItem(vanillaUtilsModule, "vanillaUtilsModule");
                
                // Register Blocks
                //GameRegistry.registerBlock(charger, "charger");
                
                // Register Recipes
                MyRecipies.addRecipies();
                MyRecipies.addCustomHandlers();
                
                // no renderers or entities to register, but whatever

                proxy.registerRenderers();
                proxy.registerTileEntities();
        }
        
        @EventHandler
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        }
}