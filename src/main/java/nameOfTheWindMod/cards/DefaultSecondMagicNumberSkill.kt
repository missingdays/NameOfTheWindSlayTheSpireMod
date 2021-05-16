package nameOfTheWindMod.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.PoisonPower
import com.megacrit.cardcrawl.powers.VulnerablePower
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.characters.Kvothe

class DefaultSecondMagicNumberSkill : AbstractDynamicCard(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET) {
    // Actions the card should do.
    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(m, p, VulnerablePower(m, magicNumber, false), magicNumber)
        )
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(m, p, PoisonPower(m, p, defaultSecondMagicNumber), defaultSecondMagicNumber)
        )
    }

    // Upgraded stats.
    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPGRADE_PLUS_VULNERABLE)
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_POISON)
            initializeDescription()
        }
    }

    companion object {
        /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Using the second magic number isn't very confusing, you just declare and use it the absolutely the same way you would your
     * your other ones (attack, block, magic, etc.)
     *
     * For how to create it, check out:
     * https://github.com/daviscook477/BaseMod/wiki/Dynamic-Variables
     * The files in this base that detail this are:
     * variables.DefaultSecondMagicNumber and cards.AbstractDefaultCard
     *
     * Apply 2(5) vulnerable and 4(9) poison to an enemy.
     */
        // TEXT DECLARATION
        @JvmField
        val ID = NameOfTheWindMod.makeID(DefaultSecondMagicNumberSkill::class.java.simpleName)
        val IMG = NameOfTheWindMod.makeCardPath("Skill.png")

        // /TEXT DECLARATION/
        // STAT DECLARATION
        private val RARITY = CardRarity.COMMON
        private val TARGET = CardTarget.ENEMY
        private val TYPE = CardType.SKILL
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = 1
        private const val VULNERABLE = 2
        private const val UPGRADE_PLUS_VULNERABLE = 3
        private const val POISON = 4
        private const val UPGRADE_PLUS_POISON = 5
    }

    // /STAT DECLARATION/
    init {
        baseMagicNumber = VULNERABLE
        magicNumber = baseMagicNumber
        defaultBaseSecondMagicNumber = POISON
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber
    }
}