package githubcat.cards;

import githubcat.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import githubcat.patches.StorageRenderPatch;

import java.util.ArrayList;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class PushToCloud extends BaseCard {
    public static final String ID = makeID("PushToCloud");
    private static final int BASE_COUNT = 2;
    private static final int UPGRADE_COUNT = 3;
    private boolean used = false;

    public PushToCloud() {
        super(ID, new CardStats(CARD_COLOR, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, 1));
        setMagic(BASE_COUNT, UPGRADE_COUNT - BASE_COUNT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = magicNumber;
        if (AbstractDungeon.player.hand.size() == 0) return;

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            c.stopGlowing();
        }

        used = false;
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.gridSelectScreen.open(
                AbstractDungeon.player.hand,
                count,
                true,
                "选择要上传到云端仓库的卡牌（最多 " + count + " 张）"
        );
    }

    @Override
    public void update() {
        super.update();
        if (used) return;
        if (StorageRenderPatch.storageUI == null) return;
        if (AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) return;

        for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
            StorageRenderPatch.storageUI.addCard(c);
        }
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.closeCurrentScreen();
        used = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
