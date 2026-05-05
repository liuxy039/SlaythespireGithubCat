package githubcat.cards;

import githubcat.util.CardStats;
import githubcat.patches.StorageRenderPatch;
import githubcat.powers.WifiSignal;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class StorageReload extends BaseCard {
    public static final String ID = makeID("StorageReload");

    public StorageReload() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, 3));
        setCostUpgrade(2);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (StorageRenderPatch.storageUI == null) return;

        AbstractCard[] handCopy = p.hand.group.toArray(new AbstractCard[0]);
        for (AbstractCard c : handCopy) {
            if (c != this) {
                p.hand.moveToExhaustPile(c);
            }
        }

        if (p.hasPower(WifiSignal.POWER_ID)) {
            int wifiAmt = p.getPower(WifiSignal.POWER_ID).amount;
            if (wifiAmt > 0) {
                p.getPower(WifiSignal.POWER_ID).amount--;
                if (p.getPower(WifiSignal.POWER_ID).amount <= 0) {
                    p.powers.remove(p.getPower(WifiSignal.POWER_ID));
                }
            }
        }

        ArrayList<AbstractCard> cloudCards = new ArrayList<>(StorageRenderPatch.storageUI.getCards());
        StorageRenderPatch.storageUI.clear();

        for (AbstractCard c : cloudCards) {
            AbstractCard copy = c.makeSameInstanceOf();
            copy.purgeOnUse = false;
            addToBot(new MakeTempCardInHandAction(copy, 1));
            githubcat.storage.StorageUI.onCardAddedToHand(copy);
        }
    }
}
