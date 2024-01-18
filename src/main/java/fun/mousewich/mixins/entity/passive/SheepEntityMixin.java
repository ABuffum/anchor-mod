package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.ModNbtKeys;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.entity.passive.sheep.MossySheepEntity;
import fun.mousewich.entity.passive.sheep.RainbowSheepEntity;
import fun.mousewich.gen.loot.ModLootTables;
import fun.mousewich.item.basic.ModDyeItem;
import fun.mousewich.util.ColorUtil;
import fun.mousewich.util.dye.ModDyeColor;
import fun.mousewich.util.dye.ModDyedSheep;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity implements Shearable, ModDyedSheep, EntityWithBloodType {
	@SuppressWarnings("WrongEntityDataParameterClass")
	private static final TrackedData<Boolean> SHEARED = DataTracker.registerData(SheepEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	@Shadow @Final private static TrackedData<Byte> COLOR;

	protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initDataTracker", at=@At("RETURN"))
	protected void InitDataTracker(CallbackInfo ci) { this.dataTracker.startTracking(SHEARED, false); }

	@Inject(method="getLootTableId", at=@At("HEAD"), cancellable=true)
	public void GetLootTableIdForModDyes(CallbackInfoReturnable<Identifier> cir) {
		if (this.isSheared()) return;
		ModDyeColor color = this.GetModColor();
		if (color == ModDyeColor.BEIGE) cir.setReturnValue(ModLootTables.BEIGE_SHEEP_ENTITY);
		else if (color == ModDyeColor.BURGUNDY) cir.setReturnValue(ModLootTables.BURGUNDY_SHEEP_ENTITY);
		else if (color == ModDyeColor.LAVENDER) cir.setReturnValue(ModLootTables.LAVENDER_SHEEP_ENTITY);
		else if (color == ModDyeColor.MINT) cir.setReturnValue(ModLootTables.MINT_SHEEP_ENTITY);
	}

	/**
	 * @author mousewich
	 * @reason moving the data tracker for whether the sheep is sheared to a value distinct from color
	 */
	@Overwrite
	public boolean isSheared() { return this.getDataTracker().get(SHEARED); }
	/**
	 * @author mousewich
	 * @reason moving the data tracker for whether the sheep is sheared to a value distinct from color
	 */
	@Overwrite
	public void setSheared(boolean sheared) { this.dataTracker.set(SHEARED, sheared); }

	@Override
	public ModDyeColor GetModColor() { return ModDyeColor.byId(this.dataTracker.get(COLOR)); }
	@Inject(method="setColor", at=@At("RETURN"))
	private void SetColor(DyeColor color, CallbackInfo ci) { this.dataTracker.set(COLOR, (byte)color.getId()); }
	@Override
	public void SetModColor(ModDyeColor color) { this.dataTracker.set(COLOR, (byte)color.getId()); }
	@Override
	public ModDyeColor GetChildModColor(AnimalEntity firstParent, AnimalEntity secondParent) {
		ModDyeColor dyeColor = ((ModDyedSheep)firstParent).GetModColor();
		ModDyeColor dyeColor2 = ((ModDyedSheep)secondParent).GetModColor();
		CraftingInventory craftingInventory = CreateDyeMixingCraftingInventory(dyeColor, dyeColor2);
		return this.world.getRecipeManager()
				.getFirstMatch(RecipeType.CRAFTING, craftingInventory, this.world)
				.map(recipe -> recipe.craft(craftingInventory))
				.map(ItemStack::getItem)
				.filter(item -> item instanceof DyeItem || item instanceof ModDyeItem)
				.map(item -> {
					if (item instanceof DyeItem dye) return ModDyeColor.byId(dye.getColor().getId());
					else return ((ModDyeItem)item).getColor();
				})
				.orElseGet(() -> this.world.random.nextBoolean() ? dyeColor : dyeColor2);
	}
	private static CraftingInventory CreateDyeMixingCraftingInventory(ModDyeColor firstColor, ModDyeColor secondColor) {
		CraftingInventory craftingInventory = new CraftingInventory(new ScreenHandler(null, -1){
			@Override
			public boolean canUse(PlayerEntity player) { return false; }
		}, 2, 1);
		DyeColor unmodded = DyeColor.byId(firstColor.getId());
		craftingInventory.setStack(0, new ItemStack(unmodded.getId() == firstColor.getId() ? DyeItem.byColor(unmodded) : ModDyeItem.byColor(firstColor)));
		unmodded = DyeColor.byId(secondColor.getId());
		craftingInventory.setStack(1, new ItemStack(unmodded.getId() == secondColor.getId() ? DyeItem.byColor(unmodded) : ModDyeItem.byColor(secondColor)));
		return craftingInventory;
	}

	@Redirect(method="writeCustomDataToNbt", at=@At(value="INVOKE", target="Lnet/minecraft/nbt/NbtCompound;putByte(Ljava/lang/String;B)V"))
	private void OverrideColorWrite(NbtCompound instance, String key, byte value) {
		if (key.equals(ModNbtKeys.COLOR)) instance.putByte(key, (byte)this.GetModColor().getId());
		else instance.putByte(key, value);
	}
	@Inject(method="readCustomDataFromNbt", at=@At("RETURN"))
	public void OverrideColorRead(NbtCompound nbt, CallbackInfo ci) {
		this.SetModColor(ModDyeColor.byId(nbt.getByte(ModNbtKeys.COLOR)));
	}

	@Redirect(method="sheared", at=@At(value="INVOKE", target="Lnet/minecraft/entity/passive/SheepEntity;dropItem(Lnet/minecraft/item/ItemConvertible;I)Lnet/minecraft/entity/ItemEntity;"))
	private ItemEntity OverrideWoolDrop(SheepEntity instance, ItemConvertible itemConvertible, int i) {
		return this.dropItem(ColorUtil.GetWoolItem(this.GetModColor()));
	}

	@Override
	public boolean canBreedWith(AnimalEntity other) {
		if (other == this) return false;
		EntityType<?> type = other.getType();
		if (!(type == ModBase.MOSSY_SHEEP_ENTITY || type == ModBase.RAINBOW_SHEEP_ENTITY || type == getType())) return false;
		else return this.isInLove() && other.isInLove();
	}

	@Inject(method="createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/SheepEntity;", at=@At("RETURN"))
	public void CreateChild(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<SheepEntity> cir) {
		ModDyedSheep sheep = (ModDyedSheep)cir.getReturnValue();
		if (sheep != null) {
			if (passiveEntity instanceof MossySheepEntity) sheep.SetModColor(GetModColor());
			else if (passiveEntity instanceof RainbowSheepEntity) sheep.SetModColor(ColorUtil.GetRandomColor(random));
			else sheep.SetModColor(this.GetChildModColor(this, (SheepEntity)passiveEntity));
		}
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.SHEEP_BLOOD_TYPE; }
}
