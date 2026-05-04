package githubcat.character;

import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static githubcat.BasicMod.characterPath;

public class StaticTextureAnimation extends AbstractAnimation {
    private static final float SCALE = 3.0f;

    private Texture currentImg;
    private int currentIndex;
    private int animTargetIndex;
    private boolean inTransition;
    private float transitionTimer;
    private float currentAlpha;

    private final Texture[] standTextures;
    private final Texture[] transitionTextures;

    public StaticTextureAnimation() {
        standTextures = new Texture[]{
                safeLoad(characterPath("stand1.png")),
                safeLoad(characterPath("stand2.png")),
                safeLoad(characterPath("stand3.png"))
        };
        transitionTextures = new Texture[]{
                safeLoad(characterPath("1to2.png")),
                safeLoad(characterPath("2to3.png"))
        };

        currentIndex = 0;
        currentImg = standTextures[0];
        inTransition = false;
        currentAlpha = 1f;
    }

    private static Texture safeLoad(String path) {
        try {
            return new Texture(path);
        } catch (Exception e) {
            return null;
        }
    }

    public void setPose(int index) {
        if (index < 0 || index > 2 || index == currentIndex) return;
        if (inTransition) return;

        animTargetIndex = index;
        Texture t = getTransitionTex(currentIndex, index);
        if (t != null) {
            inTransition = true;
            transitionTimer = 0f;
            currentAlpha = 0f;
        } else {
            currentIndex = index;
            currentImg = standTextures[currentIndex];
        }
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    private Texture getTransitionTex(int from, int to) {
        if ((from == 0 && to == 1) || (from == 1 && to == 0)) return transitionTextures[0];
        if ((from == 1 && to == 2) || (from == 2 && to == 1)) return transitionTextures[1];
        return null;
    }

    @Override
    public void renderSprite(SpriteBatch sb, float x, float y) {
        if (currentImg == null && currentAlpha <= 0f) return;

        if (inTransition) {
            transitionTimer += Gdx.graphics.getDeltaTime();
            float duration = 1.0f;
            if (transitionTimer >= duration) {
                inTransition = false;
                currentIndex = animTargetIndex;
                currentImg = standTextures[currentIndex];
                currentAlpha = 1f;
            } else {
                currentAlpha = transitionTimer / duration;
            }
        } else {
            currentAlpha = 1f;
        }

        if (currentImg == null) return;

        float w = currentImg.getWidth() * SCALE;
        float h = currentImg.getHeight() * SCALE;

        sb.setColor(1f, 1f, 1f, currentAlpha);

        float offsetY = 120f * com.megacrit.cardcrawl.core.Settings.scale;

        if (inTransition && transitionTextures[0] != null) {
            int idx = (currentIndex == 0 && animTargetIndex == 1) || (currentIndex == 1 && animTargetIndex == 0) ? 0 : 1;
            Texture t = transitionTextures[idx];
            if (t != null) {
                float tw = t.getWidth() * SCALE;
                float th = t.getHeight() * SCALE;
                sb.draw(t,
                        x - tw / 2f, y + offsetY - th / 2f,
                        tw / 2f, th / 2f,
                        tw, th,
                        1f, 1f,
                        0f,
                        0, 0,
                        t.getWidth(), t.getHeight(),
                        false, false);
                sb.setColor(1f, 1f, 1f, 1f);
                return;
            }
        }

        sb.draw(currentImg,
                x - w / 2f, y + offsetY - h / 2f,
                w / 2f, h / 2f,
                w, h,
                1f, 1f,
                0f,
                0, 0,
                currentImg.getWidth(), currentImg.getHeight(),
                false, false);

        sb.setColor(1f, 1f, 1f, 1f);
    }

    @Override
    public Type type() {
        return Type.SPRITE;
    }
}
