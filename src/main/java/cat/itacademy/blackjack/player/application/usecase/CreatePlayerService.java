package cat.itacademy.blackjack.player.application.usecase;


import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.port.in.CreatePlayerUseCase;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import reactor.core.publisher.Mono;

public class CreatePlayerService implements CreatePlayerUseCase {

    private final PlayerRepository playerRepository;

    public CreatePlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<Player> create(String playerName) {

        Player player = Player.create(playerName);
        return playerRepository.save(player);

    }
}
