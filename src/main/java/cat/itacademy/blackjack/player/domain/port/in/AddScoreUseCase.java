package cat.itacademy.blackjack.player.domain.port.in;

import cat.itacademy.blackjack.player.domain.model.Player;
import reactor.core.publisher.Mono;

public interface AddScoreUseCase {
    Mono<Player> addScore(String playerId, int points);
}
