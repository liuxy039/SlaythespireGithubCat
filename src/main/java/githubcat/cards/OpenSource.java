package githubcat.cards;

import githubcat.powers.OpenSourcePower;
import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class OpenSource extends BaseCard {
    public static final String ID = makeID("OpenSource");

    public OpenSource() {
        super(ID, new CardStats(CARD_COLOR, CardType.POWER, CardRarity.RARE, CardTarget.SELF, 1));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new OpenSourcePower(p, 1), 1));
    }
}
