package nameOfTheWindMod.cards

import basemod.AutoAdd
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.characters.Kvothe

// public class ${NAME} extends AbstractDynamicCard
@AutoAdd.Ignore // Remove this line when you make a template. Refer to https://github.com/daviscook477/BaseMod/wiki/AutoAdd if you want to know what it does.
class CardTemplate : AbstractDynamicCard(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET) {
    // Actions the card should do.
    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
            DamageAction(m, DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL)
        )
    }

    // Upgraded stats.
    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPGRADE_PLUS_DMG)
            upgradeBaseCost(UPGRADED_COST)
            initializeDescription()
        }
    }

    companion object {
        /*
     * "Hey, I wanna make a bunch of cards now." - You, probably.
     * ok cool my dude no problem here's the most convenient way to do it:
     *
     * Copy all of the code here (Ctrl+A > Ctrl+C)
     * Ctrl+Shift+A and search up "file and code template"
     * Press the + button at the top and name your template whatever it is for - "AttackCard" or "PowerCard" or something up to you.
     * Read up on the instructions at the bottom. Basically replace anywhere you'd put your cards name with ${NAME}
     * And then you can do custom ones like ${DAMAGE} and ${TARGET} if you want.
     * I'll leave some comments on things you might consider replacing with what.
     *
     * Of course, delete all the comments and add anything you want (For example, if you're making a skill card template you'll
     * likely want to replace that new DamageAction with a gain Block one, and add baseBlock instead, or maybe you want a
     * universal template where you delete everything unnecessary - up to you)
     *
     * You can create templates for anything you ever want to. Cards, relics, powers, orbs, etc. etc. etc.
     */
        // TEXT DECLARATION
        // public static final String ID = DefaultMod.makeID(${NAME}.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
        val ID = NameOfTheWindMod.makeID("DefaultCommonAttack") // DELETE THIS ONE.
        val IMG =
            NameOfTheWindMod.makeCardPath("Attack.png") // "public static final String IMG = makeCardPath("${NAME}.png");

        // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
        // /TEXT DECLARATION/
        // STAT DECLARATION
        private val RARITY = CardRarity.COMMON //  Up to you, I like auto-complete on these
        private val TARGET = CardTarget.ENEMY //   since they don't change much.
        private val TYPE = CardType.ATTACK //
        val COLOR = Kvothe.Enums.COLOR_GRAY
        private const val COST = 1 // COST = ${COST}
        private const val UPGRADED_COST = 0 // UPGRADED_COST = ${UPGRADED_COST}
        private const val DAMAGE = 7 // DAMAGE = ${DAMAGE}
        private const val UPGRADE_PLUS_DMG = 2 // UPGRADE_PLUS_DMG = ${UPGRADED_DAMAGE_INCREASE}
    }

    // /STAT DECLARATION/
    init { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        baseDamage = DAMAGE
    }
}