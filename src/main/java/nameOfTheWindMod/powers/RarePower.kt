package nameOfTheWindMod.powers

import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.powers.AbstractPower
import basemod.interfaces.CloneablePowerInterface
import com.megacrit.cardcrawl.cards.AbstractCard
import nameOfTheWindMod.cards.DefaultRareAttack
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction
import nameOfTheWindMod.powers.RarePower
import nameOfTheWindMod.NameOfTheWindMod
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import nameOfTheWindMod.util.TextureLoader

class RarePower(owner: AbstractCreature?, source: AbstractCreature, amount: Int) : AbstractPower(),
    CloneablePowerInterface {
    var source: AbstractCreature
    override fun atStartOfTurn() { // At the start of your turn
        val playCard: AbstractCard =
            DefaultRareAttack() // Declare Card - the DefaultRareAttack card. We will name it 'playCard'.
        val targetMonster =
            AbstractDungeon.getRandomMonster() // Declare Target - Random Monster. We will name the monster 'targetMonster'.
        playCard.freeToPlayOnce = true //Self Explanatory
        if (playCard.type != AbstractCard.CardType.POWER) {
            playCard.purgeOnUse = true
        }

        // Remove completely on use (Not Exhaust). A note - you don't need the '{}' in this if statement,
        // as it's just 1 line directly under. You can remove them, if you want. In fact, you can even put it all on 1 line:
        //  if (playCard.type != AbstractCard.CardType.POWER) playCard.purgeOnUse = true; - works identically
        AbstractDungeon.actionManager.addToBottom(
            NewQueueCardAction(
                playCard,
                targetMonster
            )
        ) // Play the card on the target.
    }

    override fun updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2]
        }
    }

    override fun makeCopy(): AbstractPower {
        return RarePower(owner, source, amount)
    }

    companion object {
        val POWER_ID = NameOfTheWindMod.makeID("RarePower")
        private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS

        // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
        private val tex84 = TextureLoader.getTexture("nameOfTheWindModResources/images/powers/placeholder_power84.png")
        private val tex32 = TextureLoader.getTexture("nameOfTheWindModResources/images/powers/placeholder_power32.png")
    }

    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = amount
        this.source = source
        type = PowerType.DEBUFF
        isTurnBased = false

        // We load those textures here.
        region128 = AtlasRegion(tex84, 0, 0, 84, 84)
        region48 = AtlasRegion(tex32, 0, 0, 32, 32)
        updateDescription()
    }
}