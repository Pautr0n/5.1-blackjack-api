package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import cat.itacademy.blackjack.player.domain.port.in.PlayerResponse;
import cat.itacademy.blackjack.player.domain.port.out.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPlayerServiceTest {

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    GetPlayerService getPlayerService;

    @Test
    void getById_should_return_player_when_exist() {
        Player player = Player.create("Pau");
        PlayerId id = player.id();

        when(playerRepository.findById(id)).thenReturn(Mono.just(player));


        StepVerifier.create(getPlayerService.getById(id.value()))
                .assertNext(response -> {
                    assertThat(response.id()).isEqualTo(id.value());
                    assertThat(response.name()).isEqualTo("Pau");
                    assertThat(response.score()).isZero();
                })
                .verifyComplete();

        verify(playerRepository).findById(id);
    }

    @Test
    void getById_should_return_exception_when_does_not_exist() {
        PlayerId id = PlayerId.newId();

        when(playerRepository.findById(id)).thenReturn(Mono.empty());

        Mono<PlayerResponse> result = getPlayerService.getById(id.value());

        StepVerifier.create(result)
                .expectErrorMatches(ex -> ex.getMessage().equals("Player not found"))
                .verify();

        verify(playerRepository).findById(id);
    }



}