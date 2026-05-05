package githubcat.storage;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class CardStorage {
    private static final int MAX_SIZE = 5;
    private final CardGroup storage;

    public CardStorage() {
        storage = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    }

    public void addCard(AbstractCard card) {
        AbstractCard copy = card.makeStatEquivalentCopy();
        storage.addToBottom(copy);
        if (storage.size() > MAX_SIZE) {
            storage.removeCard(storage.getBottomCard());
        }
    }

    public void removeCard(AbstractCard card) {
        storage.removeCard(card);
    }

    public CardGroup getCards() {
        return storage;
    }

    public int size() {
        return storage.size();
    }

    public boolean isFull() {
        return storage.size() >= MAX_SIZE;
    }

    public void clear() {
        storage.clear();
    }
}
