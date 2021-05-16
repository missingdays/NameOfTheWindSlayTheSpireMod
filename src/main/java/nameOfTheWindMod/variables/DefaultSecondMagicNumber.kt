package nameOfTheWindMod.variables

import basemod.abstracts.DynamicVariable
import com.megacrit.cardcrawl.cards.AbstractCard
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.cards.AbstractDefaultCard

class DefaultSecondMagicNumber : DynamicVariable() {
    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.
    override fun key(): String {
        return NameOfTheWindMod.makeID("SecondMagic")
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    override fun isModified(card: AbstractCard): Boolean {
        return (card as AbstractDefaultCard).isDefaultSecondMagicNumberModified
    }

    override fun value(card: AbstractCard): Int {
        return (card as AbstractDefaultCard).defaultSecondMagicNumber
    }

    override fun baseValue(card: AbstractCard): Int {
        return (card as AbstractDefaultCard).defaultBaseSecondMagicNumber
    }

    override fun upgraded(card: AbstractCard): Boolean {
        return (card as AbstractDefaultCard).upgradedDefaultSecondMagicNumber
    }
}