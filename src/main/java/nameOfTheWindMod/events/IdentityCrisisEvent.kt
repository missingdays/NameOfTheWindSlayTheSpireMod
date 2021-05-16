package nameOfTheWindMod.events

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.cards.colorless.Apotheosis
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.events.AbstractImageEvent
import com.megacrit.cardcrawl.helpers.RelicLibrary
import com.megacrit.cardcrawl.helpers.ScreenShake
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect
import nameOfTheWindMod.NameOfTheWindMod

class IdentityCrisisEvent : AbstractImageEvent(NAME, DESCRIPTIONS[0], IMG) {
    private val screenNum = 0 // The initial screen we will see when encountering the event - screen 0;
    private val HEALTH_LOSS_PERCENTAGE = 0.03f // 3%
    private val HEALTH_LOSS_PERCENTAGE_LOW_ASCENSION = 0.05f // 5%
    private var healthdamage //The actual number of how much Max HP we're going to lose.
            = 0

    override fun buttonEffect(i: Int) { // This is the event:
        when (screenNum) {
            0 -> when (i) {
                0 -> {
                    imageEventText.updateBodyText(DESCRIPTIONS[1]) // Update the text of the event
                    imageEventText.updateDialogOption(0, OPTIONS[5]) // 1. Change the first button to the [Leave] button
                    imageEventText.clearRemainingOptions() // 2. and remove all others
                    screenNum = 1 // Screen set the screen number to 1. Once we exit the switch (i) statement,
                    // we'll still continue the switch (screenNum) statement. It'll find screen 1 and do it's actions
                    // (in our case, that's the final screen, but you can chain as many as you want like that)
                    val relicToAdd =
                        RelicLibrary.starterList[AbstractDungeon.relicRng.random(RelicLibrary.starterList.size - 1)].makeCopy()
                    // Get a random starting relic
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                        (Settings.WIDTH / 2).toFloat(),
                        (Settings.HEIGHT / 2).toFloat(),
                        relicToAdd
                    )
                }
                1 -> {
                    CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false)
                    // Shake the screen
                    CardCrawlGame.sound.play("BLUNT_FAST") // Play a hit sound
                    AbstractDungeon.player.decreaseMaxHealth(healthdamage) // Lose max HP
                    if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.purgeableCards)
                            .size() > 0
                    ) {
                        // If you have cards you can remove - remove a card
                        AbstractDungeon.gridSelectScreen.open(
                            CardGroup.getGroupWithoutBottledCards(
                                AbstractDungeon.player.masterDeck.purgeableCards
                            ),
                            1, OPTIONS[6], false, false, false, true
                        )
                    }
                    imageEventText.updateBodyText(DESCRIPTIONS[2])
                    imageEventText.updateDialogOption(0, OPTIONS[5])
                    imageEventText.clearRemainingOptions()
                    screenNum = 1
                }
                2 -> {
                    val c = Apotheosis().makeCopy()
                    AbstractDungeon.effectList.add(
                        ShowCardAndObtainEffect(
                            c,
                            (Settings.WIDTH / 2).toFloat(),
                            (Settings.HEIGHT / 2).toFloat()
                        )
                    )
                    imageEventText.updateBodyText(DESCRIPTIONS[3])
                    imageEventText.updateDialogOption(0, OPTIONS[5])
                    imageEventText.clearRemainingOptions()
                    screenNum = 1
                }
                3 -> {
                    imageEventText.loadImage("nameOfTheWindModResources/images/events/IdentityCrisisEvent2.png") // Change the shown image
                    // Other than that, this option doesn't do anything special.
                    imageEventText.updateBodyText(DESCRIPTIONS[4])
                    imageEventText.updateDialogOption(0, OPTIONS[5])
                    imageEventText.clearRemainingOptions()
                    screenNum = 1
                }
            }
            1 -> when (i) {
                0 -> openMap() // You'll open the map and end the event.
            }
        }
    }

    override fun update() { // We need the update() when we use grid screens (such as, in this case, the screen for selecting a card to remove)
        super.update() // Do everything the original update()
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) { // Once the grid screen isn't empty (we selected a card for removal)
            val c = AbstractDungeon.gridSelectScreen.selectedCards[0] as AbstractCard // Get the card
            AbstractDungeon.topLevelEffects.add(
                PurgeCardEffect(
                    c,
                    (Settings.WIDTH / 2).toFloat(),
                    (Settings.HEIGHT / 2).toFloat()
                )
            ) // Create the card removal effect
            AbstractDungeon.player.masterDeck.removeCard(c) // Remove it from the deck
            AbstractDungeon.gridSelectScreen.selectedCards.clear() // Or you can .remove(c) instead of clear,
            // if you want to continue using the other selected cards for something
        }
    }

    companion object {
        @JvmField
        val ID = NameOfTheWindMod.makeID("IdentityCrisisEvent")
        private val eventStrings = CardCrawlGame.languagePack.getEventString(ID)
        private val NAME = eventStrings.NAME
        private val DESCRIPTIONS = eventStrings.DESCRIPTIONS
        private val OPTIONS = eventStrings.OPTIONS
        val IMG = NameOfTheWindMod.makeEventPath("IdentityCrisisEvent.png")
    }

    init {
        healthdamage =
            if (AbstractDungeon.ascensionLevel >= 15) { // If the player is ascension 15 or above, lose 5% max hp. Else, lose just 3%.
                (AbstractDungeon.player.maxHealth.toFloat() * HEALTH_LOSS_PERCENTAGE).toInt()
            } else {
                (AbstractDungeon.player.maxHealth.toFloat() * HEALTH_LOSS_PERCENTAGE_LOW_ASCENSION).toInt()
            }

        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0]) // Inspiration - Gain a Random Starting Relic
        imageEventText.setDialogOption(OPTIONS[1] + healthdamage + OPTIONS[2]) // Denial - lose healthDamage Max HP
        imageEventText.setDialogOption(OPTIONS[3], Apotheosis()) // Acceptance - Gain Apotheosis
        imageEventText.setDialogOption(OPTIONS[4]) // TOUCH THE MIRROR
    }
}