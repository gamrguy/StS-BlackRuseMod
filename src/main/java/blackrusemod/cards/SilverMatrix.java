package blackrusemod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import blackrusemod.BlackRuseMod;
import blackrusemod.actions.ApplyMatrixAction;
import blackrusemod.actions.ConvertAction;
import blackrusemod.patches.AbstractCardEnum;

public class SilverMatrix extends CustomCard {
	public static final String ID = "BlackRuseMod:SilverMatrix";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int SATELLITE = 2;

	public SilverMatrix() {
		super(ID, NAME, BlackRuseMod.makePath(BlackRuseMod.SILVER_MATRIX), COST, DESCRIPTION, AbstractCard.CardType.POWER,
				AbstractCardEnum.SILVER, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = SATELLITE;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ConvertAction(this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyMatrixAction());
	}

	public AbstractCard makeCopy() {
		return new SilverMatrix();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(1);
		}
	}
}