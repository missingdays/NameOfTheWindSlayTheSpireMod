package nameOfTheWindMod.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.StrengthPower
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.util.TextureLoader

class PlaceholderRelic2 : CustomRelic(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT) {
    // Gain 1 Strength on on equip.
    override fun atBattleStart() {
        flash()
        AbstractDungeon.actionManager.addToTop(
            ApplyPowerAction(
                AbstractDungeon.player,
                AbstractDungeon.player,
                StrengthPower(AbstractDungeon.player, 1),
                1
            )
        )
        AbstractDungeon.actionManager.addToTop(RelicAboveCreatureAction(AbstractDungeon.player, this))
    }

    // Description
    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    companion object {
        /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each combat, gain 1 Strength (i.e. Vajra)
     */
        // ID, images, text.
        val ID = NameOfTheWindMod.makeID("PlaceholderRelic2")
        private val IMG = TextureLoader.getTexture(NameOfTheWindMod.makeRelicPath("placeholder_relic2.png"))
        private val OUTLINE = TextureLoader.getTexture(NameOfTheWindMod.makeRelicOutlinePath("placeholder_relic2.png"))
    }
}