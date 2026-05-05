package githubcat.cards;

import githubcat.util.CardStats;
import githubcat.powers.WifiSignal;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class ScienceSurf extends BaseCard {
    public static final String ID = makeID("ScienceSurf");

    public ScienceSurf() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, 1));
        setMagic(1, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
        addToBot(new ApplyPowerAction(p, p, new WifiSignal(p, magicNumber), magicNumber));
    }
}
