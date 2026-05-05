package githubcat.storage;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import githubcat.patches.StorageRenderPatch;

public class UploadToStorageAction extends AbstractGameAction {
    private boolean waitingForSelection = true;
    private boolean opened = false;

    public UploadToStorageAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (!waitingForSelection) {
            isDone = true;
            return;
        }

        if (AbstractDungeon.player == null || AbstractDungeon.player.hand.size() == 0) {
            isDone = true;
            return;
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                StorageRenderPatch.storageUI.addCard(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.closeCurrentScreen();
            }
            waitingForSelection = false;
            isDone = true;
            return;
        }

        if (!opened) {
            AbstractDungeon.gridSelectScreen.open(
                    AbstractDungeon.player.hand,
                    3,
                    true,
                    "选择要上传到仓库的卡牌"
            );
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            opened = true;
        }
    }
}
