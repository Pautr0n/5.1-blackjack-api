package cat.itacademy.blackjack.game.domain.port.out;

import cat.itacademy.blackjack.player.domain.model.PlayerId;

public record PlayerInfo(PlayerId playerId,
                         String name,
                         int score
) {
}
