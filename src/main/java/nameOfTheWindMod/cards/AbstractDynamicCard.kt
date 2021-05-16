package nameOfTheWindMod.cards

import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget
import nameOfTheWindMod.cards.AbstractDefaultCard
import com.megacrit.cardcrawl.core.CardCrawlGame

abstract class AbstractDynamicCard  // "How come DefaultCommonAttack extends CustomCard and not DynamicCard like all the rest?"
// Well every card, at the end of the day, extends CustomCard.
// Abstract Default Card extends CustomCard and builds up on it, adding a second magic number. Your card can extend it and
// bam - you can have a second magic number in that card (Learn Java inheritance if you want to know how that works).
// Abstract Dynamic Card builds up on Abstract Default Card even more and makes it so that you don't need to add
// the NAME and the DESCRIPTION into your card - it'll get it automatically. Of course, this functionality could have easily
// Been added to the default card rather than creating a new Dynamic one, but was done so to deliberately.
    (
    id: String?,
    img: String?,
    cost: Int,
    type: CardType?,
    color: CardColor?,
    rarity: CardRarity?,
    target: CardTarget?
) : AbstractDefaultCard(
    id,
    CardCrawlGame.languagePack.getCardStrings(id).NAME,
    img,
    cost,
    CardCrawlGame.languagePack.getCardStrings(id).DESCRIPTION,
    type,
    color,
    rarity,
    target
)