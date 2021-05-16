package nameOfTheWindMod.cards

import com.megacrit.cardcrawl.actions.common.ExhaustAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.characters.Kvothe

class FinolSkill : AbstractDynamicCard(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET) {
    init {
        exhaust = true
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(MakeTempCardInDrawPileAction(SaikereAttack(), 1, true, true))
    }

    //Upgraded stats.
    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            initializeDescription()
        }
    }

    companion object {
        val ID = NameOfTheWindMod.makeID(FinolSkill::class.java.simpleName)
        val IMG = NameOfTheWindMod.makeCardPath("Skill.png")

        private val RARITY = CardRarity.SPECIAL
        private val TARGET = CardTarget.SELF
        private val TYPE = CardType.SKILL
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = 1
    }

}