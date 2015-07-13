package selim.omniStuff.keys;

import org.lwjgl.input.Keyboard;

import selim.omniStuff.ModInfo;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindings {

    // Declare two KeyBindings, ping and pong
    public static KeyBinding nightVisionToggle;
    public static KeyBinding zoom;
    
    public static KeyBinding modifyModules;
    public static KeyBinding switchToolMode;

    public static void init() {
        nightVisionToggle = new KeyBinding("key.nightVisionToggle", Keyboard.KEY_X, "key.categories." + ModInfo.ID);
        modifyModules = new KeyBinding("key.modifyModules", Keyboard.KEY_Z, "key.categories." + ModInfo.ID);
        switchToolMode = new KeyBinding("key.modeSwitch", Keyboard.KEY_M, "key.categories." + ModInfo.ID);

        //zoom = new KeyBinding("key.zoom", Keyboard.KEY_Z, "key.categories." + ModInfo.ID);
        
        // Register KeyBindings to the ClientRegistry
        ClientRegistry.registerKeyBinding(nightVisionToggle);
        ClientRegistry.registerKeyBinding(switchToolMode);
    }

}