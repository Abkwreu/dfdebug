package com.abkwreu.dfdebug.config;

import com.abkwreu.dfdebug.DFDebugMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.resources.Identifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting()
          .registerTypeAdapter(Identifier.class,
                (JsonDeserializer<Identifier>) (json, type, context) -> DFDebugMod.namespacedId(json.getAsString()))
          .disableHtmlEscaping()
          .create();
    private static ModConfig INSTANCE;

    protected abstract Path getConfigPath();

    private final Map<String, Object> values = new HashMap<>();

    public static ModConfig get() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Config not yet initialized");
        } else {
            return INSTANCE;
        }
    }

    private static void setInstance(ModConfig INSTANCE) {
        ModConfig.INSTANCE = INSTANCE;
    }

    public static <T extends ModConfig> T load(Supplier<T> factory) {
        T config = factory.get();
        setInstance(config);

        JsonObject json = new JsonObject();

        if (Files.exists(INSTANCE.getConfigPath())) {
            try (BufferedReader reader = Files.newBufferedReader(INSTANCE.getConfigPath())) {
                json = GSON.fromJson(reader, JsonObject.class);
            } catch (IOException e) {
                DFDebugMod.LOGGER.error("Failed to read config, using defaults");
            }
        }

        for (ModConfigEntry<?> entry : ModConfigEntries.CONFIG_ENTRIES) {
            config.loadEntry(json, entry);
        }

        config.save();
        return config;
    }

    protected <T> void loadEntry(JsonObject json, ModConfigEntry<T> entry) {
        T value = entry.defaultValue();

        if (json.has(entry.key())) {
            try {
                value = GSON.fromJson(json.get(entry.key()), entry.type());
            } catch (Exception e) {
                DFDebugMod.LOGGER.warn("Invalid value for config \"{}\", using default", entry.key());
            }
        }

        values.put(entry.key(), value);
    }

    protected void save() {
        JsonObject configJson = new JsonObject();
        for (ModConfigEntry<?> entry : ModConfigEntries.CONFIG_ENTRIES) {
            String key = entry.key();
            List<String> descriptionLines = entry.description().lines().toList();
            int numLines = descriptionLines.size();
            if (numLines == 1) {
                configJson.addProperty(String.format("_desc_%s", key), entry.description());
            } else {
                for (int i = 0; i < numLines; i++) {
                    configJson.addProperty(String.format("_desc_%s_%s", key, i + 1), descriptionLines.get(i));
                }
            }
            String typeName = getSimpleTypeName(entry.type());
            configJson.addProperty(String.format("_type_%s", key), typeName);
            configJson.add(key, ModConfig.toJsonElement(values.get(key)));
        }

        try {
            Files.createDirectories(INSTANCE.getConfigPath().getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(INSTANCE.getConfigPath())) {
                GSON.toJson(configJson, writer);
            }
        } catch (IOException e) {
            DFDebugMod.LOGGER.error("Failed to write config", e);
        }
    }

    private static String getSimpleTypeName(Type type) {
        if (type instanceof Class) {
            return ((Class<?>) type).getSimpleName();
        } else if (type instanceof ParameterizedType pType) {
            StringBuilder name = new StringBuilder(getSimpleTypeName(pType.getRawType()));
            Type[] args = pType.getActualTypeArguments();
            name.append("<");
            for (int i = 0; i < args.length; i++) {
                name.append(getSimpleTypeName(args[i]));
                if (i < args.length - 1) name.append(", ");
            }
            name.append(">");
            return name.toString();
        }
        return type.getTypeName();
    }

    private static JsonElement toJsonElement(Object value) {
        if (value == null) {
            return JsonNull.INSTANCE;
        }

        if (value instanceof List<?> list) {
            JsonArray array = new JsonArray();

            for (Object element : list) {
                array.add(ModConfig.toJsonElement(element));
            }

            return array;
        }

        if (value instanceof Map<?, ?> map) {
            JsonObject object = new JsonObject();

            for (Map.Entry<?, ?> entry : map.entrySet()) {
                Object key = entry.getKey();
                String stringKey;
                if (key instanceof String s) {
                    stringKey = s;
                } else if (key instanceof Identifier id) {
                    stringKey = id.toString();
                } else {
                    throw new IllegalArgumentException(String.format(
                          "Expected map keys to be of type String or Identifier, instead got %s",
                          key == null ? "null" : key.getClass().getName()));
                }
                Object entryValue = entry.getValue();
                object.add(stringKey, ModConfig.toJsonElement(entryValue));
            }

            return object;
        }

        if (value instanceof Identifier identifier) {
            return new JsonPrimitive(identifier.toString());
        }

        if (value instanceof Number number) {
            return new JsonPrimitive(number);
        }

        if (value instanceof String string) {
            return new JsonPrimitive(string);
        }

        if (value instanceof Boolean bool) {
            return new JsonPrimitive(bool);
        }

        if (value instanceof Character character) {
            return new JsonPrimitive(character);
        }

        throw new IllegalArgumentException(String.format("Unsupported config value type: %s",
              value.getClass().getName()));
    }

    public <T> T get(ModConfigEntry<T> entry) {
        Object value = values.get(entry.key());

        if (value == null) {
            return entry.defaultValue();
        }

        @SuppressWarnings("unchecked") T typedValue = (T) value;
        return typedValue;
    }
}
