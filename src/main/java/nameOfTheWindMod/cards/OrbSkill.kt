package nameOfTheWindMod.cards

import com.megacrit.cardcrawl.actions.defect.ChannelAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.characters.Kvothe
import nameOfTheWindMod.orbs.DefaultOrb

class OrbSkill  // /STAT DECLARATION/
    : AbstractDynamicCard(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET) {
    // Actions the card should do.
    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(ChannelAction(DefaultOrb())) // Channel a Default Orb.
    }

    // Upgraded stats.
    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            initializeDescription()
        }
    }

    companion object {
        /*
     * Orb time.
     *
     * Channel 1 Default Orb.
     */
        // TEXT DECLARATION
        @JvmField
        val ID = NameOfTheWindMod.makeID(OrbSkill::class.java.simpleName)
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val IMG = NameOfTheWindMod.makeCardPath("Skill.png")
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION

        // /TEXT DECLARATION/
        // STAT DECLARATION
        private val RARITY = CardRarity.UNCOMMON
        private val TARGET = CardTarget.SELF
        private val TYPE = CardType.SKILL
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = 1
    }
}