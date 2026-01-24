package cat.itacademy.blackjack.player.domain.port.in;

import reactor.core.publisher.Mono;

public interface GetPlayerUseCase {
    Mono<PlayerResponse> getById(String playerId);
}
