package fun.wich.origins.power;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.origins.Origins;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.origin.OriginLayers;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class PowersUtil {
	public static <T extends Power> boolean Active(Entity entity, Class<T> powerClass) {
		return PowerHolderComponent.getPowers(entity, powerClass).stream().anyMatch(Power::isActive);
	}
	public static OriginLayer GetOriginLayer(Identifier id) {
		if (id == null) return null;
		try { return OriginLayers.getLayer(id); }
		catch (Exception e) { return null; }
	}
	public static final Identifier DEFAULT_ORIGIN_LAYER = Origins.identifier("origin");
	public static Origin GetOrigin(Entity entity) { return GetOrigin(entity, DEFAULT_ORIGIN_LAYER); }
	public static Origin GetOrigin(Entity entity, Identifier layer) { return GetOrigin(entity, GetOriginLayer(layer)); }
	public static Origin GetOrigin(Entity entity, OriginLayer layer) {
		if (layer == null) return null;
		HashMap<OriginLayer, Origin> origins = Origin.get(entity);
		if (origins == null) return null;
		if (origins.containsKey(layer)) return origins.get(layer);
		return null;
	}
}
