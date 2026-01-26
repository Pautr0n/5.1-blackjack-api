package cat.itacademy.blackjack.game.domain.port.out;

import reactor.core.publisher.Mono;

public interface UpdatePlayerScorePort {
    Mono<Void> addScore(String playerDomainId, int points);
}
