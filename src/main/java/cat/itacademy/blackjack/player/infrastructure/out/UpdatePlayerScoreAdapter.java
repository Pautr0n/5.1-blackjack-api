package cat.itacademy.blackjack.player.infrastructure.out;

import cat.itacademy.blackjack.game.domain.port.out.UpdatePlayerScorePort;
import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import cat.itacademy.blackjack.player.domain.model.exception.PlayerNotFoundException;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import reactor.core.publisher.Mono;

public class UpdatePlayerScoreAdapter implements UpdatePlayerScorePort {
    private final PlayerRepository playerRepository;

    public UpdatePlayerScoreAdapter(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<Void> addScore(String playerDomainId, int points) {
        PlayerId playerId = new PlayerId(playerDomainId);
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found: " + playerDomainId)))
                .flatMap(player -> {
                    Player updated = player.addScore(points);
                    return playerRepository.save(updated);
                })
                .then();
    }

    @Override
    public Mono<Void> addTotalGames(String playerDomainId, int games) {
        PlayerId playerId = new PlayerId(playerDomainId);
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found: " + playerDomainId)))
                .flatMap(player -> {
                    Player updated = player.addGames(games);
                    return playerRepository.save(updated);
                })
                .then();
    }
}
