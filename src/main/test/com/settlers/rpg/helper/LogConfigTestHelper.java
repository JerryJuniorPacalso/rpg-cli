package com.settlers.rpg.helper;

import com.settlers.rpg.utils.LogConfig;

import java.lang.reflect.Field;

public class LogConfigTestHelper {
    public static void resetInitializedFlag() {
        try {
            Field initializedField = LogConfig.class.getDeclaredField("initialized");
            initializedField.setAccessible(true);
            initializedField.setBoolean(null, false);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset initialized flag for tests", e);
        }
    }
}
