package blackrusemod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import blackrusemod.BlackRuseMod;
import blackrusemod.actions.ThrowKnivesAction;
import blackrusemod.patches.AbstractCardEnum;
import blackrusemod.powers.KnivesPower;
import blackrusemod.powers.SilverBladesPower;

public class KillingDoll extends CustomCard {
	public static final String ID = "BlackRuseMod:KillingDoll";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int ATTACK_DMG = 2;
	private static final int UPGRADE_PLUS_DMG = 1;
	private int KNIVES = 0;

	public KillingDoll() {
		super(ID, NAME, BlackRuseMod.makePath(BlackRuseMod.KILLING_DOLL), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.SILVER, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);
		this.baseDamage = ATTACK_DMG;
		this.exhaust = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p.hasPower(KnivesPower.POWER_ID)) {
			this.KNIVES = p.getPower(KnivesPower.POWER_ID).amount;
			for (int i = 0; i < KNIVES; i++)
				AbstractDungeon.actionManager.addToBottom(new ThrowKnivesAction(p, m, new DamageInfo(p, this.baseDamage, this.damageTypeForTurn), "Golden"));
			this.KNIVES = p.getPower(KnivesPower.POWER_ID).amount;
			for (int i = 0; i < KNIVES; i++)
				AbstractDungeon.actionManager.addToBottom(new ThrowKnivesAction(p, m, new DamageInfo(p, this.baseDamage, this.damageTypeForTurn), "Golden"));
		}
	}

	public AbstractCard makeCopy() {
		return new KillingDoll();
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