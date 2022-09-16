package haven.blocks.cake;

public class CakeFlavor {
	private final String name;

	public CakeFlavor(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public static final CakeFlavor CHOCOLATE = new CakeFlavor("chocolate");
	public static final CakeFlavor STRAWBERRY = new CakeFlavor("strawberry");
	public static final CakeFlavor COFFEE = new CakeFlavor("coffee");
	public static final CakeFlavor CARROT = new CakeFlavor("carrot");
	public static final CakeFlavor CONFETTI = new CakeFlavor("confetti");
}
