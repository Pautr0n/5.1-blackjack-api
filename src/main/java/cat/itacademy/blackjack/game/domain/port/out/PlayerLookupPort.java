package cat.itacademy.blackjack.game.domain.port.out;

import reactor.core.publisher.Mono;

public interface PlayerLookupPort {

    Mono<PlayerInfo> findById(String playerId);

}
