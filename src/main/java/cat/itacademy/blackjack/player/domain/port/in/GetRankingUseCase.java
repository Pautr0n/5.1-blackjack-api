package cat.itacademy.blackjack.player.domain.port.in;

import cat.itacademy.blackjack.player.domain.port.out.query.PlayerRankingEntry;
import reactor.core.publisher.Flux;

public interface GetRankingUseCase {
    Flux<PlayerRankingEntry> getRanking();
}
