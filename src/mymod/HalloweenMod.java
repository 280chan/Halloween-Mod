package mymod;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardItem.RewardType;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import basemod.interfaces.StartGameSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import cards.*;
import relics.*;
import utils.HalloweenMiscMethods;

/**
 * @author 彼君不触
 * @version 3/6/2020
 * @since 10/14/2018
 */

@SpireInitializer
public class HalloweenMod implements EditKeywordsSubscriber, EditRelicsSubscriber, EditCardsSubscriber, EditStringsSubscriber, PostDungeonInitializeSubscriber, PostUpdateSubscriber, StartGameSubscriber, PostInitializeSubscriber, OnStartBattleSubscriber, PostBattleSubscriber, HalloweenMiscMethods {
	
	public static final String MOD_PREFIX = "HalloweenMod";
	
	public static void initialize() {
		BaseMod.subscribe(new HalloweenMod());
	}

	@Override
	public void receiveEditKeywords() {
		// TODO Auto-generated method stub
		switch (Settings.language) {
		case ZHS:
		case ZHT:
			BaseMod.addKeyword(new String[] { "万圣" }, "万圣是一张可以被多次升级的 #b0 费技能牌。");
			break;
		default:
			BaseMod.addKeyword(new String[] { "halloween" }, "Halloween is a #b0 cost Skill card which can be Upgraded any number of times.");
			break;
		}
	}

	@Override
	public void receiveEditRelics() {
		BaseMod.addRelic(new EventCelebration_Halloween(), RelicType.SHARED);
	}

	@Override
	public void receiveEditStrings() {
		String lang = "s_";
		switch (Settings.language) {
		case ZHS:
		case ZHT:
			lang += "zh.json";
			break;
		default:
			lang += "eng.json";
			break;
		}
		String pathPrefix = "localization/" + MOD_PREFIX;
	    BaseMod.loadCustomStringsFile(RelicStrings.class, pathPrefix + "Relic" + lang);
	    BaseMod.loadCustomStringsFile(CardStrings.class, pathPrefix + "Card" + lang);
	    BaseMod.loadCustomStringsFile(PowerStrings.class, pathPrefix + "Power" + lang);
	}

	@Override
	public void receiveEditCards() {
		AbstractCard[] card = {new Candy(), new Trick(), new GhostCostume(), new Halloween()};
		for (AbstractCard c : card) {
			BaseMod.addCard(c);
			CARDS.add(c);
		}
		CARDS.remove(3);
	}
	
	public static ArrayList<AbstractCard> CARDS = new ArrayList<AbstractCard>();

	private static void avoidFirstTime() {
		TipTracker.neverShowAgain("RELIC_TIP");
	}
	
	@Override
	public void receivePostDungeonInitialize() {
		if (AbstractDungeon.floorNum > 1) {
			return;
		}
		obtain(AbstractDungeon.player, new EventCelebration_Halloween(), false);
		savedFloorNum = -2;
		changeState();
	}

	public static boolean obtain(AbstractPlayer p, AbstractRelic r, boolean canDuplicate) {
		if (r == null)
			return false;
		if (!p.hasRelic(r.relicId) || canDuplicate) {
			int slot = p.relics.size();
			r.makeCopy().instantObtain(p, slot, true);
			return true;
		}
		return false;
	}
	
	private static boolean check() {
		return AbstractDungeon.player.hasRelic(EventCelebration_Halloween.ID);
	}
	
	private static boolean checkCards() {
		AbstractPlayer p = AbstractDungeon.player;
		if (ModHelper.isModEnabled("Vintage") && (!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite))
				&& (!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss))) {
			return true;
		}
		return p.hasRelic("Busted Crown") && ModHelper.isModEnabled("Binary") && !p.hasRelic("Question Card");
	}
	
	private static void changeState() {
		postBattle = startGame = false;
	}
	
	@Override
	public void receivePostUpdate() {
		// TODO Auto-generated method stub
		AbstractPlayer p = AbstractDungeon.player;
		if (p != null) {
			if (check() && !checkCards() && (postBattle || startGame)) {
				for (RewardItem r : AbstractDungeon.combatRewardScreen.rewards) {
					if (r.type == RewardType.CARD) {
						System.out.println("更改卡牌奖励");
						r.cards = this.getRewardCards();
						EventCelebration_Halloween.show();
						changeState();
					}
				}
			}
		}
	}
	
	@Override
	public void receiveStartGame() {
		AbstractDungeon.shopRelicPool.remove("PrismaticShard");
		this.setCardRNG(AbstractDungeon.cardRng);
		if (check() && !checkCards()) {
			System.out.println("当前楼层: " + AbstractDungeon.floorNum);
			System.out.println("存储楼层: " + savedFloorNum);
			if (savedFloorNum == -2 && AbstractDungeon.floorNum > 0) {
				savedFloorNum = AbstractDungeon.floorNum;
			}
			if (AbstractDungeon.floorNum == savedFloorNum) {
				startGame = true;
			}
		} else {
			System.out.println(check());
		}
	}
	
	@Override
	public void receivePostInitialize() {
		avoidFirstTime();
	}

	@Override
	public void receiveOnBattleStart(AbstractRoom room) {
		this.setRNG(AbstractDungeon.miscRng);
	}
	
	private static boolean startGame = false;
	private static boolean postBattle = false;
	private static int savedFloorNum = -2;
	
	@Override
	public void receivePostBattle(AbstractRoom room) {
		if (check() && !checkCards())
			postBattle = true;
		savedFloorNum = AbstractDungeon.floorNum;
	}

}
