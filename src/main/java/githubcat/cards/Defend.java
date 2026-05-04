package githubcat.cards;

import basemod.helpers.BaseModCardTags;
import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class Defend extends BaseCard {
    public static final String ID = makeID("Defend");

    public Defend() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF, 1));
        setBlock(5, 3);
        tags.add(BaseModCardTags.BASIC_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }
}
