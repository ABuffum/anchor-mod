package fun.wich.gen.data.minecraft;

public enum OutputType {
	DATA_PACK("data"),
	RESOURCE_PACK("assets"),
	REPORTS("reports");
	final String path;
	OutputType(String path) { this.path = path; }
}