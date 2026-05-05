package githubcat.storage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class StorageUI {
    private static final int MAX_SIZE = 5;
    private static final float CARD_SCALE = 0.6f;
    private static final float AREA_TOP = Settings.HEIGHT * 0.72f;
    private static final float BASE_GAP = AbstractCard.IMG_WIDTH * CARD_SCALE + 20f * Settings.scale;
    private static final float AREA_LEFT = Settings.WIDTH * 0.25f;
    private static final float HOVER_SCALE = 0.9f;
    private static final float HOVER_EXTRA_GAP = 50f * Settings.scale;

    private ArrayList<AbstractCard> cards = new ArrayList<>();
    private int hoveredIndex = -1;
    private boolean hasRelic = false;

    public void setHasRelic(boolean v) { hasRelic = v; }

    public void addCard(AbstractCard card) {
        AbstractCard copy = card.makeStatEquivalentCopy();
        cards.add(0, copy);
        if (cards.size() > MAX_SIZE) {
            AbstractCard removed = cards.remove(cards.size() - 1);
            removed.targetTransparency = 0f;
        }
    }

    public void clear() {
        cards.clear();
    }

    public void update() {
        if (!CardCrawlGame.isInARun() || AbstractDungeon.player == null) { clear(); return; }
        if (AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMPLETE) {
                clear();
            }
            return;
        }

        float mx = InputHelper.mX;
        float my = InputHelper.mY;

        int prevHover = hoveredIndex;
        hoveredIndex = -1;

        for (int i = 0; i < cards.size(); i++) {
            float cx = getCardX(i);
            float cy = AREA_TOP;

            AbstractCard c = cards.get(i);
            c.target_x = cx;
            c.target_y = cy;
            c.targetDrawScale = CARD_SCALE;
            c.targetTransparency = 1f;

            float halfW = AbstractCard.IMG_WIDTH * CARD_SCALE / 2f;
            float halfH = AbstractCard.IMG_HEIGHT * CARD_SCALE / 2f;
            if (mx >= cx - halfW && mx <= cx + halfW && my >= cy - halfH && my <= cy + halfH) {
                hoveredIndex = i;
                c.targetDrawScale = HOVER_SCALE;
            }

            c.update();
        }
    }

    private float getCardX(int index) {
        if (cards.isEmpty()) return AREA_LEFT;
        int hov = hoveredIndex;
        if (hov < 0) {
            return AREA_LEFT + index * BASE_GAP + AbstractCard.IMG_WIDTH * CARD_SCALE / 2f;
        }

        float[] xs = new float[cards.size()];
        float totalW = 0;
        for (int i = 0; i < cards.size(); i++) {
            float w = (i == hov) ? AbstractCard.IMG_WIDTH * HOVER_SCALE + HOVER_EXTRA_GAP
                    : AbstractCard.IMG_WIDTH * CARD_SCALE;
            xs[i] = w;
            totalW += w;
        }

        totalW -= (hov >= 0 ? HOVER_EXTRA_GAP : 0);
        float startX = AREA_LEFT + (BASE_GAP * (cards.size() - 1) + AbstractCard.IMG_WIDTH * CARD_SCALE - totalW) / 2f;

        float cx = startX + xs[index] / 2f;
        for (int i = 0; i < index; i++) {
            cx += (i == 0 ? startX + xs[0] / 2f : xs[i]);
        }
        if (index == 0) return cx;

        float prev = startX;
        for (int i = 0; i < index; i++) {
            prev += xs[i];
        }
        return prev + xs[index] / 2f;
    }

    public void render(SpriteBatch sb) {
        if (!hasRelic) return;
        if (!CardCrawlGame.isInARun() || AbstractDungeon.player == null) return;
        if (cards.isEmpty()) return;
        if (AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) return;

        FontHelper.renderFontLeft(sb, FontHelper.tipHeaderFont, "仓库",
                AREA_LEFT - 10f * Settings.scale,
                AREA_TOP + AbstractCard.IMG_HEIGHT * CARD_SCALE / 2f + 30f * Settings.scale,
                Color.WHITE);

        for (int i = 0; i < cards.size(); i++) {
            if (i != hoveredIndex) cards.get(i).render(sb);
        }
        if (hoveredIndex >= 0) cards.get(hoveredIndex).render(sb);
    }
}
