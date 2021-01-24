package wanderingOldGods.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import wanderingOldGods.relics.LeftRightInator;

@SpirePatch(
        clz = EnergyPanel.class,
        method = "setEnergy"
)
public class EnergyToCards3 {
    public static SpireReturn Prefix(int i) {
        if (AbstractDungeon.player.hasRelic(LeftRightInator.ID)) {
            AbstractDungeon.player.getRelic(LeftRightInator.ID).flash();
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(i * 2));
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}