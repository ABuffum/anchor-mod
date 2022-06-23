package haven.anchors;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.*;
import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;
import java.util.HashMap;
import static java.util.Map.entry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("deprecation")
public class Anchors implements ModInitializer {
	public static Logger LOGGER = LogManager.getLogger();
	
	public static BlockEntityType<AnchorBlockEntity> ANCHOR_BLOCK_ENTITY;

	@Override
	public void onInitialize() {
		//log(Level.INFO, "Anchoring The Pig!");
		
        Registry.register(Registry.BLOCK, new Identifier("haven", "anchor"), AnchorBlock.ANCHOR_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("haven", "anchor"), new BlockItem(AnchorBlock.ANCHOR_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
        
        ANCHOR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "haven:anchor_block_entity", FabricBlockEntityTypeBuilder.create(AnchorBlockEntity::new, AnchorBlock.ANCHOR_BLOCK).build(null));
        
        //Anchor core items
		for(Integer owner : ANCHOR_MAP.keySet()) {
        	Registry.register(Registry.ITEM, new Identifier("haven", ANCHOR_MAP.get(owner) + "_core"), ANCHOR_CORES.get(owner));
		}
	}
	
	public static Map<Integer, String> ANCHOR_MAP = Map.ofEntries(
		entry(1, "jackdaw"),
		entry(2, "august"),
		entry(3, "bird"),
		entry(4, "moth"),
		entry(5, "rat"),
		entry(6, "stars"),
		entry(7, "whimsy"),
		entry(8, "aster"),
		entry(9, "gawain"),
		entry(10, "sleep"),
		entry(11, "lux"),
		entry(12, "sylph"),
		entry(13, "angel"),
		entry(14, "captain"),
		entry(15, "oz"),
		entry(16, "navn"),
		entry(17, "amber"),
		entry(18, "kota"),
		entry(19, "rhys"),
		entry(20, "soleil"),
		entry(21, "dj"),
		entry(22, "miasma"),
		entry(23, "k")
	);
	public static Map<Integer, AnchorCoreItem> ANCHOR_CORES;

	public static void log(Level level, String message){
		LOGGER.log(level, "[Haven] " + message);
	}
	
	static {
		ANCHOR_CORES = new HashMap<Integer, AnchorCoreItem>();
		for(Integer owner : ANCHOR_MAP.keySet()) {
			ANCHOR_CORES.put(owner, new AnchorCoreItem(owner));
		}
	}
}
