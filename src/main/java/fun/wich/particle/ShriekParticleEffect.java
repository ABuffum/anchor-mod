package fun.wich.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;

import java.util.Locale;

public class ShriekParticleEffect implements ParticleEffect {
	public static final Codec<ShriekParticleEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("delay").forGetter(particleEffect -> particleEffect.delay))
			.apply(instance, delay -> new ShriekParticleEffect((int)delay)));
	public static final Factory<ShriekParticleEffect> FACTORY = new Factory<ShriekParticleEffect>(){

		@Override
		public ShriekParticleEffect read(ParticleType<ShriekParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException {
			stringReader.expect(' ');
			int i = stringReader.readInt();
			return new ShriekParticleEffect(i);
		}

		@Override
		public ShriekParticleEffect read(ParticleType<ShriekParticleEffect> particleType, PacketByteBuf packetByteBuf) {
			return new ShriekParticleEffect(packetByteBuf.readVarInt());
		}
	};
	private final int delay;

	public ShriekParticleEffect(int i) { this.delay = i; }

	@Override
	public void write(PacketByteBuf buf) { buf.writeVarInt(this.delay); }

	@Override
	public String asString() {
		return String.format(Locale.ROOT, "%s %d", Registry.PARTICLE_TYPE.getId(this.getType()), this.delay);
	}

	public ParticleType<ShriekParticleEffect> getType() { return ModParticleTypes.SHRIEK; }

	public int getDelay() { return this.delay; }
}