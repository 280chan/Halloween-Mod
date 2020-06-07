package relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.BlueCandle;

import basemod.abstracts.CustomRelic;
import cards.Halloween;
import mymod.HalloweenMod;

public class EventCelebration_Halloween extends CustomRelic {
	public static final String ID = HalloweenMod.MOD_PREFIX + "Halloween";//遗物Id，添加遗物、替换遗物时填写该id而不是遗物类名。
    public static final String IMG = "resources/images/relic.png";
	public static final String DESCRIPTION = "拾取时获得 #y蓝蜡烛 。战斗奖励掉落的卡牌只能从 #y糖果 、 #y捣乱 、 #y鬼妆 中选择。每当你打出 #y诅咒牌 时，将一张 #y万圣 加入手牌。";//遗物效果的文本描叙。
	
	public static void show() {
		AbstractDungeon.player.getRelic(ID).flash();
	}
	
	public EventCelebration_Halloween() {
		super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.SPECIAL, LandingSound.MAGICAL);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	public void onPlayCard(final AbstractCard c, final AbstractMonster m) {
		if (c.type == CardType.CURSE) {
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Halloween()));
			this.flash();
		}
    }
	
	public void onEquip() {
		HalloweenMod.obtain(AbstractDungeon.player, new BlueCandle(), false);
		AbstractDungeon.uncommonRelicPool.remove(BlueCandle.ID);
    }
	
}