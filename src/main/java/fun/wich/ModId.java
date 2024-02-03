package fun.wich;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModId {
	public static final Logger LOGGER = LoggerFactory.getLogger("Wich Pack");
	public static final String NAMESPACE = "haven";
	public static Identifier ID(String path) { return path.contains(":") ? new Identifier(path) : new Identifier(NAMESPACE, path); }
}
