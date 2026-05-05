package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class TestPowerA extends BaseCard {
    public static final String ID = makeID("TestPowerA");
    public TestPowerA() {
        super(ID, new CardStats(CARD_COLOR, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF, 1));
        setMagic(1, 1);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
    }
}
