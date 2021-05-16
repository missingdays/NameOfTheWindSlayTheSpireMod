package nameOfTheWindMod

import basemod.*
import basemod.eventUtil.AddEventParams
import basemod.helpers.RelicType
import basemod.interfaces.*
import com.badlogic.gdx.Gdx
import com.evacipated.cardcrawl.mod.stslib.Keyword
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.google.gson.Gson
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.TheCity
import com.megacrit.cardcrawl.helpers.CardHelper
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.localization.*
import com.megacrit.cardcrawl.unlock.UnlockTracker
import nameOfTheWindMod.cards.AbstractDefaultCard
import nameOfTheWindMod.characters.Kvothe
import nameOfTheWindMod.events.IdentityCrisisEvent
import nameOfTheWindMod.potions.PlaceholderPotion
import nameOfTheWindMod.relics.BottledPlaceholderRelic
import nameOfTheWindMod.relics.DefaultClickableRelic
import nameOfTheWindMod.relics.PlaceholderRelic
import nameOfTheWindMod.relics.PlaceholderRelic2
import nameOfTheWindMod.util.IDCheckDontTouchPls
import nameOfTheWindMod.util.TextureLoader.getTexture
import nameOfTheWindMod.variables.DefaultCustomVariable
import nameOfTheWindMod.variables.DefaultSecondMagicNumber
import org.apache.logging.log4j.LogManager
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*

