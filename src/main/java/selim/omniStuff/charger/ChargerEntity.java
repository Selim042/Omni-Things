package selim.omniStuff.charger;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;
import vazkii.botania.api.mana.IManaReceiver;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.TileEnergyHandler;

public class ChargerEntity extends TileEnergyHandler implements /*IManaReceiver, */IEssentiaTransport {

	int rfRate = 10;
	int essentiaRate = 15;
	int manaRate = 5;
	
	protected EnergyStorage storage = new EnergyStorage(32000);
	int maxRfRecieve = 800;
	
	private boolean aBoolean;
    private byte aByte;
    private short aShort;
    private int anInt;
    private long aLong;
    private float aFloat;
    private double aDouble;
    private String aString;
    private byte[] aByteArray;
    private int[] anIntArray;

    private ItemStack anItemStack;

    private ArrayList aList = new ArrayList();
    
    @Override
	public void readFromNBT(NBTTagCompound nbt) {

		super.readFromNBT(nbt);
		storage.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);
		storage.writeToNBT(nbt);
	}
	
	@Override
	public void updateEntity() {
		if (!this.getWorldObj().isRemote) {
			System.out.println(storage.getEnergyStored());
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
		// TODO Auto-generated method stub
		return storage.getEnergyStored() * manaRate;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return storage.getEnergyStored() >= storage.getMaxEnergyStored();
	}

	@Override
	public void recieveMana(int mana) {
		// TODO Auto-generated method stub
		storage.modifyEnergyStored(storage.getEnergyStored() + (mana * manaRate));
	}

	@Override
	public boolean canRecieveManaFromBursts() {
		// TODO Auto-generated method stub
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
}
