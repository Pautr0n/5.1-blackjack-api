package cat.itacademy.blackjack.player.domain.port.in;

import cat.itacademy.blackjack.player.domain.model.Player;
import reactor.core.publisher.Mono;

public interface RenamePlayerUseCase {
    Mono<Player> rename(String playerId, String newName);
}
