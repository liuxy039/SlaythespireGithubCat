package githubcat.cards;

import githubcat.util.CardStats;
import githubcat.patches.StorageRenderPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class DBDrop extends BaseCard {
    public static final String ID = makeID("DBDrop");

    public DBDrop() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.RARE, CardTarget.ALL_ENEMY, 3));
        setMagic(6, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (StorageRenderPatch.storageUI == null) return;
        int count = StorageRenderPatch.storageUI.getCards().size();
        if (count == 0) return;

        StorageRenderPatch.storageUI.clear();

        int[] damageArray = DamageInfo.createDamageMatrix(magicNumber * count, true);
        addToBot(new DamageAllEnemiesAction(p, damageArray, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
    }
}
