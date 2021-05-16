package nameOfTheWindMod.cards

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.actions.UncommonPowerAction
import nameOfTheWindMod.characters.Kvothe

class DefaultUncommonPower : AbstractDynamicCard(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET) {
    // Actions the card should do.
    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount
        }
        AbstractDungeon.actionManager.addToBottom(
            UncommonPowerAction(
                p, m, magicNumber,
                upgraded, damageTypeForTurn, freeToPlayOnce, energyOnUse
            )
        )
    }

    //Upgraded stats.
    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            rawDescription = UPGRADE_DESCRIPTION
            initializeDescription()
        }
    }

    companion object {
        /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Weirdness Apply X (+1) keywords to yourself.
     */
        // TEXT DECLARATION 
        @JvmField
        val ID = NameOfTheWindMod.makeID(DefaultUncommonPower::class.java.simpleName)
        val IMG = NameOfTheWindMod.makeCardPath("Power.png")
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION

        // /TEXT DECLARATION/
        // STAT DECLARATION 	
        private val RARITY = CardRarity.UNCOMMON
        private val TARGET = CardTarget.SELF
        private val TYPE = CardType.POWER
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = -1
        private const val MAGIC = 1
    }

    // /STAT DECLARATION/
    init {
        baseMagicNumber = MAGIC
        magicNumber = baseMagicNumber
    }
}