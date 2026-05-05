package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class TestSelfHarm extends BaseCard {
    public static final String ID = makeID("TestSelfHarm");
    public TestSelfHarm() {
        super(ID, new CardStats(CARD_COLOR, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, 1));
        setDamage(20, 0);
        setMagic(2, 4);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new LoseHPAction(p, p, damage / magicNumber));
    }
}
