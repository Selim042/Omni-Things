package selim.omniStuff.charger;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.TileEnergyHandler;

public class ChargerEntity extends TileEnergyHandler implements IInventory, /*IManaReceiver, */IEssentiaTransport {

	int rfRate = 10;
	int essentiaRate = 15;
	int manaRate = 5;
	
	protected EnergyStorage storage = new EnergyStorage(32000);
	int maxRfRecieve = 800;

    private ItemStack storedItem;

    private ArrayList aList = new ArrayList();
    
    public EnergyStorage getStorage() {
    	return storage;
    }
    
    @Override
	public void readFromNBT(NBTTagCompound nbt) {

		super.readFromNBT(nbt);
		storage.readFromNBT(nbt);
		
		// ItemStack
		this.storedItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("storedItem"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);
		storage.writeToNBT(nbt);
		
		// ItemStack
		if (this.storedItem != null) {
			this.storedItem.writeToNBT(nbt);
			nbt.setTag("storedItem", nbt);
		}
	}
	
	@Override
	public void updateEntity() {
		if (!this.getWorldObj().isRemote) {
			//System.out.println(storage.getEnergyStored());
		}
	}
	
	/* IEnergyConnection */
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	/* IEnergyReceiver */
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive * rfRate, simulate);
	}

	/* IEnergyProvider */
	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}

	/* IEnergyReceiver and IEnergyProvider */
	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}

	/* IManaReceiver */
/*	@Override
	public int getCurrentMana() {
		return storage.getEnergyStored() * manaRate;
	}

	@Override
	public boolean isFull() {
		return storage.getEnergyStored() >= storage.getMaxEnergyStored();
	}

	@Override
	public void recieveMana(int mana) {
		storage.modifyEnergyStored(storage.getEnergyStored() + (mana * manaRate));
	}

	@Override
	public boolean canRecieveManaFromBursts() {
		return !this.isFull();
	} */

	/* IAspectContainer */
	@Override
	public boolean isConnectable(ForgeDirection face) {
		return true;
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return true;
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return false;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return Aspect.ARMOR;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		return 6;
	}

	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
		return 0;
	}

	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
		storage.modifyEnergyStored(storage.getEnergyStored() + (amount * manaRate));
		return amount;
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return Aspect.ARMOR;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return storage.getEnergyStored() * essentiaRate;
	}

	@Override
	public int getMinimumSuction() {
		return 0;
	}

	@Override
	public boolean renderExtendedTube() {
		return false;
	}

	/* IInventory */
	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		return storedItem;
	}

	@Override
    public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
    }

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		if (slot == 0) {
			this.storedItem = itemStack;
		}
		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}
}
