package selim.omniStuff;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;
import selim.omniStuff.boots.OmniBoots;
import selim.omniStuff.charger.ChargerBlock;
import selim.omniStuff.charger.gui.HandlerGui;
import selim.omniStuff.chestplate.OmniChestplate;
import selim.omniStuff.crafting.MyRecipies;
import selim.omniStuff.helmet.OmniGoggles;
import selim.omniStuff.items.*;
import selim.omniStuff.keys.KeyBindings;
import selim.omniStuff.keys.KeyInputHandler;
import selim.omniStuff.leggings.OmniLeggings;
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
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
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
        		"omniArmorMaterial", -1, new int[]{1, 5, 4, 1}, 0);
        public static final ArmorMaterial omniArmorMaterialTeir2 = EnumHelper.addArmorMaterial(
        		"omniArmorMaterialTier2", -1, new int[]{2, 6, 5, 2}, 0);
        public static final ArmorMaterial omniArmorMaterialTeir3 = EnumHelper.addArmorMaterial(
        		"omniArmorMaterialTier3", -1, new int[]{4, 8, 7, 4}, 0);
        
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
        
        static SimpleNetworkWrapper thing = NetworkRegistry.INSTANCE.newSimpleChannel("omniThings");
        
    	// Register Items
    	public static Item omniGoggles = new OmniGoggles(0,
    			new String[]{"gogglesOfRevealing", "potionsModule", "terminalGlasses"});
    	public static Item omniChestplate = new OmniChestplate();
    	public static Item omniLeggings = new OmniLeggings();
    	public static Item omniBoots = new OmniBoots();
    	
    	public static Item omniTool = new OmniTool();
    	
    	public static Item bindingCatalyst = new BindingCatalyst();
    	public static Item paxelModule = new PaxelModule();
    	public static Item wrenchModule = new WrenchModule();
    	public static Item potionsModule = new PotionsModule();
    	public static Item revealingModule = new RevealingModule();
    	public static Item terminalModule = new TerminalModule();
    	public static Item vanillaUtilsModule = new VanillaUtilsModule();
    	public static Item stepModule = new StepAssistModule();
    	public static Item fallModule = new FallModule();
    	public static Item parachuteModule = new ParachuteModule();
    	
    	public static Item[] allModules = {paxelModule, wrenchModule, potionsModule, revealingModule,
    			revealingModule, terminalModule, vanillaUtilsModule, stepModule, fallModule,
    			parachuteModule
    	};
    	
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
                GameRegistry.registerItem(omniChestplate, "omniChestplate");
                GameRegistry.registerItem(omniLeggings, "omniLeggings");
                GameRegistry.registerItem(omniBoots, "omniBoots");
                
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
                GameRegistry.registerItem(stepModule, "stepModule");
                GameRegistry.registerItem(fallModule, "fallModule");
                GameRegistry.registerItem(parachuteModule,  "parachuteModule");
                
                // Register Blocks
                GameRegistry.registerBlock(charger, "charger");
                
                // Register Recipes
                MyRecipies.addRecipies();
                MyRecipies.addCustomHandlers();
                
                // Register GUI Handler
                NetworkRegistry.INSTANCE.registerGuiHandler(this.instance, new HandlerGui());

                proxy.registerRenderers();
                proxy.registerTileEntities();
        }
        
        @EventHandler
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        }
}