package utils;

import java.util.ArrayList;

import com.badlogic.gdx.math.RandomXS128;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.cards.green.Nightmare;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.beyond.OrbWalker;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.random.Random;

import mymod.HalloweenMod;

public interface HalloweenMiscMethods {
	public static class ExtraCardAdder {
		public static void addHoarderCard(CardGroup g, AbstractCard c) {
			if (ModHelper.isModEnabled("Hoarder")) {
				addExtraCard(g, c, 2);
			}
		}
		
		public static void addExtraCard(CardGroup g, AbstractCard c, int num) {
			for (int i = 0; i < num; i++)
				g.addToTop(c.makeStatEquivalentCopy());
		}
	}
	
	public static class RNGTools {
		private static Random rng;
		private static Random cardRNG;
		
		static void setRNG(Random source) {
			rng = new Random();
			rng.random = new RandomXS128(source.random.getState(0), source.random.getState(1));
			rng.counter = 0;
		}
		
		static void setCardRNG(Random source) {
			cardRNG = new Random();
			cardRNG.random = new RandomXS128(source.random.getState(0), source.random.getState(1));
			cardRNG.counter = 0;
		}
	}
	
	public default void setCardRNG(Random source) {
		RNGTools.setCardRNG(source);
	}
	
	public default void setRNG(Random source) {
		RNGTools.setRNG(source);
	}
	
	static AbstractPower randomPower(AbstractCreature p) {
		ArrayList<AbstractPower> l = new ArrayList<AbstractPower>();
		l.add(new AccuracyPower(p, 3));
		l.add(new AfterImagePower(p, 1));
		l.add(new AmplifyPower(p, 1));
		l.add(new AngerPower(p, 1));
		l.add(new AngryPower(p, 1));
		l.add(new ArtifactPower(p, 1));
		l.add(new BarricadePower(p));
		l.add(new BerserkPower(p, 1));
		l.add(new BiasPower(p, 1));
		l.add(new BlurPower(p, 1));
		l.add(new BrutalityPower(p, 1));
		l.add(new BufferPower(p, 1));
		l.add(new BurstPower(p, 1));
		l.add(new ChokePower(p, 3));
		l.add(new CollectPower(p, 1));
		l.add(new CombustPower(p, 1, 5));
		l.add(new ConfusionPower(p));
		l.add(new ConservePower(p, 1));
		l.add(new ConstrictedPower(p, p, 10));
		l.add(new CorruptionPower(p));
		l.add(new CreativeAIPower(p, 1));
		l.add(new CuriosityPower(p, 1));
		l.add(new DarkEmbracePower(p, 1));
		l.add(new DemonFormPower(p, 3));
		l.add(new DexterityPower(p, 1));
		l.add(new DoubleDamagePower(p, 1, false));
		l.add(new DoubleTapPower(p, 1));
		l.add(new DrawCardNextTurnPower(p, 1));
		l.add(new DrawPower(p, 1));
		AbstractPower tempDrawReduction = new DrawReductionPower(p, 1);
		tempDrawReduction.atEndOfRound();
		l.add(tempDrawReduction);
		l.add(new DuplicationPower(p, 1));
		l.add(new EchoPower(p, 1));
		l.add(new ElectroPower(p));
		l.add(new EnergizedBluePower(p, 1));
		l.add(new EnergizedPower(p, 1));
		l.add(new EntanglePower(p));
		l.add(new EnvenomPower(p, 2));
		l.add(new EquilibriumPower(p, 1));
		l.add(new EvolvePower(p, 1));
		l.add(new FeelNoPainPower(p, 3));
		l.add(new FireBreathingPower(p, 1));
		l.add(new FlameBarrierPower(p, 4));
		l.add(new FocusPower(p, 1));
		l.add(new FrailPower(p, 1, false));
		l.add(new GainStrengthPower(p, 1));
		l.add(new GenericStrengthUpPower(p, OrbWalker.MOVES[0], 3));
		l.add(new HeatsinkPower(p, 1));
		l.add(new HelloPower(p, 1));
		l.add(new HexPower(p, 1));
		l.add(new InfiniteBladesPower(p, 1));
		l.add(new IntangiblePlayerPower(p, 1));
		l.add(new JuggernautPower(p, 5));
		l.add(new LoopPower(p, 1));
		l.add(new LoseDexterityPower(p, 1));
		l.add(new LoseStrengthPower(p, 1));
		l.add(new MagnetismPower(p, 1));
		l.add(new MalleablePower(p, 3));
		l.add(new MayhemPower(p, 1));
		l.add(new MetallicizePower(p, 3));
		l.add(new NextTurnBlockPower(p, 4));
		l.add(new NightmarePower(p, 3, new Nightmare()));
		l.add(new NoBlockPower(p, 1, false));
		l.add(new NoDrawPower(p));
		l.add(new NoxiousFumesPower(p, 2));
		l.add(new PanachePower(p, 10));
		l.add(new PenNibPower(p, 1));
		l.add(new PhantasmalPower(p, 1));
		l.add(new PlatedArmorPower(p, 4));
		l.add(new PoisonPower(p, p, 5));
		l.add(new RagePower(p, 3));
		l.add(new RechargingCorePower(p, 1));
		l.add(new RegenPower(p, 5));
		l.add(new RepairPower(p, 7));
		l.add(new RetainCardPower(p, 1));
		l.add(new RitualPower(p, 3, true));
		l.add(new RupturePower(p, 1));
		l.add(new SadisticPower(p, 3));
		l.add(new ShiftingPower(p));
		l.add(new StaticDischargePower(p, 1));
		l.add(new StormPower(p, 1));
		l.add(new StrengthPower(p, 1));
		l.add(new StrikeUpPower(p, 1));
		l.add(new TheBombPower(p, 3, 30));
		l.add(new ThornsPower(p, 3));
		l.add(new ThousandCutsPower(p, 1));
		l.add(new ToolsOfTheTradePower(p, 1));
		l.add(new VulnerablePower(p, 1, false));
		l.add(new WeakPower(p, 1, false));
		//l.add(new WinterPower(p, 1));
		l.add(new WraithFormPower(p, -1));
		
		addWatcherPowerTo(l, p);
		
		AbstractPower retVal = l.get(RNGTools.rng.random(l.size() - 1));
		l.clear();
		if (!(retVal instanceof DrawPower)) {
			AbstractDungeon.player.gameHandSize--;
		}
		return retVal;
	}
	
