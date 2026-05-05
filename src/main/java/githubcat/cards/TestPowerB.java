package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class TestPowerB extends BaseCard {
    public static final String ID = makeID("TestPowerB");
    public TestPowerB() {
        super(ID, new CardStats(CARD_COLOR, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF, 1));
        setMagic(3, 2);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p, magicNumber), magicNumber));
    }
}
