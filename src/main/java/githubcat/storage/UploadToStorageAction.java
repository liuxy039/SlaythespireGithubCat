package githubcat.storage;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import githubcat.patches.StorageRenderPatch;

import java.util.ArrayList;

public class UploadToStorageAction extends AbstractGameAction {
    private boolean waitingForSelection = true;

    public UploadToStorageAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (!waitingForSelection) {
            isDone = true;
            return;
        }

        if (AbstractDungeon.player.hand.size() == 0) {
            isDone = true;
            return;
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                StorageRenderPatch.storageUI.addCard(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.closeCurrentScreen();
            waitingForSelection = false;
            isDone = true;
            return;
        }

        if (!AbstractDungeon.isScreenUp) {
            AbstractDungeon.gridSelectScreen.open(
                    AbstractDungeon.player.hand,
                    3,
                    true,
                    "选择要上传到仓库的卡牌"
            );
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                c.stopGlowing();
            }
        } else {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.beginGlowing();
            }
        }
    }
}
