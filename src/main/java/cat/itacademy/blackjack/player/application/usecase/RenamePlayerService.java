package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import cat.itacademy.blackjack.player.domain.model.exception.PlayerNotFoundException;
import cat.itacademy.blackjack.player.domain.port.in.PlayerResponse;
import cat.itacademy.blackjack.player.domain.port.in.RenamePlayerUseCase;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import reactor.core.publisher.Mono;

public class RenamePlayerService implements RenamePlayerUseCase {

    private final PlayerRepository playerRepository;

    public RenamePlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<Player> rename(String id, String newName) {
        PlayerId playerId = new PlayerId(id);
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found")))
                .flatMap(player -> playerRepository.save(player.rename(newName)));

    }

}
