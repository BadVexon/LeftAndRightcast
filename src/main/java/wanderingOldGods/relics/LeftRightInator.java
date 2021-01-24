package wanderingOldGods.relics;

import basemod.abstracts.CustomRelic;
import basemod.interfaces.AlternateCardCostModifier;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import wanderingOldGods.util.TextureLoader;

import java.util.ArrayList;

public class LeftRightInator extends CustomRelic implements AlternateCardCostModifier {
    public static String ID = "leftRight:LeftRightInator";
    private static final Texture IMG = TextureLoader.getTexture("leftRightResources/relics/Placeholder.png");
    private static final Texture OUTLINE = TextureLoader.getTexture("leftRightResources/relics/outline/Placeholder.png");

    public LeftRightInator() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    private int oldHandSize = 0;

    @Override
    public void onEquip() {
        oldHandSize = AbstractDungeon.player.masterHandSize;
        AbstractDungeon.player.masterHandSize = 0;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.masterHandSize = oldHandSize;
    }

    @Override
    public int getAlternateResource(AbstractCard abstractCard) {
        ArrayList<AbstractCard> myCardList = new ArrayList<>();
        for (AbstractCard q : AbstractDungeon.player.hand.group) {
            if (q != abstractCard)
                myCardList.add(q);
        }
        return myCardList.size();
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        int x = card.costForTurn;
        if (card.freeToPlay()) x = 0;
        if (getAlternateResource(card) >= x) return true;
        else {
            card.cantUseMessage = "I don't have enough cards!";
            return false;
        }
    }

    @Override
    public boolean prioritizeAlternateCost(AbstractCard card) {
        return true;
    }

    @Override
    public boolean canSplitCost(AbstractCard card) {
        return false;
    }

    @Override
    public int spendAlternateCost(AbstractCard card, int costToSpend) {
        ArrayList<AbstractCard> qCardList = new ArrayList<>();
        for (AbstractCard q : AbstractDungeon.player.hand.group) {
            if (q != card) qCardList.add(q);
        }
        if ((card.type == AbstractCard.CardType.POWER && card.rarity == AbstractCard.CardRarity.UNCOMMON) || card.type == AbstractCard.CardType.SKILL) {
            for (int i = 0; i < costToSpend; i++) {
                AbstractCard q = qCardList.get(0);
                AbstractDungeon.player.hand.moveToDiscardPile(q);
                GameActionManager.incrementDiscard(false);
                q.triggerOnManualDiscard();
                qCardList.remove(q);
            }
        } else {
            for (int i = 0; i < costToSpend; i++) {
                AbstractCard q = qCardList.get(qCardList.size() - 1);
                AbstractDungeon.player.hand.moveToDiscardPile(q);
                GameActionManager.incrementDiscard(false);
                q.triggerOnManualDiscard();
                qCardList.remove(q);
            }
        }
        return 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
