package cat.itacademy.blackjack.game.infrastructure.out.persistence.mongo;

import cat.itacademy.blackjack.game.domain.model.Card;
import cat.itacademy.blackjack.game.domain.model.Rank;
import cat.itacademy.blackjack.game.domain.model.Suit;

public class CardMapper {

    public static CardDocument toDocument(Card card) {
        return new CardDocument(
                card.rank().name(),
                card.suit().name()
        );
    }

    public static Card toDomain(CardDocument doc) {
        return new Card(
                Rank.valueOf(doc.getRank()),
                Suit.valueOf(doc.getSuit())
                );
    }

}
