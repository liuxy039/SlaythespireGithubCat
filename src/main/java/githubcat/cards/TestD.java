package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class TestD extends BaseCard {
    public static final String ID = makeID("TestD");
    public TestD() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, 0));
        setMagic(2, 1);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, 1));
        addToBot(new DrawCardAction(magicNumber));
    }
}
