package cat.itacademy.blackjack.player.domain.port.in;

import reactor.core.publisher.Mono;

public interface AddScoreUseCase {
    Mono<PlayerResponse> addScore(String playerId, int points);
}
