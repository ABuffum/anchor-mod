package haven.util;

public class Flavor {
	private final String name;

	public Flavor(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public static final Flavor CHOCOLATE = new Flavor("chocolate");
	public static final Flavor STRAWBERRY = new Flavor("strawberry");
	public static final Flavor COFFEE = new Flavor("coffee");
	public static final Flavor CARROT = new Flavor("carrot");
}
