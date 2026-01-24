package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePlayerServiceTest {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    DeletePlayerService deletePlayerService;

    @Test
    void deleteById_should_return_empty_when_player_is_found_and_deleted(){
        Player player = Player.create("Pau");
        PlayerId playerId = player.id();

        when(playerRepository.findById(playerId)).thenReturn(Mono.just(player));
        when(playerRepository.deleteById(playerId)).thenReturn(Mono.empty());

        StepVerifier.create(deletePlayerService.deleteById(playerId.value()))
                .expectNextCount(0)
                .verifyComplete();

        verify(playerRepository).findById(playerId);
        verify(playerRepository).deleteById(playerId);

    }

    @Test
    void deleteById_should_return_PlayerNotFoundException_when_player_does_not_exist(){
        PlayerId playerId = PlayerId.newId();

        when(playerRepository.findById(playerId)).thenReturn(Mono.empty());

        StepVerifier.create(deletePlayerService.deleteById(playerId.value()))
                .expectErrorMatches(ex -> ex.getMessage().equals("Player not found"))
                .verify();

        verify(playerRepository, never()).deleteById(any());

    }

}