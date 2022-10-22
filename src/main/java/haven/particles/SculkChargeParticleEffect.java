package haven.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import haven.HavenMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.registry.Registry;

import java.util.Locale;

public record SculkChargeParticleEffect(float roll) implements ParticleEffect {
	public static final Codec<SculkChargeParticleEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.FLOAT.fieldOf("roll").forGetter(particleEffect -> particleEffect.roll))
			.apply(instance, roll -> new SculkChargeParticleEffect((float)roll)));
	public static final ParticleEffect.Factory<SculkChargeParticleEffect> FACTORY = new ParticleEffect.Factory<SculkChargeParticleEffect>(){
		@Override
		public SculkChargeParticleEffect read(ParticleType<SculkChargeParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
			stringReader.expect(' ');
			float f = stringReader.readFloat();
			return new SculkChargeParticleEffect(f);
		}
		@Override
		public SculkChargeParticleEffect read(ParticleType<SculkChargeParticleEffect> particleType, PacketByteBuf packetByteBuf) {
			return new SculkChargeParticleEffect(packetByteBuf.readFloat());
		}
	};

	public ParticleType<SculkChargeParticleEffect> getType() { return HavenMod.SCULK_CHARGE_PARTICLE; }

	@Override
	public void write(PacketByteBuf buf) { buf.writeFloat(this.roll); }

	@Override
	public String asString() {
		return String.format(Locale.ROOT, "%s %.2f", Registry.PARTICLE_TYPE.getId(this.getType()), Float.valueOf(this.roll));
	}
}
