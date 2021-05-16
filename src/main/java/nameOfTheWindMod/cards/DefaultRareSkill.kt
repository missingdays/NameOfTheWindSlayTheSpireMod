package nameOfTheWindMod.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.VulnerablePower
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.characters.Kvothe

class DefaultRareSkill : AbstractDynamicCard(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET) {
    private var TIMES = 2
    private val UPGRADE_TIMES = 3
    private val AMOUNT = 1

    // Actions the card should do.
    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        for (i in 0 until TIMES) {
            for (mo in AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                        mo, p,
                        VulnerablePower(mo, magicNumber, false), magicNumber
                    )
                )
            }
        }
    }

    //Upgraded stats.
    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            rawDescription = UPGRADE_DESCRIPTION
            TIMES = UPGRADE_TIMES
            initializeDescription()
        }
    }

    companion object {
        /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * For Each Loop x2" "Apply 1 Vulnerable to all enemies, 2(3) times.
     */
        // TEXT DECLARATION 
        @JvmField
        val ID = NameOfTheWindMod.makeID(DefaultRareSkill::class.java.simpleName)
        val IMG = NameOfTheWindMod.makeCardPath("Skill.png")
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION

        // /TEXT DECLARATION/
        // STAT DECLARATION 	
        private val RARITY = CardRarity.RARE
        private val TARGET = CardTarget.ALL_ENEMY
        private val TYPE = CardType.SKILL
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = 1
    }

    // /STAT DECLARATION/
    init {
        magicNumber = AMOUNT
        baseMagicNumber = magicNumber
    }
}