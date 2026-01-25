package cat.itacademy.blackjack.player.domain.port.in;

import cat.itacademy.blackjack.player.domain.model.Player;
import reactor.core.publisher.Mono;

public interface GetPlayerUseCase {
    Mono<Player> getById(String playerId);
}
