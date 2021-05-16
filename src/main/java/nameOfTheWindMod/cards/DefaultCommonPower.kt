package nameOfTheWindMod.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.characters.Kvothe
import nameOfTheWindMod.powers.CommonPower

class DefaultCommonPower : AbstractDynamicCard(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET) {
    // Actions the card should do.
    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                p, p,
                CommonPower(p, p, magicNumber), magicNumber
            )
        )
        /*
        Hey do you see this "amount" and "stackAmount" up here^ (press ctrl+p inside the parentheses to see parameters)
        THIS DOES NOT MEAN APPLY 1 POWER 1 TIMES. If you put 2 in both numbers it would apply 2. NOT "2 STACKS, 2 TIMES".

        The stackAmount is for telling ApplyPowerAction what to do if a stack already exists. Which means that it will go
        "ah, I see this power has an ID ("") that matches the power I received. I will therefore instead add the stackAmount value
        to this existing power's amount" (Thank you Johnny)

        Which is why if we want to apply 2 stacks with this card every time, want 2 in both numbers -
        "Always add 2, even if the player already has this power."
        */
    }

    //Upgraded stats.
    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPGRADE_MAGIC)
            rawDescription = UPGRADE_DESCRIPTION
            initializeDescription()
        }
    }

    companion object {
        /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Hold Place Gain 1(2) Keywords(s).
     */
        // TEXT DECLARATION 
        @JvmField
        val ID = NameOfTheWindMod.makeID(DefaultCommonPower::class.java.simpleName)
        val IMG = NameOfTheWindMod.makeCardPath("Power.png")
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION

        // /TEXT DECLARATION/
        // STAT DECLARATION 	
        private val RARITY = CardRarity.COMMON
        private val TARGET = CardTarget.SELF
        private val TYPE = CardType.POWER
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = 1
        private const val MAGIC = 1
        private const val UPGRADE_MAGIC = 1
    }

    // Hey want a second magic/damage/block/unique number??? Great!
    // Go check out DefaultAttackWithVariable and theDefault.variable.DefaultCustomVariable
    // that's how you get your own custom variable that you can use for anything you like.
    // Feel free to explore other mods to see what variables they personally have and create your own ones.
    // /STAT DECLARATION/
    init {
        baseMagicNumber = MAGIC
        magicNumber = baseMagicNumber
    }
}