package nameOfTheWindMod.orbs

import basemod.abstracts.CustomOrb
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.utility.SFXAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.orbs.AbstractOrb
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect
import nameOfTheWindMod.NameOfTheWindMod

class DefaultOrb : CustomOrb(
    ORB_ID,
    orbString.NAME,
    PASSIVE_AMOUNT,
    EVOKE_AMOUNT,
    DESCRIPTIONS[1],
    DESCRIPTIONS[3],
    NameOfTheWindMod.makeOrbPath("default_orb.png")
) {
    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private var vfxTimer = 1.0f
    private val vfxIntervalMin = 0.1f
    private val vfxIntervalMax = 0.4f
    override fun updateDescription() { // Set the on-hover description of the orb
        applyFocus() // Apply Focus (Look at the next method)
        if (passiveAmount == 1) {
            description =
                DESCRIPTIONS[0] + passiveAmount + DESCRIPTIONS[1] + DESCRIPTIONS[3] + evokeAmount + DESCRIPTIONS[4]
        } else if (passiveAmount > 1) {
            description =
                DESCRIPTIONS[0] + passiveAmount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + evokeAmount + DESCRIPTIONS[4]
        }
    }

    override fun applyFocus() {
        passiveAmount = basePassiveAmount
        evokeAmount = baseEvokeAmount
    }

    override fun onEvoke() { // 1.On Orb Evoke
        AbstractDungeon.actionManager.addToBottom( // 2.Damage all enemies
            DamageAllEnemiesAction(
                AbstractDungeon.player,
                DamageInfo.createDamageMatrix(evokeAmount, true, true),
                DamageInfo.DamageType.THORNS,
                AbstractGameAction.AttackEffect.NONE
            )
        )
        // The damage matrix is how orb damage all enemies actions have to be assigned. For regular cards that do damage to everyone, check out cleave or whirlwind - they are a bit simpler.
        AbstractDungeon.actionManager.addToBottom(SFXAction("TINGSHA")) // 3.And play a Jingle Sound.
        // For a list of sound effects you can use, look under com.megacrit.cardcrawl.audio.SoundMaster - you can see the list of keys you can use there. As far as previewing what they sound like, open desktop-1.0.jar with something like 7-Zip and go to audio. Reference the file names provided. (Thanks fiiiiilth)
    }

    override fun onStartOfTurn() { // 1.At the start of your turn.
        AbstractDungeon.actionManager.addToBottom( // 2.This orb will have a flare effect
            VFXAction(OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), 0.1f)
        )
        AbstractDungeon.actionManager.addToBottom( // 3. And draw you cards.
            DrawCardAction(AbstractDungeon.player, passiveAmount)
        )
    }

    override fun updateAnimation() { // You can totally leave this as is.
        // If you want to create a whole new orb effect - take a look at conspire's Water Orb. It includes a custom sound, too!
        super.updateAnimation()
        angle += Gdx.graphics.deltaTime * 45.0f
        vfxTimer -= Gdx.graphics.deltaTime
        if (vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(
                DarkOrbPassiveEffect(
                    cX,
                    cY
                )
            ) // This is the purple-sparkles in the orb. You can change this to whatever fits your orb.
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax)
        }
    }

    // Render the orb.
    override fun render(sb: SpriteBatch) {
        sb.color = Color(1.0f, 1.0f, 1.0f, c.a / 2.0f)
        sb.draw(
            img,
            cX - 48.0f,
            cY - 48.0f + bobEffect.y,
            48.0f,
            48.0f,
            96.0f,
            96.0f,
            scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale,
            scale,
            angle,
            0,
            0,
            96,
            96,
            false,
            false
        )
        sb.color = Color(1.0f, 1.0f, 1.0f, c.a / 2.0f)
        sb.setBlendFunction(770, 1)
        sb.draw(
            img,
            cX - 48.0f,
            cY - 48.0f + bobEffect.y,
            48.0f,
            48.0f,
            96.0f,
            96.0f,
            scale,
            scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale,
            -angle,
            0,
            0,
            96,
            96,
            false,
            false
        )
        sb.setBlendFunction(770, 771)
        renderText(sb)
        hb.render(sb)
    }

    override fun triggerEvokeAnimation() { // The evoke animation of this orb is the dark-orb activation effect.
        AbstractDungeon.effectsQueue.add(DarkOrbActivateEffect(cX, cY))
    }

    override fun playChannelSFX() { // When you channel this orb, the ATTACK_FIRE effect plays ("Fwoom").
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1f)
    }

    override fun makeCopy(): AbstractOrb {
        return DefaultOrb()
    }

    companion object {
        // Standard ID/Description
        val ORB_ID = NameOfTheWindMod.makeID("DefaultOrb")
        private val orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID)
        val DESCRIPTIONS = orbString.DESCRIPTION
        private const val PASSIVE_AMOUNT = 3
        private const val EVOKE_AMOUNT = 1
        private const val ORB_WAVY_DIST = 0.04f
        private const val PI_4 = 12.566371f
    }

    init {
        // The passive/evoke description we pass in here, specifically, don't matter
        // You can ctrl+click on CustomOrb from the `extends CustomOrb` above.
        // You'll see below we override CustomOrb's updateDescription function with our own, and also, that's where the passiveDescription and evokeDescription
        // parameters are used. If your orb doesn't use any numbers/doesn't change e.g "Evoke: shuffle your draw pile."
        // then you don't need to override the update description method and can just pass in the parameters here.
        updateDescription()
        angle = MathUtils.random(360.0f) // More Animation-related Numbers
        channelAnimTimer = 0.5f
    }
}