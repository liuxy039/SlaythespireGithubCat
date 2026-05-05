package githubcat.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static githubcat.BasicMod.makeID;

public class GitHubDesktop extends BaseRelic {
    public static final String ID = makeID("GitHubDesktop");

    public GitHubDesktop() {
        super(ID, "githubdesktop", RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new githubcat.storage.UploadToStorageAction());
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
