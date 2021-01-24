package wanderingOldGods.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.neow.NeowReward.NeowRewardDef;
import wanderingOldGods.relics.LeftRightInator;

import java.util.ArrayList;


public class ReplaceNeowPatch {
    @SpirePatch(
            clz = NeowReward.class,
            method = "getRewardOptions"
    )
    public static class AddToNeow {
        public static ArrayList<NeowRewardDef> Postfix(ArrayList<NeowRewardDef> __result, NeowReward __instance, final int category) {
            if (category == 3) {
                __result.clear();
                __result.add(new NeowRewardDef(LEFT_RIGHTCAST, "[ #gObtain #yRavage #yPawn ]"));
            }
            return __result;
        }
    }

    @SpirePatch(
            clz = NeowReward.class,
            method = "activate"
    )
    public static class ActivatePatch {
        public static void Prefix(NeowReward __instance) {
            if (__instance.type == LEFT_RIGHTCAST) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 3F, Settings.HEIGHT / 2F, new LeftRightInator());
            }
        }
    }

    @SpireEnum
    public static NeowReward.NeowRewardType LEFT_RIGHTCAST;
}