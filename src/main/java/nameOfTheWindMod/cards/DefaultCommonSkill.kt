package nameOfTheWindMod.cards

import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.characters.Kvothe

class DefaultCommonSkill : AbstractDynamicCard(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET) {
    // Actions the card should do.
    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(GainBlockAction(p, p, block))
    }

    //Upgraded stats.
    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeBlock(UPGRADE_PLUS_BLOCK)
            initializeDescription()
        }
    }

    companion object {
        /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */
        // TEXT DECLARATION
        @JvmField
        val ID = NameOfTheWindMod.makeID(DefaultCommonSkill::class.java.simpleName)
        val IMG = NameOfTheWindMod.makeCardPath("Skill.png")

        // /TEXT DECLARATION/
        // STAT DECLARATION 	
        private val RARITY = CardRarity.BASIC
        private val TARGET = CardTarget.SELF
        private val TYPE = CardType.SKILL
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = 1
        private const val BLOCK = 5
        private const val UPGRADE_PLUS_BLOCK = 3
    }

    // /STAT DECLARATION/
    init {
        baseBlock = BLOCK
        tags.add(CardTags.STARTER_DEFEND) //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.
    }
}