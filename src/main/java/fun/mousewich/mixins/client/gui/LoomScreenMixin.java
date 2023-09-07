package fun.mousewich.mixins.client.gui;


import fun.mousewich.entity.ModNbtKeys;
import fun.mousewich.util.dye.ModDyeColor;
import fun.mousewich.util.banners.*;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.LoomScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.text.Text;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mixin(LoomScreen.class)
public abstract class LoomScreenMixin extends HandledScreen<LoomScreenHandler> {
	@Shadow private boolean hasTooManyPatterns;
	@Shadow private List<?> bannerPatterns;

	@Unique private List<ModBannerPatternData> modBannerPatterns = Collections.emptyList();
	@Unique private int modBannerPatternIndex;
	@Unique private static final List<ModBannerPatternData> singleBppPattern = new ArrayList<>();

	public LoomScreenMixin(LoomScreenHandler handler, PlayerInventory inventory, Text title) { super(handler, inventory, title); }
	
	@Redirect(method="<clinit>", at=@At(value="FIELD", target="Lnet/minecraft/block/entity/BannerPattern;COUNT:I"))
	private static int ModifyRowCount() { return BannerPattern.COUNT + ModBannerPattern.valuesWithoutItem().size(); }

	@Redirect(method="drawBackground", at=@At(value="FIELD", target="Lnet/minecraft/block/entity/BannerPattern;COUNT:I"))
	private int ModifyDyePatternCount() { return BannerPattern.COUNT + ModBannerPattern.valuesWithoutItem().size(); }

	@Redirect(method="drawBackground", at=@At(value="INVOKE", target="Lnet/minecraft/screen/LoomScreenHandler;getSelectedPattern()I", ordinal=0))
	private int NegateBannerPatternCmp(LoomScreenHandler self) {
		int res = self.getSelectedPattern();
		return res < 0 ? -res : res;
	}

	@ModifyConstant(method="onInventoryChanged", constant = @Constant(intValue = 6))
	private int SkipPatternLimitCheck(int limit) {
		return Integer.MAX_VALUE;
	}

	@Inject(method="onInventoryChanged", at=@At(value="FIELD", target="Lnet/minecraft/client/gui/screen/ingame/LoomScreen;hasTooManyPatterns:Z", opcode = Opcodes.GETFIELD, ordinal=0))
	private void CheckForModBannerPatternsInFullCondition(CallbackInfo info) {
		ItemStack banner = (this.handler).getBannerSlot().getStack();
		int patternLimit = ModBannerPatternLimitModifier.EVENT.invoker().computePatternLimit(6, MinecraftClient.getInstance().player);
		this.hasTooManyPatterns |= BannerBlockEntity.getPatternCount(banner) >= patternLimit;
	}

	@Inject(method="onInventoryChanged", at=@At("RETURN"))
	private void SaveLoomPatterns(CallbackInfo info) {
		if (this.bannerPatterns != null) {
			ItemStack banner = (this.handler).getOutputSlot().getStack();
			NbtList ls = ModBannerPatternConversions.getModBannerPatternNbt(banner);
			modBannerPatterns = ModBannerPatternConversions.makeModBannerPatternData(ls);
		}
		else modBannerPatterns = Collections.emptyList();
	}

	@ModifyVariable(method="drawBanner", at=@At(value="LOAD", ordinal=0), ordinal=0, argsOnly=true)
	private int ForceBannerPatternIndexWithinRange(int pattern) {
		return (modBannerPatternIndex = pattern) < 0 ? 0 : pattern;
	}

	@Redirect(method="drawBanner", at=@At(value="INVOKE", target="Lnet/minecraft/nbt/NbtCompound;put(Ljava/lang/String;Lnet/minecraft/nbt/NbtElement;)Lnet/minecraft/nbt/NbtElement;", ordinal=0))
	private NbtElement DrawBanner(NbtCompound nbt, String key, NbtElement patterns) {
		singleBppPattern.clear();
		if (modBannerPatternIndex < 0) {
			ModBannerPattern pattern = ModBannerPattern.byOrdinal(modBannerPatternIndex);
			NbtList loomPatterns = new NbtList();
			NbtCompound patternNbtElement = new NbtCompound();
			patternNbtElement.putString(ModNbtKeys.PATTERN, pattern.getId());
			patternNbtElement.putInt(ModNbtKeys.COLOR, 0);
			patternNbtElement.putInt(ModNbtKeys.INDEX, 1);
			loomPatterns.add(patternNbtElement);
			NbtList vanillaPatterns = (NbtList) patterns;
			assert vanillaPatterns.size() == 2 : vanillaPatterns.size();
			vanillaPatterns.remove(1);
			nbt.put(ModNbtKeys.MOD_BANNER_PATTERNS, loomPatterns);
			singleBppPattern.add(new ModBannerPatternData(pattern, ModDyeColor.WHITE, 1));
		}
		ModBannerPatternRenderContext.setModBannerPatterns(singleBppPattern);
		return nbt.put(key, patterns);
	}

	@Inject(method="drawBackground", at=@At(value="INVOKE", target="Lnet/minecraft/client/render/block/entity/BannerBlockEntityRenderer;renderCanvas(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/SpriteIdentifier;ZLjava/util/List;)V"))
	private void SetEmptyModBannerPattern(CallbackInfo info) { ModBannerPatternRenderContext.setModBannerPatterns(modBannerPatterns); }

	@ModifyArg(method="drawBackground", at=@At( value="INVOKE", target="Lnet/minecraft/client/gui/screen/ingame/LoomScreen;drawBanner(III)V", ordinal=0), index=0)
	private int ModifyBannerPatternIndexArgument(int patternIdx) {
		if (patternIdx > BannerPattern.LOOM_APPLICABLE_COUNT) patternIdx = -patternIdx;
		return patternIdx;
	}
}
