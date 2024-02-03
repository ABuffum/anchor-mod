package fun.wich.gen.data.minecraft;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.Identifier;

import java.nio.file.Path;

public class PathResolver {
	private final Path rootPath;
	private final String directoryName;
	public PathResolver(DataGenerator dataGenerator, OutputType outputType, String directoryName) {
		this.rootPath = dataGenerator.getOutput().resolve(outputType.path);
		this.directoryName = directoryName;
	}
	public Path resolve(Identifier id, String fileExtension) {
		return this.rootPath.resolve(id.getNamespace()).resolve(this.directoryName).resolve(id.getPath() + "." + fileExtension);
	}
	public Path resolveJson(Identifier id) {
		return this.rootPath.resolve(id.getNamespace()).resolve(this.directoryName).resolve(id.getPath() + ".json");
	}
}
