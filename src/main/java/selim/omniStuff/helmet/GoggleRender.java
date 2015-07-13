package selim.omniStuff.helmet;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class GoggleRender implements IItemRenderer {

	private static RenderItem renderItem = new RenderItem();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		return type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {
		// TODO Auto-generated method stub
		IIcon icon = itemStack.getIconIndex();
        renderItem.renderIcon(0, 0, icon, 16, 16);
        
        Tessellator tessellator = Tessellator.instance;
        // Set drawing mode. Tessellator should support most drawing modes.
        tessellator.startDrawing(GL11.GL_QUADS);
        // Set semi-transparent black color
        tessellator.setColorRGBA(0, 0, 0, 128);

        // Draw a 8x8 square
        tessellator.addVertex(0, 0, 0);
        tessellator.addVertex(0, 8, 0);
        tessellator.addVertex(8, 8, 0);
        tessellator.addVertex(8, 0, 0);
        
        tessellator.draw();
	}
}
