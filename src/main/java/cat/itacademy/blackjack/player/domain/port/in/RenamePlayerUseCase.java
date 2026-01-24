package cat.itacademy.blackjack.player.domain.port.in;

import reactor.core.publisher.Mono;

public interface RenamePlayerUseCase {
    Mono<PlayerResponse> rename(String playerId, String newName);
}
