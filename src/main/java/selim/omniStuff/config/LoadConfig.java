package selim.omniStuff.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.lang.reflect.Field;

public class LoadConfig {
	public static boolean enableGogglesOfRevealing = true;
	public static boolean enableTerminalGlasses = true;
	
	public static int rfPerModule = 5;
	public static int rfCapacity = 80000;
	public static int rfIn = 80;
	
    public static void init(File file) {
  /*      Configuration config = new Configuration(file);
        
        config.load();
        
        enableGogglesOfRevealing = config.get(
        		"gogglesOfRevealing", "helmetmodules", true).getBoolean();
        enableTerminalGlasses = config.get(
        		"terminalGlasses", "helmetmodules", true).getBoolean();
        
        rfCapacity = config.get("maxRFStored", "rfsettings", 80000).getInt();
        rfIn = config.get("maxRFInput", "rfsettings", 80).getInt();
        rfPerModule = config.get("rfPerModule", "rfsettings", 5).getInt(); */
    }
}
