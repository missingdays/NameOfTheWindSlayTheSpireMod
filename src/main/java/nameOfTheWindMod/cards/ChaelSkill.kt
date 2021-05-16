package nameOfTheWindMod.cards

import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.characters.Kvothe

class ChaelSkill : AbstractDynamicCard(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET) {
    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(MakeTempCardInDrawPileAction(FinolSkill(), 1, true, true))
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            initializeDescription()
        }
    }

    companion object {
        val ID = NameOfTheWindMod.makeID(ChaelSkill::class.java.simpleName)
        val IMG = NameOfTheWindMod.makeCardPath("Skill.png")

        private val RARITY = CardRarity.BASIC
        private val TARGET = CardTarget.SELF
        private val TYPE = CardType.SKILL
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = 1
    }

}