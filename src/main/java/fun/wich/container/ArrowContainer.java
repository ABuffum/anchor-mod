package fun.wich.container;

import fun.wich.ModFactory;
import fun.wich.entity.projectile.ModArrowEntity;
import fun.wich.entity.projectile.SummoningArrowEntity;
import fun.wich.gen.data.ModDatagen;
import fun.wich.item.projectile.ModArrowItem;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Pair;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArrowContainer implements IContainer, ItemConvertible {
	private final ModArrowItem item;
	@Override
	public Item asItem() { return item; }
	@Override
	public boolean contains(Block block) { return false; }
	@Override
	public boolean contains(Item item) { return item == asItem(); }
	private final EntityType<ModArrowEntity> entityType;
	public EntityType<ModArrowEntity> getEntityType() { return entityType; }

	private ArrowContainer(EntityType<? extends MobEntity> summon) {
		ModArrowItem.ShotFactory shot = (World world, LivingEntity shooter) -> new SummoningArrowEntity(summon, this.getEntityType(), world, shooter);
		ModArrowItem.DispensedFactory dispensed = (World world, double x, double y, double z) -> new SummoningArrowEntity(summon, this.getEntityType(), world, x, y, z);
		this.item = new ModArrowItem(shot, dispensed);
		this.entityType = ModFactory.MakeArrowEntity((EntityType<ModArrowEntity> entity, World world) -> new SummoningArrowEntity(summon, entity, world));
		ARROW_CONTAINERS.add(this);
	}
	public ArrowContainer(ModArrowItem.ShotFactory shot, ModArrowItem.DispensedFactory dispensed, EntityType.EntityFactory<ModArrowEntity> entityFactory) {
		this.item = new ModArrowItem(shot, dispensed);
		this.entityType = ModFactory.MakeArrowEntity(entityFactory);
		ARROW_CONTAINERS.add(this);
	}
	public static ArrowContainer Summoning(EntityType<? extends MobEntity> summon, int primaryColor, int secondaryColor) {
		ArrowContainer container = new ArrowContainer(summon);
		SUMMONING_ARROWS.put(summon, container.asItem());
		SUMMONING_ARROW_COLORS.put(container.asItem(), new Pair<>(primaryColor, secondaryColor));
		return container;
	}

	public static final Set<ArrowContainer> ARROW_CONTAINERS = new HashSet<>();
	public static final Map<EntityType<? extends MobEntity>, Item> SUMMONING_ARROWS = new HashMap<>();
	public static final Map<Item, Pair<Integer, Integer>> SUMMONING_ARROW_COLORS = new HashMap<>();

	public ArrowContainer generatedItemModel() { ModDatagen.Cache.Models.GENERATED.add(this.item); return this; }
}
