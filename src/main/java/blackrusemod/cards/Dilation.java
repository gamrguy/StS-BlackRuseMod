package blackrusemod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import blackrusemod.BlackRuseMod;
import blackrusemod.patches.AbstractCardEnum;
import blackrusemod.powers.ElegancePower;
import blackrusemod.powers.ProtectionPower;

public class Dilation extends CustomCard {
	public static final String ID = "BlackRuseMod:Dilation";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	private static final int PROTECTION_AMT = 3;
	private static final int UPGRADE_PROTECTION_BLOCK = 1;
	public int used;

	public Dilation() {
		super(ID, NAME, BlackRuseMod.makePath(BlackRuseMod.DILATION), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.SILVER, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = PROTECTION_AMT;
		this.used = 0;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ProtectionPower(p, this.magicNumber), this.magicNumber));
		this.used += 1;
	}

	public AbstractCard makeCopy() {
		return new Dilation();
	}
	
	public void applyPowers() {
		this.magicNumber = this.baseMagicNumber = (PROTECTION_AMT + this.used);
		if (!this.canUpgrade()) upgradeMagicNumber(UPGRADE_PROTECTION_BLOCK);
		if (AbstractDungeon.player.hasPower(ElegancePower.POWER_ID)) {
			upgradeMagicNumber(AbstractDungeon.player.getPower(ElegancePower.POWER_ID).amount);
			this.isMagicNumberModified = true;
		}
		super.applyPowers();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_PROTECTION_BLOCK);
		}
	}
}