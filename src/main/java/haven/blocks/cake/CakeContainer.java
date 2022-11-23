package haven.blocks.cake;

import haven.ModBase;
import haven.containers.BlockContainer;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;

import java.util.List;
import java.util.Map;

public class CakeContainer {
	private final Flavor flavor;
	public Flavor getFlavor() { return this.flavor; }
	public BlockContainer getCake() { return flavor.getCake(); }
	public HavenCandleCakeBlock getCandleCake() { return flavor.getCandleCake(); }
	public HavenCandleCakeBlock getSoulCandleCake() { return flavor.getSoulCandleCake(); }
	public HavenCandleCakeBlock getEnderCandleCake() { return flavor.getEnderCandleCake(); }
	public HavenCandleCakeBlock getCandleCake(DyeColor color) { return flavor.getCandleCake(color); }

	public CakeContainer(Flavor flavor) { this(flavor, ModBase.ItemSettings().maxCount(1)); }
	public CakeContainer(Flavor flavor, Item.Settings settings) {
		this.flavor = flavor;
		flavor.cake = new BlockContainer(new HavenCakeBlock(flavor), settings);
		flavor.candle_cake = new HavenCandleCakeBlock(flavor, 3);
		flavor.soul_candle_cake = new HavenCandleCakeBlock(flavor, 2);
		flavor.ender_candle_cake = new HavenCandleCakeBlock(flavor, 3);
		flavor.candle_cakes = ModBase.MapDyeColor((color) -> new HavenCandleCakeBlock(flavor, 3));
	}

	public static class Flavor {
		private final String name;
		public String getName() { return this.name; }
		protected BlockContainer cake;
		public BlockContainer getCake() { return this.cake; }
		protected HavenCandleCakeBlock candle_cake;
		public HavenCandleCakeBlock getCandleCake() { return this.candle_cake; }
		protected HavenCandleCakeBlock soul_candle_cake;
		public HavenCandleCakeBlock getSoulCandleCake() { return this.soul_candle_cake; }
		protected HavenCandleCakeBlock ender_candle_cake;
		public HavenCandleCakeBlock getEnderCandleCake() { return this.ender_candle_cake; }
		protected Map<DyeColor, HavenCandleCakeBlock> candle_cakes;
		public HavenCandleCakeBlock getCandleCake(DyeColor color) { return this.candle_cakes.get(color); }
		public Flavor(String name) { this.name = name; }

		public static final Flavor CHOCOLATE = new Flavor("chocolate");
		public static final Flavor COFFEE = new Flavor("coffee");
		public static final Flavor STRAWBERRY = new Flavor("strawberry");
		public static final Flavor VANILLA = new Flavor("vanilla");
		public static final Flavor CARROT = new Flavor("carrot");
		public static final Flavor CONFETTI = new Flavor("confetti");
		public static final Flavor CHORUS = new Flavor("chorus");

		public static final List<Flavor> FLAVORS = List.of(
			CHOCOLATE, COFFEE, STRAWBERRY, VANILLA, CARROT, CONFETTI, CHORUS
		);
	}
}
