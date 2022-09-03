package haven.util;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.sound.BlockSoundGroup;

public class HavenFlower extends PottedBlock {

	public static final Block.Settings SETTINGS = FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS);

	public HavenFlower(StatusEffect effect, int effectDuration) {
		this(effect, effectDuration, SETTINGS);
	}
	public HavenFlower(StatusEffect effect, int effectDuration, AbstractBlock.Settings settings) {
		super(new FlowerBlock(effect, effectDuration, settings));
	}
}
