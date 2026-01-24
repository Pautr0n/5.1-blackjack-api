package cat.itacademy.blackjack.player.domain.port.out.query;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerQueryRepository {
    Flux<PlayerSummary> findAll();

    Mono<PlayerSummary> findById(String playerId);

    Flux<PlayerSummary> searchByName(String partialName);

    Flux<PlayerRankingEntry> getRanking();

}
