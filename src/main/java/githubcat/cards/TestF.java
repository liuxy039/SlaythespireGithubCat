package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class TestF extends BaseCard {
    public static final String ID = makeID("TestF");
    public TestF() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, 2));
        setMagic(1, 1);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, magicNumber), magicNumber));
    }
}
