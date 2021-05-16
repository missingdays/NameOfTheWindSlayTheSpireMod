package nameOfTheWindMod.powers

import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.powers.AbstractPower
import basemod.interfaces.CloneablePowerInterface
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.powers.DexterityPower
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import nameOfTheWindMod.powers.CommonPower
import nameOfTheWindMod.NameOfTheWindMod
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import nameOfTheWindMod.util.TextureLoader

//Gain 1 dex for the turn for each card played.
class CommonPower(owner: AbstractCreature?, source: AbstractCreature, amount: Int) : AbstractPower(),
    CloneablePowerInterface {
    var source: AbstractCreature

    // On use card, apply (amount) of Dexterity. (Go to the actual power card for the amount.)
    override fun onUseCard(card: AbstractCard, action: UseCardAction) {
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                owner, owner,
                DexterityPower(owner, amount), amount
            )
        )
    }

    // Note: If you want to apply an effect when a power is being applied you have 3 options:
    //onInitialApplication is "When THIS power is first applied for the very first time only."
    //onApplyPower is "When the owner applies a power to something else (only used by Sadistic Nature)."
    //onReceivePowerPower from StSlib is "When any (including this) power is applied to the owner."
    // At the end of the turn, remove gained Dexterity.
    override fun atEndOfTurn(isPlayer: Boolean) {
        var count = 0
        for (c in AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            // This is how you iterate through arrays (like the one above) and card groups like
            // "AbstractDungeon.player.masterDeck.getAttacks().group" - every attack in your actual master deck.
            // Read up on java's enhanced for-each loops if you want to know more on how these work.
            ++count // At the end of your turn, increase the count by 1 for each card played this turn.
        }
        if (count > 0) {
            flash() // Makes the power icon flash.
            for (i in 0 until count) {
                AbstractDungeon.actionManager.addToBottom(
                    ReducePowerAction(owner, owner, DexterityPower.POWER_ID, amount)
                )
                // Reduce the power by 1 for each count - i.e. for each card played this turn.
                // DO NOT HARDCODE YOUR STRINGS ANYWHERE: i.e. don't write any Strings directly i.e. "Dexterity" for the power ID above.
                // Use the power/card/relic etc. and fetch it's ID like shown above. It's really bad practice to have "Strings" in your code:

                /*
                 * 1. It's bad for if somebody likes your mod enough (or if you decide) to translate it.
                 * Having only the JSON files for translation rather than 15 different instances of "Dexterity" in some random cards is A LOT easier.
                 *
                 * 2. You don't have a centralised file for all strings for easy proof-reading, and if you ever want to change a string
                 * you now have to go through all your files individually.
                 *
                 * 3. Without hardcoded strings, editing a string doesn't require a compile, saving you time (unless you clean+package).
                 *
                 */
            }
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    override fun updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2]
        }
    }

    override fun makeCopy(): AbstractPower {
        return CommonPower(owner, source, amount)
    }

    companion object {
        val POWER_ID = NameOfTheWindMod.makeID("CommonPower")
        private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS

        // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
        // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
        private val tex84 = TextureLoader.getTexture(NameOfTheWindMod.makePowerPath("placeholder_power84.png"))
        private val tex32 = TextureLoader.getTexture(NameOfTheWindMod.makePowerPath("placeholder_power32.png"))
    }

    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = amount
        this.source = source
        type = PowerType.BUFF
        isTurnBased = false

        // We load those txtures here.
        region128 = AtlasRegion(tex84, 0, 0, 84, 84)
        region48 = AtlasRegion(tex32, 0, 0, 32, 32)
        updateDescription()
    }
}