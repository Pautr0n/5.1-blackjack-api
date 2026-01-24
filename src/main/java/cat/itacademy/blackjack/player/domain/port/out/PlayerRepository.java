package cat.itacademy.blackjack.player.domain.port.out;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import reactor.core.publisher.Mono;

public interface PlayerRepository {
    Mono<Player> findById(PlayerId id);

    Mono<Player> save(Player player);

    Mono<Void> deleteById(PlayerId id);
}
