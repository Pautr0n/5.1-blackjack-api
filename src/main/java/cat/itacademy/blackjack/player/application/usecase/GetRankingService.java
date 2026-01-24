package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.port.in.GetRankingUseCase;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerQueryRepository;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerRankingEntry;
import reactor.core.publisher.Flux;

public class GetRankingService implements GetRankingUseCase {

    private final PlayerQueryRepository queryRepository;

    public GetRankingService(PlayerQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public Flux<PlayerRankingEntry> getRanking() {
        return queryRepository.getRanking();
    }

}
