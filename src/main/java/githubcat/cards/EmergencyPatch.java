package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class EmergencyPatch extends BaseCard {
    public static final String ID = makeID("EmergencyPatch");

    public EmergencyPatch() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, 1));
        setBlock(6, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new DrawCardAction(1));
    }
}
