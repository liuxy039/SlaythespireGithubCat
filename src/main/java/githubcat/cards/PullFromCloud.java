package githubcat.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import githubcat.patches.StorageRenderPatch;

import java.util.ArrayList;

public class PullFromCloud extends CustomCard {
    public static final String ID = "githubcat:PullFromCloud";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public PullFromCloud() {
        super(ID, cardStrings.NAME, "githubcat/images/cards/skill/default.png",
                0, cardStrings.DESCRIPTION,
                CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS,
                CardRarity.UNCOMMON, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stored = StorageRenderPatch.storageUI.getCards();
        if (stored.isEmpty()) return;

        CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : stored) {
            temp.addToTop(c.makeStatEquivalentCopy());
        }

        AbstractDungeon.gridSelectScreen.open(
                temp, 1, true, "选择要从云端仓库拉取的卡牌"
        );

        addToBot(new AbstractGameAction() {
            private boolean started = false;
            @Override
            public void update() {
                if (!started) {
                    started = true;
                    return;
                }
                if (AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    isDone = true;
                    return;
                }
                AbstractCard selected = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                AbstractCard played = selected.makeSameInstanceOf();
                played.purgeOnUse = false;
                if (PullFromCloud.this.upgraded) {
                    played.setCostForTurn(played.costForTurn - 1);
                    if (played.costForTurn < 0) played.costForTurn = 0;
                }
                AbstractDungeon.player.hand.addToHand(played);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.closeCurrentScreen();
                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new PullFromCloud();
    }
}
