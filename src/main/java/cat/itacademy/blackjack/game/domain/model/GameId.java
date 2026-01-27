package cat.itacademy.blackjack.game.domain.model;

import java.util.UUID;

public record GameId(String value) {

    public GameId {

        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException("GameId cannot be null or empty");
        }
    }

    public static GameId newId() {
        return new GameId(UUID.randomUUID().toString());
    }

}
