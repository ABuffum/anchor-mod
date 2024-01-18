package fun.mousewich.trim;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.mousewich.ModId;
import fun.mousewich.mixins.entity.decoration.ArmorStandEntityAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

import java.util.List;
import java.util.Optional;

@Environment(value= EnvType.CLIENT)
public class TrimmingScreen extends ModForgingScreen<TrimmingScreenHandler> {
	private static final Identifier TEXTURE = ModId.ID("textures/gui/container/trimming.png");
	private static final Text MISSING_TEMPLATE_TOOLTIP = new TranslatableText("container.upgrade.missing_template_tooltip");
	private static final Text ERROR_TOOLTIP = new TranslatableText("container.upgrade.error_tooltip");
	private static final List<Identifier> EMPTY_SLOT_TEXTURES = List.of(
			new Identifier("item/empty_slot_smithing_template_armor_trim"),
			new Identifier("item/empty_slot_smithing_template_netherite_upgrade"));
	public static final Quaternion ARMOR_STAND_ROTATION = Quaternion.method_35821(0.43633232f, 0.0f, (float)Math.PI);
	private final CyclingSlotIcon templateSlotIcon = new CyclingSlotIcon(0);
	private final CyclingSlotIcon baseSlotIcon = new CyclingSlotIcon(1);
	private final CyclingSlotIcon additionsSlotIcon = new CyclingSlotIcon(2);
	private ArmorStandEntity armorStand;

	public TrimmingScreen(TrimmingScreenHandler handler, PlayerInventory playerInventory, Text title) {
		super(handler, playerInventory, title, TEXTURE);
		this.titleX = 44;
		this.titleY = 15;
	}

	@Override
	protected void setup() {
		this.armorStand = new ArmorStandEntity(this.client.world, 0.0, 0.0, 0.0);
		ArmorStandEntityAccessor asea = (ArmorStandEntityAccessor)this.armorStand;
		asea.SetHideBasePlate(true);
		asea.SetShowArms(true);
		this.armorStand.bodyYaw = 210.0f;
		this.armorStand.setPitch(25.0f);
		this.armorStand.headYaw = this.armorStand.getYaw();
		this.armorStand.prevHeadYaw = this.armorStand.getYaw();
		this.equipArmorStand(this.handler.getSlot(3).getStack());
	}

	@Override
	public void handledScreenTick() {
		super.handledScreenTick();
		Optional<SmithingTemplateItem> optional = this.getSmithingTemplate();
		this.templateSlotIcon.updateTexture(EMPTY_SLOT_TEXTURES);
		this.baseSlotIcon.updateTexture(optional.map(SmithingTemplateItem::getEmptyBaseSlotTextures).orElse(List.of()));
		this.additionsSlotIcon.updateTexture(optional.map(SmithingTemplateItem::getEmptyAdditionsSlotTextures).orElse(List.of()));
	}

	private Optional<SmithingTemplateItem> getSmithingTemplate() {
		ItemStack itemStack = this.handler.getSlot(0).getStack();
		if (!itemStack.isEmpty() && itemStack.getItem() instanceof SmithingTemplateItem item) return Optional.of(item);
		return Optional.empty();
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
		this.renderSlotTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		super.drawBackground(matrices, delta, mouseX, mouseY);
		this.templateSlotIcon.render(this.handler, matrices, delta, this.x, this.y);
		this.baseSlotIcon.render(this.handler, matrices, delta, this.x, this.y);
		this.additionsSlotIcon.render(this.handler, matrices, delta, this.x, this.y);
		drawEntity(this.x + 141, this.y + 75, 25, ARMOR_STAND_ROTATION, null, this.armorStand);
	}
	public static void drawEntity(int x, int y, int size, Quaternion quaternionf, Quaternion quaternionf2, LivingEntity entity) {
		MatrixStack matrixStack = RenderSystem.getModelViewStack();
		matrixStack.push();
		matrixStack.translate(x, y, 1050.0f);
		matrixStack.scale(1.0f, 1.0f, -1.0f);
		RenderSystem.applyModelViewMatrix();
		MatrixStack matrixStack2 = new MatrixStack();
		matrixStack2.translate(0.0f, 0.0f, 1000.0f);
		matrixStack2.scale(size, size, size);
		matrixStack2.multiply(quaternionf);
		DiffuseLighting.method_34742();
		EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
		if (quaternionf2 != null) {
			quaternionf2.conjugate();
			entityRenderDispatcher.setRotation(quaternionf2);
		}
		entityRenderDispatcher.setRenderShadows(false);
		VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
		RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, 1.0f, matrixStack2, immediate, 0xF000F0));
		immediate.draw();
		entityRenderDispatcher.setRenderShadows(true);
		matrixStack.pop();
		RenderSystem.applyModelViewMatrix();
		DiffuseLighting.enableGuiDepthLighting();
	}

	@Override
	public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) { if (slotId == 3) this.equipArmorStand(stack); }

	private void equipArmorStand(ItemStack stack) {
		if (this.armorStand == null) return;
		for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) this.armorStand.equipStack(equipmentSlot, ItemStack.EMPTY);
		if (!stack.isEmpty()) {
			ItemStack itemStack = stack.copy();
			Item item = stack.getItem();
			if (item instanceof ArmorItem armorItem) this.armorStand.equipStack(armorItem.getSlotType(), itemStack);
			else this.armorStand.equipStack(EquipmentSlot.OFFHAND, itemStack);
		}
	}

	@Override
	protected void drawInvalidRecipeArrow(MatrixStack matrices, int x, int y) {
		if (this.hasInvalidRecipe()) this.drawTexture(matrices, x + 65, y + 46, this.backgroundWidth, 0, 28, 21);
	}

	private void renderSlotTooltip(MatrixStack matrices, int mouseX, int mouseY) {
		Optional<Text> optional = Optional.empty();
		if (this.hasInvalidRecipe() && this.isPointWithinBounds(65, 46, 28, 21, mouseX, mouseY)) optional = Optional.of(ERROR_TOOLTIP);
		if (this.focusedSlot != null) {
			ItemStack itemStack = this.handler.getSlot(0).getStack();
			ItemStack itemStack2 = this.focusedSlot.getStack();
			if (itemStack.isEmpty()) {
				if (this.focusedSlot.id == 0) optional = Optional.of(MISSING_TEMPLATE_TOOLTIP);
			}
			else {
				Item item = itemStack.getItem();
				if (item instanceof SmithingTemplateItem smithingTemplateItem) {
					if (itemStack2.isEmpty()) {
						if (this.focusedSlot.id == 1) optional = Optional.of(smithingTemplateItem.getBaseSlotDescription());
						else if (this.focusedSlot.id == 2) optional = Optional.of(smithingTemplateItem.getAdditionsSlotDescription());
					}
				}
			}
		}
		optional.ifPresent(text -> this.renderOrderedTooltip(matrices, this.textRenderer.wrapLines(text, 115), mouseX, mouseY));
	}

	private boolean hasInvalidRecipe() {
		return this.handler.getSlot(0).hasStack() && this.handler.getSlot(1).hasStack() && this.handler.getSlot(2).hasStack() && !this.handler.getSlot(this.handler.getResultSlotIndex()).hasStack();
	}
}
