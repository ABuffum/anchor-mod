package fun.wich.gen.data.language;

import fun.wich.util.dye.ModDyeColor;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class ModLanguageCache {
	public final Map<String, String> TranslationKeys = new HashMap<>();
	public abstract String getLanguageCode();

	public abstract String Subtitle_Block_Hit(String name);
	public abstract String Subtitle_Block_Break(String name);
	public abstract String Subtitle_Block_Place(String name);
	public abstract String Subtitle_Block_Step(String name);

	public abstract String Subtitle_Boat_Paddle(String name);
	public abstract String Subtitle_Boat_Row(String name);

	public abstract String Subtitle_Entity_Big_Fall(String name);
	public abstract String Subtitle_Entity_Crash(String name);
	public abstract String Subtitle_Entity_Death(String name);
	public abstract String Subtitle_Entity_Small_Fall(String name);
	public abstract String Subtitle_Entity_Splash(String name);
	public abstract String Subtitle_Entity_Splash_High_Speed(String name);
	public abstract String Subtitle_Entity_Step(String name);
	public abstract String Subtitle_Entity_Swim(String name);

	public static class en_us extends ModLanguageCache {
		@Override
		public String getLanguageCode() { return "en_us"; }

		private String _add(String prefix, String postfix) { return (prefix + " " + postfix).trim(); }
		private Pair<String, String> _apply(Function<String, String> func, Pair<String, String> pair) {
			return new Pair<>(func.apply(pair.getLeft()), func.apply(pair.getRight()));
		}

		public String a(String word) { return _add(word, "a"); }
		public String advances(String word) { return _add(word, "advances"); }
		public String agrees(String word) { return _add(word, "agrees"); }
		public String allays(String word) { return _add(word, "allays"); }
		public String and(String word) { return _add(word, "and"); }
		public String angrily(String word) { return _add(word, "angrily"); }
		public String applied(String word) { return _add(word, "applied"); }
		public String approaches(String word) { return _add(word, "approaches"); }
		public String attacks(String word) { return _add(word, "attacks"); }
		public String awkwardly(String word) { return _add(word, "awkwardly"); }
		public String bagged(String word) { return _add(word, "bagged"); }
		public String beats(String word) { return _add(word, "beats"); }
		public String blooms(String word) { return _add(word, "blooms"); }
		public String book(String word) { return _add(word, "book"); }
		public String booms(String word) { return _add(word, "booms"); }
		public String boot(String word) { return _add(word, "boot"); }
		public String breaking(String word) { return _add(word, "breaking"); }
		public String broken(String word) { return _add(word, "broken"); }
		public String bubbles(String word) { return _add(word, "bubbles"); }
		public String burned(String word) { return _add(word, "burned"); }
		public String cannonballs(String word) { return _add(word, "cannonballs"); }
		public String casts(String word) { return _add(word, "casts"); }
		public String charges(String word) { return _add(word, "charges"); }
		public String cheers(String word) { return _add(word, "cheers"); }
		public String chimes(String word) { return _add(word, "chimes"); }
		public String chivalrously(String word) { return _add(word, "chivalrously"); }
		public String chortles(String word) { return _add(word, "chortles"); }
		public String clacking(String word) { return _add(word, "clacking"); }
		public String clangs(String word) { return _add(word, "clangs"); }
		public String click(String word) { return _add(word, "click"); }
		public String clicking(String word) { return _add(word, "clicking"); }
		public String close(String word) { return _add(word, "close"); }
		public String closes(String word) { return _add(word, "closes"); }
		public String collected(String word) { return _add(word, "collected"); }
		public String completed(String word) { return _add(word, "completed"); }
		public String converts(String word) { return _add(word, "converts"); }
		public String cracks(String word) { return _add(word, "cracks"); }
		public String crashed(String word) { return _add(word, "crashed"); }
		public String crashes(String word) { return _add(word, "crashes"); }
		public String creaks(String word) { return _add(word, "creaks"); }
		public String croaks(String word) { return _add(word, "croaks"); }
		public String crunches(String word) { return _add(word, "crunches"); }
		public String delights(String word) { return _add(word, "delights"); }
		public String deployed(String word) { return _add(word, "deployed"); }
		public String dies(String word) { return _add(word, "dies"); }
		public String digs(String word) { return _add(word, "digs"); }
		public String disagrees(String word) { return _add(word, "disagrees"); }
		public String down(String word) { return _add(word, "down"); }
		public String draws(String word) { return _add(word, "draws"); }
		public String dropped(String word) { return _add(word, "dropped"); }
		public String drops(String word) { return _add(word, "drops"); }
		public String eats(String word) { return _add(word, "eats"); }
		public String emerges(String word) { return _add(word, "emerges"); }
		public String empties(String word) { return _add(word, "empties"); }
		public String equips(String word) { return _add(word, "equips"); }
		public String explodes(String word) { return _add(word, "explodes"); }
		public String fell(String word) { return _add(word, "fell"); }
		public String fills(String word) { return _add(word, "fills"); }
		public String fizzes(String word) { return _add(word, "fizzes"); }
		public String flies(String word) { return _add(word, "flies"); }
		public String flops(String word) { return _add(word, "flops"); }
		public String footsteps(String word) { return _add(word, "footsteps"); }
		public String _for(String word) { return _add(word, "for"); }
		public String gallops(String word) { return _add(word, "gallops"); }
		public String glugs(String word) { return _add(word, "glugs"); }
		public String goos(String word) { return _add(word, "goos"); }
		public String groans(String word) { return _add(word, "groans"); }
		public String grunts(String word) { return _add(word, "grunts"); }
		public String hard(String word) { return _add(word, "hard"); }
		public String hatches(String word) { return _add(word, "hatches"); }
		public String heart(String word) { return _add(word, "heart"); }
		public String here(String word) { return _add(word, "here"); }
		public String hisses(String word) { return _add(word, "hisses"); }
		public String hit(String word) { return _add(word, "hit"); }
		public String hot(String word) { return _add(word, "hot"); }
		public String howls(String word) { return _add(word, "howls"); }
		public String hurts(String word) { return _add(word, "hurts"); }
		public String in(String word) { return _add(word, "in"); }
		public String injected(String word) { return _add(word, "injected"); }
		public String into(String word) { return _add(word, "into"); }
		public String jingles(String word) { return _add(word, "jingles"); }
		public String juiced(String word) { return _add(word, "juiced"); }
		public String jumps(String word) { return _add(word, "jumps"); }
		public String lands(String word) { return _add(word, "lands"); }
		public String lays(String word) { return _add(word, "lays"); }
		public String lightly(String word) { return _add(word, "lightly"); }
		public String loaded(String word) { return _add(word, "loaded"); }
		public String loudly(String word) { return _add(word, "loudly"); }
		public String marching(String word) { return _add(word, "marching"); }
		public String medium(String word) { return _add(word, "medium"); }
		public String melts(String word) { return _add(word, "melts"); }
		public String moos(String word) { return _add(word, "moos"); }
		public String murmurs(String word) { return _add(word, "murmurs"); }
		public String notice(String word) { return _add(word, "notice"); }
		public String of(String word) { return _add(word, "of"); }
		public String on(String word) { return _add(word, "on"); }
		public String opens(String word) { return _add(word, "opens"); }
		public String paddles(String word) { return _add(word, "paddles"); }
		public String placed(String word) { return _add(word, "placed"); }
		public String plays(String word) { return _add(word, "plays"); }
		public String porcelain(String word) { return _add(word, "porcelain"); }
		public String prances(String word) { return _add(word, "prances"); }
		public String prepares(String word) { return _add(word, "prepares"); }
		public String quietly(String word) { return _add(word, "quietly"); }
		public String rages(String word) { return _add(word, "rages"); }
		public String rattles(String word) { return _add(word, "rattles"); }
		public String recovers(String word) { return _add(word, "recovers"); }
		public String released(String word) { return _add(word, "released"); }
		public String resonates(String word) { return _add(word, "resonates"); }
		public String roars(String word) { return _add(word, "roars"); }
		public String saddled(String word) { return _add(word, "saddled"); }
		public String scents(String word) { return _add(word, "scents"); }
		public String scuttles(String word) { return _add(word, "scuttles"); }
		public String scooped(String word) { return _add(word, "scooped"); }
		public String searches(String word) { return _add(word, "searches"); }
		public String seed(String word) { return _add(word, "seed"); }
		public String seeks(String word) { return _add(word, "seeks"); }
		public String shatters(String word) { return _add(word, "shatters"); }
		public String shoots(String word) { return _add(word, "shoots"); }
		public String shrieks(String word) { return _add(word, "shrieks"); }
		public String sits(String word) { return _add(word, "sits"); }
		public String sizzles(String word) { return _add(word, "sizzles"); }
		public String sloshing(String word) { return _add(word, "sloshing"); }
		public String small(String word) { return _add(word, "small"); }
		public String sniffs(String word) { return _add(word, "sniffs"); }
		public String softly(String word) { return _add(word, "softly"); }
		public String spawn(String word) { return _add(word, "spawn"); }
		public String spell(String word) { return _add(word, "spell"); }
		public String spits(String word) { return _add(word, "spits"); }
		public String splashes(String word) { return _add(word, "splashes"); }
		public String splashing(String word) { return _add(word, "splashing"); }
		public String splats(String word) { return _add(word, "splats"); }
		public String sploshes(String word) { return _add(word, "sploshes"); }
		public String spreads(String word) { return _add(word, "spreads"); }
		public String squeezed(String word) { return _add(word, "squeezed"); }
		public String squelches(String word) { return _add(word, "squelches"); }
		public String stands(String word) { return _add(word, "stands"); }
		public String starts(String word) { return _add(word, "starts"); }
		public String steps(String word) { return _add(word, "steps"); }
		public String stomping(String word) { return _add(word, "stomping"); }
		public String stomps(String word) { return _add(word, "stomps"); }
		public String stops(String word) { return _add(word, "stops"); }
		public String summoning(String word) { return _add(word, "summoning"); }
		public String swims(String word) { return _add(word, "swims"); }
		public String taken(String word) { return _add(word, "taken"); }
		public String takes(String word) { return _add(word, "takes"); }
		public String tapping(String word) { return _add(word, "tapping"); }
		public String tendrils(String word) { return _add(word, "tendrils"); }
		public String the(String word) { return _add(word, "the"); }
		public String to(String word) { return _add(word, "to"); }
		public String tosses(String word) { return _add(word, "tosses"); }
		public String trips(String word) { return _add(word, "trips"); }
		public String undignified(String word) { return _add(word, "undignified"); }
		public String up(String word) { return _add(word, "up"); }
		public String used(String word) { return _add(word, "used"); }
		public String wetly(String word) { return _add(word, "wetly"); }
		public String whines(String word) { return _add(word, "whines"); }
		public String with() { return "with"; } public String with(String word) { return _add(word, with()); }
		public String works(String word) { return _add(word, "works"); }
		public String yearns(String word) { return _add(word, "yearns"); }
		public String yeets(String word) { return _add(word, "yeets"); }

		public String Acacia() { return "Acacia"; } public String Acacia(String word) { return _add(word, Acacia()); }
		public String Active() { return "Active"; } public String Active(String word) { return _add(word, Active()); }
		public String Add() { return "Add"; } public String Add(String word) { return _add(word, Add()); }
		public String Admire() { return "Admire"; } public String Admire(String word) { return _add(word, Admire()); }
		public String Aggressive() { return "Aggressive"; } public String Aggressive(String word) { return _add(word, Aggressive()); }
		public String Albino() { return "Albino"; } public String Albino(String word) { return _add(word, Albino()); }
		public String All() { return "All"; } public String All(String word) { return _add(word, All()); }
		public String Allay() { return "Allay"; } public String Allay(String word) { return _add(word, Allay()); }
		public String Allium() { return "Allium"; } public String Allium(String word) { return _add(word, Allium()); }
		public String Aloof() { return "Aloof"; } public String Aloof(String word) { return _add(word, Aloof()); }
		public String Amaranth() { return "Amaranth"; } public String Amaranth(String word) { return _add(word, Amaranth()); }
		public String Amber() { return "Amber"; } public String Amber(String word) { return _add(word, Amber()); }
		public String Amethyst() { return "Amethyst"; } public String Amethyst(String word) { return _add(word, Amethyst()); }
		public String Anatomy() { return "Anatomy"; } public String Anatomy(String word) { return _add(word, Anatomy()); }
		public String Anchor() { return "Anchor"; } public String Anchor(String word) { return _add(word, Anchor()); }
		public String Ancient() { return "Ancient"; } public String Ancient(String word) { return _add(word, Ancient()); }
		public String Andesite() { return "Andesite"; } public String Andesite(String word) { return _add(word, Andesite()); }
		public String Angel() { return "Angel"; } public String Angel(String word) { return _add(word, Angel()); }
		public String Angler() { return "Angler"; } public String Angler(String word) { return _add(word, Angler()); }
		public String Anvil() { return "Anvil"; } public String Anvil(String word) { return _add(word, Anvil()); }
		public String Apple() { return "Apple"; } public String Apple(String word) { return _add(word, Apple()); }
		public String Archer() { return "Archer"; } public String Archer(String word) { return _add(word, Archer()); }
		public String Armor() { return "Armor"; } public String Armor(String word) { return _add(word, Armor()); }
		public String Arms() { return "Arms"; } public String Arms(String word) { return _add(word, Arms()); }
		public String ArmsUp() { return Up(Arms()); } public String ArmsUp(String word) { return Up(Arms(word)); }
		public String Arrow() { return "Arrow"; } public String Arrow(String word) { return _add(word, Arrow()); }
		public String Ashen() { return "Ashen"; } public String Ashen(String word) { return _add(word, Ashen()); }
		public String Aster() { return "Aster"; } public String Aster(String word) { return _add(word, Aster()); }
		public String Attached() { return "Attached"; } public String Attached(String word) { return _add(word, Attached()); }
		public String Axe() { return "Axe"; } public String Axe(String word) { return _add(word, Axe()); }
		public String Axolotl() { return "Axolotl"; } public String Axolotl(String word) { return _add(word, Axolotl()); }
		public String Azalea() { return "Azalea"; } public String Azalea(String word) { return _add(word, Azalea()); }
		public String Azure() { return "Azure"; } public String Azure(String word) { return _add(word, Azure()); }
		public String Baked() { return "Baked"; } public String Baked(String word) { return _add(word, Baked()); }
		public String BakedPotato() { return Baked(Potato()); } public String BakedPotato(String word) { return Baked(Potato(word)); }
		public String Bale() { return "Bale"; } public String Bale(String word) { return _add(word, Bale()); }
		public String Ball() { return "Ball"; } public String Ball(String word) { return _add(word, Ball()); }
		public String Bamboo() { return "Bamboo"; } public String Bamboo(String word) { return _add(word, Bamboo()); }
		public String Banner() { return "Banner"; } public String Banner(String word) { return _add(word, Banner()); }
		public String Bark() { return "Bark"; } public String Bark(String word) { return _add(word, Bark()); }
		public String Barrel() { return "Barrel"; } public String Barrel(String word) { return _add(word, Barrel()); }
		public String Bars() { return "Bars"; } public String Bars(String word) { return _add(word, Bars()); }
		public String Basalt() { return "Basalt"; } public String Basalt(String word) { return _add(word, Basalt()); }
		public String Bat() { return "Bat"; } public String Bat(String word) { return _add(word, Bat()); }
		public String Beacon() { return "Beacon"; } public String Beacon(String word) { return _add(word, Beacon()); }
		public String Bean() { return "Bean"; } public String Bean(String word) { return _add(word, Bean()); }
		public String Beans() { return "Beans"; } public String Beans(String word) { return _add(word, Beans()); }
		public String Bear() { return "Bear"; } public String Bear(String word) { return _add(word, Bear()); }
		public String Bed() { return "Bed"; } public String Bed(String word) { return _add(word, Bed()); }
		public String Bedrock() { return "Bedrock"; } public String Bedrock(String word) { return _add(word, Bedrock()); }
		public String Bee() { return "Bee"; } public String Bee(String word) { return _add(word, Bee()); }
		public String Beehive() { return "Beehive"; } public String Beehive(String word) { return _add(word, Beehive()); }
		public String Beef() { return "Beef"; } public String Beef(String word) { return _add(word, Beef()); }
		public String Beetroot() { return "Beetroot"; } public String Beetroot(String word) { return _add(word, Beetroot()); }
		public String Beetroots() { return "Beetroots"; } public String Beetroots(String word) { return _add(word, Beetroots()); }
		public String Beige() { return "Beige"; } public String Beige(String word) { return _add(word, Beige()); }
		public String Bell() { return "Bell"; } public String Bell(String word) { return _add(word, Bell()); }
		public String Berries() { return "Berries"; } public String Berries(String word) { return _add(word, Berries()); }
		public String Berry() { return "Berry"; } public String Berry(String word) { return _add(word, Berry()); }
		public String Big() { return "Big"; } public String Big(String word) { return _add(word, Big()); }
		public String Birch() { return "Birch"; } public String Birch(String word) { return _add(word, Birch()); }
		public String Black() { return "Black"; } public String Black(String word) { return _add(word, Black()); }
		public String Blast() { return "Blast"; } public String Blast(String word) { return _add(word, Blast()); }
		public String Blackstone() { return "Blackstone"; } public String Blackstone(String word) { return _add(word, Blackstone()); }
		public String Blade() { return "Blade"; } public String Blade(String word) { return _add(word, Blade()); }
		public String Blaze() { return "Blaze"; } public String Blaze(String word) { return _add(word, Blaze()); }
		public String Bleeding() { return "Bleeding"; } public String Bleeding(String word) { return _add(word, Bleeding()); }
		public String Blinding() { return "Blinding"; } public String Blinding(String word) { return _add(word, Blinding()); }
		public String Blindness() { return "Blindness"; } public String Blindness(String word) { return _add(word, Blindness()); }
		public String Block() { return "Block"; } public String Block(String word) { return _add(word, Block()); }
		public String Blood() { return "Blood"; } public String Blood(String word) { return _add(word, Blood()); }
		public String Blossom() { return "Blossom"; } public String Blossom(String word) { return _add(word, Blossom()); }
		public String Blue() { return "Blue"; } public String Blue(String word) { return _add(word, Blue()); }
		public String Bluet() { return "Bluet"; } public String Bluet(String word) { return _add(word, Bluet()); }
		public String Boat() { return "Boat"; } public String Boat(String word) { return _add(word, Boat()); }
		public Pair<String, String> _Boat(String word) { return new Pair<>(Boat(word), ChestBoat(word)); }
		public String Bone() { return "Bone"; } public String Bone(String word) { return _add(word, Bone()); }
		public String Boo() { return "Boo"; } public String Boo(String word) { return _add(word, Boo()); }
		public String Book() { return "Book"; } public String Book(String word) { return _add(word, Book()); }
		public String Bookshelf() { return "Bookshelf"; } public String Bookshelf(String word) { return _add(word, Bookshelf()); }
		public String Boots() { return "Boots"; } public String Boots(String word) { return _add(word, Boots()); }
		public String Bottle() { return "Bottle"; } public String Bottle(String word) { return _add(word, Bottle()); }
		public String Bottled() { return "Bottled"; } public String Bottled(String word) { return _add(word, Bottled()); }
		public String Bowl() { return "Bowl"; } public String Bowl(String word) { return _add(word, Bowl()); }
		public String Box() { return "Box"; } public String Box(String word) { return _add(word, Box()); }
		public String Bread() { return "Bread"; } public String Bread(String word) { return _add(word, Bread()); }
		public String Breath() { return "Breath"; } public String Breath(String word) { return _add(word, Breath()); }
		public String Brew() { return "Brew"; } public String Brew(String word) { return _add(word, Brew()); }
		public String Brewer() { return "Brewer"; } public String Brewer(String word) { return _add(word, Brewer()); }
		public String Brewing() { return "Brewing"; } public String Brewing(String word) { return _add(word, Brewing()); }
		public String Brick() { return "Brick"; } public String Brick(String word) { return _add(word, Brick()); }
		public String Bricks() { return "Bricks"; } public String Bricks(String word) { return _add(word, Bricks()); }
		public String British() { return "British"; } public String British(String word) { return _add(word, British()); }
		public String Broken() { return "Broken"; } public String Broken(String word) { return _add(word, Broken()); }
		public String Broom() { return "Broom"; } public String Broom(String word) { return _add(word, Broom()); }
		public String Bronze() { return "Bronze"; } public String Bronze(String word) { return _add(word, Bronze()); }
		public String Bronzed() { return "Bronzed"; } public String Bronzed(String word) { return _add(word, Bronzed()); }
		public String Brown() { return "Brown"; } public String Brown(String word) { return _add(word, Brown()); }
		public String Brush() { return "Brush"; } public String Brush(String word) { return _add(word, Brush()); }
		public String Brushable() { return "Brushable"; } public String Brushable(String word) { return _add(word, Brushable()); }
		public String Brushing() { return "Brushing"; } public String Brushing(String word) { return _add(word, Brushing()); }
		public String Brute() { return "Brute"; } public String Brute(String word) { return _add(word, Brute()); }
		public String Bucket() { return "Bucket"; } public String Bucket(String word) { return _add(word, Bucket()); }
		public String BucketOf() { return of(Bucket()); } public String BucketOf(String word) { return of(Bucket(word)); }
		public String Bud() { return "Bud"; } public String Bud(String word) { return _add(word, Bud()); }
		public String Budding() { return "Budding"; } public String Budding(String word) { return _add(word, Budding()); }
		public String Burgundy() { return "Burgundy"; } public String Burgundy(String word) { return _add(word, Burgundy()); }
		public String Burn() { return "Burn"; } public String Burn(String word) { return _add(word, Burn()); }
		public String Burnt() { return "Burnt"; } public String Burnt(String word) { return _add(word, Burnt()); }
		public String Bush() { return "Bush"; } public String Bush(String word) { return _add(word, Bush()); }
		public String Buttercup() { return "Buttercup"; } public String Buttercup(String word) { return _add(word, Buttercup()); }
		public String Button() { return "Button"; } public String Button(String word) { return _add(word, Button()); }
		public String Cabbage() { return "Cabbage"; } public String Cabbage(String word) { return _add(word, Cabbage()); }
		public String Cabbages() { return "Cabbages"; } public String Cabbages(String word) { return _add(word, Cabbages()); }
		public String Cactus() { return "Cactus"; } public String Cactus(String word) { return _add(word, Cactus()); }
		public String Cake() { return "Cake"; } public String Cake(String word) { return _add(word, Cake()); }
		public String Calcite() { return "Calcite"; } public String Calcite(String word) { return _add(word, Calcite()); }
		public String Calibrated() { return "Calibrated"; } public String Calibrated(String word) { return _add(word, Calibrated()); }
		public String Calico() { return "Calico"; } public String Calico(String word) { return _add(word, Calico()); }
		public String Call() { return "Call"; } public String Call(String word) { return _add(word, Call()); }
		public String Camel() { return "Camel"; } public String Camel(String word) { return _add(word, Camel()); }
		public String Campfire() { return "Campfire"; } public String Campfire(String word) { return _add(word, Campfire()); }
		public String Candle() { return "Candle"; } public String Candle(String word) { return _add(word, Candle()); }
		public String Candy() { return "Candy"; } public String Candy(String word) { return _add(word, Candy()); }
		public String Cane() { return "Cane"; } public String Cane(String word) { return _add(word, Cane()); }
		public String Cap() { return "Cap"; } public String Cap(String word) { return _add(word, Cap()); }
		public String Caramel() { return "Caramel"; } public String Caramel(String word) { return _add(word, Caramel()); }
		public String Carnation() { return "Carnation"; } public String Carnation(String word) { return _add(word, Carnation()); }
		public String Carpet() { return "Carpet"; } public String Carpet(String word) { return _add(word, Carpet()); }
		public String Carpeted() { return "Carpeted"; } public String Carpeted(String word) { return _add(word, Carpeted()); }
		public String Carrot() { return "Carrot"; } public String Carrot(String word) { return _add(word, Carrot()); }
		public String Carrots() { return "Carrots"; } public String Carrots(String word) { return _add(word, Carrots()); }
		public String Cartography() { return "Cartography"; } public String Cartography(String word) { return _add(word, Cartography()); }
		public String Carved() { return "Carved"; } public String Carved(String word) { return _add(word, Carved()); }
		public String Cassia() { return "Cassia"; } public String Cassia(String word) { return _add(word, Cassia()); }
		public String Cat() { return "Cat"; } public String Cat(String word) { return _add(word, Cat()); }
		public String Catalyst() { return "Catalyst"; } public String Catalyst(String word) { return _add(word, Catalyst()); }
		public String Catalyzing() { return "Catalyzing"; } public String Catalyzing(String word) { return _add(word, Catalyzing()); }
		public String Cauldron() { return "Cauldron"; } public String Cauldron(String word) { return _add(word, Cauldron()); }
		public String Cave() { return "Cave"; } public String Cave(String word) { return _add(word, Cave()); }
		public String Chain() { return "Chain"; } public String Chain(String word) { return _add(word, Chain()); }
		public String Charcoal() { return "Charcoal"; } public String Charcoal(String word) { return _add(word, Charcoal()); }
		public String Charge() { return "Charge"; } public String Charge(String word) { return _add(word, Charge()); }
		public String Charred() { return "Charred"; } public String Charred(String word) { return _add(word, Charred()); }
		public String Cheese() { return "Cheese"; } public String Cheese(String word) { return _add(word, Cheese()); }
		public String Cherry() { return "Cherry"; } public String Cherry(String word) { return _add(word, Cherry()); }
		public String Chest() { return "Chest"; } public String Chest(String word) { return _add(word, Chest()); }
		public String Chestplate() { return "Chestplate"; } public String Chestplate(String word) { return _add(word, Chestplate()); }
		public String ChestBoat() { return Chest(with(Boat())); } public String ChestBoat(String word) { return _add(word, ChestBoat()); }
		public String Chevon() { return "Chevon"; } public String Chevon(String word) { return _add(word, Chevon()); }
		public String Chicken() { return "Chicken"; } public String Chicken(String word) { return _add(word, Chicken()); }
		public String Chip() { return "Chip"; } public String Chip(String word) { return _add(word, Chip()); }
		public String Chiseled() { return "Chiseled"; } public String Chiseled(String word) { return _add(word, Chiseled()); }
		public String ChiseledBookshelf() { return Bookshelf(Chiseled()); } public String ChiseledBookshelf(String word) { return Bookshelf(Chiseled(word)); }
		public String Chocolate() { return "Chocolate"; } public String Chocolate(String word) { return _add(word, Chocolate()); }
		public String Chorus() { return "Chorus"; } public String Chorus(String word) { return _add(word, Chorus()); }
		public String Chum() { return "Chum"; } public String Chum(String word) { return _add(word, Chum()); }
		public String Chunk() { return "Chunk"; } public String Chunk(String word) { return _add(word, Chunk()); }
		public String Chunkeater() { return "Chunkeater"; } public String Chunkeater(String word) { return _add(word, Chunkeater()); }
		public String Cider() { return "Cider"; } public String Cider(String word) { return _add(word, Cider()); }
		public String Cinnamon() { return "Cinnamon"; } public String Cinnamon(String word) { return _add(word, Cinnamon()); }
		public String Cindering() { return "Cindering"; } public String Cindering(String word) { return _add(word, Cindering()); }
		public String Clay() { return "Clay"; } public String Clay(String word) { return _add(word, Clay()); }
		public String Cleaving() { return "Cleaving"; } public String Cleaving(String word) { return _add(word, Cleaving()); }
		public String Cloud() { return "Cloud"; } public String Cloud(String word) { return _add(word, Cloud()); }
		public String Club() { return "Club"; } public String Club(String word) { return _add(word, Club()); }
		public String Cluster() { return "Cluster"; } public String Cluster(String word) { return _add(word, Cluster()); }
		public String Clumsy() { return "Clumsy"; } public String Clumsy(String word) { return _add(word, Clumsy()); }
		public String Coal() { return "Coal"; } public String Coal(String word) { return _add(word, Coal()); }
		public String Coarse() { return "Coarse"; } public String Coarse(String word) { return _add(word, Coarse()); }
		public String Coast() { return "Coast"; } public String Coast(String word) { return _add(word, Coast()); }
		public String Cobbled() { return "Cobbled"; } public String Cobbled(String word) { return _add(word, Cobbled()); }
		public String Cobblestone() { return "Cobblestone"; } public String Cobblestone(String word) { return _add(word, Cobblestone()); }
		public String Cobweb() { return "Cobweb"; } public String Cobweb(String word) { return _add(word, Cobweb()); }
		public String Cocoa() { return "Cocoa"; } public String Cocoa(String word) { return _add(word, Cocoa()); }
		public String Cod() { return "Cod"; } public String Cod(String word) { return _add(word, Cod()); }
		public String Coffee() { return "Coffee"; } public String Coffee(String word) { return _add(word, Coffee()); }
		public String Coin() { return "Coin"; } public String Coin(String word) { return _add(word, Coin()); }
		public String Coins() { return "Coins"; } public String Coins(String word) { return _add(word, Coins()); }
		public String Cold() { return "Cold"; } public String Cold(String word) { return _add(word, Cold()); }
		public String Color() { return "Color"; } public String Color(String word) { return _add(word, Color()); }
		public String Color(DyeColor color) {
			return switch (color) {
				case WHITE -> White(); case ORANGE -> Orange(); case MAGENTA -> Magenta(); case LIGHT_BLUE -> Blue(Light());
				case YELLOW -> Yellow(); case LIME -> Lime(); case PINK -> Pink(); case GRAY -> Gray();
				case LIGHT_GRAY -> Gray(Light()); case CYAN -> Cyan(); case PURPLE -> Purple(); case BLUE -> Blue();
				case BROWN -> Brown(); case GREEN -> Green(); case RED -> Red(); case BLACK -> Black();
			};
		}
		public String Color(DyeColor color, String word) { return _add(word, Color(color)); }
		public String Color(ModDyeColor color) {
			DyeColor dye = DyeColor.byId(color.getId());
			if (color.getId() == dye.getId()) return Color(dye);
			else if (color == ModDyeColor.BEIGE) return Beige();
			else if (color == ModDyeColor.BURGUNDY) return Burgundy();
			else if (color == ModDyeColor.LAVENDER) return Lavender();
			else if (color == ModDyeColor.MINT) return Mint();
			else return null;
		}
		public String Color(ModDyeColor color, String word) { return _add(word, Color(color)); }
		public String Committed() { return "Committed"; } public String Committed(String word) { return _add(word, Committed()); }
		public String Compass() { return "Compass"; } public String Compass(String word) { return _add(word, Compass()); }
		public String Components() { return "Components"; } public String Components(String word) { return _add(word, Components()); }
		public String Composter() { return "Composter"; } public String Composter(String word) { return _add(word, Composter()); }
		public String Concrete() { return "Concrete"; } public String Concrete(String word) { return _add(word, Concrete()); }
		public String Cone() { return "Cone"; } public String Cone(String word) { return _add(word, Cone()); }
		public String Conduit() { return "Conduit"; } public String Conduit(String word) { return _add(word, Conduit()); }
		public String Confetti() { return "Confetti"; } public String Confetti(String word) { return _add(word, Confetti()); }
		public String Confident() { return "Confident"; } public String Confident(String word) { return _add(word, Confident()); }
		public String Cooked() { return "Cooked"; } public String Cooked(String word) { return _add(word, Cooked()); }
		public String Cookie() { return "Cookie"; } public String Cookie(String word) { return _add(word, Cookie()); }
		public String Cooled() { return "Cooled"; } public String Cooled(String word) { return _add(word, Cooled()); }
		public String Copper() { return "Copper"; } public String Copper(String word) { return _add(word, Copper()); }
		public String Coral() { return "Coral"; } public String Coral(String word) { return _add(word, Coral()); }
		public String Core() { return "Core"; } public String Core(String word) { return _add(word, Core()); }
		public String Corn() { return "Corn"; } public String Corn(String word) { return _add(word, Corn()); }
		public String Cornflower() { return "Cornflower"; } public String Cornflower(String word) { return _add(word, Cornflower()); }
		public String Cottage() { return "Cottage"; } public String Cottage(String word) { return _add(word, Cottage()); }
		public String Cotton() { return "Cotton"; } public String Cotton(String word) { return _add(word, Cotton()); }
		public String Cow() { return "Cow"; } public String Cow(String word) { return _add(word, Cow()); }
		public String Cracked() { return "Cracked"; } public String Cracked(String word) { return _add(word, Cracked()); }
		public String Crafting() { return "Crafting"; } public String Crafting(String word) { return _add(word, Crafting()); }
		public String Cream() { return "Cream"; } public String Cream(String word) { return _add(word, Cream()); }
		public String Creamy() { return "Creamy"; } public String Creamy(String word) { return _add(word, Creamy()); }
		public String Creeper() { return "Creeper"; } public String Creeper(String word) { return _add(word, Creeper()); }
		public String Crest() { return "Crest"; } public String Crest(String word) { return _add(word, Crest()); }
		public String Crested() { return "Crested"; } public String Crested(String word) { return _add(word, Crested()); }
		public String Crimson() { return "Crimson"; } public String Crimson(String word) { return _add(word, Crimson()); }
		public String Crop() { return "Crop"; } public String Crop(String word) { return _add(word, Crop()); }
		public String Crown() { return "Crown"; } public String Crown(String word) { return _add(word, Crown()); }
		public String Crushing() { return "Crushing"; } public String Crushing(String word) { return _add(word, Crushing()); }
		public String Crying() { return "Crying"; } public String Crying(String word) { return _add(word, Crying()); }
		public String Crystal() { return "Crystal"; } public String Crystal(String word) { return _add(word, Crystal()); }
		public String Cube() { return "Cube"; } public String Cube(String word) { return _add(word, Cube()); }
		public String Cut() { return "Cut"; } public String Cut(String word) { return _add(word, Cut()); }
		public String Cyan() { return "Cyan"; } public String Cyan(String word) { return _add(word, Cyan()); }
		public String Cyanide() { return "Cyanide"; } public String Cyanide(String word) { return _add(word, Cyanide()); }
		public String Dairy() { return "Dairy"; } public String Dairy(String word) { return _add(word, Dairy()); }
		public String Daisy() { return "Daisy"; } public String Daisy(String word) { return _add(word, Daisy()); }
		public String Dandelion() { return "Dandelion"; } public String Dandelion(String word) { return _add(word, Dandelion()); }
		public String Danger() { return "Danger"; } public String Danger(String word) { return _add(word, Danger()); }
		public String Dark() { return "Dark"; } public String Dark(String word) { return _add(word, Dark()); }
		public String DarkOak() { return Oak(Dark()); } public String DarkOak(String word) { return Oak(Dark(word)); }
		public String Darkness() { return "Darkness"; } public String Darkness(String word) { return _add(word, Darkness()); }
		public String Daylight() { return "Daylight"; } public String Daylight(String word) { return _add(word, Daylight()); }
		public String Dead() { return "Dead"; } public String Dead(String word) { return _add(word, Dead()); }
		public String Death() { return "Death"; } public String Death(String word) { return _add(word, Death()); }
		public String Debris() { return "Debris"; } public String Debris(String word) { return _add(word, Debris()); }
		public String Decoration() { return "Decoration"; } public String Decoration(String word) { return _add(word, Decoration()); }
		public String Deep() { return "Deep"; } public String Deep(String word) { return _add(word, Deep()); }
		public String Deepslate() { return "Deepslate"; } public String Deepslate(String word) { return _add(word, Deepslate()); }
		public String Delayed() { return "Delayed"; } public String Delayed(String word) { return _add(word, Delayed()); }
		public String Dense() { return "Dense"; } public String Dense(String word) { return _add(word, Dense()); }
		public String Detector() { return "Detector"; } public String Detector(String word) { return _add(word, Detector()); }
		public String Deterioration() { return "Deterioration"; } public String Deterioration(String word) { return _add(word, Deterioration()); }
		public String Devouring() { return "Devouring"; } public String Devouring(String word) { return _add(word, Devouring()); }
		public String Diamond() { return "Diamond"; } public String Diamond(String word) { return _add(word, Diamond()); }
		public String Die() { return "Die"; } public String Die(String word) { return _add(word, Die()); }
		public String Diorite() { return "Diorite"; } public String Diorite(String word) { return _add(word, Diorite()); }
		public String Dirt() { return "Dirt"; } public String Dirt(String word) { return _add(word, Dirt()); }
		public String Dirty() { return "Dirty"; } public String Dirty(String word) { return _add(word, Dirty()); }
		public String Disc() { return "Disc"; } public String Disc(String word) { return _add(word, Disc()); }
		public String Dispenser() { return "Dispenser"; } public String Dispenser(String word) { return _add(word, Dispenser()); }
		public String Distilled() { return "Distilled"; } public String Distilled(String word) { return _add(word, Distilled()); }
		public String Dogwood() { return "Dogwood"; } public String Dogwood(String word) { return _add(word, Dogwood()); }
		public String Dolphin() { return "Dolphin"; } public String Dolphin(String word) { return _add(word, Dolphin()); }
		public String Donkey() { return "Donkey"; } public String Donkey(String word) { return _add(word, Donkey()); }
		public String Door() { return "Door"; } public String Door(String word) { return _add(word, Door()); }
		public String Dragon() { return "Dragon"; } public String Dragon(String word) { return _add(word, Dragon()); }
		public String Dream() { return "Dream"; } public String Dream(String word) { return _add(word, Dream()); }
		public String Dried() { return "Dried"; } public String Dried(String word) { return _add(word, Dried()); }
		public String Drinkable() { return "Drinkable"; } public String Drinkable(String word) { return _add(word, Drinkable()); }
		public String Dripleaf() { return "Dripleaf"; } public String Dripleaf(String word) { return _add(word, Dripleaf()); }
		public String Dripstone() { return "Dripstone"; } public String Dripstone(String word) { return _add(word, Dripstone()); }
		public String Dropped() { return "Dropped"; } public String Dropped(String word) { return _add(word, Dropped()); }
		public String Dropper() { return "Dropper"; } public String Dropper(String word) { return _add(word, Dropper()); }
		public String Drowned() { return "Drowned"; } public String Drowned(String word) { return _add(word, Drowned()); }
		public String Dune() { return "Dune"; } public String Dune(String word) { return _add(word, Dune()); }
		public String Dye() { return "Dye"; } public String Dye(String word) { return _add(word, Dye()); }
		public String Echo() { return "Echo"; } public String Echo(String word) { return _add(word, Echo()); }
		public String Egg() { return "Egg"; } public String Egg(String word) { return _add(word, Egg()); }
		public String Elder() { return "Elder"; } public String Elder(String word) { return _add(word, Elder()); }
		public String Emerald() { return "Emerald"; } public String Emerald(String word) { return _add(word, Emerald()); }
		public String Empty() { return "Empty"; } public String Empty(String word) { return _add(word, Empty()); }
		public String Enchanted() { return "Enchanted"; } public String Enchanted(String word) { return _add(word, Enchanted()); }
		public String Enchanting() { return "Enchanting"; } public String Enchanting(String word) { return _add(word, Enchanting()); }
		public String End() { return "End"; } public String End(String word) { return _add(word, End()); }
		public String Ender() { return "Ender"; } public String Ender(String word) { return _add(word, Ender()); }
		public String EnderLantern() { return Lantern(Ender()); } public String EnderLantern(String word) { return Lantern(Ender(word)); }
		public Pair<String, String> EnderTorch() { return _Torch(Ender()); } public Pair<String, String> EnderTorch(String word) { return _Torch(Ender(word)); }
		public String Enderman() { return "Enderman"; } public String Enderman(String word) { return _add(word, Enderman()); }
		public String Endermite() { return "Endermite"; } public String Endermite(String word) { return _add(word, Endermite()); }
		public String Equipment() { return "Equipment"; } public String Equipment(String word) { return _add(word, Equipment()); }
		public String Evoker() { return "Evoker"; } public String Evoker(String word) { return _add(word, Evoker()); }
		public String Excited() { return "Excited"; } public String Excited(String word) { return _add(word, Excited()); }
		public String Experience() { return "Experience"; } public String Experience(String word) { return _add(word, Experience()); }
		public String Experimental() { return "Experimental"; } public String Experimental(String word) { return _add(word, Experimental()); }
		public String Explorer() { return "Explorer"; } public String Explorer(String word) { return _add(word, Explorer()); }
		public String Exposed() { return "Exposed"; } public String Exposed(String word) { return _add(word, Exposed()); }
		public String Eye() { return "Eye"; } public String Eye(String word) { return _add(word, Eye()); }
		public String Faceting() { return "Faceting"; } public String Faceting(String word) { return _add(word, Faceting()); }
		public String Fall() { return "Fall"; } public String Fall(String word) { return _add(word, Fall()); }
		public String Fan() { return "Fan"; } public String Fan(String word) { return _add(word, Fan()); }
		public String Fancy() { return "Fancy"; } public String Fancy(String word) { return _add(word, Fancy()); }
		public String Farmland() { return "Farmland"; } public String Farmland(String word) { return _add(word, Farmland()); }
		public String Fatigue() { return "Farmland"; } public String Fatigue(String word) { return _add(word, Fatigue()); }
		public String Feather() { return "Feather"; } public String Feather(String word) { return _add(word, Feather()); }
		public String Feathers() { return "Feathers"; } public String Feathers(String word) { return _add(word, Feathers()); }
		public String Fence() { return "Fence"; } public String Fence(String word) { return _add(word, Fence()); }
		public String Fern() { return "Fern"; } public String Fern(String word) { return _add(word, Fern()); }
		public String Fire() { return "Fire"; } public String Fire(String word) { return _add(word, Fire()); }
		public String Fireball() { return "Fireball"; } public String Fireball(String word) { return _add(word, Fireball()); }
		public String Firebird() { return "Firebird"; } public String Firebird(String word) { return _add(word, Firebird()); }
		public String Firing() { return "Firing"; } public String Firing(String word) { return _add(word, Firing()); }
		public String Fish() { return "Fish"; } public String Fish(String word) { return _add(word, Fish()); }
		public String Flashbanged() { return "Flashbanged"; } public String Flashbanged(String word) { return _add(word, Flashbanged()); }
		public String Flecked() { return "Flecked"; } public String Flecked(String word) { return _add(word, Flecked()); }
		public String Fleece() { return "Fleece"; } public String Fleece(String word) { return _add(word, Fleece()); }
		public String Fletching() { return "Fletching"; } public String Fletching(String word) { return _add(word, Fletching()); }
		public String Flint() { return "Flint"; } public String Flint(String word) { return _add(word, Flint()); }
		public Pair<String, String> _FlowerParts(String word) { return new Pair<>(Seeds(word), Petals(word)); }
		public String Flower() { return "Flower"; } public String Flower(String word) { return _add(word, Flower()); }
		public String Flowering() { return "Flowering"; } public String Flowering(String word) { return _add(word, Flowering()); }
		public String Flowers() { return "Flowers"; } public String Flowers(String word) { return _add(word, Flowers()); }
		public String Flowing() { return "Flowing"; } public String Flowing(String word) { return _add(word, Flowing()); }
		public String Fluid() { return "Fluid"; } public String Fluid(String word) { return _add(word, Fluid()); }
		public String Footed() { return "Footed"; } public String Footed(String word) { return _add(word, Footed()); }
		public String Footsteps() { return "Footsteps"; } public String Footsteps(String word) { return _add(word, Footsteps()); }
		public String Fox() { return "Fox"; } public String Fox(String word) { return _add(word, Fox()); }
		public String Fragment() { return "Fragment"; } public String Fragment(String word) { return _add(word, Fragment()); }
		public String Frame() { return "Frame"; } public String Frame(String word) { return _add(word, Frame()); }
		public String Freezing() { return "Freezing"; } public String Freezing(String word) { return _add(word, Freezing()); }
		public String Frenzied() { return "Frenzied"; } public String Frenzied(String word) { return _add(word, Frenzied()); }
		public String Frenzy() { return "Frenzy"; } public String Frenzy(String word) { return _add(word, Frenzy()); }
		public String Friend() { return "Friend"; } public String Friend(String word) { return _add(word, Friend()); }
		public String Frog() { return "Frog"; } public String Frog(String word) { return _add(word, Frog()); }
		public String Froglight() { return "Froglight"; } public String Froglight(String word) { return _add(word, Froglight()); }
		public String Frogspawn() { return "Frogspawn"; } public String Frogspawn(String word) { return _add(word, Frogspawn()); }
		public String Frozen() { return "Frozen"; } public String Frozen(String word) { return _add(word, Frozen()); }
		public String Fruit() { return "Fruit"; } public String Fruit(String word) { return _add(word, Fruit()); }
		public String Fry() { return "Fry"; } public String Fry(String word) { return _add(word, Fry()); }
		public String Fungus() { return "Fungus"; } public String Fungus(String word) { return _add(word, Fungus()); }
		public String Furnace() { return "Furnace"; } public String Furnace(String word) { return _add(word, Furnace()); }
		public String Fuse() { return "Fuse"; } public String Fuse(String word) { return _add(word, Fuse()); }
		public String Fuzzy() { return "Fuzzy"; } public String Fuzzy(String word) { return _add(word, Fuzzy()); }
		public String Gate() { return "Gate"; } public String Gate(String word) { return _add(word, Gate()); }
		public String Ghast() { return "Ghast"; } public String Ghast(String word) { return _add(word, Ghast()); }
		public String Giant() { return "Giant"; } public String Giant(String word) { return _add(word, Giant()); }
		public String Gilded() { return "Gilded"; } public String Gilded(String word) { return _add(word, Gilded()); }
		public String Glass() { return "Glass"; } public String Glass(String word) { return _add(word, Glass()); }
		public String GlassPane() { return Pane(Glass()); } public String GlassPane(String word) { return Pane(Glass(word)); }
		public String GlassSlab() { return Slab(Glass()); } public String GlassSlab(String word) { return Slab(Glass(word)); }
		public String GlassTrapdoor() { return Trapdoor(Glass()); } public String GlassTrapdoor(String word) { return Trapdoor(Glass(word)); }
		public String Glazed() { return "Glazed"; } public String Glazed(String word) { return _add(word, Glazed()); }
		public String Glow() { return "Glow"; } public String Glow(String word) { return _add(word, Glow()); }
		public String Glowstone() { return "Glowstone"; } public String Glowstone(String word) { return _add(word, Glowstone()); }
		public String Goat() { return "Goat"; } public String Goat(String word) { return _add(word, Goat()); }
		public String Goggles() { return "Goggles"; } public String Goggles(String word) { return _add(word, Goggles()); }
		public String Gold() { return "Gold"; } public String Gold(String word) { return _add(word, Gold()); }
		public String Golden() { return "Golden"; } public String Golden(String word) { return _add(word, Golden()); }
		public String Golem() { return "Golem"; } public String Golem(String word) { return _add(word, Golem()); }
		public String Granite() { return "Granite"; } public String Granite(String word) { return _add(word, Granite()); }
		public String Grape() { return "Grape"; } public String Grape(String word) { return _add(word, Grape()); }
		public String Grapes() { return "Grapes"; } public String Grapes(String word) { return _add(word, Grapes()); }
		public String Grappling() { return "Grappling"; } public String Grappling(String word) { return _add(word, Grappling()); }
		public String Grass() { return "Grass"; } public String Grass(String word) { return _add(word, Grass()); }
		public String Grate() { return "Grate"; } public String Grate(String word) { return _add(word, Grate()); }
		public String Gravel() { return "Gravel"; } public String Gravel(String word) { return _add(word, Gravel()); }
		public String Gravity() { return "Gravity"; } public String Gravity(String word) { return _add(word, Gravity()); }
		public String Gray() { return "Gray"; } public String Gray(String word) { return _add(word, Gray()); }
		public String Green() { return "Green"; } public String Green(String word) { return _add(word, Green()); }
		public String Greg() { return "Greg"; } public String Greg(String word) { return _add(word, Greg()); }
		public String Grey() { return "Grey"; } public String Grey(String word) { return _add(word, Grey()); }
		public String Grilled() { return "Grilled"; } public String Grilled(String word) { return _add(word, Grilled()); }
		public String Grindstone() { return "Grindstone"; } public String Grindstone(String word) { return _add(word, Grindstone()); }
		public String Grove() { return "Grove"; } public String Grove(String word) { return _add(word, Grove()); }
		public String Guardian() { return "Guardian"; } public String Guardian(String word) { return _add(word, Guardian()); }
		public String Gunpowder() { return "Gunpowder"; } public String Gunpowder(String word) { return _add(word, Gunpowder()); }
		public String Hammer() { return "Hammer"; } public String Hammer(String word) { return _add(word, Hammer()); }
		public String Hanging() { return "Hanging"; } public String Hanging(String word) { return _add(word, Hanging()); }
		public String HangingSign() { return Sign(Hanging()); } public String HangingSign(String word) { return Sign(Hanging(word)); }
		public Pair<String, String> _HangingSign(String word) { return _apply(this::HangingSign, _Wall(word)); }
		public Pair<String, String> _HangingSign(String word, String withChain) { return _apply(x -> _add(Sign(Hanging(x)), withChain), _Wall(word)); }
		public String Hay() { return "Hay"; } public String Hay(String word) { return _add(word, Hay()); }
		public String Head() { return "Head"; } public String Head(String word) { return _add(word, Head()); }
		public Pair<String, String> _Head(String word) { return _apply(this::Head, _Wall(word)); }
		public String Heart() { return "Heart"; } public String Heart(String word) { return _add(word, Heart()); }
		public String Heartbreak() { return "Heartbreak"; } public String Heartbreak(String word) { return _add(word, Heartbreak()); }
		public String Heavy() { return "Heavy"; } public String Heavy(String word) { return _add(word, Heavy()); }
		public String Hedge() { return "Hedge"; } public String Hedge(String word) { return _add(word, Hedge()); }
		public String Hedgehog() { return "Hedgehog"; } public String Hedgehog(String word) { return _add(word, Hedgehog()); }
		public String Heels() { return "Heels"; } public String Heels(String word) { return _add(word, Heels()); }
		public String Helmet() { return "Helmet"; } public String Helmet(String word) { return _add(word, Helmet()); }
		public String Hoe() { return "Hoe"; } public String Hoe(String word) { return _add(word, Hoe()); }
		public String Hoglin() { return "Hoglin"; } public String Hoglin(String word) { return _add(word, Hoglin()); }
		public String Honey() { return "Honey"; } public String Honey(String word) { return _add(word, Honey()); }
		public String Honeycomb() { return "Honeycomb"; } public String Honeycomb(String word) { return _add(word, Honeycomb()); }
		public String Hopper() { return "Hopper"; } public String Hopper(String word) { return _add(word, Hopper()); }
		public String Horn() { return "Horn"; } public String Horn(String word) { return _add(word, Horn()); }
		public String Horse() { return "Horse"; } public String Horse(String word) { return _add(word, Horse()); }
		public String Host() { return "Host"; } public String Host(String word) { return _add(word, Host()); }
		public String Hot() { return "Hot"; } public String Hot(String word) { return _add(word, Hot()); }
		public String Hotdog() { return "Hotdog"; } public String Hotdog(String word) { return _add(word, Hotdog()); }
		public String Howl() { return "Howl"; } public String Howl(String word) { return _add(word, Howl()); }
		public String Husk() { return "Husk"; } public String Husk(String word) { return _add(word, Husk()); }
		public String Hybrid() { return "Hybrid"; } public String Hybrid(String word) { return _add(word, Hybrid()); }
		public String Hydrangea() { return "Hydrangea"; } public String Hydrangea(String word) { return _add(word, Hydrangea()); }
		public String Hyphae() { return "Hyphae"; } public String Hyphae(String word) { return _add(word, Hyphae()); }
		public String I() { return "I"; } public String I(String word) { return _add(word, I()); }
		public String II() { return "II"; } public String II(String word) { return _add(word, II()); }
		public String III() { return "III"; } public String III(String word) { return _add(word, III()); }
		public String IV() { return "IV"; } public String IV(String word) { return _add(word, IV()); }
		public String Ice() { return "Ice"; } public String Ice(String word) { return _add(word, Ice()); }
		public String Iceologer() { return "Iceologer"; } public String Iceologer(String word) { return _add(word, Iceologer()); }
		public String Ichor() { return "Ichor"; } public String Ichor(String word) { return _add(word, Ichor()); }
		public String Ichored() { return "Ichored"; } public String Ichored(String word) { return _add(word, Ichored()); }
		public String Icy() { return "Icy"; } public String Icy(String word) { return _add(word, Icy()); }
		public String Illusioner() { return "Illusioner"; } public String Illusioner(String word) { return _add(word, Illusioner()); }
		public String Indigo() { return "Indigo"; } public String Indigo(String word) { return _add(word, Indigo()); }
		public String Inducing() { return "Inducing"; } public String Inducing(String word) { return _add(word, Inducing()); }
		public String Infested() { return "Infested"; } public String Infested(String word) { return _add(word, Infested()); }
		public String Ingredient() { return "Ingredient"; } public String Ingredient(String word) { return _add(word, Ingredient()); }
		public String Ingot() { return "Ingot"; } public String Ingot(String word) { return _add(word, Ingot()); }
		public String Ink() { return "Ink"; } public String Ink(String word) { return _add(word, Ink()); }
		public String Inky() { return "Inky"; } public String Inky(String word) { return _add(word, Inky()); }
		public String Instantly() { return "Instantly"; } public String Instantly(String word) { return _add(word, Instantly()); }
		public String Interactions() { return "Interactions"; } public String Interactions(String word) { return _add(word, Interactions()); }
		public String Iron() { return "Iron"; } public String Iron(String word) { return _add(word, Iron()); }
		public String JackOLantern() { return "Jack O' Lantern"; } public String JackOLantern(String word) { return _add(word, JackOLantern()); }
		public String Javelin() { return "Javelin"; } public String Javelin(String word) { return _add(word, Javelin()); }
		public String Jellie() { return "Jellie"; } public String Jellie(String word) { return _add(word, Jellie()); }
		public String Jolly() { return "Jolly"; } public String Jolly(String word) { return _add(word, Jolly()); }
		public String Juice() { return "Juice"; } public String Juice(String word) { return _add(word, Juice()); }
		public String Juicer() { return "Juicer"; } public String Juicer(String word) { return _add(word, Juicer()); }
		public String Jukebox() { return "Jukebox"; } public String Jukebox(String word) { return _add(word, Jukebox()); }
		public String Jumping() { return "Jumping"; } public String Jumping(String word) { return _add(word, Jumping()); }
		public String Jungle() { return "Jungle"; } public String Jungle(String word) { return _add(word, Jungle()); }
		public String Keg() { return "Keg"; } public String Keg(String word) { return _add(word, Keg()); }
		public String Kelp() { return "Kelp"; } public String Kelp(String word) { return _add(word, Kelp()); }
		public String Key() { return "Key"; } public String Key(String word) { return _add(word, Key()); }
		public String Killjoy() { return "Killjoy"; } public String Killjoy(String word) { return _add(word, Killjoy()); }
		public String Knife() { return "Knife"; } public String Knife(String word) { return _add(word, Knife()); }
		public String Ladder() { return "Ladder"; } public String Ladder(String word) { return _add(word, Ladder()); }
		public String Lamp() { return "Lamp"; } public String Lamp(String word) { return _add(word, Lamp()); }
		public String Lantern() { return "Lantern"; } public String Lantern(String word) { return _add(word, Lantern()); }
		public String Lapidary() { return "Lapidary"; } public String Lapidary(String word) { return _add(word, Lapidary()); }
		public String Lapis() { return "Lapis"; } public String Lapis(String word) { return _add(word, Lapis()); }
		public String Large() { return "Large"; } public String Large(String word) { return _add(word, Large()); }
		public String Lava() { return "Lava"; } public String Lava(String word) { return _add(word, Lava()); }
		public String Lavender() { return "Lavender"; } public String Lavender(String word) { return _add(word, Lavender()); }
		public String Leather() { return "Leather"; } public String Leather(String word) { return _add(word, Leather()); }
		public String Leaves() { return "Leaves"; } public String Leaves(String word) { return _add(word, Leaves()); }
		public String Lectern() { return "Lectern"; } public String Lectern(String word) { return _add(word, Lectern()); }
		public String Leeching() { return "Leeching"; } public String Leeching(String word) { return _add(word, Leeching()); }
		public String Leggings() { return "Leggings"; } public String Leggings(String word) { return _add(word, Leggings()); }
		public String Lichen() { return "Lichen"; } public String Lichen(String word) { return _add(word, Lichen()); }
		public String Light() { return "Light"; } public String Light(String word) { return _add(word, Light()); }
		public String Lightning() { return "Lightning"; } public String Lightning(String word) { return _add(word, Lightning()); }
		public String Lilac() { return "Lilac"; } public String Lilac(String word) { return _add(word, Lilac()); }
		public String Lily() { return "Lily"; } public String Lily(String word) { return _add(word, Lily()); }
		public String Lime() { return "Lime"; } public String Lime(String word) { return _add(word, Lime()); }
		public String Llama() { return "Llama"; } public String Llama(String word) { return _add(word, Llama()); }
		public String Locket() { return "Locket"; } public String Locket(String word) { return _add(word, Locket()); }
		public String Lodestone() { return "Lodestone"; } public String Lodestone(String word) { return _add(word, Lodestone()); }
		public String Log() { return "Log"; } public String Log(String word) { return _add(word, Log()); }
		public String Lollipop() { return "Lollipop"; } public String Lollipop(String word) { return _add(word, Lollipop()); }
		public String Long() { return "Long"; } public String Long(String word) { return _add(word, Long()); }
		public String Loom() { return "Loom"; } public String Loom(String word) { return _add(word, Loom()); }
		public String Loud() { return "Loud"; } public String Loud(String word) { return _add(word, Loud()); }
		public String Lucia() { return "Lucia"; } public String Lucia(String word) { return _add(word, Lucia()); }
		public String Lucy() { return "Lucy"; } public String Lucy(String word) { return _add(word, Lucy()); }
		public String Mage() { return "Mage"; } public String Mage(String word) { return _add(word, Mage()); }
		public String Magenta() { return "Magenta"; } public String Magenta(String word) { return _add(word, Magenta()); }
		public String Magic() { return "Magic"; } public String Magic(String word) { return _add(word, Magic()); }
		public String Magma() { return "Magma"; } public String Magma(String word) { return _add(word, Magma()); }
		public String Mangrove() { return "Mangrove"; } public String Mangrove(String word) { return _add(word, Mangrove()); }
		public String Marigold() { return "Marigold"; } public String Marigold(String word) { return _add(word, Marigold()); }
		public String Marked() { return "Marked"; } public String Marked(String word) { return _add(word, Marked()); }
		public String Marshmallow() { return "Marshmallow"; } public String Marshmallow(String word) { return _add(word, Marshmallow()); }
		public String Mask() { return "Mask"; } public String Mask(String word) { return _add(word, Mask()); }
		public String Medium() { return "Medium"; } public String Medium(String word) { return _add(word, Medium()); }
		public String Melon() { return "Melon"; } public String Melon(String word) { return _add(word, Melon()); }
		public String Metal() { return "Metal"; } public String Metal(String word) { return _add(word, Metal()); }
		public String Midnight() { return "Midnight"; } public String Midnight(String word) { return _add(word, Midnight()); }
		public String Milk() { return "Milk"; } public String Milk(String word) { return _add(word, Milk()); }
		public String Milkshake() { return "Milkshake"; } public String Milkshake(String word) { return _add(word, Milkshake()); }
		public String Minecart() { return "Minecart"; } public String Minecart(String word) { return _add(word, Minecart()); }
		public String Miner() { return "Miner"; } public String Miner(String word) { return _add(word, Miner()); }
		public String Mining() { return "Mining"; } public String Mining(String word) { return _add(word, Mining()); }
		public String Mint() { return "Mint"; } public String Mint(String word) { return _add(word, Mint()); }
		public String Mocha() { return "Mocha"; } public String Mocha(String word) { return _add(word, Mocha()); }
		public String Mold() { return "Mold"; } public String Mold(String word) { return _add(word, Mold()); }
		public String Moldy() { return "Moldy"; } public String Moldy(String word) { return _add(word, Moldy()); }
		public String Moobloom() { return "Moobloom"; } public String Moobloom(String word) { return _add(word, Moobloom()); }
		public String Mooblossom() { return "Mooblossom"; } public String Mooblossom(String word) { return _add(word, Mooblossom()); }
		public String Moolip() { return "Moolip"; } public String Moolip(String word) { return _add(word, Moolip()); }
		public String Mooshroom() { return "Mooshroom"; } public String Mooshroom(String word) { return _add(word, Mooshroom()); }
		public String Mosaic() { return "Mosaic"; } public String Mosaic(String word) { return _add(word, Mosaic()); }
		public String Moss() { return "Moss"; } public String Moss(String word) { return _add(word, Moss()); }
		public String Mossy() { return "Mossy"; } public String Mossy(String word) { return _add(word, Mossy()); }
		public String Mottled() { return "Mottled"; } public String Mottled(String word) { return _add(word, Mottled()); }
		public String Mourner() { return "Mourner"; } public String Mourner(String word) { return _add(word, Mourner()); }
		public String Mud() { return "Mud"; } public String Mud(String word) { return _add(word, Mud()); }
		public String Muddy() { return "Muddy"; } public String Muddy(String word) { return _add(word, Muddy()); }
		public String Mule() { return "Mule"; } public String Mule(String word) { return _add(word, Mule()); }
		public String Mushroom() { return "Mushroom"; } public String Mushroom(String word) { return _add(word, Mushroom()); }
		public String Music() { return "Music"; } public String Music(String word) { return _add(word, Music()); }
		public String Mutton() { return "Mutton"; } public String Mutton(String word) { return _add(word, Mutton()); }
		public String Mycelium() { return "Mycelium"; } public String Mycelium(String word) { return _add(word, Mycelium()); }
		public String Nephal() { return "Nephal"; } public String Nephal(String word) { return _add(word, Nephal()); }
		public String Nest() { return "Nest"; } public String Nest(String word) { return _add(word, Nest()); }
		public String Nether() { return "Nether"; } public String Nether(String word) { return _add(word, Nether()); }
		public String Netherite() { return "Netherite"; } public String Netherite(String word) { return _add(word, Netherite()); }
		public String Netherrack() { return "Netherrack"; } public String Netherrack(String word) { return _add(word, Netherrack()); }
		public String Nethershroom() { return "Nethershroom"; } public String Nethershroom(String word) { return _add(word, Nethershroom()); }
		public String Nog() { return "Nog"; } public String Nog(String word) { return _add(word, Nog()); }
		public String Nose() { return "Nose"; } public String Nose(String word) { return _add(word, Nose()); }
		public String Note() { return "Note"; } public String Note(String word) { return _add(word, Note()); }
		public String Nugget() { return "Nugget"; } public String Nugget(String word) { return _add(word, Nugget()); }
		public String Nylium() { return "Nylium"; } public String Nylium(String word) { return _add(word, Nylium()); }
		public String Oak() { return "Oak"; } public String Oak(String word) { return _add(word, Oak()); }
		public String Observer() { return "Observer"; } public String Observer(String word) { return _add(word, Observer()); }
		public String Obsidian() { return "Obsidian"; } public String Obsidian(String word) { return _add(word, Obsidian()); }
		public String Ocelot() { return "Ocelot"; } public String Ocelot(String word) { return _add(word, Ocelot()); }
		public String Ochre() { return "Ochre"; } public String Ochre(String word) { return _add(word, Ochre()); }
		public String Onion() { return "Onion"; } public String Onion(String word) { return _add(word, Onion()); }
		public String Onions() { return "Onions"; } public String Onions(String word) { return _add(word, Onions()); }
		public String Only() { return "Only"; } public String Only(String word) { return _add(word, Only()); }
		public String Orange() { return "Orange"; } public String Orange(String word) { return _add(word, Orange()); }
		public String Ore() { return "Ore"; } public String Ore(String word) { return _add(word, Ore()); }
		public String Orchid() { return "Orchid"; } public String Orchid(String word) { return _add(word, Orchid()); }
		public String Oxeye() { return "Oxeye"; } public String Oxeye(String word) { return _add(word, Oxeye()); }
		public String Oxidized() { return "Oxidized"; } public String Oxidized(String word) { return _add(word, Oxidized()); }
		public String Paced() { return "Paced"; } public String Paced(String word) { return _add(word, Paced()); }
		public String Packed() { return "Packed"; } public String Packed(String word) { return _add(word, Packed()); }
		public String Pad() { return "Pad"; } public String Pad(String word) { return _add(word, Pad()); }
		public String Paddling() { return "Paddling"; } public String Paddling(String word) { return _add(word, Paddling()); }
		public String Paeonia() { return "Paeonia"; } public String Paeonia(String word) { return _add(word, Paeonia()); }
		public String Pain() { return "Pain"; } public String Pain(String word) { return _add(word, Pain()); }
		public String Pale() { return "Pale"; } public String Pale(String word) { return _add(word, Pale()); }
		public String Panda() { return "Panda"; } public String Panda(String word) { return _add(word, Panda()); }
		public String Pane() { return "Pane"; } public String Pane(String word) { return _add(word, Pane()); }
		public String Parrot() { return "Parrot"; } public String Parrot(String word) { return _add(word, Parrot()); }
		public String Patched() { return "Patched"; } public String Patched(String word) { return _add(word, Patched()); }
		public String Path() { return "Path"; } public String Path(String word) { return _add(word, Path()); }
		public String Pattern() { return "Pattern"; } public String Pattern(String word) { return _add(word, Pattern()); }
		public String Pearlescent() { return "Pearlescent"; } public String Pearlescent(String word) { return _add(word, Pearlescent()); }
		public String Peony() { return "Peony"; } public String Peony(String word) { return _add(word, Peony()); }
		public String Persian() { return "Persian"; } public String Persian(String word) { return _add(word, Persian()); }
		public String Petal() { return "Petal"; } public String Petal(String word) { return _add(word, Petal()); }
		public String Petals() { return "Petals"; } public String Petals(String word) { return _add(word, Petals()); }
		public String Phantom() { return "Phantom"; } public String Phantom(String word) { return _add(word, Phantom()); }
		public String Phantoms() { return "Phantoms"; } public String Phantoms(String word) { return _add(word, Phantoms()); }
		public String Pickaxe() { return "Pickaxe"; } public String Pickaxe(String word) { return _add(word, Pickaxe()); }
		public String Pickle() { return "Pickle"; } public String Pickle(String word) { return _add(word, Pickle()); }
		public String Pie() { return "Pie"; } public String Pie(String word) { return _add(word, Pie()); }
		public String Piebald() { return "Piebald"; } public String Piebald(String word) { return _add(word, Piebald()); }
		public String Pig() { return "Pig"; } public String Pig(String word) { return _add(word, Pig()); }
		public String Piglin() { return "Piglin"; } public String Piglin(String word) { return _add(word, Piglin()); }
		public String Pillager() { return "Pillager"; } public String Pillager(String word) { return _add(word, Pillager()); }
		public String Pillar() { return "Pillar"; } public String Pillar(String word) { return _add(word, Pillar()); }
		public String Pine() { return "Pine"; } public String Pine(String word) { return _add(word, Pine()); }
		public String Pink() { return "Pink"; } public String Pink(String word) { return _add(word, Pink()); }
		public String Pinto() { return "Pinto"; } public String Pinto(String word) { return _add(word, Pinto()); }
		public String Piranha() { return "Piranha"; } public String Piranha(String word) { return _add(word, Piranha()); }
		public String Piston() { return "Piston"; } public String Piston(String word) { return _add(word, Piston()); }
		public String Pitcher() { return "Pitcher"; } public String Pitcher(String word) { return _add(word, Pitcher()); }
		public String Planks() { return "Planks"; } public String Planks(String word) { return _add(word, Planks()); }
		public String Plant() { return "Plant"; } public String Plant(String word) { return _add(word, Plant()); }
		public String Plate() { return "Plate"; } public String Plate(String word) { return _add(word, Plate()); }
		public String Plenty() { return "Plenty"; } public String Plenty(String word) { return _add(word, Plenty()); }
		public String Plush() { return "Plush"; } public String Plush(String word) { return _add(word, Plush()); }
		public String Plushie() { return "Plushie"; } public String Plushie(String word) { return _add(word, Plushie()); }
		public String Pod() { return "Pod"; } public String Pod(String word) { return _add(word, Pod()); }
		public String Podzol() { return "Podzol"; } public String Podzol(String word) { return _add(word, Podzol()); }
		public String Pointed() { return "Pointed"; } public String Pointed(String word) { return _add(word, Pointed()); }
		public String Poison() { return "Poison"; } public String Poison(String word) { return _add(word, Poison()); }
		public String Poisonous() { return "Poisonous"; } public String Poisonous(String word) { return _add(word, Poisonous()); }
		public String Polar() { return "Polar"; } public String Polar(String word) { return _add(word, Polar()); }
		public String Polished() { return "Polished"; } public String Polished(String word) { return _add(word, Polished()); }
		public String Ponder() { return "Ponder"; } public String Ponder(String word) { return _add(word, Ponder()); }
		public String Poppy() { return "Poppy"; } public String Poppy(String word) { return _add(word, Poppy()); }
		public String Porcelain() { return "Porcelain"; } public String Porcelain(String word) { return _add(word, Porcelain()); }
		public String Pork() { return "Pork"; } public String Pork(String word) { return _add(word, Pork()); }
		public String Porkchop() { return "Porkchop"; } public String Porkchop(String word) { return _add(word, Porkchop()); }
		public String Portal() { return "Portal"; } public String Portal(String word) { return _add(word, Portal()); }
		public String Post() { return "Post"; } public String Post(String word) { return _add(word, Post()); }
		public String Pot() { return "Pot"; } public String Pot(String word) { return _add(word, Pot()); }
		public String Potato() { return "Potato"; } public String Potato(String word) { return _add(word, Potato()); }
		public String Potatoes() { return "Potatoes"; } public String Potatoes(String word) { return _add(word, Potatoes()); }
		public String Potion() { return "Potion"; } public String Potion(String word) { return _add(word, Potion()); }
		public String Potted() { return "Potted"; } public String Potted(String word) { return _add(word, Potted()); }
		public Pair<String, String> _Potted(String word) { return new Pair<>(word, _add(Potted(), word)); }
		public String Pottery() { return "Pottery"; } public String Pottery(String word) { return _add(word, Pottery()); }
		public String Pouch() { return "Pouch"; } public String Pouch(String word) { return _add(word, Pouch()); }
		public String Powder() { return "Powder"; } public String Powder(String word) { return _add(word, Powder()); }
		public String PowderKeg() { return Keg(Powder()); } public String PowderKeg(String word) { return Keg(Powder(word)); }
		public String Pressure() { return "Pressure"; } public String Pressure(String word) { return _add(word, Pressure()); }
		public String Pride() { return "Pride"; } public String Pride(String word) { return _add(word, Pride()); }
		public String Prismarine() { return "Prismarine"; } public String Prismarine(String word) { return _add(word, Prismarine()); }
		public String Prize() { return "Prize"; } public String Prize(String word) { return _add(word, Prize()); }
		public String Propagule() { return "Propagule"; } public String Propagule(String word) { return _add(word, Propagule()); }
		public String Protected() { return "Protected"; } public String Protected(String word) { return _add(word, Protected()); }
		public String Protection() { return "Protection"; } public String Protection(String word) { return _add(word, Protection()); }
		public String Pufferfish() { return "Pufferfish"; } public String Pufferfish(String word) { return _add(word, Pufferfish()); }
		public String Pumice() { return "Pumice"; } public String Pumice(String word) { return _add(word, Pumice()); }
		public String Pumpkin() { return "Pumpkin"; } public String Pumpkin(String word) { return _add(word, Pumpkin()); }
		public String Purple() { return "Purple"; } public String Purple(String word) { return _add(word, Purple()); }
		public String Purpur() { return "Purpur"; } public String Purpur(String word) { return _add(word, Purpur()); }
		public String Put() { return "Put"; } public String Put(String word) { return _add(word, Put()); }
		public String Quartz() { return "Quartz"; } public String Quartz(String word) { return _add(word, Quartz()); }
		public String Rabbit() { return "Rabbit"; } public String Rabbit(String word) { return _add(word, Rabbit()); }
		public String Raccoon() { return "Raccoon"; } public String Raccoon(String word) { return _add(word, Raccoon()); }
		public String Ragdoll() { return "Ragdoll"; } public String Ragdoll(String word) { return _add(word, Ragdoll()); }
		public String Raider() { return "Raider"; } public String Raider(String word) { return _add(word, Raider()); }
		public String Rainbow() { return "Rainbow"; } public String Rainbow(String word) { return _add(word, Rainbow()); }
		public String Raft() { return "Raft"; } public String Raft(String word) { return _add(word, Raft()); }
		public Pair<String, String> _Raft(String word) { return new Pair<>(Raft(word), Chest(with(Raft(word)))); }
		public String Rail() { return "Rail"; } public String Rail(String word) { return _add(word, Rail()); }
		public String Ramen() { return "Ramen"; } public String Ramen(String word) { return _add(word, Ramen()); }
		public String Ravager() { return "Ravager"; } public String Ravager(String word) { return _add(word, Ravager()); }
		public String Raw() { return "Raw"; } public String Raw(String word) { return _add(word, Raw()); }
		public String Recovery() { return "Recovery"; } public String Recovery(String word) { return _add(word, Recovery()); }
		public String Recycling() { return "Recycling"; } public String Recycling(String word) { return _add(word, Recycling()); }
		public String Red() { return "Red"; } public String Red(String word) { return _add(word, Red()); }
		public String Redstone() { return "Redstone"; } public String Redstone(String word) { return _add(word, Redstone()); }
		public String Regeneration() { return "Regeneration"; } public String Regeneration(String word) { return _add(word, Regeneration()); }
		public String Regenerative() { return "Regenerative"; } public String Regenerative(String word) { return _add(word, Regenerative()); }
		public String Reinforced() { return "Reinforced"; } public String Reinforced(String word) { return _add(word, Reinforced()); }
		public String Relieved() { return "Relieved"; } public String Relieved(String word) { return _add(word, Relieved()); }
		public String Repulsion() { return "Repulsion"; } public String Repulsion(String word) { return _add(word, Repulsion()); }
		public String Resistance() { return "Resistance"; } public String Resistance(String word) { return _add(word, Resistance()); }
		public String Respawn() { return "Respawn"; } public String Respawn(String word) { return _add(word, Respawn()); }
		public String Rhythmic() { return "Rhythmic"; } public String Rhythmic(String word) { return _add(word, Rhythmic()); }
		public String Rib() { return "Rib"; } public String Rib(String word) { return _add(word, Rib()); }
		public String Ricochet() { return "Ricochet"; } public String Ricochet(String word) { return _add(word, Ricochet()); }
		public String Roast() { return "Roast"; } public String Roast(String word) { return _add(word, Roast()); }
		public String Rock() { return "Rock"; } public String Rock(String word) { return _add(word, Rock()); }
		public String Rocky() { return "Rocky"; } public String Rocky(String word) { return _add(word, Rocky()); }
		public String Rod() { return "Rod"; } public String Rod(String word) { return _add(word, Rod()); }
		public String Roll() { return "Roll"; } public String Roll(String word) { return _add(word, Roll()); }
		public String Rooted() { return "Rooted"; } public String Rooted(String word) { return _add(word, Rooted()); }
		public String Roots() { return "Roots"; } public String Roots(String word) { return _add(word, Roots()); }
		public String Rose() { return "Rose"; } public String Rose(String word) { return _add(word, Rose()); }
		public String Rot() { return "Rot"; } public String Rot(String word) { return _add(word, Rot()); }
		public String Rotten() { return "Rotten"; } public String Rotten(String word) { return _add(word, Rotten()); }
		public String Row() { return "Row"; } public String Row(String word) { return _add(word, Row()); }
		public String Rowing() { return "Rowing"; } public String Rowing(String word) { return _add(word, Rowing()); }
		public String Royalty() { return "Royalty"; } public String Royalty(String word) { return _add(word, Royalty()); }
		public String Ruby() { return "Ruby"; } public String Ruby(String word) { return _add(word, Ruby()); }
		public String RubyOre() { return Ore(Ruby()); } public String RubyOre(String word) { return Ore(Ruby(word)); }
		public String Rush() { return "Rush"; } public String Rush(String word) { return _add(word, Rush()); }
		public String Saddle() { return "Saddle"; } public String Saddle(String word) { return _add(word, Saddle()); }
		public String Saddled() { return "Saddled"; } public String Saddled(String word) { return _add(word, Saddled()); }
		public String Salmon() { return "Salmon"; } public String Salmon(String word) { return _add(word, Salmon()); }
		public String Salve() { return "Salve"; } public String Salve(String word) { return _add(word, Salve()); }
		public String Sand() { return "Sand"; } public String Sand(String word) { return _add(word, Sand()); }
		public String Sandstone() { return "Sandstone"; } public String Sandstone(String word) { return _add(word, Sandstone()); }
		public String Sandy() { return "Sandy"; } public String Sandy(String word) { return _add(word, Sandy()); }
		public String Sap() { return "Sap"; } public String Sap(String word) { return _add(word, Sap()); }
		public String Sapling() { return "Sapling"; } public String Sapling(String word) { return _add(word, Sapling()); }
		public String Sapphire() { return "Sapphire"; } public String Sapphire(String word) { return _add(word, Sapphire()); }
		public String Saturating() { return "Saturating"; } public String Saturating(String word) { return _add(word, Saturating()); }
		public String Saturation() { return "Saturation"; } public String Saturation(String word) { return _add(word, Saturation()); }
		public String Scaffolding() { return "Scaffolding"; } public String Scaffolding(String word) { return _add(word, Scaffolding()); }
		public String Sculk() { return "Sculk"; } public String Sculk(String word) { return _add(word, Sculk()); }
		public String SculkCatalyst() { return Catalyst(Sculk()); } public String SculkCatalyst(String word) { return Catalyst(Sculk(word)); }
		public String SculkSensor() { return Sensor(Sculk()); } public String SculkSensor(String word) { return Sensor(Sculk(word)); }
		public String SculkShrieker() { return Shrieker(Sculk()); } public String SculkShrieker(String word) { return Shrieker(Sculk(word)); }
		public String SculkVein() { return Vein(Sculk()); } public String SculkVein(String word) { return Vein(Sculk(word)); }
		public String Sea() { return "Sea"; } public String Sea(String word) { return _add(word, Sea()); }
		public String Seagrass() { return "Seagrass"; } public String Seagrass(String word) { return _add(word, Seagrass()); }
		public String Secret() { return "Secret"; } public String Secret(String word) { return _add(word, Secret()); }
		public String Seed() { return "Seed"; } public String Seed(String word) { return _add(word, Seed()); }
		public String Seeds() { return "Seeds"; } public String Seeds(String word) { return _add(word, Seeds()); }
		public String Sensor() { return "Sensor"; } public String Sensor(String word) { return _add(word, Sensor()); }
		public String Sentry() { return "Sentry"; } public String Sentry(String word) { return _add(word, Sentry()); }
		public String Serrated() { return "Serrated"; } public String Serrated(String word) { return _add(word, Serrated()); }
		public String Shale() { return "Shale"; } public String Shale(String word) { return _add(word, Shale()); }
		public String Shaper() { return "Shaper"; } public String Shaper(String word) { return _add(word, Shaper()); }
		public String Shard() { return "Shard"; } public String Shard(String word) { return _add(word, Shard()); }
		public String Sharp() { return "Sharp"; } public String Sharp(String word) { return _add(word, Sharp()); }
		public String Sheaf() { return "Sheaf"; } public String Sheaf(String word) { return _add(word, Sheaf()); }
		public String Sheared() { return "Sheared"; } public String Sheared(String word) { return _add(word, Sheared()); }
		public String Shears() { return "Shears"; } public String Shears(String word) { return _add(word, Shears()); }
		public String Sheep() { return "Sheep"; } public String Sheep(String word) { return _add(word, Sheep()); }
		public String Shelter() { return "Shelter"; } public String Shelter(String word) { return _add(word, Shelter()); }
		public String Sherd() { return "Sherd"; } public String Sherd(String word) { return _add(word, Sherd()); }
		public String Shoddy() { return "Shoddy"; } public String Shoddy(String word) { return _add(word, Shoddy()); }
		public String Short() { return "Short"; } public String Short(String word) { return _add(word, Short()); }
		public String Shorthair() { return "Shorthair"; } public String Shorthair(String word) { return _add(word, Shorthair()); }
		public String Shot() { return "Shot"; } public String Shot(String word) { return _add(word, Shot()); }
		public String Shovel() { return "Shovel"; } public String Shovel(String word) { return _add(word, Shovel()); }
		public String Shrieker() { return "Shrieker"; } public String Shrieker(String word) { return _add(word, Shrieker()); }
		public String Shroomlight() { return "Shroomlight"; } public String Shroomlight(String word) { return _add(word, Shroomlight()); }
		public String Shulker() { return "Shulker"; } public String Shulker(String word) { return _add(word, Shulker()); }
		public String Siamese() { return "Siamese"; } public String Siamese(String word) { return _add(word, Siamese()); }
		public String Sign() { return "Sign"; } public String Sign(String word) { return _add(word, Sign()); }
		public Pair<Pair<String, String>, Pair<String, String>> _Sign(String word) { return new Pair<>(_apply(this::Sign, _Wall(word)), _HangingSign(word)); }
		public String Silence() { return "Silence"; } public String Silence(String word) { return _add(word, Silence()); }
		public String Silent() { return "Silent"; } public String Silent(String word) { return _add(word, Silent()); }
		public String Silverfish() { return "Silverfish"; } public String Silverfish(String word) { return _add(word, Silverfish()); }
		public String Sing() { return "Sing"; } public String Sing(String word) { return _add(word, Sing()); }
		public String Siphon() { return "Siphon"; } public String Siphon(String word) { return _add(word, Siphon()); }
		public String Skeleton() { return "Skeleton"; } public String Skeleton(String word) { return _add(word, Skeleton()); }
		public String Skewbald() { return "Skewbald"; } public String Skewbald(String word) { return _add(word, Skewbald()); }
		public String Skull() { return "Skull"; } public String Skull(String word) { return _add(word, Skull()); }
		public String Slab() { return "Slab"; } public String Slab(String word) { return _add(word, Slab()); }
		public String Slate() { return "Slate"; } public String Slate(String word) { return _add(word, Slate()); }
		public String Slicken() { return "Slicken"; } public String Slicken(String word) { return _add(word, Slicken()); }
		public String Slider() { return "Slider"; } public String Slider(String word) { return _add(word, Slider()); }
		public String Slime() { return "Slime"; } public String Slime(String word) { return _add(word, Slime()); }
		public String Slimy() { return "Slimy"; } public String Slimy(String word) { return _add(word, Slimy()); }
		public String Slorse() { return "Slorse"; } public String Slorse(String word) { return _add(word, Slorse()); }
		public String Slowing() { return "Slowing"; } public String Slowing(String word) { return _add(word, Slowing()); }
		public String Slowness() { return "Slowness"; } public String Slowness(String word) { return _add(word, Slowness()); }
		public String Sludge() { return "Sludge"; } public String Sludge(String word) { return _add(word, Sludge()); }
		public String Small() { return "Small"; } public String Small(String word) { return _add(word, Small()); }
		public String Smoker() { return "Smoker"; } public String Smoker(String word) { return _add(word, Smoker()); }
		public String Smooth() { return "Smooth"; } public String Smooth(String word) { return _add(word, Smooth()); }
		public String Smoothie() { return "Smoothie"; } public String Smoothie(String word) { return _add(word, Smoothie()); }
		public String Smithing() { return "Smithing"; } public String Smithing(String word) { return _add(word, Smithing()); }
		public String Sneak() { return "Sneak"; } public String Sneak(String word) { return _add(word, Sneak()); }
		public String Snickerdoodle() { return "Snickerdoodle"; } public String Snickerdoodle(String word) { return _add(word, Snickerdoodle()); }
		public String Sniffer() { return "Sniffer"; } public String Sniffer(String word) { return _add(word, Sniffer()); }
		public String Snort() { return "Snort"; } public String Snort(String word) { return _add(word, Snort()); }
		public String Snout() { return "Snout"; } public String Snout(String word) { return _add(word, Snout()); }
		public String Snow() { return "Snow"; } public String Snow(String word) { return _add(word, Snow()); }
		public String Snowball() { return "Snowball"; } public String Snowball(String word) { return _add(word, Snowball()); }
		public String Soft() { return "Soft"; } public String Soft(String word) { return _add(word, Soft()); }
		public String Soil() { return "Soil"; } public String Soil(String word) { return _add(word, Soil()); }
		public String Someone() { return "Someone"; } public String Someone(String word) { return _add(word, Someone()); }
		public String Something() { return "Something"; } public String Something(String word) { return _add(word, Something()); }
		public String Sooty() { return "Sooty"; } public String Sooty(String word) { return _add(word, Sooty()); }
		public String Soul() { return "Soul"; } public String Soul(String word) { return _add(word, Soul()); }
		public String SoulLantern() { return Lantern(Soul()); } public String SoulLantern(String word) { return Lantern(Soul(word)); }
		public Pair<String, String> SoulTorch(String word) { return _Torch(Soul(word)); }
		public String Soup() { return "Soup"; } public String Soup(String word) { return _add(word, Soup()); }
		public String Spade() { return "Spade"; } public String Spade(String word) { return _add(word, Spade()); }
		public String Spawn() { return "Spawn"; } public String Spawn(String word) { return _add(word, Spawn()); }
		public String Spawner() { return "Spawner"; } public String Spawner(String word) { return _add(word, Spawner()); }
		public String Spider() { return "Spider"; } public String Spider(String word) { return _add(word, Spider()); }
		public String Spire() { return "Spire"; } public String Spire(String word) { return _add(word, Spire()); }
		public String Spiteful() { return "Spiteful"; } public String Spiteful(String word) { return _add(word, Spiteful()); }
		public String Spoiled() { return "Spoiled"; } public String Spoiled(String word) { return _add(word, Spoiled()); }
		public String Sponge() { return "Sponge"; } public String Sponge(String word) { return _add(word, Sponge()); }
		public String Spore() { return "Spore"; } public String Spore(String word) { return _add(word, Spore()); }
		public String Spotted() { return "Spotted"; } public String Spotted(String word) { return _add(word, Spotted()); }
		public String Spring() { return "Spring"; } public String Spring(String word) { return _add(word, Spring()); }
		public String Sprouts() { return "Sprouts"; } public String Sprouts(String word) { return _add(word, Sprouts()); }
		public String Spruce() { return "Spruce"; } public String Spruce(String word) { return _add(word, Spruce()); }
		public String Squid() { return "Squid"; } public String Squid(String word) { return _add(word, Squid()); }
		public String Stained() { return "Stained"; } public String Stained(String word) { return _add(word, Stained()); }
		public String Stairs() { return "Stairs"; } public String Stairs(String word) { return _add(word, Stairs()); }
		public String Stand() { return "Stand"; } public String Stand(String word) { return _add(word, Stand()); }
		public String Statue() { return "Statue"; } public String Statue(String word) { return _add(word, Statue()); }
		public String Stem() { return "Stem"; } public String Stem(String word) { return _add(word, Stem()); }
		public String Stick() { return "Stick"; } public String Stick(String word) { return _add(word, Stick()); }
		public String Sticky() { return "Sticky"; } public String Sticky(String word) { return _add(word, Sticky()); }
		public String Still() { return "Still"; } public String Still(String word) { return _add(word, Still()); }
		public String Stir() { return "Stir"; } public String Stir(String word) { return _add(word, Stir()); }
		public String Stone() { return "Stone"; } public String Stone(String word) { return _add(word, Stone()); }
		public String Stonecutter() { return "Stonecutter"; } public String Stonecutter(String word) { return _add(word, Stonecutter()); }
		public String Stormy() { return "Stormy"; } public String Stormy(String word) { return _add(word, Stormy()); }
		public String Strawberry() { return "Strawberry"; } public String Strawberry(String word) { return _add(word, Strawberry()); }
		public String Stray() { return "Stray"; } public String Stray(String word) { return _add(word, Stray()); }
		public String Strider() { return "Strider"; } public String Strider(String word) { return _add(word, Strider()); }
		public String Stripped() { return "Stripped"; } public String Stripped(String word) { return _add(word, Stripped()); }
		public String Studded() { return "Studded"; } public String Studded(String word) { return _add(word, Studded()); }
		public String Substitute() { return "Substitute"; } public String Substitute(String word) { return _add(word, Substitute()); }
		public String Sugar() { return "Sugar"; } public String Sugar(String word) { return _add(word, Sugar()); }
		public String Summoned() { return "Summoned"; } public String Summoned(String word) { return _add(word, Summoned()); }
		public String Summoning() { return "Summoning"; } public String Summoning(String word) { return _add(word, Summoning()); }
		public String Sunflower() { return "Sunflower"; } public String Sunflower(String word) { return _add(word, Sunflower()); }
		public String Sunken() { return "Sunken"; } public String Sunken(String word) { return _add(word, Sunken()); }
		public String Sunset() { return "Sunset"; } public String Sunset(String word) { return _add(word, Sunset()); }
		public String Suspicious() { return "Suspicious"; } public String Suspicious(String word) { return _add(word, Suspicious()); }
		public String Swamp() { return "Swamp"; } public String Swamp(String word) { return _add(word, Swamp()); }
		public String Sweet() { return "Sweet"; } public String Sweet(String word) { return _add(word, Sweet()); }
		public String Swift() { return "Swift"; } public String Swift(String word) { return _add(word, Swift()); }
		public String Sword() { return "Sword"; } public String Sword(String word) { return _add(word, Sword()); }
		public String Syringe() { return "Syringe"; } public String Syringe(String word) { return _add(word, Syringe()); }
		public String Syrup() { return "Syrup"; } public String Syrup(String word) { return _add(word, Syrup()); }
		public String Tabby() { return "Tabby"; } public String Tabby(String word) { return _add(word, Tabby()); }
		public String Table() { return "Table"; } public String Table(String word) { return _add(word, Table()); }
		public String Tadpole() { return "Tadpole"; } public String Tadpole(String word) { return _add(word, Tadpole()); }
		public String Tall() { return "Tall"; } public String Tall(String word) { return _add(word, Tall()); }
		public String Target() { return "Target"; } public String Target(String word) { return _add(word, Target()); }
		public String Temperate() { return "Temperate"; } public String Temperate(String word) { return _add(word, Temperate()); }
		public String Template() { return "Template"; } public String Template(String word) { return _add(word, Template()); }
		public String Terracotta() { return "Terracotta"; } public String Terracotta(String word) { return _add(word, Terracotta()); }
		public String Throwable() { return "Throwable"; } public String Throwable(String word) { return _add(word, Throwable()); }
		public String Thunder() { return "Thunder"; } public String Thunder(String word) { return _add(word, Thunder()); }
		public String Thundering() { return "Thundering"; } public String Thundering(String word) { return _add(word, Thundering()); }
		public String Tide() { return "Tide"; } public String Tide(String word) { return _add(word, Tide()); }
		public String Tiki() { return "Tiki"; } public String Tiki(String word) { return _add(word, Tiki()); }
		public String Tile() { return "Tile"; } public String Tile(String word) { return _add(word, Tile()); }
		public String Tiles() { return "Tiles"; } public String Tiles(String word) { return _add(word, Tiles()); }
		public String Tinker() { return "Tinker"; } public String Tinker(String word) { return _add(word, Tinker()); }
		public String Tinted() { return "Tinted"; } public String Tinted(String word) { return _add(word, Tinted()); }
		public String TNT() { return "TNT"; } public String TNT(String word) { return _add(word, TNT()); }
		public String Tomato() { return "Tomato"; } public String Tomato(String word) { return _add(word, Tomato()); }
		public String Torchflower() { return "Torchflower"; } public String Torchflower(String word) { return _add(word, Torchflower()); }
		public String Torch() { return "Torch"; } public String Torch(String word) { return _add(word, Torch()); }
		public Pair<String, String> _Torch(String word) { return _apply(this::Torch, _Wall(word)); }
		public String Toy() { return "Toy"; } public String Toy(String word) { return _add(word, Toy()); }
		public String Trader() { return "Trader"; } public String Trader(String word) { return _add(word, Trader()); }
		public String Trapdoor() { return "Trapdoor"; } public String Trapdoor(String word) { return _add(word, Trapdoor()); }
		public String Trial() { return "Trial"; } public String Trial(String word) { return _add(word, Trial()); }
		public String Trident() { return "Trident"; } public String Trident(String word) { return _add(word, Trident()); }
		public String Trim() { return "Trim"; } public String Trim(String word) { return _add(word, Trim()); }
		public String Trimming() { return "Trimming"; } public String Trimming(String word) { return _add(word, Trimming()); }
		public String Tripwire() { return "Tripwire"; } public String Tripwire(String word) { return _add(word, Tripwire()); }
		public String Tropical() { return "Tropical"; } public String Tropical(String word) { return _add(word, Tropical()); }
		public String Tuff() { return "Tuff"; } public String Tuff(String word) { return _add(word, Tuff()); }
		public String Tulip() { return "Tulip"; } public String Tulip(String word) { return _add(word, Tulip()); }
		public String Turf() { return "Turf"; } public String Turf(String word) { return _add(word, Turf()); }
		public String Turtle() { return "Turtle"; } public String Turtle(String word) { return _add(word, Turtle()); }
		public String Umbra() { return "Umbra"; } public String Umbra(String word) { return _add(word, Umbra()); }
		public String Underwater() { return "Underwater"; } public String Underwater(String word) { return _add(word, Underwater()); } public String Underwater_(String word) { return _add(Underwater(), word); }
		public Pair<String, String> Underwater_Torch() { return _Torch(Underwater()); } public Pair<String, String> Underwater_Torch(String word) { return _Torch(Underwater_(word)); }
		public String Undignified() { return "Undignified"; } public String Undignified(String word) { return _add(word, Undignified()); }
		public String Unlit() { return "Unlit"; } public String Unlit(String word) { return _add(word, Unlit()); }
		public String Unreadable() { return "Unreadable"; } public String Unreadable(String word) { return _add(word, Unreadable()); }
		public String Up() { return "Up"; } public String Up(String word) { return _add(word, Up()); }
		public String Upgrade() { return "Upgrade"; } public String Upgrade(String word) { return _add(word, Upgrade()); }
		public String Valley() { return "Valley"; } public String Valley(String word) { return _add(word, Valley()); }
		public String Vampire() { return "Vampire"; } public String Vampire(String word) { return _add(word, Vampire()); }
		public String Vanilla() { return "Vanilla"; } public String Vanilla(String word) { return _add(word, Vanilla()); }
		public String Vein() { return "Vein"; } public String Vein(String word) { return _add(word, Vein()); }
		public String Venom() { return "Venom"; } public String Venom(String word) { return _add(word, Venom()); }
		public String Verdant() { return "Verdant"; } public String Verdant(String word) { return _add(word, Verdant()); }
		public String Vex() { return "Vex"; } public String Vex(String word) { return _add(word, Vex()); }
		public String Vial() { return "Vial"; } public String Vial(String word) { return _add(word, Vial()); }
		public String Villager() { return "Villager"; } public String Villager(String word) { return _add(word, Villager()); }
		public String Vindicator() { return "Vindicator"; } public String Vindicator(String word) { return _add(word, Vindicator()); }
		public String Vine() { return "Vine"; } public String Vine(String word) { return _add(word, Vine()); }
		public String Vines() { return "Vines"; } public String Vines(String word) { return _add(word, Vines()); }
		public String Violent() { return "Violent"; } public String Violent(String word) { return _add(word, Violent()); }
		public String Wall() { return "Wall"; } public String Wall(String word) { return _add(word, Wall()); }
		public Pair<String, String> _Wall(String word) { return new Pair<>(word, Wall(word)); }
		public String Wandering() { return "Wandering"; } public String Wandering(String word) { return _add(word, Wandering()); }
		public String Ward() { return "Ward"; } public String Ward(String word) { return _add(word, Ward()); }
		public String Warden() { return "Warden"; } public String Warden(String word) { return _add(word, Warden()); }
		public String Wardens() { return "Wardens"; } public String Wardens(String word) { return _add(word, Wardens()); }
		public String Warm() { return "Warm"; } public String Warm(String word) { return _add(word, Warm()); }
		public String Warped() { return "Warped"; } public String Warped(String word) { return _add(word, Warped()); }
		public String Wart() { return "Wart"; } public String Wart(String word) { return _add(word, Wart()); }
		public String Water() { return "Water"; } public String Water(String word) { return _add(word, Water()); }
		public String Wax() { return "Wax"; } public String Wax(String word) { return _add(word, Wax()); }
		public String Waxed() { return "Waxed"; } public String Waxed(String word) { return _add(word, Waxed()); }
		public String Wayfinder() { return "Wayfinder"; } public String Wayfinder(String word) { return _add(word, Wayfinder()); }
		public String Weathered() { return "Weathered"; } public String Weathered(String word) { return _add(word, Weathered()); }
		public String Weakening() { return "Weakening"; } public String Weakening(String word) { return _add(word, Weakening()); }
		public String Weeping() { return "Weeping"; } public String Weeping(String word) { return _add(word, Weeping()); }
		public String Weighted() { return "Weighted"; } public String Weighted(String word) { return _add(word, Weighted()); }
		public String Wet() { return "Wet"; } public String Wet(String word) { return _add(word, Wet()); }
		public String Wheat() { return "Wheat"; } public String Wheat(String word) { return _add(word, Wheat()); }
		public String White() { return "White"; } public String White(String word) { return _add(word, White()); }
		public String Wild() { return "Wild"; } public String Wild(String word) { return _add(word, Wild()); }
		public String Wilder() { return "Wilder"; } public String Wilder(String word) { return _add(word, Wilder()); }
		public String Wildfire() { return "Wildfire"; } public String Wildfire(String word) { return _add(word, Wildfire()); }
		public String Wilted() { return "Wilted"; } public String Wilted(String word) { return _add(word, Wilted()); }
		public String Wind() { return "Wind"; } public String Wind(String word) { return _add(word, Wind()); }
		public String Wine() { return "Wine"; } public String Wine(String word) { return _add(word, Wine()); }
		public String Winter() { return "Winter"; } public String Winter(String word) { return _add(word, Winter()); }
		public String Wire() { return "Wire"; } public String Wire(String word) { return _add(word, Wire()); }
		public String Witch() { return "Witch"; } public String Witch(String word) { return _add(word, Witch()); }
		public String Wither() { return "Wither"; } public String Wither(String word) { return _add(word, Wither()); }
		public String Withering() { return "Withering"; } public String Withering(String word) { return _add(word, Withering()); }
		public String Wolf() { return "Wolf"; } public String Wolf(String word) { return _add(word, Wolf()); }
		public String Wood() { return "Wood"; } public String Wood(String word) { return _add(word, Wood()); }
		public String Woodcutter() { return "Woodcutter"; } public String Woodcutter(String word) { return _add(word, Woodcutter()); }
		public String Wool() { return "Wool"; } public String Wool(String word) { return _add(word, Wool()); }
		public String Wooly() { return "Wooly"; } public String Wooly(String word) { return _add(word, Wooly()); }
		public String Yellow() { return "Yellow"; } public String Yellow(String word) { return _add(word, Yellow()); }
		public String Zoglin() { return "Zoglin"; } public String Zoglin(String word) { return _add(word, Zoglin()); }
		public String Zombie() { return "Zombie"; } public String Zombie(String word) { return _add(word, Zombie()); }
		public String Zombified() { return "Zombified"; } public String Zombified(String word) { return _add(word, Zombified()); }

		public String Subtitle_Block_Step(String name) { return _add(on(Footsteps()), name); }
		public String Subtitle_Block_Hit(String name) { return breaking(name); }
		public String Subtitle_Block_Break(String name) { return broken(name); }
		public String Subtitle_Block_Place(String name) { return placed(name); }

		public String Subtitle_Boat_Paddle(String name) { return Paddling(Boat(name)); }
		public String Subtitle_Boat_Row(String name) { return Rowing(Boat(name)); }

		public String Subtitle_Entity_Big_Fall(String name) { return fell(name); }
		public String Subtitle_Entity_Crash(String name) { return crashes(name); }
		public String Subtitle_Entity_Death(String name) { return dies(name); }
		public String Subtitle_Entity_Small_Fall(String name) { return trips(name); }
		public String Subtitle_Entity_Splash(String name) { return splashes(name); }
		public String Subtitle_Entity_Splash_High_Speed(String name) { return hard(splashes(name)); }
		public String Subtitle_Entity_Step(String name) { return steps(name); }
		public String Subtitle_Entity_Swim(String name) { return swims(name); }
	}
}
