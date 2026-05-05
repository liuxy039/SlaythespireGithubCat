package githubcat.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import githubcat.relics.GitHubDesktop;
import githubcat.storage.StorageUI;

public class StorageRenderPatch {
    public static StorageUI storageUI = new StorageUI();

    @SpirePatch(clz = AbstractDungeon.class, method = "update")
    public static class StorageUpdatePatch {
        @SpirePostfixPatch
        public static void update(AbstractDungeon __instance) {
            if (!CardCrawlGame.isInARun() || AbstractDungeon.player == null) {
                storageUI.setHasRelic(false);
                storageUI.clear();
                return;
            }
            boolean hasIt = AbstractDungeon.player.hasRelic(GitHubDesktop.ID);
            storageUI.setHasRelic(hasIt);
            if (!hasIt) return;
            storageUI.update();
        }
    }

    @SpirePatch(clz = AbstractRoom.class, method = "render")
    public static class StorageRoomRenderPatch {
        @SpirePostfixPatch
        public static void render(AbstractRoom __instance, SpriteBatch sb) {
            if (!CardCrawlGame.isInARun() || AbstractDungeon.player == null) return;
            storageUI.render(sb);
        }
    }
}
