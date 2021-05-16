package nameOfTheWindMod.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.util.TextureLoader

class PlaceholderRelic : CustomRelic(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL) {
    // Flash at the start of Battle.
    override fun atBattleStartPreDraw() {
        flash()
    }

    // Gain 1 energy on equip.
    override fun onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1
    }

    // Lose 1 energy on unequip.
    override fun onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1
    }

    // Description
    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    companion object {
        /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */
        // ID, images, text.
        val ID = NameOfTheWindMod.makeID("PlaceholderRelic")
        private val IMG = TextureLoader.getTexture(NameOfTheWindMod.makeRelicPath("placeholder_relic.png"))
        private val OUTLINE = TextureLoader.getTexture(NameOfTheWindMod.makeRelicOutlinePath("placeholder_relic.png"))
    }
}