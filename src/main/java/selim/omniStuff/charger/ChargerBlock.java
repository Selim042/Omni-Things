package selim.omniStuff.charger;

import selim.omniStuff.OmniStuff;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ChargerBlock extends Block implements ITileEntityProvider {

	public ChargerBlock() {
		super(Material.iron);
		setBlockName("charger");
        setCreativeTab(OmniStuff.omniStuffTab);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return new ChargerEntity();
	}

	@Override
    public boolean hasTileEntity(int metadata) {
        return true;
	}
}
