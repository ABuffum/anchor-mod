package fun.wich.entity.ai.goal;

import fun.wich.entity.ModMerchant;
import net.minecraft.entity.ai.goal.Goal;

public class TryRestockingGoal extends Goal {
	public final ModMerchant merchant;
	public TryRestockingGoal(ModMerchant merchant) { this.merchant = merchant; }
	@Override
	public boolean canStart() { return this.merchant.canRestock(); }
	@Override
	public void start() { if (this.merchant.shouldRestock()) this.merchant.restock(); }
}
