package blackrusemod.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import blackrusemod.BlackRuseMod;
import blackrusemod.cards.TemporalEssence;

public class RomanBracelet extends CustomRelic {
	public static final String ID = "BlackRuseMod:RomanBracelet";
	
	public RomanBracelet() {
		super(ID, ImageMaster.loadImage(BlackRuseMod.ROMAN_BRACELET_RELIC), ImageMaster.loadImage(BlackRuseMod.ROMAN_BRACELET_RELIC_OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
	}

	@Override
	public void atBattleStart() {
		flash();
		AbstractCard c = new TemporalEssence().makeCopy();
		addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		addToBot(new MakeTempCardInDrawPileAction(c, 2, true, false));
		this.grayscale = true;
	}
	
	@Override
	public void onVictory() {
		this.grayscale = false;
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new RomanBracelet();
	}	
}