/*
* With that out of the way:
* Welcome to this super over-commented Slay the Spire modding base.
* Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
* cards, relics), this is a good starting point.
* It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
* If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
* https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
* Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
*
* And pls. Read the comments.
*/
@SpireInitializer
class NameOfTheWindMod : EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
    EditCharactersSubscriber, PostInitializeSubscriber {
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    // =============== LOAD THE CHARACTER =================
    override fun receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + Kvothe.Enums.THE_DEFAULT.toString())
        BaseMod.addCharacter(
            Kvothe("Kvothe", Kvothe.Enums.THE_DEFAULT),
            THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, Kvothe.Enums.THE_DEFAULT
        )
        receiveEditPotions()
        logger.info("Added " + Kvothe.Enums.THE_DEFAULT.toString())
    }

    // =============== /LOAD THE CHARACTER/ =================
    // =============== POST-INITIALIZE =================
    override fun receivePostInitialize() {
        logger.info("Loading badge image and mod options")

        // Load the Mod Badge
        val badgeTexture = getTexture(BADGE_IMAGE)

        // Create the Mod Menu
        val settingsPanel = ModPanel()

        // Create the on/off button:
        val enableNormalsButton = ModLabeledToggleButton("This is the text which goes next to the checkbox.",
            350.0f,
            700.0f,
            Settings.CREAM_COLOR,
            FontHelper.charDescFont,  // Position (trial and error it), color, font
            enablePlaceholder,  // Boolean it uses
            settingsPanel,  // The mod panel in which this button will be in
            { label: ModLabel? -> }
        )  // thing??????? idk
        { button: ModToggleButton ->  // The actual button:
            enablePlaceholder = button.enabled // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                val config = SpireConfig("nameOfTheWindMod", "nameOfTheWindConfig", theDefaultDefaultSettings)
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder)
                config.save()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        settingsPanel.addUIElement(enableNormalsButton) // Add the button to the settings panel. Button is a go.
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel)


        // =============== EVENTS =================
        // https://github.com/daviscook477/BaseMod/wiki/Custom-Events

        // You can add the event like so:
        // BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        // Then, this event will be exclusive to the City (act 2), and will show up for all characters.
        // If you want an event that's present at any part of the game, simply don't include the dungeon ID

        // If you want to have more specific event spawning (e.g. character-specific or so)
        // deffo take a look at that basemod wiki link as well, as it explains things very in-depth
        // btw if you don't provide event type, normal is assumed by default

        // Create a new event builder
        // Since this is a builder these method calls (outside of create()) can be skipped/added as necessary
        val eventParams =
            AddEventParams.Builder(IdentityCrisisEvent.ID, IdentityCrisisEvent::class.java) // for this specific event
                .dungeonID(TheCity.ID) // The dungeon (act) this event will appear in
                .playerClass(Kvothe.Enums.THE_DEFAULT) // Character specific event
                .create()

        // Add the event
        BaseMod.addEvent(eventParams)

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options")
    }

    // =============== / POST-INITIALIZE/ =================
    // ================ ADD POTIONS ===================
    fun receiveEditPotions() {
        logger.info("Beginning to edit potions")

        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(
            PlaceholderPotion::class.java,
            PLACEHOLDER_POTION_LIQUID,
            PLACEHOLDER_POTION_HYBRID,
            PLACEHOLDER_POTION_SPOTS,
            PlaceholderPotion.POTION_ID,
            Kvothe.Enums.THE_DEFAULT
        )
        logger.info("Done editing potions")
    }

    // ================ /ADD POTIONS/ ===================
    // ================ ADD RELICS ===================
    override fun receiveEditRelics() {
        logger.info("Adding relics")

        // Take a look at https://github.com/daviscook477/BaseMod/wiki/AutoAdd
        // as well as
        // https://github.com/kiooeht/Bard/blob/e023c4089cc347c60331c78c6415f489d19b6eb9/src/main/java/com/evacipated/cardcrawl/mod/bard/BardMod.java#L319
        // for reference as to how to turn this into an "Auto-Add" rather than having to list every relic individually.
        // Of note is that the bard mod uses it's own custom relic class (not dissimilar to our AbstractDefaultCard class for cards) that adds the 'color' field,
        // in order to automatically differentiate which pool to add the relic too.

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(PlaceholderRelic(), Kvothe.Enums.COLOR_GRAY)
        BaseMod.addRelicToCustomPool(BottledPlaceholderRelic(), Kvothe.Enums.COLOR_GRAY)
        BaseMod.addRelicToCustomPool(DefaultClickableRelic(), Kvothe.Enums.COLOR_GRAY)

        // This adds a relic to the Shared pool. Every character can find this relic.
        BaseMod.addRelic(PlaceholderRelic2(), RelicType.SHARED)

        // Mark relics as seen - makes it visible in the compendium immediately
        // If you don't have this it won't be visible in the compendium until you see them in game
        // (the others are all starters so they're marked as seen in the character file)
        UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID)
        logger.info("Done adding relics!")
    }

    // ================ /ADD RELICS/ ===================
    // ================ ADD CARDS ===================
    override fun receiveEditCards() {
        logger.info("Adding variables")
        //Ignore this
        pathCheck()
        // Add the Custom Dynamic Variables
        logger.info("Add variables")
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(DefaultCustomVariable())
        BaseMod.addDynamicVariable(DefaultSecondMagicNumber())
        logger.info("Adding cards")
        AutoAdd("NameOfTheWindMod") // ${project.artifactId}
            .packageFilter(AbstractDefaultCard::class.java) // filters to any class in the same package as AbstractDefaultCard, nested packages included
            .setDefaultSeen(true)
            .cards()

        // .setDefaultSeen(true) unlocks the cards
        // This is so that they are all "seen" in the library,
        // for people who like to look at the card list before playing your mod
        logger.info("Done adding cards!")
    }

    // ================ /ADD CARDS/ ===================
    // ================ LOAD THE TEXT ===================
    override fun receiveEditStrings() {
        logger.info("You seeing this?")
        logger.info("Beginning to edit strings for mod with ID: " + modID)

        // CardStrings
        BaseMod.loadCustomStringsFile(
            CardStrings::class.java,
            modID + "Resources/localization/eng/DefaultMod-Card-Strings.json"
        )

        // PowerStrings
        BaseMod.loadCustomStringsFile(
            PowerStrings::class.java,
            modID + "Resources/localization/eng/DefaultMod-Power-Strings.json"
        )

        // RelicStrings
        BaseMod.loadCustomStringsFile(
            RelicStrings::class.java,
            modID + "Resources/localization/eng/DefaultMod-Relic-Strings.json"
        )

        // Event Strings
        BaseMod.loadCustomStringsFile(
            EventStrings::class.java,
            modID + "Resources/localization/eng/DefaultMod-Event-Strings.json"
        )

        // PotionStrings
        BaseMod.loadCustomStringsFile(
            PotionStrings::class.java,
            modID + "Resources/localization/eng/DefaultMod-Potion-Strings.json"
        )

        // CharacterStrings
        BaseMod.loadCustomStringsFile(
            CharacterStrings::class.java,
            modID + "Resources/localization/eng/DefaultMod-Character-Strings.json"
        )

        // OrbStrings
        BaseMod.loadCustomStringsFile(
            OrbStrings::class.java,
            modID + "Resources/localization/eng/DefaultMod-Orb-Strings.json"
        )
        logger.info("Done edittting strings")
    }

    // ================ /LOAD THE TEXT/ ===================
    // ================ LOAD THE KEYWORDS ===================
    override fun receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        val gson = Gson()
        val json = Gdx.files.internal(modID + "Resources/localization/eng/DefaultMod-Keyword-Strings.json").readString(
            StandardCharsets.UTF_8.toString()
        )
        val keywords = gson.fromJson(json, Array<Keyword>::class.java)
        if (keywords != null) {
            for (keyword in keywords) {
                BaseMod.addKeyword(modID!!.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION)
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }

    companion object {
        // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
        // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
        val logger = LogManager.getLogger(NameOfTheWindMod::class.java.name)

        // NO
        // DOUBLE NO
        // NU-UH
        var modID: String? = null
            private set

        // Mod-settings settings. This is if you want an on/off savable button
        var theDefaultDefaultSettings = Properties()
        const val ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder"
        var enablePlaceholder = true // The boolean we'll be setting on/off (true/false)

        //This is for the in-game mod settings panel.
        private const val MODNAME = "Name of the Wind Mod"
        private const val AUTHOR = "MissingDays" // And pretty soon - You!
        private const val DESCRIPTION = "Adds Kvothe as a playable character"

        // =============== INPUT TEXTURE LOCATION =================
        // Colors (RGB)
        // Character Color
        val DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f)

        // Potion Colors in RGB
        val PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f) // Orange-ish Red
        val PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f) // Near White
        val PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f) // Super Dark Red/Brown

        // Card backgrounds - The actual rectangular card.
        private const val ATTACK_DEFAULT_GRAY = "nameOfTheWindModResources/images/512/bg_attack_default_gray.png"
        private const val SKILL_DEFAULT_GRAY = "nameOfTheWindModResources/images/512/bg_skill_default_gray.png"
        private const val POWER_DEFAULT_GRAY = "nameOfTheWindModResources/images/512/bg_power_default_gray.png"
        private const val ENERGY_ORB_DEFAULT_GRAY = "nameOfTheWindModResources/images/512/card_default_gray_orb.png"
        private const val CARD_ENERGY_ORB = "nameOfTheWindModResources/images/512/card_small_orb.png"
        private const val ATTACK_DEFAULT_GRAY_PORTRAIT =
            "nameOfTheWindModResources/images/1024/bg_attack_default_gray.png"
        private const val SKILL_DEFAULT_GRAY_PORTRAIT =
            "nameOfTheWindModResources/images/1024/bg_skill_default_gray.png"
        private const val POWER_DEFAULT_GRAY_PORTRAIT =
            "nameOfTheWindModResources/images/1024/bg_power_default_gray.png"
        private const val ENERGY_ORB_DEFAULT_GRAY_PORTRAIT =
            "nameOfTheWindModResources/images/1024/card_default_gray_orb.png"

        // Character assets
        private const val THE_DEFAULT_BUTTON = "nameOfTheWindModResources/images/charSelect/DefaultCharacterButton.png"
        private const val THE_DEFAULT_PORTRAIT =
            "nameOfTheWindModResources/images/charSelect/DefaultCharacterPortraitBG.png"
        const val THE_DEFAULT_SHOULDER_1 = "nameOfTheWindModResources/images/char/defaultCharacter/shoulder.png"
        const val THE_DEFAULT_SHOULDER_2 = "nameOfTheWindModResources/images/char/defaultCharacter/shoulder2.png"
        const val THE_DEFAULT_CORPSE = "nameOfTheWindModResources/images/char/defaultCharacter/corpse.png"

        //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
        const val BADGE_IMAGE = "nameOfTheWindModResources/images/Badge.png"

        // Atlas and JSON files for the Animations
        const val THE_DEFAULT_SKELETON_ATLAS = "nameOfTheWindModResources/images/char/defaultCharacter/skeleton.atlas"
        const val THE_DEFAULT_SKELETON_JSON = "nameOfTheWindModResources/images/char/defaultCharacter/skeleton.json"

        // =============== MAKE IMAGE PATHS =================
        fun makeCardPath(resourcePath: String): String {
            return modID + "Resources/images/cards/" + resourcePath
        }

        fun makeRelicPath(resourcePath: String): String {
            return modID + "Resources/images/relics/" + resourcePath
        }

        fun makeRelicOutlinePath(resourcePath: String): String {
            return modID + "Resources/images/relics/outline/" + resourcePath
        }

        fun makeOrbPath(resourcePath: String): String {
            return modID + "Resources/images/orbs/" + resourcePath
        }

        fun makePowerPath(resourcePath: String): String {
            return modID + "Resources/images/powers/" + resourcePath
        }

        fun makeEventPath(resourcePath: String): String {
            return modID + "Resources/images/events/" + resourcePath
        }

        // ====== NO EDIT AREA ======
        // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
        // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
        fun setModID(ID: String) { // DON'T EDIT
            val coolG = Gson() // EY DON'T EDIT THIS
            //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
            val `in` =
                NameOfTheWindMod::class.java.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json") // DON'T EDIT THIS ETHER
            val EXCEPTION_STRINGS = coolG.fromJson(
                InputStreamReader(`in`, StandardCharsets.UTF_8),
                IDCheckDontTouchPls::class.java
            ) // OR THIS, DON'T EDIT IT
            logger.info("You are attempting to set your mod ID as: $ID") // NO WHY
            if (ID == EXCEPTION_STRINGS.DEFAULTID) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
                throw RuntimeException(EXCEPTION_STRINGS.EXCEPTION) // THIS ALSO DON'T EDIT
            } else if (ID == EXCEPTION_STRINGS.DEVID) { // NO
                modID = EXCEPTION_STRINGS.DEFAULTID // DON'T
            } else { // NO EDIT AREA
                modID = ID // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
            } // NO
            logger.info("Success! ID is " + modID) // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
        } // NO

        private fun pathCheck() { // ALSO NO
            val coolG = Gson() // NOPE DON'T EDIT THIS
            //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
            val `in` =
                NameOfTheWindMod::class.java.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json") // DON'T EDIT THISSSSS
            val EXCEPTION_STRINGS = coolG.fromJson(
                InputStreamReader(`in`, StandardCharsets.UTF_8),
                IDCheckDontTouchPls::class.java
            ) // NAH, NO EDIT
            val packageName = NameOfTheWindMod::class.java.getPackage().name // STILL NO EDIT ZONE
            val resourcePathExists = Gdx.files.internal(modID + "Resources") // PLEASE DON'T EDIT THINGS HERE, THANKS
            if (modID != EXCEPTION_STRINGS.DEVID) { // LEAVE THIS EDIT-LESS
                if (packageName != modID) { // NOT HERE ETHER
                    throw RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + modID) // THIS IS A NO-NO
                } // WHY WOULD U EDIT THIS
                if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                    throw RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + modID + "Resources") // NOT THIS
                } // NO
            } // NO
        } // NO

        // ====== YOU CAN EDIT AGAIN ======
        fun initialize() {
            logger.info("========================= Initializing Name of the Wind mod =========================")
            val nameOfTheWindMod = NameOfTheWindMod()
            logger.info("========================= /Done Initializing Name of the Wind mod/ =========================")
        }

        // ================ /LOAD THE KEYWORDS/ ===================    
        // this adds "ModName:" before the ID of any card/relic/power etc.
        // in order to avoid conflicts if any other mod uses the same ID.
        fun makeID(idText: String): String {
            return modID + ":" + idText
        }
    }

    // =============== /MAKE IMAGE PATHS/ =================
    // =============== /INPUT TEXTURE LOCATION/ =================
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    init {
        logger.info("Subscribe to BaseMod hooks")
        BaseMod.subscribe(this)

        /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */setModID("nameOfTheWindMod")
        logger.info("Done subscribing")
        logger.info("Creating the color " + Kvothe.Enums.COLOR_GRAY.toString())
        BaseMod.addColor(
            Kvothe.Enums.COLOR_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
            DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
            ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
            ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
            ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB
        )
        logger.info("Done creating the color")
        logger.info("Adding mod settings")
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(
            ENABLE_PLACEHOLDER_SETTINGS,
            "FALSE"
        ) // This is the default setting. It's actually set...
        try {
            val config =
                SpireConfig("nameOfTheWindMod", "nameOfTheWindConfig", theDefaultDefaultSettings) // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load() // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        logger.info("Done adding mod settings")
    }
}