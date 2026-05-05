package githubcat.storage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    private static final float CARD_GAP = AbstractCard.IMG_WIDTH * CARD_SCALE + 30f * Settings.scale;
    private static final float AREA_WIDTH = MAX_SIZE * CARD_GAP + 20f * Settings.scale;
    private static final float AREA_HEIGHT = AbstractCard.IMG_HEIGHT * CARD_SCALE + 60f * Settings.scale;
    private static final float AREA_LEFT = Settings.WIDTH * 0.35f - AREA_WIDTH / 2f + 10f * Settings.scale;
    private static final float AREA_TOP = Settings.HEIGHT * 0.72f;

    private ArrayList<AbstractCard> cards = new ArrayList<>();
    private AbstractCard hoveredCard = null;

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
        if (AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) return;

        float mx = InputHelper.mX;
        float my = InputHelper.mY;
        hoveredCard = null;

        for (int i = 0; i < cards.size(); i++) {
            AbstractCard c = cards.get(i);
            float cx = AREA_LEFT + i * CARD_GAP + AbstractCard.IMG_WIDTH * CARD_SCALE / 2f;
            float cy = AREA_TOP;

            c.target_x = cx;
            c.target_y = cy;
            c.targetDrawScale = CARD_SCALE;
            c.targetTransparency = 1f;

            float hbX = cx - AbstractCard.IMG_WIDTH * CARD_SCALE / 2f;
            float hbY = cy - AbstractCard.IMG_HEIGHT * CARD_SCALE / 2f;
            if (mx >= hbX && mx <= hbX + AbstractCard.IMG_WIDTH * CARD_SCALE
                    && my >= hbY && my <= hbY + AbstractCard.IMG_HEIGHT * CARD_SCALE) {
                hoveredCard = c;
                c.targetDrawScale = 0.75f;
                c.target_y = AREA_TOP + 80f * Settings.scale;
            }

            c.update();
        }
    }

    public void render(SpriteBatch sb) {
        if (!CardCrawlGame.isInARun() || AbstractDungeon.player == null) return;
        if (cards.isEmpty()) return;
        if (AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) return;

        sb.end();
        ShapeRenderer sr = new ShapeRenderer();
        sr.setProjectionMatrix(sb.getProjectionMatrix().cpy());
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(new Color(0.5f, 0.5f, 0.8f, 0.8f));
        sr.rect(AREA_LEFT - 10f * Settings.scale, AREA_TOP - AREA_HEIGHT / 2f, AREA_WIDTH, AREA_HEIGHT);
        sr.end();
        sr.dispose();
        sb.begin();

        FontHelper.renderFontLeft(sb, FontHelper.tipHeaderFont, "仓库",
                AREA_LEFT - 10f * Settings.scale,
                AREA_TOP + AREA_HEIGHT / 2f + 20f * Settings.scale,
                Color.WHITE);

        for (AbstractCard c : cards) {
            if (c != hoveredCard) c.render(sb);
        }
        if (hoveredCard != null) hoveredCard.render(sb);
    }
}
