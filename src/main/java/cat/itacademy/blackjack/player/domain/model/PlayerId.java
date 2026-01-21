package cat.itacademy.blackjack.player.domain.model;

import java.util.UUID;

public record PlayerId(String value) {

    public static PlayerId newId() {
        return new PlayerId(UUID.randomUUID().toString());
    }

}
