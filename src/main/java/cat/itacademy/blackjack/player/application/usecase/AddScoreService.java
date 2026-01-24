package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import cat.itacademy.blackjack.player.domain.model.exception.PlayerNotFoundException;
import cat.itacademy.blackjack.player.domain.port.in.AddScoreUseCase;
import cat.itacademy.blackjack.player.domain.port.in.PlayerResponse;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import reactor.core.publisher.Mono;

public class AddScoreService implements AddScoreUseCase {

    private final PlayerRepository playerRepository;

    public AddScoreService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<PlayerResponse> addScore(String id, int points) {
        PlayerId playerId = new PlayerId(id);

        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found")))
                .flatMap(player -> {
                    Player updated = player.addScore(points);
                    return playerRepository.save(updated);
                })
                .map(saved -> new PlayerResponse(
                        saved.id().value(),
                        saved.name(),
                        saved.score()
                ));
    }

}
