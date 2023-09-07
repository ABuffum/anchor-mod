package fun.mousewich.block.ragdoll;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import fun.mousewich.ModBase;
import fun.mousewich.entity.ModNbtKeys;
import fun.mousewich.mixins.block.entity.SkullBlockEntityAccessor;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.UserCache;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class RagdollBlockEntity extends BlockEntity implements BlockEntityClientSerializable {
	@Nullable
	private GameProfile owner;
	public float prevTick = 0;
	private boolean powered;
	public boolean isPowered() { return powered; }

	private int poseIndex = 0;
	private static final RagdollPose[] POSES = RagdollPose.values();
	public RagdollPose getRagdollPose() { return POSES[poseIndex]; }
	public void nextRagdollPose(int diff) {
		poseIndex += diff;
		if (poseIndex > POSES.length - 1) poseIndex = 0;
		else if (poseIndex < 0) poseIndex = POSES.length - 1;
	}
	private int pitch = 0, roll = 0, yaw = 0;
	public float getRagdollPitch() { return pitch * 22.5f; }
	public void nextRagdollPitch(int diff) {
		pitch += diff;
		if (pitch > 14) pitch = 0;
		else if (pitch < 0) pitch = 15;
	}
	public float getRagdollRoll() { return roll * 22.5f; }
	public void nextRagdollRoll(int diff) {
		roll += diff;
		if (roll > 14) roll = 0;
		else if (roll < 0) roll = 15;
	}
	public float getRagdollYaw() { return yaw * 22.5f; }
	public void nextRagdollYaw(int diff) {
		yaw += diff;
		if (yaw > 14) yaw = 0;
		else if (yaw < 0) yaw = 15;
	}
	private int x = 0, y = 0, z = 0;
	public int getRagdollX() { return x; }
	public void nextRagdollX(int diff) { x += diff; }
	public int getRagdollY() { return y; }
	public void nextRagdollY(int diff) { y += diff; }
	public int getRagdollZ() { return z; }
	public void nextRagdollZ(int diff) { z += diff; }
	private boolean slim;
	public boolean isSlim() { return slim; }
	public void toggleSlim() { slim = !slim; }
	private boolean renderHead = true;
	public boolean renderHead() { return renderHead; }
	public void toggleHead() { renderHead = !renderHead; }
	private boolean renderBody = true;
	public boolean renderBody() { return renderBody; }
	public void toggleBody() { renderBody = !renderBody; }
	private boolean renderLeftArm = true;
	public boolean renderLeftArm() { return renderLeftArm; }
	public void toggleLeftArm() { renderLeftArm = !renderLeftArm; }
	private boolean renderRightArm = true;
	public boolean renderRightArm() { return renderRightArm; }
	public void toggleRightArm() { renderRightArm = !renderRightArm; }
	private boolean renderLeftLeg = true;
	public boolean renderLeftLeg() { return renderLeftLeg; }
	public void toggleLeftLeg() { renderLeftLeg = !renderLeftLeg; }
	private boolean renderRightLeg = true;
	public boolean renderRightLeg() { return renderRightLeg; }
	public void toggleRightLeg() { renderRightLeg = !renderRightLeg; }
	public boolean renderAny() { return renderHead || renderBody || renderLeftArm || renderRightArm || renderLeftLeg || renderRightLeg; }


	public RagdollBlockEntity(BlockPos pos, BlockState state) { super(ModBase.RAGDOLL_BLOCK_ENTITY, pos, state); }

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		if (this.owner != null) {
			NbtCompound nbtCompound = new NbtCompound();
			NbtHelper.writeGameProfile(nbtCompound, this.owner);
			nbt.put(ModNbtKeys.OWNER, nbtCompound);
			nbt.putInt(ModNbtKeys.POSE, poseIndex);
			nbt.putInt(ModNbtKeys.PITCH, pitch);
			nbt.putInt(ModNbtKeys.ROLL, roll);
			nbt.putInt(ModNbtKeys.YAW, yaw);
			nbt.putInt(ModNbtKeys.X, x);
			nbt.putInt(ModNbtKeys.Y, y);
			nbt.putInt(ModNbtKeys.Z, z);
			nbt.putBoolean(ModNbtKeys.SLIM, slim);
			nbt.putBoolean(ModNbtKeys.RENDER_HEAD, renderHead);
			nbt.putBoolean(ModNbtKeys.RENDER_BODY, renderBody);
			nbt.putBoolean(ModNbtKeys.RENDER_LEFT_ARM, renderLeftArm);
			nbt.putBoolean(ModNbtKeys.RENDER_RIGHT_ARM, renderRightArm);
			nbt.putBoolean(ModNbtKeys.RENDER_LEFT_LEG, renderLeftLeg);
			nbt.putBoolean(ModNbtKeys.RENDER_RIGHT_LEG, renderRightLeg);
		}
		return nbt;
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		String string;
		super.readNbt(nbt);
		if (nbt.contains(ModNbtKeys.OWNER, 10)) {
			this.setOwner(NbtHelper.toGameProfile(nbt.getCompound(ModNbtKeys.OWNER)));
		}
		else if (nbt.contains("ExtraType", 8) && !StringUtils.isEmpty(string = nbt.getString("ExtraType"))) {
			this.setOwner(new GameProfile(null, string));
		}
		poseIndex = nbt.contains(ModNbtKeys.POSE, NbtElement.INT_TYPE) ? nbt.getInt(ModNbtKeys.POSE) : 0;
		pitch = nbt.contains(ModNbtKeys.PITCH, NbtElement.INT_TYPE) ? nbt.getInt(ModNbtKeys.PITCH) : 0;
		roll = nbt.contains(ModNbtKeys.ROLL, NbtElement.INT_TYPE) ? nbt.getInt(ModNbtKeys.ROLL) : 0;
		yaw = nbt.contains(ModNbtKeys.YAW, NbtElement.INT_TYPE) ? nbt.getInt(ModNbtKeys.YAW) : 0;
		x = nbt.contains(ModNbtKeys.X, NbtElement.INT_TYPE) ? nbt.getInt(ModNbtKeys.X) : 0;
		y = nbt.contains(ModNbtKeys.Y, NbtElement.INT_TYPE) ? nbt.getInt(ModNbtKeys.Y) : 0;
		z = nbt.contains(ModNbtKeys.Z, NbtElement.INT_TYPE) ? nbt.getInt(ModNbtKeys.Z) : 0;
		slim = nbt.contains(ModNbtKeys.SLIM) && nbt.getBoolean(ModNbtKeys.SLIM);
		renderHead = !nbt.contains(ModNbtKeys.RENDER_HEAD) || nbt.getBoolean(ModNbtKeys.RENDER_HEAD);
		renderBody = !nbt.contains(ModNbtKeys.RENDER_BODY) || nbt.getBoolean(ModNbtKeys.RENDER_BODY);
		renderLeftArm = !nbt.contains(ModNbtKeys.RENDER_LEFT_ARM) || nbt.getBoolean(ModNbtKeys.RENDER_LEFT_ARM);
		renderRightArm = !nbt.contains(ModNbtKeys.RENDER_RIGHT_ARM) || nbt.getBoolean(ModNbtKeys.RENDER_RIGHT_ARM);
		renderLeftLeg = !nbt.contains(ModNbtKeys.RENDER_LEFT_LEG) || nbt.getBoolean(ModNbtKeys.RENDER_LEFT_LEG);
		renderRightLeg = !nbt.contains(ModNbtKeys.RENDER_RIGHT_LEG) || nbt.getBoolean(ModNbtKeys.RENDER_RIGHT_LEG);
	}

	public static void tick(World world, BlockPos pos, BlockState state, RagdollBlockEntity blockEntity) {
		blockEntity.powered = world.isReceivingRedstonePower(pos);
	}

	@Nullable
	public GameProfile getOwner() { return this.owner; }
	@Override
	public void fromClientTag(NbtCompound tag) { this.readNbt(tag); }
	@Override
	public NbtCompound toClientTag(NbtCompound tag) { return this.writeNbt(tag); }

	/*
	 * WARNING - Removed try catching itself - possible behaviour change.
	 */
	public void setOwner(@Nullable GameProfile owner) {
		RagdollBlockEntity entity = this;
		synchronized (entity) { this.owner = owner; }
		this.loadOwnerProperties();
	}

	private void loadOwnerProperties() {
		loadProperties(this.owner, owner -> {
			this.owner = owner;
			this.slim = this.owner != null && (PlayerEntity.getUuidFromProfile(this.owner).hashCode() & 1) == 1;
			this.markDirty();
		});
	}

	public static void loadProperties(@Nullable GameProfile owner, Consumer<GameProfile> callback) {
		UserCache userCache = SkullBlockEntityAccessor.getUserCache();
		MinecraftSessionService sessionService = SkullBlockEntityAccessor.getSessionService();
		Executor executor = SkullBlockEntityAccessor.getExecutor();
		if (owner == null || StringUtils.isEmpty(owner.getName()) || owner.isComplete() && owner.getProperties().containsKey("textures") || userCache == null || sessionService == null) {
			callback.accept(owner);
			return;
		}
		userCache.findByNameAsync(owner.getName(), profile -> Util.getMainWorkerExecutor().execute(() -> Util.ifPresentOrElse(profile, p -> {
			Property property = Iterables.getFirst(p.getProperties().get("textures"), null);
			if (property == null) p = sessionService.fillProfileProperties(p, true);
			GameProfile gameProfile = p;
			executor.execute(() -> {
				userCache.add(gameProfile);
				callback.accept(gameProfile);
			});
		}, () -> executor.execute(() -> callback.accept(owner)))));
	}
}
