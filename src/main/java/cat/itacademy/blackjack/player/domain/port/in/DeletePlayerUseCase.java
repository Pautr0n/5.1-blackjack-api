package cat.itacademy.blackjack.player.domain.port.in;

import reactor.core.publisher.Mono;

public interface DeletePlayerUseCase {
    Mono<Void> deleteById(String playerId);
}
