package nameOfTheWindMod.potions

import basemod.abstracts.CustomPotion
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.powers.LoseStrengthPower
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.rooms.AbstractRoom
import nameOfTheWindMod.NameOfTheWindMod

class PlaceholderPotion : CustomPotion(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.M, PotionColor.SMOKE) {
    // See that description? It has DESCRIPTIONS[1] instead of just hard-coding the "text " + potency + " more text" inside.
    // DO NOT HARDCODE YOUR STRINGS ANYWHERE, it's really bad practice to have "Strings" in your code:
    /*
     * 1. It's bad for if somebody likes your mod enough (or if you decide) to translate it.
     * Having only the JSON files for translation rather than 15 different instances of "Dexterity" in some random cards is A LOT easier.
     *
     * 2. You don't have a centralised file for all strings for easy proof-reading. If you ever want to change a string
     * you don't have to go through all your files individually/pray that a mass-replace doesn't screw something up.
     *
     * 3. Without hardcoded strings, editing a string doesn't require a compile, saving you time (unless you clean+package).
     *
     */
    override fun use(target: AbstractCreature) {
        val actualTarget = AbstractDungeon.player
        // If you are in combat, gain strength and the "lose strength at the end of your turn" power, equal to the potency of this potion.
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                    actualTarget,
                    AbstractDungeon.player,
                    StrengthPower(actualTarget, potency),
                    potency
                )
            )
            AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                    actualTarget,
                    AbstractDungeon.player,
                    LoseStrengthPower(actualTarget, potency),
                    potency
                )
            )
        }
    }

    override fun makeCopy(): AbstractPotion {
        return PlaceholderPotion()
    }

    // This is your potency.
    override fun getPotency(potency: Int): Int {
        return 2
    }

    fun upgradePotion() {
        potency += 1
        tips.clear()
        tips.add(PowerTip(name, description))
    }

    companion object {
        @JvmField
        val POTION_ID = NameOfTheWindMod.makeID("PlaceholderPotion")
        private val potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID)
        val NAME = potionStrings.NAME
        val DESCRIPTIONS = potionStrings.DESCRIPTIONS
    }

    init {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java

        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency()

        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[2] + DESCRIPTIONS[1] + potency + DESCRIPTIONS[2]

        // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false

        // Initialize the on-hover name + description
        tips.add(PowerTip(name, description))
    }
}