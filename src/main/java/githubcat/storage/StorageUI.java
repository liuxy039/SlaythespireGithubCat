package githubcat.storage;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import githubcat.powers.WifiSignal;

import java.util.ArrayList;

public class StorageUI {
    private static final int MAX_SIZE = 5;
    private static final float CARD_SCALE = 0.6f;
    private static final float HOVER_SCALE = 0.9f;
    private static final float AREA_TOP = Settings.HEIGHT * 0.72f;
    private static final float GAP = 20f * Settings.scale;
    private static final float BASE_W = AbstractCard.IMG_WIDTH * CARD_SCALE;
    private static final float AREA_LEFT = Settings.WIDTH * 0.12f;

    private ArrayList<AbstractCard> cards = new ArrayList<>();
    private int hoveredIndex = -1;
    private boolean hasRelic = false;

    public void setHasRelic(boolean v) { hasRelic = v; }

    public void addCard(AbstractCard card) {
        AbstractCard copy = card.makeStatEquivalentCopy();
        if (AbstractDungeon.player != null) {
            if (copy.cardID.equals(githubcat.cards.PortableRouter.ID)) {
                com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new githubcat.powers.WifiSignal(AbstractDungeon.player, 1), 1));
            }
            if (copy.cardID.equals(githubcat.cards.HackAttack.ID)) {
                card.baseDamage += 5;
                copy.baseDamage += 5;
            }
        }
        cards.add(0, copy);
        if (cards.size() > MAX_SIZE) {
            AbstractCard removed = cards.remove(cards.size() - 1);
            onCardRemovedFromStorage(removed);
            removed.targetTransparency = 0f;
        }
    }

    public void clear() {
        if (com.megacrit.cardcrawl.core.CardCrawlGame.isInARun() && AbstractDungeon.player != null) {
            for (AbstractCard c : cards) {
                onCardRemovedFromStorage(c);
            }
        }
        cards.clear();
    }

    public static void onCardAddedToHand(AbstractCard card) {
        if (card.cardID.equals(githubcat.cards.ResourceSched.ID)) {
            if (card instanceof githubcat.cards.ResourceSched) {
                ((githubcat.cards.ResourceSched) card).reduceCostForTurn();
            }
        }
        if (card.cardID.equals(githubcat.cards.Ransomware.ID)) {
            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(
                new GainEnergyAction(1));
        }
    }

    private void onCardRemovedFromStorage(AbstractCard card) {
        if (AbstractDungeon.player == null) return;
        if (card.cardID.equals(githubcat.cards.Firewall.ID)) {
            AbstractPower plated = AbstractDungeon.player.getPower(PlatedArmorPower.POWER_ID);
            if (plated != null) {
                int doubled = 2 * plated.amount;
                AbstractDungeon.player.powers.remove(plated);
                com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new PlatedArmorPower(AbstractDungeon.player, doubled), doubled));
            }
        }
        if (card.cardID.equals(githubcat.cards.ScrumMaster.ID)) {
            com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect effect =
                new com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect(
                    card.makeSameInstanceOf(),
                    com.megacrit.cardcrawl.core.Settings.WIDTH / 2f,
                    com.megacrit.cardcrawl.core.Settings.HEIGHT / 2f);
            com.megacrit.cardcrawl.dungeons.AbstractDungeon.effectList.add(effect);
            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new DrawCardAction(2));
            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
        if (card.cardID.equals(githubcat.cards.IdleRecycle.ID)) {
            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(3));
        }
    }

    public ArrayList<AbstractCard> getCards() { return cards; }

    public void update() {
        if (!CardCrawlGame.isInARun() || AbstractDungeon.player == null) { clear(); return; }
        if (AbstractDungeon.getCurrRoom() == null) return;
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMPLETE) clear();
            return;
        }

        float mx = InputHelper.mX;
        float my = InputHelper.mY;
        hoveredIndex = -1;

        for (int i = 0; i < cards.size(); i++) {
            float cx = getCenterX(i);
            float halfW = BASE_W / 2f;
            float halfH = AbstractCard.IMG_HEIGHT * CARD_SCALE / 2f;
            if (mx >= cx - halfW && mx <= cx + halfW && my >= AREA_TOP - halfH && my <= AREA_TOP + halfH) {
                hoveredIndex = i;
            }
        }

        for (int i = 0; i < cards.size(); i++) {
            AbstractCard c = cards.get(i);
            c.target_x = getCenterX(i);
            c.target_y = AREA_TOP;
            c.targetDrawScale = (i == hoveredIndex) ? HOVER_SCALE : CARD_SCALE;
            c.targetTransparency = 1f;
            c.update();
        }
    }

    private float getCenterX(int index) {
        return AREA_LEFT + BASE_W / 2f + index * (BASE_W + GAP);
    }

    public void render(SpriteBatch sb) {
        if (!hasRelic) return;
        if (!CardCrawlGame.isInARun() || AbstractDungeon.player == null) return;
        if (cards.isEmpty()) return;
        if (AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) return;

        float labelX = AREA_LEFT;
        float labelY = AREA_TOP + AbstractCard.IMG_HEIGHT * CARD_SCALE / 2f + 30f * Settings.scale;

        FontHelper.renderFontLeft(sb, FontHelper.tipHeaderFont, "云端仓库",
                labelX, labelY, Color.WHITE);

        float textW = FontHelper.getWidth(FontHelper.tipHeaderFont, "云端仓库", 1f);
        float mx = InputHelper.mX;
        float my = InputHelper.mY;
        if (mx >= labelX && mx <= labelX + textW && my >= labelY - 20f * Settings.scale && my <= labelY + 10f * Settings.scale) {
            String tip = "云端仓库 - 复制手牌到云端，或从云端拉取到手上。 NL 仓库上限为 5，超出范围的卡牌会被删除。 NL 新牌从左侧加入，超出时最右侧的牌被删除。 NL NL 需要 WiFi 信号才能与云端仓库交互。";
            TipHelper.renderGenericTip(labelX + textW + 20f * Settings.scale, labelY, "云端仓库", tip);
        }

        for (int i = 0; i < cards.size(); i++) {
            if (i != hoveredIndex) cards.get(i).render(sb);
        }
        if (hoveredIndex >= 0) cards.get(hoveredIndex).render(sb);
    }
}
