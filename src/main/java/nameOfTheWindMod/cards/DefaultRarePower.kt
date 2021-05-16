package nameOfTheWindMod.cards

import basemod.helpers.BaseModCardTags
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.characters.Kvothe
import nameOfTheWindMod.powers.RarePower

class DefaultRarePower : AbstractDynamicCard(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET) {
    // Actions the card should do.
    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(p, p, RarePower(p, p, magicNumber), magicNumber)
        )
    }

    //Upgraded stats.
    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeBaseCost(UPGRADE_COST)
            initializeDescription()
        }
    }

    companion object {
        /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * In-Progress Form At the start of your turn, play a TOUCH.
     */
        // TEXT DECLARATION 
        @JvmField
        val ID = NameOfTheWindMod.makeID(DefaultRarePower::class.java.simpleName)
        val IMG = NameOfTheWindMod.makeCardPath("Power.png")

        // /TEXT DECLARATION/
        // STAT DECLARATION 	
        private val RARITY = CardRarity.RARE
        private val TARGET = CardTarget.SELF
        private val TYPE = CardType.POWER
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = 3
        private const val UPGRADE_COST = 2
        private const val MAGIC = 1
    }

    // /STAT DECLARATION/
    init {
        baseMagicNumber = MAGIC
        magicNumber = baseMagicNumber
        tags.add(BaseModCardTags.FORM) //Tag your strike, defend and form cards so that they work correctly.
    }
}