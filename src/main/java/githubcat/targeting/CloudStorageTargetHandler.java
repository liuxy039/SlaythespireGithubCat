package githubcat.targeting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.cards.targeting.TargetingHandler;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import githubcat.patches.StorageRenderPatch;

import java.util.ArrayList;

public class CloudStorageTargetHandler extends TargetingHandler<AbstractCard> {

    public AbstractCard hovered = null;

    public static AbstractCard getTarget(AbstractCard card) {
        Object target = CustomTargeting.getCardTarget(card);
        if (target instanceof AbstractCard) {
            return (AbstractCard) target;
        }
        return null;
    }

    @Override
    public boolean hasTarget() {
        return hovered != null;
    }

    @Override
    public void updateHovered() {
        hovered = null;
        ArrayList<AbstractCard> stored = StorageRenderPatch.storageUI.getCards();
        float mx = InputHelper.mX;
        float my = InputHelper.mY;
        for (int i = stored.size() - 1; i >= 0; i--) {
            AbstractCard c = stored.get(i);
            float hbX = c.current_x - AbstractCard.IMG_WIDTH * c.drawScale / 2f;
            float hbY = c.current_y - AbstractCard.IMG_HEIGHT * c.drawScale / 2f;
            if (mx >= hbX && mx <= hbX + AbstractCard.IMG_WIDTH * c.drawScale
                    && my >= hbY && my <= hbY + AbstractCard.IMG_HEIGHT * c.drawScale) {
                hovered = c;
                break;
            }
        }
    }

    @Override
    public AbstractCard getHovered() {
        return hovered;
    }

    @Override
    public void clearHovered() {
        hovered = null;
    }

    @Override
    public void renderReticle(SpriteBatch sb) {
    }
}
