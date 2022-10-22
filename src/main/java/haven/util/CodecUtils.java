package haven.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import haven.mixins.RegistryInvoker;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

public class CodecUtils {
	public static <E> Codec<E> getCodec(Registry<E> reg) {
		Codec<E> codec = Identifier.CODEC.flatXmap(id -> Optional.ofNullable(reg.get((Identifier)id))
						.map(DataResult::success).orElseGet(() -> DataResult.error("Unknown registry key in game_event: " + id)),
				value -> reg.getKey(value).map(RegistryKey::getValue)
						.map(DataResult::success)
						.orElseGet(() -> DataResult.error("Unknown registry element in game_event:" + value)));
		Codec<E> codec2 = rawIdChecked(value -> reg.getKey(value).isPresent() ? reg.getRawId(value) : -1, reg::get, -1);
		return withLifecycle(orCompressed(codec, codec2), value -> ((RegistryInvoker<E>)reg).InvokeGetEntryLifecycle(value), value -> reg.getLifecycle());
	}
	public static <E> Codec<E> rawIdChecked(ToIntFunction<E> elementToRawId, IntFunction<E> rawIdToElement, int errorRawId) {
		return Codec.INT.flatXmap(rawId -> Optional.ofNullable(rawIdToElement.apply((int)rawId)).map(DataResult::success).orElseGet(() -> DataResult.error("Unknown element id: " + rawId)), element -> {
			int j = elementToRawId.applyAsInt(element);
			return j == errorRawId ? DataResult.error("Element with unknown id: " + element) : DataResult.success(j);
		});
	}
	public static <E> Codec<E> withLifecycle(Codec<E> originalCodec, final Function<E, Lifecycle> entryLifecycleGetter, final Function<E, Lifecycle> lifecycleGetter) {
		return originalCodec.mapResult(new Codec.ResultFunction<E>(){
			@Override
			public <T> DataResult<Pair<E, T>> apply(DynamicOps<T> ops, T input, DataResult<Pair<E, T>> result) {
				return result.result().map(pair -> result.setLifecycle((Lifecycle)entryLifecycleGetter.apply(pair.getFirst()))).orElse(result);
			}
			@Override
			public <T> DataResult<T> coApply(DynamicOps<T> ops, E input, DataResult<T> result) {
				return result.setLifecycle((Lifecycle)lifecycleGetter.apply(input));
			}
			public String toString() {
				return "WithLifecycle[" + entryLifecycleGetter + " " + lifecycleGetter + "]";
			}
		});
	}
	public static <E> Codec<E> orCompressed(final Codec<E> uncompressedCodec, final Codec<E> compressedCodec) {
		return new Codec<E>(){
			@Override
			public <T> DataResult<T> encode(E input, DynamicOps<T> ops, T prefix) {
				if (ops.compressMaps()) return compressedCodec.encode(input, ops, prefix);
				return uncompressedCodec.encode(input, ops, prefix);
			}
			@Override
			public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> ops, T input) {
				if (ops.compressMaps()) return compressedCodec.decode(ops, input);
				return uncompressedCodec.decode(ops, input);
			}

			public String toString() {
				return uncompressedCodec + " orCompressed " + compressedCodec;
			}
		};
	}
}
