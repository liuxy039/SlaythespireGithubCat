package githubcat.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import githubcat.character.MyCharacter;

@SpirePatch(clz = AbstractPlayer.class, method = "damage")
public class DamagePosePatch {

    @SpirePostfixPatch
    public static void onDamage(AbstractPlayer __instance, com.megacrit.cardcrawl.cards.DamageInfo info) {
        if (__instance != AbstractDungeon.player) return;
        if (!(__instance instanceof MyCharacter)) return;
        if (info.output <= 0) return;

        MyCharacter pc = (MyCharacter) __instance;
        int next = pc.getPose() + 1;
        if (next <= 2) {
            pc.setPose(next);
        }
    }
}
