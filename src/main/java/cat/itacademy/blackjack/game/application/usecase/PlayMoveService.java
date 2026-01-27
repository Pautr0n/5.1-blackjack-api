package cat.itacademy.blackjack.game.application.usecase;

import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.domain.model.GameId;
import cat.itacademy.blackjack.game.domain.port.in.PlayCommand;
import cat.itacademy.blackjack.game.domain.port.in.PlayMoveUseCase;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import cat.itacademy.blackjack.game.domain.port.out.UpdatePlayerScorePort;
import cat.itacademy.blackjack.game.domain.service.DealerService;
import reactor.core.publisher.Mono;

public class PlayMoveService implements PlayMoveUseCase {

    private final GameRepository gameRepository;
    private final DealerService dealerService;
    private final UpdatePlayerScorePort updatePlayerScorePort;
    private static final int WIN_POINTS = 1;

    public PlayMoveService(GameRepository gameRepository,
                           DealerService dealerService,
                           UpdatePlayerScorePort updatePlayerScorePort) {
        this.gameRepository = gameRepository;
        this.dealerService = dealerService;
        this.updatePlayerScorePort = updatePlayerScorePort;
    }

    @Override
    public Mono<Game> play(String gameId, PlayCommand command) {

        return gameRepository.findById(new GameId(gameId))
                .flatMap(game -> {
                    Game updated;
                    switch (command.move()) {
                        case HIT -> updated = game.hit();
                        case STAND -> updated = game.stand(dealerService);
                        default -> updated = game;
                    }

                    return gameRepository.save(updated)
                            .flatMap(savedGame -> {
                                if (savedGame.isFinished()) {
                                    Mono<Void> updateGames = updatePlayerScorePort.addTotalGames(
                                            savedGame.playerId().value(), 1);
                                    if (savedGame.isPlayerWinner()) {
                                        Mono<Void> updateScore = updatePlayerScorePort
                                                .addScore(savedGame.playerId().value(), WIN_POINTS);
                                        return updateGames.then(updateScore).thenReturn(savedGame);
                                    }

                                    return updateGames.thenReturn(savedGame);
                                }
                                return Mono.just(savedGame);
                            });

                });
    }
}
