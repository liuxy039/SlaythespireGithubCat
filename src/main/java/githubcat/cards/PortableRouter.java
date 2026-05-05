package githubcat.cards;

import githubcat.util.CardStats;
import githubcat.powers.WifiSignal;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class PortableRouter extends BaseCard {
    public static final String ID = makeID("PortableRouter");

    public PortableRouter() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, 2));
        setMagic(2, 0);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WifiSignal(p, magicNumber), magicNumber));
    }

    @Override
    public void triggerOnExhaust() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null) {
            addToTop(new ApplyPowerAction(p, p, new WifiSignal(p, 1), 1));
        }
    }
}
