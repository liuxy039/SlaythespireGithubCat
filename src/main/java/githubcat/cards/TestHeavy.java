package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class TestHeavy extends BaseCard {
    public static final String ID = makeID("TestHeavy");
    public TestHeavy() {
        super(ID, new CardStats(CARD_COLOR, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY, 3));
        setDamage(30, 10);
        setMagic(1, 1);
        setCostUpgrade(2);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, magicNumber), magicNumber));
    }
}
