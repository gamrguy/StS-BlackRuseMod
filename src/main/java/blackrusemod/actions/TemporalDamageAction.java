package blackrusemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class TemporalDamageAction extends AbstractGameAction {
	private DamageInfo info;
	public TemporalDamageAction(int damage)
	{
		this.duration = com.megacrit.cardcrawl.core.Settings.ACTION_DUR_XFAST;
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.info = new DamageInfo(AbstractDungeon.player, damage, DamageType.NORMAL);
	}

	public void update()
	{
		AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new CleaveEffect(), 0.0F));
		for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
			if ((mo != null) && (!mo.isDeadOrEscaped())) {
				this.info.applyPowers(this.info.owner, mo);
				mo.damage(this.info);
			}
		}
		this.isDone = true;
	}

}