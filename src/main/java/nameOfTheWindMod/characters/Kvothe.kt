package nameOfTheWindMod.characters

import basemod.abstracts.CustomPlayer
import basemod.animations.SpriterAnimation
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.MathUtils
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.EnergyManager
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.CardLibrary
import com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.ScreenShake
import com.megacrit.cardcrawl.screens.CharSelectInfo
import com.megacrit.cardcrawl.unlock.UnlockTracker
import nameOfTheWindMod.NameOfTheWindMod
import nameOfTheWindMod.cards.*
import nameOfTheWindMod.relics.DefaultClickableRelic
import nameOfTheWindMod.relics.PlaceholderRelic
import nameOfTheWindMod.relics.PlaceholderRelic2
import org.apache.logging.log4j.LogManager

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in DefaultMod-character-Strings.json in the resources
class Kvothe(name: String?, setClass: PlayerClass?) : CustomPlayer(
    name, setClass, orbTextures,
    "nameOfTheWindModResources/images/char/defaultCharacter/orb/vfx.png", null,
    SpriterAnimation(
        "nameOfTheWindModResources/images/char/defaultCharacter/Spriter/theDefaultAnimation.scml"
    )
) {
    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole
    object Enums {
        @JvmField
        @SpireEnum
        var KVOTHE: PlayerClass? = null

        @JvmField
        @SpireEnum(name = "DEFAULT_GRAY_COLOR") // These two HAVE to have the same absolutely identical name.
        var COLOR_GRAY: CardColor? = null

        @SpireEnum(name = "DEFAULT_GRAY_COLOR")
        var LIBRARY_COLOR: LibraryType? = null
    }

    // =============== /CHARACTER CLASS END/ =================
    // Starting description and loadout
    override fun getLoadout(): CharSelectInfo {
        return CharSelectInfo(
            NAMES[0], TEXT[0],
            STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, startingRelics,
            startingDeck, false
        )
    }

    // Starting Deck
    override fun getStartingDeck(): ArrayList<String> {
        val retVal = ArrayList<String>()
        logger.info("Begin loading starter Deck Strings")
        retVal.add(DefaultCommonAttack.ID)
        retVal.add(DefaultUncommonAttack.ID)
        retVal.add(DefaultRareAttack.ID)
        retVal.add(DefaultCommonSkill.ID)
        retVal.add(ChaelSkill.ID)
        retVal.add(DefaultUncommonSkill.ID)
        retVal.add(DefaultRareSkill.ID)
        retVal.add(DefaultCommonPower.ID)
        retVal.add(DefaultUncommonPower.ID)
        retVal.add(DefaultRarePower.ID)
        retVal.add(DefaultAttackWithVariable.ID)
        retVal.add(DefaultSecondMagicNumberSkill.ID)
        retVal.add(OrbSkill.ID)
        return retVal
    }

    // Starting Relics	
    override fun getStartingRelics(): ArrayList<String> {
        val retVal = ArrayList<String>()
        retVal.add(PlaceholderRelic.ID)
        retVal.add(PlaceholderRelic2.ID)
        retVal.add(DefaultClickableRelic.ID)

        // Mark relics as seen - makes it visible in the compendium immediately
        // If you don't have this it won't be visible in the compendium until you see them in game
        UnlockTracker.markRelicAsSeen(PlaceholderRelic.ID)
        UnlockTracker.markRelicAsSeen(PlaceholderRelic2.ID)
        UnlockTracker.markRelicAsSeen(DefaultClickableRelic.ID)
        return retVal
    }

    // character Select screen effect
    override fun doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f) // Sound Effect
        CardCrawlGame.screenShake.shake(
            ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
            false
        ) // Screen Effect
    }

    // character Select on-button-press sound effect
    override fun getCustomModeCharacterButtonSoundKey(): String {
        return "ATTACK_DAGGER_1"
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    override fun getAscensionMaxHPLoss(): Int {
        return 0
    }

    // Should return the card color enum to be associated with your character.
    override fun getCardColor(): CardColor {
        return Enums.COLOR_GRAY!!
    }

    // Should return a color object to be used to color the trail of moving cards
    override fun getCardTrailColor(): Color {
        return NameOfTheWindMod.DEFAULT_GRAY
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    override fun getEnergyNumFont(): BitmapFont {
        return FontHelper.energyNumFontRed
    }

    // Should return class name as it appears in run history screen.
    override fun getLocalizedCharacterName(): String {
        return NAMES[0]
    }

    //Which card should be obtainable from the Match and Keep event?
    override fun getStartCardForEvent(): AbstractCard {
        return DefaultCommonAttack()
    }

    // The class name as it appears next to your player name in-game
    override fun getTitle(playerClass: PlayerClass): String {
        return NAMES[1]
    }

    // Should return a new instance of your character, sending name as its name parameter.
    override fun newInstance(): AbstractPlayer {
        return Kvothe(name, chosenClass)
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    override fun getCardRenderColor(): Color {
        return NameOfTheWindMod.DEFAULT_GRAY
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    override fun getSlashAttackColor(): Color {
        return NameOfTheWindMod.DEFAULT_GRAY
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    override fun getSpireHeartSlashEffect(): Array<AttackEffect> {
        return arrayOf(
            AttackEffect.BLUNT_HEAVY,
            AttackEffect.BLUNT_HEAVY,
            AttackEffect.BLUNT_HEAVY
        )
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    override fun getSpireHeartText(): String {
        return TEXT[1]
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    override fun getVampireText(): String {
        return TEXT[2]
    }

    companion object {
        val logger = LogManager.getLogger(NameOfTheWindMod::class.java.name)

        // =============== CHARACTER ENUMERATORS  =================
        // =============== BASE STATS =================
        const val ENERGY_PER_TURN = 3
        const val STARTING_HP = 75
        const val MAX_HP = 75
        const val STARTING_GOLD = 99
        const val CARD_DRAW = 9
        const val ORB_SLOTS = 3

        // =============== /BASE STATS/ =================
        // =============== STRINGS =================
        private val ID = NameOfTheWindMod.makeID("DefaultCharacter")
        private val characterStrings = CardCrawlGame.languagePack.getCharacterString(ID)
        private val NAMES = characterStrings.NAMES
        private val TEXT = characterStrings.TEXT

        // =============== /STRINGS/ =================
        // =============== TEXTURES OF BIG ENERGY ORB ===============
        val orbTextures = arrayOf(
            "nameOfTheWindModResources/images/char/defaultCharacter/orb/layer1.png",
            "nameOfTheWindModResources/images/char/defaultCharacter/orb/layer2.png",
            "nameOfTheWindModResources/images/char/defaultCharacter/orb/layer3.png",
            "nameOfTheWindModResources/images/char/defaultCharacter/orb/layer4.png",
            "nameOfTheWindModResources/images/char/defaultCharacter/orb/layer5.png",
            "nameOfTheWindModResources/images/char/defaultCharacter/orb/layer6.png",
            "nameOfTheWindModResources/images/char/defaultCharacter/orb/layer1d.png",
            "nameOfTheWindModResources/images/char/defaultCharacter/orb/layer2d.png",
            "nameOfTheWindModResources/images/char/defaultCharacter/orb/layer3d.png",
            "nameOfTheWindModResources/images/char/defaultCharacter/orb/layer4d.png",
            "nameOfTheWindModResources/images/char/defaultCharacter/orb/layer5d.png"
        )
    }

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============
    // =============== CHARACTER CLASS START =================
    init {


        // =============== TEXTURES, ENERGY, LOADOUT =================  
        initializeClass(
            null,  // required call to load textures and setup energy/loadout.
            // I left these in DefaultMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
            NameOfTheWindMod.THE_DEFAULT_SHOULDER_2,  // campfire pose
            NameOfTheWindMod.THE_DEFAULT_SHOULDER_1,  // another campfire pose
            NameOfTheWindMod.THE_DEFAULT_CORPSE,  // dead corpse
            loadout, 20.0f, -10.0f, 220.0f, 290.0f, EnergyManager(ENERGY_PER_TURN)
        ) // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================  
        loadAnimation(
            NameOfTheWindMod.THE_DEFAULT_SKELETON_ATLAS,
            NameOfTheWindMod.THE_DEFAULT_SKELETON_JSON,
            1.0f
        )
        val e = state.setAnimation(0, "animation", true)
        e.time = e.endTime * MathUtils.random()

        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================
        dialogX = drawX + 0.0f * Settings.scale // set location for text bubbles
        dialogY = drawY + 220.0f * Settings.scale // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================
    }
}