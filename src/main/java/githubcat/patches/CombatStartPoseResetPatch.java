package githubcat.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import githubcat.character.MyCharacter;

@SpirePatch(clz = AbstractRoom.class, method = "update")
public class CombatStartPoseResetPatch {

    private static boolean wasInCombat = false;

    @SpirePostfixPatch
    public static void onUpdate(AbstractRoom __instance) {
        if (AbstractDungeon.player instanceof MyCharacter) {
            boolean inCombat = __instance.phase == AbstractRoom.RoomPhase.COMBAT;
            if (inCombat && !wasInCombat) {
                MyCharacter pc = (MyCharacter) AbstractDungeon.player;
                pc.setPose(0);
            }
            wasInCombat = inCombat;
        }
    }
}
