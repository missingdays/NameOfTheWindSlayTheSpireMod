package nameOfTheWindMod.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.characters.Kvothe

class DefaultUncommonAttack : AbstractDynamicCard(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET) {
    // Actions the card should do.
    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
            DamageAction(
                m, DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT
            )
        )
    }

    //Upgraded stats.
    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPGRADE_PLUS_DMG)
            initializeDescription()
        }
    }

    companion object {
        /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */
        // TEXT DECLARATION 
        @JvmField
        val ID = NameOfTheWindMod.makeID(DefaultUncommonAttack::class.java.simpleName)
        val IMG = NameOfTheWindMod.makeCardPath("Attack.png")

        // /TEXT DECLARATION/
        // STAT DECLARATION 	
        private val RARITY = CardRarity.UNCOMMON
        private val TARGET = CardTarget.ENEMY
        private val TYPE = CardType.ATTACK
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = 1
        private const val DAMAGE = 10
        private const val UPGRADE_PLUS_DMG = 5
    }

    // /STAT DECLARATION/
    init {
        baseDamage = DAMAGE
    }
}