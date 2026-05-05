package githubcat.cards;

import githubcat.util.CardStats;
import githubcat.patches.StorageRenderPatch;
import githubcat.powers.WifiSignal;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class WorkFromHome extends BaseCard {
    public static final String ID = makeID("WorkFromHome");
    private boolean used = false;

    public WorkFromHome() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, 0));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (StorageRenderPatch.storageUI == null) return;
        ArrayList<AbstractCard> cloudCards = StorageRenderPatch.storageUI.getCards();
        if (cloudCards.isEmpty()) return;

        AbstractPower wifi = p.getPower(WifiSignal.POWER_ID);
        if (wifi == null || wifi.amount <= 0) return;

        wifi.amount--;
        if (wifi.amount <= 0) {
            p.powers.remove(wifi);
        }

        used = false;
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : cloudCards) {
            group.addToTop(c.makeStatEquivalentCopy());
        }
        AbstractDungeon.gridSelectScreen.open(group, 1, true, "选择从仓库拿取一张牌");
    }

    @Override
    public void update() {
        super.update();
        if (used) return;
        if (StorageRenderPatch.storageUI == null) return;
        if (AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) return;

        for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
            AbstractCard copy = c.makeSameInstanceOf();
            copy.purgeOnUse = false;
            addToBot(new MakeTempCardInHandAction(copy, 1));
            githubcat.storage.StorageUI.onCardAddedToHand(copy);
            StorageRenderPatch.storageUI.getCards().remove(c);
            break;
        }
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.closeCurrentScreen();
        used = true;
    }
}
