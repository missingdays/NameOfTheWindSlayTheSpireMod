package nameOfTheWindMod.variables

import basemod.abstracts.DynamicVariable
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import nameOfTheWindMod.NameOfTheWindMod

class DefaultCustomVariable : DynamicVariable() {
    // Custom Dynamic Variables are what you do if you need your card text to display a cool, changing number that the base game doesn't provide.
    // If the !D! and !B! (for Damage and Block) etc. are not enough for you, this is how you make your own one. It Changes In Real Time!
    // This is what you type in your card string to make the variable show up. Remember to encase it in "!"'s in the json!
    override fun key(): String {
        return NameOfTheWindMod.makeID("ENERGY_DAMAGE")
    }

    // Checks whether the current value is different than the base one. 
    // For example, this will check whether your damage is modified (i.e. by strength) and color the variable appropriately (Green/Red).
    override fun isModified(card: AbstractCard): Boolean {
        return card.isDamageModified
    }

    // The value the variable should display.
    // In our case, it displays the damage the card would do, multiplied by the amount of energy the player currently has.
    override fun value(card: AbstractCard): Int {
        return card.damage * EnergyPanel.getCurrentEnergy()
    }

    // The baseValue the variable should display.
    // just like baseBlock or baseDamage, this is what the variable should reset to by default. (the base value before any modifications)
    override fun baseValue(card: AbstractCard): Int {
        return card.baseDamage * EnergyPanel.getCurrentEnergy()
    }

    // If the card has it's damage upgraded, this variable will glow green on the upgrade selection screen as well.
    override fun upgraded(card: AbstractCard): Boolean {
        return card.upgradedDamage
    }
}