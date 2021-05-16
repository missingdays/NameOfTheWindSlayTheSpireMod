package nameOfTheWindMod.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.characters.Kvothe

class SaikereAttack : CustomCard(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET) {
    override fun use(p: AbstractPlayer?, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
            DamageAction(
                m,
                DamageInfo(
                    p,
                    damage,
                    damageTypeForTurn
                ),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
            )
        )
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPGRADE_PLUS_DMG)
            initializeDescription()
        }
    }

    companion object {
        val ID = NameOfTheWindMod.makeID(SaikereAttack::class.java.simpleName)
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val IMG = NameOfTheWindMod.makeCardPath("Attack.png")

        // Setting the image as as easy as can possibly be now. You just need to provide the image name
        // and make sure it's in the correct folder. That's all.
        // There's makeCardPath, makeRelicPath, power, orb, event, etc..
        // The list of all of them can be found in the main DefaultMod.java file in the
        // ==INPUT TEXTURE LOCATION== section under ==MAKE IMAGE PATHS==
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION

        // /TEXT DECLARATION/
        // STAT DECLARATION
        private val RARITY = CardRarity.SPECIAL
        private val TARGET = CardTarget.ENEMY
        private val TYPE = CardType.ATTACK
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = 1
        private const val DAMAGE = 15
        private const val UPGRADE_PLUS_DMG = 6
    }

    init {
        baseDamage = DAMAGE
        tags.add(CardTags.STRIKE)
    }
}