package fun.wich.entity.passive;

import fun.wich.ModBase;
import fun.wich.entity.ai.goal.llama.EatFernGoal;
import fun.wich.entity.ai.goal.llama.FrolicGoal;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.world.World;

public class JollyLlamaEntity extends LlamaEntity {
	protected int eatFernTimer;
	protected EatFernGoal eatFernGoal;
	protected int frolicTimer;
	protected FrolicGoal frolicGoal;
	public boolean canFrolic;

	public JollyLlamaEntity(EntityType<? extends JollyLlamaEntity> entityType, World world) { super(entityType, world); }

	@Override
	protected LlamaEntity createChild() {
		return ModBase.JOLLY_LLAMA_ENTITY.create(this.world);
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(5, this.eatFernGoal = new EatFernGoal(this));
		this.goalSelector.add(1, this.frolicGoal = new FrolicGoal(this, 2, 1200));
	}

	@Override
	protected void mobTick() {
		this.eatFernTimer = this.eatFernGoal.getTimer();
		this.frolicTimer = this.frolicGoal.getTimer();
		if (this.isAlive() && this.random.nextInt(1000) < this.ambientSoundChance++) {
			this.ambientSoundChance = -this.getMinAmbientSoundDelay();
			this.playSound(ModSoundEvents.ENTITY_JOLLY_LLAMA_BELL, 1, 1);
		}
		super.mobTick();
	}

	@Override
	public void tickMovement() {
		if (this.world.isClient) {
			this.eatFernTimer = Math.max(0, this.eatFernTimer - 1);
			this.frolicTimer = Math.max(0, this.frolicTimer - 1);
		}
		super.tickMovement();
	}

	@Override
	public void handleStatus(byte status) {
		if (status == 10) this.eatFernTimer = 40;
		else super.handleStatus(status);
	}

	public void onEatingFern() {
		this.canFrolic = true;
		this.playSound(ModSoundEvents.ENTITY_JOLLY_LLAMA_EAT_FERN, 1, 1);
	}
}
