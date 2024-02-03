package fun.wich.entity.hostile.warden;

import com.google.common.collect.Streams;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.dynamic.DynamicSerializableUuid;
import net.minecraft.util.math.MathHelper;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WardenAngerManager {
	protected static final int field_38733 = 2;
	protected static final int maxAnger = 150;
	private static final int angerDecreasePerTick = 1;
	private static final Random random = new Random(System.nanoTime());
	private int updateTimer =  MathHelper.nextBetween(random, 0, 2);
	int primeAnger;
	private static final Codec<Pair<UUID, Integer>> SUSPECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
			DynamicSerializableUuid.CODEC.fieldOf("uuid").forGetter(Pair::getLeft),
			Codecs.NONNEGATIVE_INT.fieldOf("anger").forGetter(Pair::getRight))
			.apply(instance, Pair::new));
	private final Predicate<Entity> suspectPredicate;
	protected final ArrayList<Entity> suspects;
	private final SuspectComparator suspectComparator;
	protected final Object2IntMap<Entity> suspectsToAngerLevel;
	protected final Object2IntMap<UUID> suspectUuidsToAngerLevel;
	public static Codec<WardenAngerManager> createCodec(Predicate<Entity> suspectPredicate) {
		return RecordCodecBuilder.create(instance -> instance.group(
				SUSPECT_CODEC.listOf().fieldOf("suspects").orElse(Collections.emptyList())
						.forGetter(WardenAngerManager::getSuspects))
				.apply(instance, suspectUuidsToAngerLevel -> new WardenAngerManager(suspectPredicate, suspectUuidsToAngerLevel)));
	}
	public WardenAngerManager(Predicate<Entity> suspectPredicate, List<Pair<UUID, Integer>> suspectUuidsToAngerLevel) {
		this.suspectPredicate = suspectPredicate;
		this.suspects = new ArrayList<>();
		this.suspectComparator = new SuspectComparator(this);
		this.suspectsToAngerLevel = new Object2IntOpenHashMap<>();
		this.suspectUuidsToAngerLevel = new Object2IntOpenHashMap<>(suspectUuidsToAngerLevel.size());
		suspectUuidsToAngerLevel.forEach(suspect -> this.suspectUuidsToAngerLevel.put(suspect.getLeft(), suspect.getRight()));
	}
	private List<Pair<UUID, Integer>> getSuspects() {
		return Streams.concat(this.suspects.stream().map(suspect -> new Pair<>(suspect.getUuid(), this.suspectsToAngerLevel.getInt(suspect))), this.suspectUuidsToAngerLevel.object2IntEntrySet().stream().map(suspect -> new Pair<>(suspect.getKey(), suspect.getIntValue()))).collect(Collectors.toList());
	}
	public void tick(ServerWorld world, Predicate<Entity> suspectPredicate) {
		--this.updateTimer;
		if (this.updateTimer <= 0) {
			this.updateSuspectsMap(world);
			this.updateTimer = 2;
		}
		Iterator<Object2IntMap.Entry<UUID>> objectIterator = this.suspectUuidsToAngerLevel.object2IntEntrySet().iterator();
		while (objectIterator.hasNext()) {
			Object2IntMap.Entry<UUID> entry = objectIterator.next();
			int i = entry.getIntValue();
			if (i <= angerDecreasePerTick) {
				objectIterator.remove();
				continue;
			}
			entry.setValue(i - angerDecreasePerTick);
		}
		Iterator<Object2IntMap.Entry<Entity>> objectIterator2 = this.suspectsToAngerLevel.object2IntEntrySet().iterator();
		while (objectIterator2.hasNext()) {
			Object2IntMap.Entry<Entity> entry2 = objectIterator2.next();
			int j = entry2.getIntValue();
			Entity entity = entry2.getKey();
			Entity.RemovalReason removalReason = entity.getRemovalReason();
			if (j <= angerDecreasePerTick || !suspectPredicate.test(entity) || removalReason != null) {
				this.suspects.remove(entity);
				objectIterator2.remove();
				if (j <= 1 || removalReason == null) continue;
				switch (removalReason) {
					case CHANGED_DIMENSION, UNLOADED_TO_CHUNK, UNLOADED_WITH_PLAYER -> {
						this.suspectUuidsToAngerLevel.put(entity.getUuid(), j - angerDecreasePerTick);
					}
				}
				continue;
			}
			entry2.setValue(j - angerDecreasePerTick);
		}
		this.updatePrimeAnger();
	}
	private void updatePrimeAnger() {
		this.primeAnger = 0;
		this.suspects.sort(this.suspectComparator);
		if (this.suspects.size() == 1) this.primeAnger = this.suspectsToAngerLevel.getInt(this.suspects.get(0));
	}
	private void updateSuspectsMap(ServerWorld world) {
		Iterator<Object2IntMap.Entry<UUID>> objectIterator = this.suspectUuidsToAngerLevel.object2IntEntrySet().iterator();
		while (objectIterator.hasNext()) {
			Object2IntMap.Entry<UUID> entry = objectIterator.next();
			int i = entry.getIntValue();
			Entity entity = world.getEntity(entry.getKey());
			if (entity == null) continue;
			this.suspectsToAngerLevel.put(entity, i);
			this.suspects.add(entity);
			objectIterator.remove();
		}
	}
	public int increaseAngerAt(Entity entity, int amount) {
		boolean bl = !this.suspectsToAngerLevel.containsKey(entity);
		int i = this.suspectsToAngerLevel.computeInt(entity, (suspect, anger) -> Math.min(maxAnger, (anger == null ? 0 : anger) + amount));
		if (bl) {
			int j = this.suspectUuidsToAngerLevel.removeInt(entity.getUuid());
			this.suspectsToAngerLevel.put(entity, i += j);
			this.suspects.add(entity);
		}
		this.updatePrimeAnger();
		return i;
	}
	public void removeSuspect(Entity entity) {
		this.suspectsToAngerLevel.removeInt(entity);
		this.suspects.remove(entity);
		this.updatePrimeAnger();
	}
	private Entity getPrimeSuspectInternal() {
		return this.suspects.stream().filter(this.suspectPredicate).findFirst().orElse(null);
	}
	public int getAngerFor(Entity entity) {
		return entity == null ? this.primeAnger : this.suspectsToAngerLevel.getInt(entity);
	}
	public Optional<LivingEntity> getPrimeSuspect() {
		return Optional.ofNullable(this.getPrimeSuspectInternal()).filter(suspect -> suspect instanceof LivingEntity).map(suspect -> (LivingEntity)suspect);
	}
	protected record SuspectComparator(WardenAngerManager angerManagement) implements Comparator<Entity> {
		@Override
		public int compare(Entity entity, Entity entity2) {
			if (entity.equals(entity2)) return 0;
			int i = this.angerManagement.suspectsToAngerLevel.getOrDefault(entity, 0);
			int j = this.angerManagement.suspectsToAngerLevel.getOrDefault(entity2, 0);
			this.angerManagement.primeAnger = Math.max(this.angerManagement.primeAnger, Math.max(i, j));
			boolean bl = Angriness.getForAnger(i).isAngry();
			if (bl != Angriness.getForAnger(j).isAngry()) return bl ? -1 : 1;
			boolean bl2 = entity instanceof PlayerEntity;
			if (bl2 != entity2 instanceof PlayerEntity) return bl2 ? -1 : 1;
			return Integer.compare(j, i);
		}
	}
}