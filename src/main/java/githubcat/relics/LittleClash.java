package githubcat.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static githubcat.BasicMod.makeID;

public class LittleClash extends BaseRelic {
    public static final String ID = makeID("LittleClash");

    public LittleClash() {
        super(ID, "littleclash", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void onVictory() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new HealAction(p, p, 10));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
