package githubcat.cards;

import githubcat.util.CardStats;
import githubcat.patches.StorageRenderPatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class CodeReview extends BaseCard {
    public static final String ID = makeID("CodeReview");

    public CodeReview() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, 0));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
        if (StorageRenderPatch.storageUI != null) {
            boolean found = false;
            for (AbstractCard c : StorageRenderPatch.storageUI.getCards()) {
                if (c.cardID.equals(this.cardID)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                addToBot(new GainEnergyAction(1));
            }
        }
    }
}
