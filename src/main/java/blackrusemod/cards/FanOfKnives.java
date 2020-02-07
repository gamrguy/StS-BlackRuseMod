package blackrusemod.cards;

import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomCard;
import blackrusemod.BlackRuseMod;
import blackrusemod.actions.ThrowKnivesAction;
import blackrusemod.patches.AbstractCardEnum;
import blackrusemod.powers.SilverBladesPower;

public class FanOfKnives extends CustomCard {
	public static final String ID = "BlackRuseMod:FanOfKnives";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = -1;
	private static final int ATTACK_DMG = 7;
	private static final int UPGRADE_PLUS_DMG = 3;

	public FanOfKnives() {
		super(ID, NAME, BlackRuseMod.makePath(BlackRuseMod.FAN_OF_KNIVES), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.SILVER, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.ALL_ENEMY);
		this.baseDamage = ATTACK_DMG;
		this.isMultiDamage = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (this.energyOnUse < EnergyPanel.totalCount) {
			this.energyOnUse = EnergyPanel.totalCount;
		}
		if (p.hasRelic("Chemical X")) p.getRelic("Chemical X").flash();
		if (AbstractDungeon.player.hasRelic("Chemical X")) this.energyOnUse += 2;
		for (int i = 0; i < this.energyOnUse; i++)
			for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) 
				AbstractDungeon.actionManager.addToBottom(new ThrowKnivesAction(p, mo, new DamageInfo(p, this.baseDamage, this.damageTypeForTurn), null));
		if (AbstractDungeon.player.hasRelic("Chemical X")) this.energyOnUse -= 2;
		AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(this.energyOnUse));
	}

	public AbstractCard makeCopy() {
		return new FanOfKnives();
	}
	
	public void applyPowers() {
		this.baseDamage = ATTACK_DMG;
		if (!this.canUpgrade()) this.baseDamage += UPGRADE_PLUS_DMG;
		if (AbstractDungeon.player.hasPower(SilverBladesPower.POWER_ID)) 
			this.baseDamage += AbstractDungeon.player.getPower(SilverBladesPower.POWER_ID).amount;
		super.applyPowers();
		if (AbstractDungeon.player.hasPower(SilverBladesPower.POWER_ID))
			this.isDamageModified = true;
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
		}
	}
}