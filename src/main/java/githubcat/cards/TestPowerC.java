package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class TestPowerC extends BaseCard {
    public static final String ID = makeID("TestPowerC");
    public TestPowerC() {
        super(ID, new CardStats(CARD_COLOR, CardType.POWER, CardRarity.RARE, CardTarget.SELF, 1));
        setMagic(2, 2);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RegenPower(p, magicNumber), magicNumber));
    }
}
