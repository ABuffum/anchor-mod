package fun.wich.mixins.block.entity;

import fun.wich.entity.ModNbtKeys;
import fun.wich.util.dye.ModDyeColor;
import fun.wich.util.dye.ModDyedSign;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlockEntity.class)
public abstract class SignBlockEntityMixin extends BlockEntity implements ModDyedSign {
	@Shadow protected abstract void updateListeners();
	@Shadow private DyeColor textColor;
	@Shadow public abstract DyeColor getTextColor();

	private ModDyeColor modTextColor = null;

	public SignBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) { super(type, pos, state); }

	@Inject(method="writeNbt", at=@At("TAIL"))
	protected void WriteNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
		nbt.putString(ModNbtKeys.COLOR, GetModTextColor().getName());
	}

	@Inject(method="readNbt", at=@At("TAIL"))
	public void ReadNbt(NbtCompound nbt, CallbackInfo ci) {
		this.modTextColor = ModDyeColor.byName(nbt.getString(ModNbtKeys.COLOR), ModDyeColor.BLACK);
	}

	@Inject(method="setTextColor", at=@At(value="INVOKE", target="Lnet/minecraft/block/entity/SignBlockEntity;updateListeners()V"))
	private void SetTextColor(DyeColor value, CallbackInfoReturnable<Boolean> cir) {
		this.modTextColor = ModDyeColor.byId(value.getId());
	}

	@Override
	public ModDyeColor GetModTextColor() {
		if (this.modTextColor == null) {
			this.modTextColor = ModDyeColor.byId(this.getTextColor().getId());
		}
		return this.modTextColor;
	}
	@Override
	public boolean SetModTextColor(ModDyeColor value) {
		if (value != this.GetModTextColor()) {
			this.textColor = DyeColor.byId(value.getId());
			this.modTextColor = value;
			this.updateListeners();
			return true;
		}
		return false;
	}
}
