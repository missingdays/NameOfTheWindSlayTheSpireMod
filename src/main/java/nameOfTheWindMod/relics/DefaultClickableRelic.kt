package nameOfTheWindMod.relics

import basemod.abstracts.CustomRelic
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic
import com.megacrit.cardcrawl.actions.animations.TalkAction
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction
import com.megacrit.cardcrawl.actions.utility.SFXAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.rooms.AbstractRoom
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.util.TextureLoader

class DefaultClickableRelic : CustomRelic(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK), ClickableRelic {
    private var usedThisTurn =
        false // You can also have a relic be only usable once per combat. Check out Hubris for more examples, including other StSlib things.
    private var isPlayerTurn =
        false // We should make sure the relic is only activateable during our turn, not the enemies'.

    override fun onRightClick() { // On right click
        if (!isObtained || usedThisTurn || !isPlayerTurn) {
            // If it has been used this turn, the player doesn't actually have the relic (i.e. it's on display in the shop room), or it's the enemy's turn
            return  // Don't do anything.
        }
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) { // Only if you're in combat
            usedThisTurn = true // Set relic as "Used this turn"
            flash() // Flash
            stopPulse() // And stop the pulsing animation (which is started in atPreBattle() below)
            AbstractDungeon.actionManager.addToBottom(
                TalkAction(
                    true,
                    DESCRIPTIONS[1],
                    4.0f,
                    2.0f
                )
            ) // Player speech bubble saying "YOU ARE MINE!" (See relic strings)
            AbstractDungeon.actionManager.addToBottom(SFXAction("MONSTER_COLLECTOR_DEBUFF")) // Sound Effect Action of The Collector Nails
            AbstractDungeon.actionManager.addToBottom(
                VFXAction( // Visual Effect Action of the nails applies on a random monster's position.
                    CollectorCurseEffect(
                        AbstractDungeon.getRandomMonster().hb.cX,
                        AbstractDungeon.getRandomMonster().hb.cY
                    ), 2.0f
                )
            )
            AbstractDungeon.actionManager.addToBottom(EvokeOrbAction(1)) // Evoke your rightmost orb
        }
        // See that talk action? It has DESCRIPTIONS[1] instead of just hard-coding "You are mine" inside.
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
    }

    override fun atPreBattle() {
        usedThisTurn = false // Make sure usedThisTurn is set to false at the start of each combat.
        beginLongPulse() // Pulse while the player can click on it.
    }

    override fun atTurnStart() {
        usedThisTurn =
            false // Resets the used this turn. You can remove this to use a relic only once per combat rather than per turn.
        isPlayerTurn = true // It's our turn!
        beginLongPulse() // Pulse while the player can click on it.
    }

    override fun onPlayerEndTurn() {
        isPlayerTurn = false // Not our turn now.
        stopPulse()
    }

    override fun onVictory() {
        stopPulse() // Don't keep pulsing past the victory screen/outside of combat.
    }

    // Description
    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    companion object {
        // You must implement things you want to use from StSlib
        /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */
        // ID, images, text.
        val ID = NameOfTheWindMod.makeID("DefaultClickableRelic")
        private val IMG = TextureLoader.getTexture(NameOfTheWindMod.makeRelicPath("default_clickable_relic.png"))
        private val OUTLINE =
            TextureLoader.getTexture(NameOfTheWindMod.makeRelicOutlinePath("default_clickable_relic.png"))
    }

    init {
        tips.clear()
        tips.add(PowerTip(name, description))
    }
}