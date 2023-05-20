package fun.mousewich;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.mixin.gamerule.GameRulesAccessor;
import net.minecraft.world.GameRules;

public class ModGameRules {
	public static final GameRules.Key<GameRules.BooleanRule> DO_WARDEN_SPAWNING = GameRulesAccessor.callRegister("doWardenSpawning", GameRules.Category.SPAWNING, GameRuleFactory.createBooleanRule(true));
	public static final GameRules.Key<GameRules.BooleanRule> DO_WARDEN_DARKNESS = GameRulesAccessor.callRegister("doWardenDarkness", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
	public static final GameRules.Key<GameRules.BooleanRule> DO_WARDEN_BLINDNESS = GameRulesAccessor.callRegister("doWardenBlindness", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
	public static final GameRules.Key<GameRules.BooleanRule> DO_VINES_SPREAD = GameRulesAccessor.callRegister("doVinesSpread", GameRules.Category.UPDATES, GameRuleFactory.createBooleanRule(true));

	public static void Initialize() { }
}
