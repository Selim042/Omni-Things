package selim.omniStuff.charger;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import selim.omniStuff.OmniStuff;

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
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float x2, float y2, float z2) {
		return false;
	}
}
