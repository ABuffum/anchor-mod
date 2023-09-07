package fun.mousewich.item;

import com.mojang.authlib.GameProfile;
import fun.mousewich.block.ragdoll.RagdollBlockEntity;
import fun.mousewich.entity.ModNbtKeys;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.StringUtils;

public class RagdollItem extends BlockItem {

	public RagdollItem(Block block, Settings settings) { super(block, settings); }

	@Override
	public Text getName(ItemStack stack) {
		NbtCompound nbtCompound;
		if (stack.isOf(Items.PLAYER_HEAD) && stack.hasNbt() && (nbtCompound = stack.getNbt()) != null) {
			NbtCompound nbtCompound2;
			String string = null;
			if (nbtCompound.contains(ModNbtKeys.OWNER, 8)) string = nbtCompound.getString(ModNbtKeys.OWNER);
			else if (nbtCompound.contains(ModNbtKeys.OWNER, 10) && (nbtCompound2 = nbtCompound.getCompound(ModNbtKeys.OWNER)).contains("Name", 8)) {
				string = nbtCompound2.getString("Name");
			}
			if (string != null) return new TranslatableText(this.getTranslationKey() + ".named", string);
		}
		return super.getName(stack);
	}

	@Override
	public void postProcessNbt(NbtCompound nbt) {
		super.postProcessNbt(nbt);
		if (nbt.contains(ModNbtKeys.OWNER, 8) && !StringUtils.isBlank(nbt.getString(ModNbtKeys.OWNER))) {
			GameProfile gameProfile = new GameProfile(null, nbt.getString(ModNbtKeys.OWNER));
			RagdollBlockEntity.loadProperties(gameProfile, profile -> nbt.put(ModNbtKeys.OWNER, NbtHelper.writeGameProfile(new NbtCompound(), profile)));
		}
	}
}
