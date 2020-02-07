package blackrusemod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import blackrusemod.BlackRuseMod;
import blackrusemod.powers.ElegancePower;
import blackrusemod.powers.KnivesPower;
import blackrusemod.powers.SatellitePower;

public class SplendidAttire extends CustomRelic {
	public static final String ID = "BlackRuseMod:SplendidAttire";
	public static final int KNIVES = 12;
	
	public SplendidAttire() {
		super(ID, ImageMaster.loadImage(BlackRuseMod.SPLENDID_ATTIRE_RELIC), ImageMaster.loadImage(BlackRuseMod.SPLENDID_ATTIRE_RELIC_OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
	}

	@Override
	public void obtain() {
		if (AbstractDungeon.player.hasRelic(Uniform.ID)) this.instantObtain(AbstractDungeon.player, 0, false);
		else super.obtain();
	}
	
	@Override
	public void atBattleStart() {
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, 
				new KnivesPower(AbstractDungeon.player, KNIVES)));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
				new ElegancePower(AbstractDungeon.player, 1), 1));
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, 
				new SatellitePower(AbstractDungeon.player, 3), 3));
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new SplendidAttire();
	}	
}