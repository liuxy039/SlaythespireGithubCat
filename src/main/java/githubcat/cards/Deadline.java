package githubcat.cards;

import githubcat.util.CardStats;
import githubcat.patches.StorageRenderPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class Deadline extends BaseCard {
    public static final String ID = makeID("Deadline");

    public Deadline() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, 1));
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (StorageRenderPatch.storageUI == null) return;
        if (p.hand.size() == 0) return;

        int count = Math.min(magicNumber, p.hand.size());
        java.util.ArrayList<AbstractCard> toUpload = new java.util.ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = AbstractDungeon.cardRandomRng.random(p.hand.size() - 1);
            AbstractCard c = p.hand.group.get(index);
            if (!toUpload.contains(c)) {
                toUpload.add(c);
            } else {
                i--;
            }
        }
        for (AbstractCard c : toUpload) {
            StorageRenderPatch.storageUI.addCard(c);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            selfRetain = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
