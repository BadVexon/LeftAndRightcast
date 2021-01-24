package wanderingOldGods;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.localization.RelicStrings;
import wanderingOldGods.relics.LeftRightInator;
import wanderingOldGods.util.TextureLoader;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class LeftRightcastMod implements EditRelicsSubscriber, EditStringsSubscriber, PostInitializeSubscriber {

    public static Texture imaginOrb;
    public static Texture imaginOrb2;
    public static Texture imaginOrb3;
    public static Texture vimOrb;
    public static Texture vimOrb2;
    public static Texture vimOrb3;
    public static final TextureAtlas UIAtlas = new TextureAtlas();
    public LeftRightcastMod() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        LeftRightcastMod oldGodMod = new LeftRightcastMod();
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelic(new LeftRightInator(), RelicType.SHARED);
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(RelicStrings.class, "leftRightResources/loc/Relicstrings.json");
    }

    @Override
    public void receivePostInitialize() {
        imaginOrb = TextureLoader.getTexture("leftRightResources/images/imagin_orb_1.png");
        UIAtlas.addRegion("imaginOrb", imaginOrb, 0, 0, imaginOrb.getWidth(), imaginOrb.getHeight());

        vimOrb = TextureLoader.getTexture("leftRightResources/images/vim_orb_1.png");
        UIAtlas.addRegion("vimOrb", vimOrb, 0, 0, vimOrb.getWidth(), vimOrb.getHeight());
    }
}
