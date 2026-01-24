package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.port.in.FindAllPlayersUseCase;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerQueryRepository;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerSummary;
import reactor.core.publisher.Flux;

public class FindAllPlayersService implements FindAllPlayersUseCase {

    private final PlayerQueryRepository queryRepository;

    public FindAllPlayersService(PlayerQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public Flux<PlayerSummary> findAll() {
        return queryRepository.findAll();
    }

}
