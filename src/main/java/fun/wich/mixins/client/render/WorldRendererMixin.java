package fun.wich.mixins.client.render;

import fun.wich.block.MultifaceGrowthBlock;
import fun.wich.block.basic.ModFenceGateBlock;
import fun.wich.block.basic.ModTrapdoorBlock;
import fun.wich.block.basic.ModDoorBlock;
import fun.wich.block.dust.Brushable;
import fun.wich.block.sculk.SculkShriekerBlock;
import fun.wich.event.ModWorldEvents;
import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.particle.ModParticleTypes;
import fun.wich.particle.SculkChargeParticleEffect;
import fun.wich.particle.ShriekParticleEffect;
import fun.wich.registry.ModBambooRegistry;
import fun.wich.sound.IdentifiedSounds;
import fun.wich.sound.ModSoundEvents;
import fun.wich.util.MixinStore;
import fun.wich.util.ParticleUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.Supplier;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin implements SynchronousResourceReloader {
	@Shadow private ClientWorld world;

	@Redirect(method="tickRainSplashing", at=@At(value="INVOKE", target="Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
	public boolean SizzleRaindrops(BlockState instance, Block block) {
		return (block == Blocks.MAGMA_BLOCK && instance.isIn(ModBlockTags.SIZZLE_RAIN_BLOCKS)) || instance.isOf(block);
	}

	@Inject(method="render", at = @At("HEAD"))
	public void SetTickDelta(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci) {
		MixinStore.worldrenderer_render_tickDelta = tickDelta;
	}

	@Inject(method="processWorldEvent", at = @At("HEAD"), cancellable = true)
	public void ProcessWorldEvent(PlayerEntity source, int eventId, BlockPos pos, int data, CallbackInfo ci) {
		Random random = this.world.random;
		BlockState state = this.world.getBlockState(pos);
		Block block = state.getBlock();
		switch (eventId) {
			case WorldEvents.BLOCK_BROKEN -> {
				BlockState blockState = Block.getStateFromRawId(data);
				SoundEvent breakSound = IdentifiedSounds.getBreakSound(blockState);
				if (breakSound != null) {
					this.world.playSound(pos, breakSound, SoundCategory.BLOCKS, 1f, 0.8f, false);
					this.world.addBlockBreakParticles(pos, blockState);
					ci.cancel();
				}
			}
			case WorldEvents.FENCE_GATE_OPENS -> {
				if (block == ModBambooRegistry.BAMBOO_FENCE_GATE.asBlock() || block == ModBambooRegistry.DRIED_BAMBOO_FENCE_GATE.asBlock()) {
					this.world.playSound(pos, ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
				else if (block == Blocks.CRIMSON_FENCE_GATE || block == Blocks.WARPED_FENCE_GATE) {
					this.world.playSound(pos, ModSoundEvents.BLOCK_NETHER_WOOD_FENCE_GATE_OPEN, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
				else if (block instanceof ModFenceGateBlock fenceGate) {
					this.world.playSound(pos, fenceGate.openSound, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
			}
			case WorldEvents.FENCE_GATE_CLOSES -> {
				if (block == ModBambooRegistry.BAMBOO_FENCE_GATE.asBlock() || block == ModBambooRegistry.DRIED_BAMBOO_FENCE_GATE.asBlock()) {
					this.world.playSound(pos, ModSoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
				else if (block == Blocks.CRIMSON_FENCE_GATE || block == Blocks.WARPED_FENCE_GATE) {
					this.world.playSound(pos, ModSoundEvents.BLOCK_NETHER_WOOD_FENCE_GATE_CLOSE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
				else if (block instanceof ModFenceGateBlock fenceGate) {
					this.world.playSound(pos, fenceGate.closeSound, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
			}
			case WorldEvents.WOODEN_DOOR_OPENS -> {
				if (block == ModBambooRegistry.BAMBOO_DOOR.asBlock() || block == ModBambooRegistry.DRIED_BAMBOO_DOOR.asBlock()) {
					this.world.playSound(pos, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_OPEN, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
				else if (block == Blocks.CRIMSON_DOOR || block == Blocks.WARPED_DOOR) {
					this.world.playSound(pos, ModSoundEvents.BLOCK_NETHER_WOOD_DOOR_OPEN, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
				else if (block instanceof ModDoorBlock door) {
					this.world.playSound(pos, door.openSound, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
			}
			case WorldEvents.WOODEN_DOOR_CLOSES -> {
				if (block == ModBambooRegistry.BAMBOO_DOOR.asBlock() || block == ModBambooRegistry.DRIED_BAMBOO_DOOR.asBlock()) {
					this.world.playSound(pos, ModSoundEvents.BLOCK_BAMBOO_WOOD_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
				else if (block == Blocks.CRIMSON_DOOR || block == Blocks.WARPED_DOOR) {
					this.world.playSound(pos, ModSoundEvents.BLOCK_NETHER_WOOD_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
				else if (block instanceof ModDoorBlock door) {
					this.world.playSound(pos, door.closeSound, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
			}
			case WorldEvents.WOODEN_TRAPDOOR_OPENS -> {
				if (block == ModBambooRegistry.BAMBOO_TRAPDOOR.asBlock() || block == ModBambooRegistry.DRIED_BAMBOO_TRAPDOOR.asBlock()) {
					this.world.playSound(pos, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
				else if (block == Blocks.CRIMSON_TRAPDOOR || block == Blocks.WARPED_TRAPDOOR) {
					this.world.playSound(pos, ModSoundEvents.BLOCK_NETHER_WOOD_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
				else if (block instanceof ModTrapdoorBlock trapdoor) {
					this.world.playSound(pos, trapdoor.openSound, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
			}
			case WorldEvents.WOODEN_TRAPDOOR_CLOSES -> {
				if (block == ModBambooRegistry.BAMBOO_TRAPDOOR.asBlock() || block == ModBambooRegistry.DRIED_BAMBOO_TRAPDOOR.asBlock()) {
					this.world.playSound(pos, ModSoundEvents.BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
				else if (block == Blocks.CRIMSON_TRAPDOOR || block == Blocks.WARPED_TRAPDOOR) {
					this.world.playSound(pos, ModSoundEvents.BLOCK_NETHER_WOOD_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
				else if (block instanceof ModTrapdoorBlock trapdoor) {
					this.world.playSound(pos, trapdoor.closeSound, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
					ci.cancel();
				}
			}
			case ModWorldEvents.SCULK_CHARGE -> {
				int i = data >> 6;
				if (i > 0) {
					if (random.nextFloat() < 0.3f + (float) i * 0.1f) {
						float v = 0.15f + 0.02f * (float) i * (float) i * random.nextFloat();
						float w = 0.4f + 0.3f * (float) i * random.nextFloat();
						this.world.playSound(pos, ModSoundEvents.BLOCK_SCULK_CHARGE, SoundCategory.BLOCKS, v, w, false);
					}
					byte b = (byte) (data & 0x3F);
					UniformIntProvider intProvider = UniformIntProvider.create(0, i);
					Supplier<Vec3d> supplier = () -> new Vec3d(MathHelper.nextDouble(random, -0.005f, 0.005f), MathHelper.nextDouble(random, -0.005f, 0.005f), MathHelper.nextDouble(random, -0.005f, 0.005f));
					if (b == 0) {
						for (Direction direction2 : Direction.values()) {
							float ad = direction2 == Direction.DOWN ? (float) Math.PI : 0.0f;
							double g = direction2.getAxis() == Direction.Axis.Y ? 0.65 : 0.57;
							ParticleUtil.spawnParticles(this.world, pos, new SculkChargeParticleEffect(ad), intProvider, direction2, supplier, g);
						}
					}
					else {
						for (Direction direction3 : MultifaceGrowthBlock.flagToDirections(b)) {
							float ae = direction3 == Direction.UP ? (float) Math.PI : 0.0f;
							ParticleUtil.spawnParticles(this.world, pos, new SculkChargeParticleEffect(ae), intProvider, direction3, supplier, 0.35);
						}
					}
				}
				else {
					this.world.playSound(pos, ModSoundEvents.BLOCK_SCULK_CHARGE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
					boolean bl = this.world.getBlockState(pos).isFullCube(this.world, pos);
					int k = bl ? 40 : 20;
					float ac = bl ? 0.45f : 0.25f;
					for (int t = 0; t < k; ++t) {
						float ah = 2.0f * random.nextFloat() - 1.0f;
						float ae = 2.0f * random.nextFloat() - 1.0f;
						float ai = 2.0f * random.nextFloat() - 1.0f;
						this.world.addParticle(ModParticleTypes.SCULK_CHARGE_POP, (double) pos.getX() + 0.5 + (double) (ah * ac), (double) pos.getY() + 0.5 + (double) (ae * ac), (double) pos.getZ() + 0.5 + (double) (ai * ac), ah * 0.07f, ae * 0.07f, ai * 0.07f);
					}
				}
				ci.cancel();
			}
			case ModWorldEvents.SCULK_SHRIEKS -> {
				for (int j = 0; j < 10; ++j) {
					this.world.addParticle(new ShriekParticleEffect(j * 5), false, (double) pos.getX() + 0.5, (double) pos.getY() + SculkShriekerBlock.TOP, (double) pos.getZ() + 0.5, 0.0, 0.0, 0.0);
				}
				this.world.playSound((double) pos.getX() + 0.5, (double) pos.getY() + SculkShriekerBlock.TOP, (double) pos.getZ() + 0.5, ModSoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK, SoundCategory.BLOCKS, 2.0f, 0.6f + this.world.random.nextFloat() * 0.4f, false);
				ci.cancel();
			}
			case ModWorldEvents.SUSPICIOUS_SAND_BRUSH -> {
				BlockState blockState2 = Block.getStateFromRawId(data);
				if (blockState2.getBlock() instanceof Brushable brushable) {
					this.world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
							brushable.getBrushCompleteSound(), SoundCategory.PLAYERS, 1.0f, 1.0f, false);
				}
				this.world.addBlockBreakParticles(pos, blockState2);
				ci.cancel();
			}
			case ModWorldEvents.SNIFFER_EGG_PLACE -> {
				net.minecraft.client.util.ParticleUtil.spawnParticle(this.world, pos, ModParticleTypes.EGG_CRACK, data == 1 ? UniformIntProvider.create(3, 6) : UniformIntProvider.create(1, 3));
				ci.cancel();
			}
		}
	}
}
