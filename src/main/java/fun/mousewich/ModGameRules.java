package fun.mousewich;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.mixin.gamerule.GameRulesAccessor;
import net.minecraft.world.GameRules;

public class ModGameRules {
	public static final GameRules.Key<GameRules.BooleanRule> DO_RED_PHANTOM_SPAWNING = GameRulesAccessor.callRegister("doRedPhantomSpawning", GameRules.Category.SPAWNING, GameRuleFactory.createBooleanRule(false));
	public static final GameRules.Key<GameRules.IntRule> PHANTOM_START_DAYS = GameRulesAccessor.callRegister("phantomStartDays", GameRules.Category.SPAWNING, GameRuleFactory.createIntRule(3, 0, 65536));
	public static final GameRules.Key<GameRules.IntRule> RED_PHANTOM_START_DAYS = GameRulesAccessor.callRegister("redPhantomStartDays", GameRules.Category.SPAWNING, GameRuleFactory.createIntRule(7, 0, 65536));
	public static final GameRules.Key<GameRules.BooleanRule> DO_VINES_SPREAD = GameRulesAccessor.callRegister("doVinesSpread", GameRules.Category.UPDATES, GameRuleFactory.createBooleanRule(true));
	public static final GameRules.Key<GameRules.BooleanRule> DO_WARDEN_SPAWNING = GameRulesAccessor.callRegister("doWardenSpawning", GameRules.Category.SPAWNING, GameRuleFactory.createBooleanRule(true));
	public static final GameRules.Key<GameRules.BooleanRule> DO_WARDEN_DARKNESS = GameRulesAccessor.callRegister("doWardenDarkness", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
	public static final GameRules.Key<GameRules.BooleanRule> DO_WARDEN_BLINDNESS = GameRulesAccessor.callRegister("doWardenBlindness", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));

	public static void Initialize() { }
}
