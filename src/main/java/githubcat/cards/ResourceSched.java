package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class ResourceSched extends BaseCard {
    public static final String ID = makeID("ResourceSched");

    public ResourceSched() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, 4));
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(magicNumber));
    }

    @Override
    public void triggerWhenDrawn() {
        reduceCostForTurn();
    }

    public void reduceCostForTurn() {
        if (costForTurn > 0) {
            this.cost = costForTurn - 1;
            this.costForTurn = this.cost;
            this.isCostModified = true;
        }
    }

    @Override
    public void onMoveToDiscard() {
    }
}
