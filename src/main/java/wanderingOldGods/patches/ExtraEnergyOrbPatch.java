package wanderingOldGods.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import wanderingOldGods.LeftRightcastMod;
import wanderingOldGods.relics.LeftRightInator;

public class ExtraEnergyOrbPatch {
    private static TextureAtlas.AtlasRegion imaginOrb1 = LeftRightcastMod.UIAtlas.findRegion("imaginOrb");

    private static TextureAtlas.AtlasRegion vimOrb1 = LeftRightcastMod.UIAtlas.findRegion("vimOrb");

    private static Color myColorOne = new Color(1.0F, 0.3F, 0.3F, 1.0F);
    private static Color myColorTwo = new Color(0.4F, 1.0F, 0.4F, 1.0F);// 201

    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class SecondEnergyRenderPatch {
        public static SpireReturn Prefix(AbstractCard __instance, SpriteBatch sb) {
            if (CardCrawlGame.isInARun()) {
                if (__instance.cost > -2 && !__instance.isLocked && __instance.isSeen) {// 2706
                    if (AbstractDungeon.player.hasRelic(LeftRightInator.ID)) {
                        if ((__instance.type == AbstractCard.CardType.POWER && __instance.rarity == AbstractCard.CardRarity.UNCOMMON) || __instance.type == AbstractCard.CardType.SKILL) {
                            if (__instance.costForTurn != -2) {
                                renderHelper(sb, Color.WHITE.cpy(), imaginOrb1, __instance.current_x, __instance.current_y, __instance);
                            }
                        } else {
                            if (__instance.costForTurn != -2) {
                                renderHelper(sb, Color.WHITE.cpy(), vimOrb1, __instance.current_x, __instance.current_y, __instance);
                            }
                        }
                        Color costColor = Color.WHITE.cpy();// 2731
                        if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(__instance) && !__instance.hasEnoughEnergy()) {// 2732
                            costColor = myColorOne;
                        } else if (__instance.isCostModified || __instance.isCostModifiedForTurn || __instance.freeToPlay()) {// 2734
                            costColor = myColorTwo;
                        }

                        costColor.a = __instance.transparency;// 2737
                        String text = getCost(__instance);
                        BitmapFont font = getEnergyFont(__instance);
                        if ((__instance.type != AbstractCard.CardType.STATUS || __instance.cardID.equals("Slimed")) && (__instance.color != AbstractCard.CardColor.CURSE || __instance.cardID.equals("Pride"))) {// 2742
                            if ((__instance.type == AbstractCard.CardType.POWER && __instance.rarity == AbstractCard.CardRarity.UNCOMMON) || __instance.type == AbstractCard.CardType.SKILL) {
                                FontHelper.renderRotatedText(sb, font, text, __instance.current_x, __instance.current_y, -132.0F * __instance.drawScale * Settings.scale, 192.0F * __instance.drawScale * Settings.scale, __instance.angle, false, costColor);// 2744
                            } else {
                                FontHelper.renderRotatedText(sb, font, text, __instance.current_x, __instance.current_y, -132.0F * __instance.drawScale * Settings.scale, 192.0F * __instance.drawScale * Settings.scale, __instance.angle, false, costColor);// 2744
                            }
                        }
                        return SpireReturn.Return(null);
                    }
                    return SpireReturn.Continue();
                }
                return SpireReturn.Continue();
            }
            return SpireReturn.Continue();
        }

        private static void renderHelper(SpriteBatch sb, Color color, TextureAtlas.AtlasRegion img, float drawX, float drawY, AbstractCard q) {
            sb.setColor(color);// 1427
            sb.draw(img, drawX + img.offsetX - (float) img.originalWidth / 2.0F, drawY + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, q.drawScale * Settings.scale, q.drawScale * Settings.scale, q.angle);// 1428
        }// 1439

        private static BitmapFont getEnergyFont(AbstractCard q) {
            FontHelper.cardEnergyFont_L.getData().setScale(q.drawScale);// 2877
            return FontHelper.cardEnergyFont_L;// 2878
        }

        private static String getCost(AbstractCard q) {
            if (q.cost == -1) {// 2855
                return "X";// 2856
            } else {
                return q.freeToPlay() ? "0" : Integer.toString(q.costForTurn);// 2857 2858 2860
            }
        }
    }
}