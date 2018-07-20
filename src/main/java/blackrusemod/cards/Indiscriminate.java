package blackrusemod.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import basemod.abstracts.CustomCard;
import blackrusemod.BlackRuseMod;
import blackrusemod.patches.AbstractCardEnum;

public class Indiscriminate extends CustomCard {
	public static final String ID = "Indiscriminate";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 8;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final int STRENGTH_LOSS = 4;
	private static final int UPGRADE_STRENGTH_LOSS = 2;

	public Indiscriminate() {
		super(ID, NAME, BlackRuseMod.makePath(BlackRuseMod.INDISCRIMINATE), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.SILVER, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.ALL_ENEMY);
		this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = STRENGTH_LOSS;
		this.isMultiDamage = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.0F));
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));
	}
	
	public void triggerOnManualDiscard() {
		for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, 
					new StrengthPower(mo, -this.magicNumber), -this.magicNumber));
			if ((mo != null) && (!mo.hasPower("Artifact"))) {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, 
						new GainStrengthPower(mo, this.magicNumber), this.magicNumber));
			}
		}
		if (AbstractDungeon.player.hasRelic("KneeBrace")) 
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 3));
	}

	public AbstractCard makeCopy() {
		return new Indiscriminate();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
			upgradeMagicNumber(UPGRADE_STRENGTH_LOSS);
		}
	}
}