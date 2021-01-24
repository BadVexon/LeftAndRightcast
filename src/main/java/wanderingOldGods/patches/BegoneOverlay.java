package wanderingOldGods.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import wanderingOldGods.relics.LeftRightInator;

@SpirePatch(
        clz = EnergyPanel.class,
        method = "render"
)
public class BegoneOverlay {
    public static SpireReturn Prefix(EnergyPanel __instance, SpriteBatch sb) {
        if (AbstractDungeon.player.hasRelic(LeftRightInator.ID)) {
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}