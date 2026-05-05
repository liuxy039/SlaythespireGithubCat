package githubcat.cards;

import githubcat.util.CardStats;
import githubcat.patches.StorageRenderPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class IndianDev extends BaseCard {
    public static final String ID = makeID("IndianDev");
    private boolean used = false;

    public IndianDev() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, 1));
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (StorageRenderPatch.storageUI == null) return;

        ArrayList<AbstractCard> allCards = com.megacrit.cardcrawl.helpers.CardLibrary.getAllCards();
        if (allCards.isEmpty()) return;

        ArrayList<AbstractCard> choices = new ArrayList<>();
        for (int i = 0; i < 50 && choices.size() < 3; i++) {
            AbstractCard c = allCards.get(AbstractDungeon.cardRandomRng.random(allCards.size() - 1));
            AbstractCard copy = c.makeStatEquivalentCopy();
            if (!choicesContainsSameCard(choices, copy)) {
                choices.add(copy);
            }
        }

        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : choices) {
            group.addToTop(c);
        }

        used = false;
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.gridSelectScreen.open(group, 1, false, "选择一张牌上传到云端仓库");
    }

    private boolean choicesContainsSameCard(ArrayList<AbstractCard> list, AbstractCard card) {
        for (AbstractCard c : list) {
            if (c.cardID.equals(card.cardID)) return true;
        }
        return false;
    }

    @Override
    public void update() {
        super.update();
        if (used) return;
        if (StorageRenderPatch.storageUI == null) return;
        if (AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) return;

        AbstractCard chosen = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
        StorageRenderPatch.storageUI.addCard(chosen);
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.closeCurrentScreen();
        used = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.cost = 0;
            this.costForTurn = 0;
            upgradedCost = true;
        }
    }
}
