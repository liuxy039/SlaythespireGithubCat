package githubcat.cards;

import githubcat.util.CardStats;
import githubcat.patches.StorageRenderPatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class Hotfix extends BaseCard {
    public static final String ID = makeID("Hotfix");

    public Hotfix() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, 0));
        setMagic(1, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (StorageRenderPatch.storageUI == null) return;
        for (int i = 0; i < magicNumber; i++) {
            if (!AbstractDungeon.player.discardPile.isEmpty()) {
                AbstractCard c = AbstractDungeon.player.discardPile.getTopCard();
                StorageRenderPatch.storageUI.addCard(c);
                if (c.type == CardType.SKILL) {
                    addToBot(new DrawCardAction(1));
                }
            }
        }
    }
}
