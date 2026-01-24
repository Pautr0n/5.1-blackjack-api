package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.port.in.SearchPlayerByNameUseCase;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerQueryRepository;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerSummary;
import reactor.core.publisher.Flux;

public class SearchPlayersByNameService implements SearchPlayerByNameUseCase {

    private final PlayerQueryRepository queryRepository;

    public SearchPlayersByNameService(PlayerQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public Flux<PlayerSummary> searchByName(String partialName) {
        return queryRepository.searchByName(partialName);
    }

}
