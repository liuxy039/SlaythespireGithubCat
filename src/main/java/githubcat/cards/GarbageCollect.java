package githubcat.cards;

import githubcat.util.CardStats;
import githubcat.patches.StorageRenderPatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class GarbageCollect extends BaseCard {
    public static final String ID = makeID("GarbageCollect");

    public GarbageCollect() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, 2));
        setBlock(3, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (StorageRenderPatch.storageUI == null) return;
        int count = StorageRenderPatch.storageUI.getCards().size();
        if (count > 0) {
            StorageRenderPatch.storageUI.clear();
        }
        addToBot(new GainBlockAction(p, p, count * block));
    }
}
