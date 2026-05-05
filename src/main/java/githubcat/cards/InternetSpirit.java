package githubcat.cards;

import githubcat.util.CardStats;
import githubcat.powers.InternetSpiritPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class InternetSpirit extends BaseCard {
    public static final String ID = makeID("InternetSpirit");

    public InternetSpirit() {
        super(ID, new CardStats(CARD_COLOR, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF, 2));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new InternetSpiritPower(p, 1), 1));
    }
}
