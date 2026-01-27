package cat.itacademy.blackjack.game.application.usecase;

import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.domain.model.GameId;
import cat.itacademy.blackjack.game.domain.port.in.PlayCommand;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import cat.itacademy.blackjack.game.domain.port.out.UpdatePlayerScorePort;
import cat.itacademy.blackjack.game.domain.service.DealerService;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PlayMoveServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private DealerService dealerService;

    @Mock
    private UpdatePlayerScorePort updatePlayerScorePort;

    @InjectMocks
    private PlayMoveService playMoveService;

    private PlayerId playerId;

    void setup() {
        playerId = new PlayerId("player-123");
        lenient().when(gameRepository.save(any(Game.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        lenient().when(updatePlayerScorePort.addScore(anyString(), anyInt()))
                .thenReturn(Mono.empty());
    }

    @Test
    void play_hit_updates_game_with_hit() {
        String gameId = "game-123";

        Game original = mock(Game.class);
        Game updated = mock(Game.class);

        when(gameRepository.findById(new GameId(gameId)))
                .thenReturn(Mono.just(original));

        when(original.hit()).thenReturn(updated);
        when(updated.isFinished()).thenReturn(false);

        StepVerifier.create(playMoveService.play(gameId, new PlayCommand(PlayCommand.Move.HIT)))
                .expectNext(updated)
                .verifyComplete();

        verify(gameRepository).findById(new GameId(gameId));
        verify(original).hit();
        verify(gameRepository).save(updated);
        verify(original, never()).stand(any());
        verify(updatePlayerScorePort, never()).addScore(anyString(), anyInt());
    }

    @Test
    void play_stand_updates_game_with_stand() {
        String gameId = "game-456";

        Game original = mock(Game.class);
        Game updated = mock(Game.class);

        when(gameRepository.findById(new GameId(gameId)))
                .thenReturn(Mono.just(original));

        when(original.stand(dealerService)).thenReturn(updated);
        when(updated.isFinished()).thenReturn(false);

        StepVerifier.create(playMoveService.play(gameId, new PlayCommand(PlayCommand.Move.STAND)))
                .expectNext(updated)
                .verifyComplete();

        verify(gameRepository).findById(new GameId(gameId));
        verify(original).stand(dealerService);
        verify(gameRepository).save(updated);
        verify(original, never()).hit();
        verify(updatePlayerScorePort, never()).addScore(anyString(), anyInt());
    }

    @Test
    void play_when_game_not_found_returns_empty() {
        String gameId = "missing";

        when(gameRepository.findById(new GameId(gameId)))
                .thenReturn(Mono.empty());

        StepVerifier.create(playMoveService.play(gameId, new PlayCommand(PlayCommand.Move.HIT)))
                .verifyComplete();

        verify(gameRepository).findById(new GameId(gameId));
        verify(gameRepository, never()).save(any());
        verify(updatePlayerScorePort, never()).addScore(anyString(), anyInt());
    }

    @Test
    void play_awards_points_when_player_wins() {
        String gameId = "game-789";
        String playerIdValue = "player-123";

        Game original = mock(Game.class);
        Game finishedGame = mock(Game.class);
        PlayerId playerId = new PlayerId(playerIdValue);

        when(gameRepository.findById(new GameId(gameId)))
                .thenReturn(Mono.just(original));

        when(original.stand(dealerService)).thenReturn(finishedGame);
        when(finishedGame.isFinished()).thenReturn(true);
        when(finishedGame.isPlayerWinner()).thenReturn(true);
        when(finishedGame.playerId()).thenReturn(playerId);
        when(updatePlayerScorePort.addScore(eq(playerIdValue), eq(10)))
                .thenReturn(Mono.empty());

        StepVerifier.create(playMoveService.play(gameId, new PlayCommand(PlayCommand.Move.STAND)))
                .expectNext(finishedGame)
                .verifyComplete();

        verify(gameRepository).findById(new GameId(gameId));
        verify(original).stand(dealerService);
        verify(gameRepository).save(finishedGame);
        verify(finishedGame).isFinished();
        verify(finishedGame).isPlayerWinner();
        verify(updatePlayerScorePort).addScore(eq(playerIdValue), eq(10));
    }

    @Test
    void play_does_not_award_points_when_player_loses() {
        String gameId = "game-999";

        Game original = mock(Game.class);
        Game finishedGame = mock(Game.class);

        when(gameRepository.findById(new GameId(gameId)))
                .thenReturn(Mono.just(original));

        when(original.stand(dealerService)).thenReturn(finishedGame);
        when(finishedGame.isFinished()).thenReturn(true);
        when(finishedGame.isPlayerWinner()).thenReturn(false);

        StepVerifier.create(playMoveService.play(gameId, new PlayCommand(PlayCommand.Move.STAND)))
                .expectNext(finishedGame)
                .verifyComplete();

        verify(gameRepository).findById(new GameId(gameId));
        verify(original).stand(dealerService);
        verify(gameRepository).save(finishedGame);
        verify(finishedGame).isFinished();
        verify(finishedGame).isPlayerWinner();
        verify(updatePlayerScorePort, never()).addScore(anyString(), anyInt());
    }

    @Test
    void play_does_not_award_points_when_game_is_not_finished() {
        String gameId = "game-111";

        Game original = mock(Game.class);
        Game updated = mock(Game.class);

        when(gameRepository.findById(new GameId(gameId)))
                .thenReturn(Mono.just(original));

        when(original.hit()).thenReturn(updated);
        when(updated.isFinished()).thenReturn(false);

        StepVerifier.create(playMoveService.play(gameId, new PlayCommand(PlayCommand.Move.HIT)))
                .expectNext(updated)
                .verifyComplete();

        verify(gameRepository).findById(new GameId(gameId));
        verify(original).hit();
        verify(gameRepository).save(updated);
        verify(updated).isFinished();
        verify(updated, never()).isPlayerWinner();
        verify(updatePlayerScorePort, never()).addScore(anyString(), anyInt());
    }

    @Test
    void play_awards_points_when_player_busts_and_dealer_wins() {
        String gameId = "game-bust";
        String playerIdValue = "player-123";

        Game original = mock(Game.class);
        Game bustGame = mock(Game.class);
        PlayerId playerId = new PlayerId(playerIdValue);

        when(gameRepository.findById(new GameId(gameId)))
                .thenReturn(Mono.just(original));

        when(original.hit()).thenReturn(bustGame);
        when(bustGame.isFinished()).thenReturn(true);
        when(bustGame.isPlayerWinner()).thenReturn(false); // Player bust = dealer wins
        when(bustGame.playerId()).thenReturn(playerId);

        StepVerifier.create(playMoveService.play(gameId, new PlayCommand(PlayCommand.Move.HIT)))
                .expectNext(bustGame)
                .verifyComplete();

        verify(gameRepository).findById(new GameId(gameId));
        verify(original).hit();
        verify(gameRepository).save(bustGame);
        verify(bustGame).isFinished();
        verify(bustGame).isPlayerWinner();
        verify(updatePlayerScorePort, never()).addScore(anyString(), anyInt());
    }
}