	static void addWatcherPowerTo(ArrayList<AbstractPower> l, AbstractCreature p) {
		l.add(new BattleHymnPower(p, 1));
		l.add(new BlockReturnPower(p, 2));
		l.add(new DevaPower(p));
		l.add(new DevotionPower(p, 1));
		l.add(new EnergyDownPower(p, 1));
		l.add(new EstablishmentPower(p, 1));
		l.add(new ForesightPower(p, 1));
		l.add(new FreeAttackPower(p, 1));
		l.add(new LikeWaterPower(p, 1));
		l.add(new LiveForeverPower(p, 1));
		l.add(new MantraPower(p, 1));
		l.add(new MasterRealityPower(p));
		l.add(new MentalFortressPower(p, 1));
		l.add(new NirvanaPower(p, 1));
		l.add(new NoSkillsPower(p));
		l.add(new OmegaPower(p, 1));
		l.add(new RushdownPower(p, 1));
		l.add(new StudyPower(p, 1));
		l.add(new VigorPower(p, 1));
		l.add(new WaveOfTheHandPower(p, 1));
		l.add(new WrathNextTurnPower(p));
	}
	
	public default void addRandomPower(AbstractCreature p) {
		AbstractPower po = randomPower(p);
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, po, po.amount));
	}
	
	public default ArrayList<AbstractCard> getRewardCards() {
		ArrayList<AbstractCard> retVal = new ArrayList<AbstractCard>();
		AbstractPlayer player = AbstractDungeon.player;
		float cardUpgradedChance = 0f;
		if (AbstractDungeon.id.equals(TheCity.ID)) {
			cardUpgradedChance = 0.25f;
		} else if (AbstractDungeon.id.equals(TheBeyond.ID)) {
			cardUpgradedChance = 0.5f;
		}
		if (AbstractDungeon.ascensionLevel >= 12) {
			cardUpgradedChance /= 2f;
		}
		int numCards = 3;
		if (player.hasRelic("Question Card")) {
			numCards++;
		}
		if (player.hasRelic("Busted Crown")) {
			numCards -= 2;
		}
		if (ModHelper.isModEnabled("Binary")) {
			numCards--;
		}
		CardGroup g = new CardGroup(CardGroupType.UNSPECIFIED);
		g.group = HalloweenMod.CARDS;
		g.shuffle(RNGTools.cardRNG);
		for (int i = 0; i < numCards; i++) {
			if (i < 3) {
				retVal.add(HalloweenMod.CARDS.get(i));
			} else {
				retVal.add(HalloweenMod.CARDS.get(RNGTools.cardRNG.random(2)));
			}
		}
		ArrayList<AbstractCard> retVal2 = new ArrayList<AbstractCard>();
		for (AbstractCard c : retVal) {
			retVal2.add(c.makeCopy());
		}
		for (AbstractCard c : retVal2) {
			if (!c.canUpgrade())
				continue;
			if ((RNGTools.cardRNG.randomBoolean(cardUpgradedChance))) {
				c.upgrade();
			} else if ((c.type == AbstractCard.CardType.ATTACK) && (player.hasRelic("Molten Egg 2"))) {
				c.upgrade();
			} else if ((c.type == AbstractCard.CardType.SKILL) && (player.hasRelic("Toxic Egg 2"))) {
				c.upgrade();
			} else if ((c.type == AbstractCard.CardType.POWER) && (player.hasRelic("Frozen Egg 2"))) {
				c.upgrade();
			}
		}
		return retVal2;
	}
	
}
