package selim.omniStuff.keys;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler {

	public static boolean nightVisionToggle = true;
	public static boolean zoomToggle = false;
	
	public static boolean moduleKey = false;
	public static boolean switchToolMode = false;
	
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.nightVisionToggle.isPressed()) {
            if (nightVisionToggle) {
            	nightVisionToggle = false;
            }
            else {
            	nightVisionToggle = true;
            }
        }
        if (KeyBindings.modifyModules.isPressed()) {
        	moduleKey = true;
        	moduleKey = false;
        }
/*        if (KeyBindings.switchToolMode.isPressed()) {
        	switchToolMode = true;
        	switchToolMode = false;
        } */
    }

}