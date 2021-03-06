package haven.anchors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class AnchorBlockEntity extends BlockEntity {
    public AnchorBlockEntity(BlockPos pos, BlockState state) {
        super(Anchors.ANCHOR_BLOCK_ENTITY, pos, state);
        this.pos = pos;
        update(state);
    }
    
    private static final Identifier ERROR_TEXTURE = new Identifier("haven", "textures/anchor/0.png");
    
    private int owner = 0;
    public final BlockPos pos;
    
    private static HashMap<Integer, Identifier> TEXTURE_IDS;
    
    public void update(BlockState state) {
    	int owner = state.get(AnchorBlock.OWNER);
    	if (owner != this.owner) {
    		this.owner = owner;
            if (!TEXTURE_IDS.containsKey(owner)) {
            	if (Identifier.isValid("haven:textures/anchor/" + owner + ".png")) {
                	TEXTURE_IDS.put(owner, new Identifier("haven", "textures/anchor/" + owner + ".png"));
            	}
            }
    	}
    }
    
    public static void tick(World world, BlockPos pos, BlockState state, AnchorBlockEntity be) {
    	if (be != null) be.update(state);
    }
    
    public int getOwner() {
    	return owner;
    }
    public Identifier getTextureId() {
    	if (!TEXTURE_IDS.containsKey(owner)) return ERROR_TEXTURE;
    	else return TEXTURE_IDS.get(owner);
    }
    
    static {
    	TEXTURE_IDS = new HashMap<Integer, Identifier>();
    	TEXTURE_IDS.put(0, ERROR_TEXTURE);
    }
}
