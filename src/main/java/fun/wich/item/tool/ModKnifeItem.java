package fun.wich.item.tool;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import fun.wich.ModFactory;
import net.minecraft.item.ToolMaterial;

public class ModKnifeItem extends KnifeItem {
	public ModKnifeItem(ToolMaterial material) { this(material, ModFactory.ItemSettings()); }
	public ModKnifeItem(ToolMaterial material, Settings settings) { super(material, settings); }
}
