package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class TestG extends BaseCard {
    public static final String ID = makeID("TestG");
    public TestG() {
        super(ID, new CardStats(CARD_COLOR, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY, 2));
        setDamage(25, 5);
        setMagic(1, 0);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new LoseHPAction(p, p, 1));
    }
}
