package selim.omniStuff.tool;

import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import selim.omniStuff.ModInfo;
import selim.omniStuff.OmniStuff;
import buildcraft.api.tools.IToolWrench;
import cofh.api.item.IMultiModeItem;
import cofh.api.item.IToolHammer;
import cofh.lib.util.helpers.StringHelper;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class OmniTool extends ItemPickaxe implements
		IToolHammer, IToolWrench, IMultiModeItem {

	String[] neiLore = {"One tool, very customizable"};
	String[] craftingHelp = {"Put tool in middle", "slot of crafting table,",
							"then put any compatible", "module anywhere else."};
	
	String[] moduleID = {"potionsModule", "paxelModule", "wrenchModule"};
	String[] enableableModules = {"paxelModule", "wrenchModule"};
	
	public OmniTool() {
		super(OmniStuff.omniToolMaterial);
		setUnlocalizedName("omniTool");
		setTextureName(ModInfo.ID + ":tool/main");
		setCreativeTab(OmniStuff.omniStuffTab);
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
            boolean nightVision = itemStack.stackTagCompound.getBoolean("potionsModule");
            if (nightVision == true) {
              	list.add(" - Potions Module");
              	list.add("     - Haste");
              	list.add("     - Strength");
            }
            boolean paxelModule = itemStack.stackTagCompound.getBoolean("paxelModule");
            if (paxelModule) {
            	list.add(" - Paxel Module");
            }
            boolean wrenchModule = itemStack.stackTagCompound.getBoolean("wrenchModule");
            if (wrenchModule) {
            	list.add(" - Wrench Module");
            }
            boolean vanillaUtilsModule = itemStack.stackTagCompound.getBoolean("vanillaUtilsModule");
            if (vanillaUtilsModule) {
            	list.add(" - Vanilla Utils Module");
            }
            int numModules = itemStack.stackTagCompound.getInteger("numModules");
            if (numModules == 0) {
            	list.add(" - No Modules");
            	for (int i = 0; i < craftingHelp.length; i++) {
            		list.add(craftingHelp[i]);
            	}
            }
            list.add("Current mode: " + this.getMode(itemStack));
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
		
	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		return ImmutableSet.of("pickaxe", "spade", "axe");
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
		    for (int i = 0; i < moduleID.length; i++) {
		    	itemStack.stackTagCompound.setBoolean(moduleID[i], false);
		    }
		}
		
		boolean potionsModule = itemStack.stackTagCompound.getBoolean("potionsModule");
		if (potionsModule) {
			EntityPlayer player = (EntityPlayer) p_77663_3_;
			if (player.getCurrentEquippedItem() !=null && (player.getCurrentEquippedItem().getItem().equals(OmniStuff.omniTool))) {
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 320, 0, true));
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 320, 0, true));
			}
		}
		
		/*if (!world.isRemote) {
			boolean changed = false;
			if (KeyBindings.switchToolMode.isPressed()) {
				int enabledModule = itemStack.stackTagCompound.getInteger("currentEnabled");
				for (int i = 0; i < enableableModules.length; i++) {
					if (itemStack.stackTagCompound.getBoolean(enableableModules[i])) {
						enabledModule++;
						changed = true;
					}
				}
				if (!changed) {
					enabledModule--;
				}
				itemStack.stackTagCompound.setInteger("currentEnabled", enabledModule);
			}
		} */
	}
	
	private static Set effectiveAgainst = Sets.newHashSet(new Block[] {
		    Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, 
		    Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, 
		    Blocks.soul_sand, Blocks.mycelium}
	);
	
	@Override
	public float func_150893_a(ItemStack stack, Block block) {
		boolean paxelModule = stack.stackTagCompound.getBoolean("paxelModule");
        if (paxelModule) {
        	if (block.getMaterial() == Material.wood || block.getMaterial() == Material.vine || block.getMaterial() == Material.plants)
        		return this.efficiencyOnProperMaterial;
        	return effectiveAgainst.contains(block) ? this.efficiencyOnProperMaterial : super.func_150893_a(stack, block);
        }
        else {
        	return 1;
        }
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ) {
		int sneak = Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode();
		boolean result = false;
		
		if (stack.stackTagCompound != null) {
			boolean paxelModule = stack.stackTagCompound.getBoolean("paxelModule");
			if (paxelModule) {
				if (!player.canPlayerEdit(x, y, z, side, stack)) {
					result = false;
				}
				else {
					UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
					if (MinecraftForge.EVENT_BUS.post(event)) {
						result = false;
					}
					
					if (event.getResult() == Result.ALLOW) {
						stack.damageItem(1, player);
						result =  true;
					}
					
					Block block = world.getBlock(x, y, z);
					
					if (side != 0 && world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z) && (block == Blocks.grass || block == Blocks.dirt)) {
						Block block1 = Blocks.farmland;
						world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);
						
						if (world.isRemote) {
							result =  true;
						} else {
							world.setBlock(x, y, z, block1);
							stack.damageItem(1, player);
							result =  true;
						}
					} else {
						result =  false;
					}
				}
			}
		}
	    return result;
	}

	/* Wrench Module */
	  /* Thermal Expansion */
	@Override
	public boolean isUsable(ItemStack itemStack, EntityLivingBase user, int x,
			int y, int z) {
		boolean wrenchModule = itemStack.stackTagCompound.getBoolean("wrenchModule");
		if (wrenchModule) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, int x, int y,
			int z) {
		user.swingItem();
	}

	  /* Buildcraft */
	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z) {
		boolean wrenchModule = player.getHeldItem().stackTagCompound.getBoolean("wrenchModule");
		if (wrenchModule) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
		player.swingItem();
	}

	/* IMultiModeItem */
	@Override
	public int getMode(ItemStack stack) {return stack.stackTagCompound.getInteger("currentMode");}

	@Override
	public boolean setMode(ItemStack stack, int mode) {
		stack.stackTagCompound.setInteger("currentMode", mode);
		return true;
	}

	@Override
	public boolean incrMode(ItemStack stack) {
		int currentMode = stack.stackTagCompound.getInteger("currentMode");
		if (currentMode < stack.stackTagCompound.getInteger("numModes")) {
			stack.stackTagCompound.setInteger("currentMode", currentMode + 1);
		}
		else {
			stack.stackTagCompound.setInteger("currentMode", 1);
		}
		return true;
	}

	@Override
	public boolean decrMode(ItemStack stack) {
		int currentMode = stack.stackTagCompound.getInteger("currentMode");
		int numModes = stack.stackTagCompound.getInteger("numModes");
		if (currentMode > 1) {
			stack.stackTagCompound.setInteger("currentMode", currentMode - 1);
		}
		else {
			stack.stackTagCompound.setInteger("currentMode", numModes);
		}
		return true;
	}

	@Override
	public int getNumModes(ItemStack stack) {return stack.stackTagCompound.getInteger("numModes");}

	@Override
	public void onModeChange(EntityPlayer player, ItemStack stack) {
		player.worldObj.playSoundAtEntity(player, "ambient.weather.thunder", 0.4F, 1.0F);
	}
}
