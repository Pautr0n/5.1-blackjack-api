package cat.itacademy.blackjack.game.infrastructure.config;

import cat.itacademy.blackjack.game.application.usecase.CreateGameService;
import cat.itacademy.blackjack.game.application.usecase.DeleteGameService;
import cat.itacademy.blackjack.game.application.usecase.GetGameService;
import cat.itacademy.blackjack.game.application.usecase.PlayMoveService;
import cat.itacademy.blackjack.game.domain.port.in.CreateGameUseCase;
import cat.itacademy.blackjack.game.domain.port.in.DeleteGameUseCase;
import cat.itacademy.blackjack.game.domain.port.in.GetGameUseCase;
import cat.itacademy.blackjack.game.domain.port.in.PlayMoveUseCase;
import cat.itacademy.blackjack.game.domain.port.out.DeckFactory;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import cat.itacademy.blackjack.game.domain.port.out.PlayerLookupPort;
import cat.itacademy.blackjack.game.domain.port.out.UpdatePlayerScorePort;
import cat.itacademy.blackjack.game.domain.service.DealerService;
import cat.itacademy.blackjack.game.infrastructure.out.deck.StandardDeckFactory;
import cat.itacademy.blackjack.game.infrastructure.out.persistence.mongo.GameRepositoryAdapter;
import cat.itacademy.blackjack.game.infrastructure.out.persistence.mongo.SpringDataGameRepository;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import cat.itacademy.blackjack.player.infrastructure.out.UpdatePlayerScoreAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfig {


    @Bean
    public GameRepository gameRepository(SpringDataGameRepository springRepo) {
        return new GameRepositoryAdapter(springRepo);
    }


    @Bean
    public DeckFactory deckFactory() {
        return new StandardDeckFactory();
    }


    @Bean
    public CreateGameUseCase createGameUseCase(
            PlayerLookupPort playerLookupPort,
            GameRepository gameRepository,
            DeckFactory deckFactory
    ) {
        return new CreateGameService(playerLookupPort, gameRepository, deckFactory);
    }

    @Bean
    public DeleteGameUseCase deleteGameUseCase(GameRepository gameRepository) {
        return new DeleteGameService(gameRepository);
    }

    @Bean
    public GetGameUseCase getGameUseCase(GameRepository gameRepository) {
        return new GetGameService(gameRepository);
    }

    @Bean
    public DealerService dealerService() {
        return new DealerService();
    }

    @Bean
    public UpdatePlayerScorePort updatePlayerScorePort(PlayerRepository playerRepository) {
        return new UpdatePlayerScoreAdapter(playerRepository);
    }

    @Bean
    public PlayMoveUseCase playMoveUseCase(GameRepository gameRepository,
                                           DealerService dealerService,
                                           UpdatePlayerScorePort updatePlayerScorePort) {
        return new PlayMoveService(gameRepository, dealerService, updatePlayerScorePort);
    }

}
