package nameOfTheWindMod.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.characters.Kvothe

// "How come this card extends CustomCard and not DynamicCard like all the rest?"
// Skip this question until you start figuring out the AbstractDefaultCard/AbstractDynamicCard and just extend DynamicCard
// for your own ones like all the other cards.
// Well every card, at the end of the day, extends CustomCard.
// Abstract Default Card extends CustomCard and builds up on it, adding a second magic number. Your card can extend it and
// bam - you can have a second magic number in that card (Learn Java inheritance if you want to know how that works).
// Abstract Dynamic Card builds up on Abstract Default Card even more and makes it so that you don't need to add
// the NAME and the DESCRIPTION into your card - it'll get it automatically. Of course, this functionality could have easily
// Been added to the default card rather than creating a new Dynamic one, but was done so to deliberately to showcase custom cards/inheritance a bit more.
class DefaultCommonAttack : CustomCard(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET) {
    // Actions the card should do.
    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom( // The action managed queues all the actions a card should do.
            // addToTop - first
            // addToBottom - last
            // 99.99% of the time you just want to addToBottom all of them.
            // Please do that unless you need to add to top for some specific reason.
            DamageAction(
                m,
                DamageInfo(
                    p,
                    damage,
                    damageTypeForTurn
                ),  // a list of existing actions can be found at com.megacrit.cardcrawl.actions but
                // Chances are you'd instead look at "hey my card is similar to this basegame card"
                // Let's find out what action *it* uses.
                // I.e. i want energy gain or card draw, lemme check out Adrenaline
                // P.s. if you want to damage ALL enemies OUTSIDE of a card, check out the custom orb.
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
            )
        ) // The animation the damage action uses to hit.
    }

    // Upgraded stats.
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
     * Strike Deal 7(9) damage.
     */
        // TEXT DECLARATION
        @JvmField
        val ID = NameOfTheWindMod.makeID(DefaultCommonAttack::class.java.simpleName)
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val IMG = NameOfTheWindMod.makeCardPath("Attack.png")

        // Setting the image as as easy as can possibly be now. You just need to provide the image name
        // and make sure it's in the correct folder. That's all.
        // There's makeCardPath, makeRelicPath, power, orb, event, etc..
        // The list of all of them can be found in the main DefaultMod.java file in the
        // ==INPUT TEXTURE LOCATION== section under ==MAKE IMAGE PATHS==
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION

        // /TEXT DECLARATION/
        // STAT DECLARATION
        private val RARITY = CardRarity.BASIC
        private val TARGET = CardTarget.ENEMY
        private val TYPE = CardType.ATTACK
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = 1
        private const val DAMAGE = 6
        private const val UPGRADE_PLUS_DMG = 3
    }

    // Hey want a second damage/magic/block/unique number??? Great!
    // Go check out DefaultAttackWithVariable and theDefault.variable.DefaultCustomVariable
    // that's how you get your own custom variable that you can use for anything you like.
    // Feel free to explore other mods to see what variables they personally have and create your own ones.
    // /STAT DECLARATION/
    // IMPORTANT NOTE: If you add parameters to your constructor, you'll crash the auto-add cards with a
    // `NoSuchMethodException` because it except a constructor with no params.
    // (If you don't know what a constructor or params are or what not pls google, java questions = java study)
    // You have two option:
    // 1. Create a new constructor with empty parameters call your custom one with default params in it
    // 2. Mark the card with @AutoAdd.NotSeen (https://github.com/daviscook477/BaseMod/wiki/AutoAdd) to prevent it from
    // being auto-add it, and then load it manually with
    // BaseMod.addCard(new DefaultCommonAttack());
    // UnlockTracker.unlockCard(DefaultCommonAttack.ID);
    // in your main class, in the receiveEditCards() method
    init {

        // Aside from baseDamage/MagicNumber/Block there's also a few more.
        // Just type this.base and let intelliJ auto complete for you, or, go read up AbstractCard
        baseDamage = DAMAGE
        tags.add(CardTags.STARTER_STRIKE) //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.
        tags.add(CardTags.STRIKE)
    }
}