package cat.itacademy.blackjack.game.application.usecase;

import cat.itacademy.blackjack.game.domain.model.Game;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class GetGameServiceTest {

    @Mock
    GameRepository gameRepository;

    @InjectMocks
    GetGameService getGameService;

    @Test
    void getById_returns_game_when_gameId_found() {

        String gameId = "game-123";

        Game foundGame = mock(Game.class);

        when(gameRepository.findById(gameId)).thenReturn(Mono.just(foundGame));

        StepVerifier.create(getGameService.getById(gameId))
                .expectNext(foundGame)
                .verifyComplete();

        verify(gameRepository).findById(gameId);

    }

    @Test
    void getById_returns_empty_when_gameId_does_not_exist() {
        String gameId = "game-167";

        when(gameRepository.findById(gameId)).thenReturn(Mono.empty());

        StepVerifier.create(getGameService.getById(gameId))
                .verifyComplete();

        verify(gameRepository).findById(gameId);
    }

}