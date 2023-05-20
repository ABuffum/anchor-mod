package fun.mousewich.gen.data.fabric;

/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Changes made to this file by mousewich to create compatibility with older versions of Minecraft
 */

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import fun.mousewich.ModBase;
import fun.mousewich.gen.data.minecraft.OutputType;
import fun.mousewich.gen.data.minecraft.PathResolver;
import fun.mousewich.mixins.JsonObjectAccessor;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.advancement.Advancement;
import net.minecraft.block.Block;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.stat.StatType;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.biome.Biome;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public abstract class FabricLanguageProvider implements DataProvider {
	protected final FabricDataGenerator dataOutput;
	private final String languageCode;
	protected FabricLanguageProvider(FabricDataGenerator dataOutput, String languageCode) {
		this.dataOutput = dataOutput;
		this.languageCode = languageCode;
	}

	/**
	 * Implement this method to register languages.
	 *
	 * <p>Call {@link TranslationBuilder#add(String, String)} to add a translation.
	 */
	public abstract void generateTranslations(TranslationBuilder translationBuilder);

	@Override
	public void run(DataCache cache) {
		TreeMap<String, String> translationEntries = new TreeMap<>();
		generateTranslations((String key, String value) -> {
			Objects.requireNonNull(key);
			Objects.requireNonNull(value);
			if (translationEntries.containsKey(key)) {
				throw new RuntimeException("Existing translation key found - " + key + " - Duplicate will be ignored.");
			}
			translationEntries.put(key, value);
		});
		JsonObject langEntryJson = new JsonObject();
		for (Map.Entry<String, String> entry : translationEntries.entrySet()) {
			langEntryJson.addProperty(entry.getKey(), entry.getValue());
		}
		saveLanguageFile(cache, langEntryJson, getLangFilePath(this.languageCode));
	}
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static void saveLanguageFile(DataCache cache, JsonObject json, Path path) {
		try {
			String string = GSON.toJson(json);
			String string2 = SHA1.hashUnencodedChars(string).toString();
			if (!Objects.equals(cache.getOldSha1(path), string2) || !Files.exists(path)) {
				Files.createDirectories(path.getParent());
				try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
					bufferedWriter.write(string);
				}
			}
			cache.updateSha1(path, string2);
		}
		catch (IOException iOException) {
			ModBase.LOGGER.error("Couldn't save language file {}", path, iOException);
		}
	}


	private Path getLangFilePath(String code) {
		return new PathResolver(dataOutput, OutputType.RESOURCE_PACK, "lang").resolveJson(new Identifier(dataOutput.getModId(), code));
	}
	@Override
	public String getName() { return "Language (%s)".formatted(languageCode); }

	/**
	 * A consumer used by {@link FabricLanguageProvider#generateTranslations(TranslationBuilder)}.
	 */
	@FunctionalInterface
	public interface TranslationBuilder {
		/**
		 * Adds a translation.
		 *
		 * @param translationKey The key of the translation.
		 * @param value          The value of the entry.
		 */
		void add(String translationKey, String value);
		/**
		 * Adds a translation for an {@link Item}.
		 *
		 * @param item  The {@link Item} to get the translation key from.
		 * @param value The value of the entry.
		 */
		default void add(Item item, String value) { add(item.getTranslationKey(), value); }
		/**
		 * Adds a translation for a {@link Block}.
		 *
		 * @param block The {@link Block} to get the translation key from.
		 * @param value The value of the entry.
		 */
		default void add(Block block, String value) { add(block.getTranslationKey(), value); }
		/**
		 * Adds a translation for an {@link ItemGroup}.
		 *
		 * @param group The {@link ItemGroup} to get the translation key from.
		 * @param value The value of the entry.
		 */
		default void add(ItemGroup group, String value) {
			if (group.getTranslationKey() instanceof TranslatableText translatableTextContent) {
				add(translatableTextContent.getKey(), value);
				return;
			}
			throw new UnsupportedOperationException("Cannot add language entry for ItemGroup (%s) as the display name is not translatable.".formatted(group.getTranslationKey().getString()));
		}
		/**
		 * Adds a translation for an {@link EntityType}.
		 *
		 * @param entityType The {@link EntityType} to get the translation key from.
		 * @param value      The value of the entry.
		 */
		default void add(EntityType<?> entityType, String value) { add(entityType.getTranslationKey(), value); }
		/**
		 * Adds a translation for an {@link Advancement}.
		 *
		 * @param advancement The {@link Advancement} to get the translation key from.
		 * @param title      The title of the entry.
		 * @param description      The description of the entry.
		 */
		default void addAdvancement(String advancement, String title, String description) {
			add(advancement + ".title", title);
			add(advancement + ".description", description);
		}
		/**
		 * Adds a translation for an {@link Enchantment}.
		 *
		 * @param enchantment The {@link Enchantment} to get the translation key from.
		 * @param value       The value of the entry.
		 */
		default void add(Enchantment enchantment, String value) { add(enchantment.getTranslationKey(), value); }
		/**
		 * Adds a translation for an {@link EntityAttribute}.
		 *
		 * @param entityAttribute The {@link EntityAttribute} to get the translation key from.
		 * @param value           The value of the entry.
		 */
		default void add(EntityAttribute entityAttribute, String value) { add(entityAttribute.getTranslationKey(), value); }
		/**
		 * Adds a translation for a {@link StatType}.
		 *
		 * @param statType The {@link StatType} to get the translation key from.
		 * @param value    The value of the entry.
		 */
		default void add(StatType<?> statType, String value) { add(statType.getTranslationKey(), value); }
		/**
		 * Adds a translation for a {@link Biome}.
		 *
		 * @param biome The {@link Biome} to get the translation key from.
		 * @param value    The value of the entry.
		 */
		default void add(RegistryKey<Biome> biome, String value) { add("biome." + biome.getValue(), value); }
		/**
		 * Adds a translation for a {@link GameRules.Key}.
		 *
		 * @param gameRule The {@link GameRules.Key} to get the translation key from.
		 * @param value        The value of the entry.
		 */
		default void add(GameRules.Key<?> gameRule, String value) { add(gameRule.getTranslationKey(), value); }
		/**
		 * Adds a translation for a {@link GameRules.Key}.
		 *
		 * @param gameRule The {@link GameRules.Key} to get the translation key from.
		 * @param value        The value of the entry.
		 * @param description  The description of the entry.
		 */
		default void add(GameRules.Key<?> gameRule, String value, String description) {
			add(gameRule, value);
			add(gameRule.getTranslationKey() + ".description", description);
		}
		/**
		 * Adds a translation for a {@link StatusEffect}.
		 *
		 * @param statusEffect The {@link StatusEffect} to get the translation key from.
		 * @param value        The value of the entry.
		 */
		default void add(StatusEffect statusEffect, String value) { add(statusEffect.getTranslationKey(), value); }
		/**
		 * Adds a translation for an {@link Identifier}.
		 *
		 * @param identifier The {@link Identifier} to get the translation key from.
		 * @param value      The value of the entry.
		 */
		default void add(Identifier identifier, String value) { add(identifier.getNamespace() + "." + identifier.getPath(), value); }
		/**
		 * Merges an existing language file into the generated language file.
		 *
		 * @param existingLanguageFile The path to the existing language file.
		 * @throws IOException If loading the language file failed.
		 */
		default void add(Path existingLanguageFile) throws IOException {
			try (Reader reader = Files.newBufferedReader(existingLanguageFile)) {
				JsonObject translations = parseReader(reader).getAsJsonObject();
				for (String key : ((JsonObjectAccessor)(Object)translations).getMembers().keySet()) add(key, translations.get(key).getAsString());
			}
		}
	}

	public static JsonElement parseReader(Reader reader) throws JsonIOException, JsonSyntaxException {
		try {
			JsonReader jsonReader = new JsonReader(reader);
			JsonElement element = parseReader(jsonReader);
			if (!element.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
				throw new JsonSyntaxException("Did not consume the entire document.");
			}
			else return element;
		}
		catch (MalformedJsonException | NumberFormatException var3) { throw new JsonSyntaxException(var3); }
		catch (IOException var4) { throw new JsonIOException(var4); }
	}

	public static JsonElement parseReader(JsonReader reader) throws JsonIOException, JsonSyntaxException {
		boolean lenient = reader.isLenient();
		reader.setLenient(true);
		JsonElement var2;
		try { var2 = Streams.parse(reader); }
		catch (StackOverflowError | OutOfMemoryError var7) {
			throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", var7);
		}
		finally { reader.setLenient(lenient); }
		return var2;
	}
}