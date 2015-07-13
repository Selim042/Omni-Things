package selim.omniStuff.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.lang.reflect.Field;

public final class Config {

    public static void init(File file) {
        Configuration config = new Configuration(file);
        
        // Modules
        boolean googlesOfRevealing = config.getBoolean(
        		"gogglesOfRevealing", "helmetmodules", true,
        		"Is the Thuamcraft Goggles of Revealing module enabled?");
        
        boolean terminalGlasses = config.getBoolean(
        		"terminalGlasses", "helmetmodules", true,
        		"Is the OpenPeripheral Terminal Glasses module enabled?");

        // RF Settings
        int rfPerModule = config.getInt("rfPerModule", "rfsettings", 5, 1, 20, "RF/t used per module");
        
        int maxRF = config.getInt(
        		"maxRFStored", "rfsettings", 80000,
        		80000, 20000000, "Maximum RF storage");
        
        int maxRFInput = config.getInt(
        		"maxRFInput", "rfsettings", 80,
        		5, 200, "Maximum RF storage");
        
        if (config.hasChanged()) {
            config.save();
        }
    }

    private static void trySet(Field field, Object value) {
        try {
            field.set(null, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}