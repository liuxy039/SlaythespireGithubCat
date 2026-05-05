package githubcat.cards;

import githubcat.util.CardStats;
import githubcat.powers.WifiSignal;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static githubcat.character.MyCharacter.Meta.CARD_COLOR;

public class PacketStorm extends BaseCard {
    public static final String ID = makeID("PacketStorm");

    public PacketStorm() {
        super(ID, new CardStats(CARD_COLOR, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, 1));
        setDamage(4, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int wifiLayers = 0;
        if (p.hasPower(WifiSignal.POWER_ID)) {
            wifiLayers = p.getPower(WifiSignal.POWER_ID).amount;
        }
        int hits = wifiLayers;
        for (int i = 0; i < hits; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));
        }
    }
}
