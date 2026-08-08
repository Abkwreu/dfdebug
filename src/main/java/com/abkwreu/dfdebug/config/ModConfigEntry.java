package com.abkwreu.dfdebug.config;

import java.lang.reflect.Type;

public record ModConfigEntry<T>(String key, T defaultValue, String description, Type type) {
}
