package org.mleon.tetris;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

import org.mleon.tetris.util.Log;

public class ConfigurationMgr {
    public static String configFileName = "config.ini";

    private static Properties config = new Properties();

    public static enum CONFIG {
        UNKNOWN         (0x00,      ""),
        WIDTH           (0x01,      800),
        HEIGHT          (0x02,      600),
        FULLSCREEN      (0x03,      false),
        VSYNC           (0x04,      false),
        FPS_COUNTER     (0x05,      false);

        private int index;
        private String defaultValue;

        CONFIG(int index, Object defaultValue) {
            this.index = index;
            this.defaultValue = String.valueOf(defaultValue);
        }

        public int getIndex() {
            return index;
        }

        public String getDefault() {
            return defaultValue;
        }

        public static CONFIG getCONFIGByIndex(int index) {
            for (CONFIG current : values())
                if (current.getIndex() == index)
                    return current;

            return UNKNOWN;
        }
    }

    public static void init() {
        if (!new File(configFileName).exists()) {
            setDefaultValues();
            saveConfiguration();
        } else {
            loadConfiguration();
            ensureValues();
            saveConfiguration();
        }
    }

    public static void loadConfiguration() {
        try {
            config.load(new FileInputStream(configFileName));
        } catch (FileNotFoundException e) {
            Log.error("Configuration", "", e);
        } catch (IOException e) {
            Log.error("Configuration", "", e);
        }
    }

    public static void saveConfiguration() {
        try {
            config.store(new FileOutputStream(configFileName), null);
        } catch (FileNotFoundException e) {
            Log.error("Configuration", "", e);
        } catch (IOException e) {
            Log.error("Configuration", "", e);
        }
    }

    private static void ensureValues() {
        boolean[] configAssigned = new boolean[CONFIG.values().length];
        Arrays.fill(configAssigned, false);
        Enumeration<Object> en = config.keys();
        String enumName;
        while (en.hasMoreElements()) {
            enumName = String.valueOf(en.nextElement());
            for (CONFIG current : CONFIG.values()) {
                if (current.toString().equals(enumName)) {
                    configAssigned[current.getIndex()] = true;                    
                }
            }
        }

        CONFIG current;
        for (int i = 0; i < configAssigned.length; i++) {
            current = CONFIG.getCONFIGByIndex(i);
            if (!configAssigned[i]) {
                if (current != CONFIG.UNKNOWN) {
                    config.setProperty(current.name(), current.getDefault());
                }
            }
        }
    }

    private static void setDefaultValues() {
        for (CONFIG current : CONFIG.values()) {
            if (current != CONFIG.UNKNOWN) {
                config.setProperty(current.name(), current.getDefault());
            }
        }
    }

    public static String getStringConfig(CONFIG enumValue) {
        return config.getProperty(enumValue.name(), enumValue.getDefault());
    }

    public static boolean getBoolConfig(CONFIG enumValue) {
        return Boolean.getBoolean(getStringConfig(enumValue));
    }

    public static int getIntConfig(CONFIG enumValue) {
        return Integer.parseInt(getStringConfig(enumValue));
    }
}
