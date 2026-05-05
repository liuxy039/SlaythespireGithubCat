package githubcat.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
    public void atTurnStart() {
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new githubcat.powers.WifiSignal(AbstractDungeon.player, 1), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
