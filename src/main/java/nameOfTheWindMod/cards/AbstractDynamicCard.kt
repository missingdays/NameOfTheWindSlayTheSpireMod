package nameOfTheWindMod.cards

import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget
import nameOfTheWindMod.cards.AbstractDefaultCard
import com.megacrit.cardcrawl.core.CardCrawlGame

abstract class AbstractDynamicCard
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