package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class GroundPose extends BaseCard {
    public static final String ID = makeID("GroundPose");

    public GroundPose() {
        super(ID, new CardStats(CARD_COLOR, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY, 2));
        setDamage(2, 0);
        setBlock(10, 0);
        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }
}
