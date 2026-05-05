package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class TestMixed extends BaseCard {
    public static final String ID = makeID("TestMixed");
    public TestMixed() {
        super(ID, new CardStats(CARD_COLOR, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY, 2));
        setDamage(15, 5);
        setBlock(10, 5);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new LoseHPAction(p, p, 1));
    }
}
