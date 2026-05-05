package githubcat.cards;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import githubcat.targeting.CloudCardTargetEnum;
import githubcat.targeting.CloudStorageTargetHandler;

public class PullFromCloud extends CustomCard {
    public static final String ID = "githubcat:PullFromCloud";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public PullFromCloud() {
        super(ID, cardStrings.NAME, "githubcat/images/cards/skill/default.png",
                0, cardStrings.DESCRIPTION,
                CardType.SKILL, githubcat.character.MyCharacter.Meta.CARD_COLOR,
                CardRarity.UNCOMMON, CloudCardTargetEnum.CloudStorageCard);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(githubcat.powers.WifiSignal.POWER_ID)) return;

        AbstractCard targeted = CloudStorageTargetHandler.getTarget(this);
        if (targeted == null) return;

        AbstractCard toHand = targeted.makeSameInstanceOf();
        toHand.purgeOnUse = false;
        if (upgraded) {
            toHand.setCostForTurn(toHand.costForTurn - 1);
            if (toHand.costForTurn < 0) toHand.costForTurn = 0;
        }
        addToBot(new MakeTempCardInHandAction(toHand, 1));
        githubcat.storage.StorageUI.onCardAddedToHand(toHand);
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
