package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class TailCut extends BaseCard {
    public static final String ID = makeID("TailCut");

    public TailCut() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, 3));
        setMagic(1, 1);
        setCostUpgrade(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int halfHp = p.currentHealth / 2;
        if (halfHp > 0) {
            addToBot(new com.megacrit.cardcrawl.actions.common.LoseHPAction(p, p, p.currentHealth - halfHp));
        }
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, magicNumber), magicNumber));
    }
}
