package cat.itacademy.blackjack.player.domain.port.in;

import reactor.core.publisher.Mono;

public interface CreatePlayerUseCase {
    Mono<PlayerResponse> create(String name);
}
