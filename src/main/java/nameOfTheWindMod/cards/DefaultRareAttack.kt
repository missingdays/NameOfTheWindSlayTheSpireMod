package nameOfTheWindMod.cards

import nameOfTheWindMod.cards.AbstractDynamicCard
import nameOfTheWindMod.cards.DefaultRareAttack
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.actions.AbstractGameAction
import nameOfTheWindMod.NameOfTheWindMod
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor
import nameOfTheWindMod.characters.Kvothe

class DefaultRareAttack : AbstractDynamicCard(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET) {
    // Actions the card should do.
    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        if (m != null) {
            AbstractDungeon.actionManager.addToBottom(VFXAction(WeightyImpactEffect(m.hb.cX, m.hb.cY)))
        }
        AbstractDungeon.actionManager.addToBottom(
            DamageAction(
                m, DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.NONE
            )
        )
    }

    //Upgraded stats.
    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPGRADE_PLUS_DMG)
        }
    }

    companion object {
        /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * TOUCH Deal 30(35) damage.
     */
        // TEXT DECLARATION 
        @JvmField
        val ID = NameOfTheWindMod.makeID(DefaultRareAttack::class.java.simpleName)
        val IMG = NameOfTheWindMod.makeCardPath("Attack.png")

        // /TEXT DECLARATION/
        // STAT DECLARATION 	
        private val RARITY = CardRarity.RARE
        private val TARGET = CardTarget.ENEMY
        private val TYPE = CardType.ATTACK
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = 2
        private const val DAMAGE = 30
        private const val UPGRADE_PLUS_DMG = 5
    }

    // /STAT DECLARATION/
    init {
        baseDamage = DAMAGE
    }
}