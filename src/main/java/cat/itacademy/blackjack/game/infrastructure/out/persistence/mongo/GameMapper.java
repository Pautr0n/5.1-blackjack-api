package cat.itacademy.blackjack.game.infrastructure.out.persistence.mongo;

import cat.itacademy.blackjack.game.domain.model.*;

public class GameMapper {

    public static GameDocument toDocument(Game game) {
        return new GameDocument(
                game.id().value(),
                game.playerId(),
                game.deck().getCards().stream().map(CardMapper::toDocument).toList(),
                game.playerHand().cards().stream().map(CardMapper::toDocument).toList(),
                game.dealerHand().cards().stream().map(CardMapper::toDocument).toList(),
                game.status().name()
        );
    }

    public static Game toDomain(GameDocument doc) {
        return Game.restore(
                new GameId(doc.getId()),
                doc.getPlayerId(),
                new Hand(doc.getPlayerHand().stream().map(CardMapper::toDomain).toList()),
                new Hand(doc.getDealerHand().stream().map(CardMapper::toDomain).toList()),
                new Deck(doc.getDeck().stream().map(CardMapper::toDomain).toList()),
                GameStatus.valueOf(doc.getStatus())
        );
    }

}
