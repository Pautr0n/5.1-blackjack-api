package cat.itacademy.blackjack.player.domain.model;

import java.util.UUID;

public record PlayerId(String value) {

    public PlayerId{
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException("PlayerId cannot be null or empty");
        }
    }

    public static PlayerId newId() {
        return new PlayerId(UUID.randomUUID().toString());
    }

}
