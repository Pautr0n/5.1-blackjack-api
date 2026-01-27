package cat.itacademy.blackjack.player.application.usecase;

import cat.itacademy.blackjack.player.domain.port.out.query.PlayerQueryRepository;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerSummary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchPlayersByNameServiceTest {

    @Mock
    PlayerQueryRepository queryRepository;

    @InjectMocks
    SearchPlayersByNameService searchPlayersByNameService;

    @Test
    void searchByName_should_return_flux_with_players_names_matching() {
        PlayerSummary p1 = new PlayerSummary("1", "Pau", 100,2);
        PlayerSummary p2 = new PlayerSummary("2", "Paula", 50,50);

        when(queryRepository.searchByName("pau"))
                .thenReturn(Flux.just(p1, p2));

        StepVerifier.create(searchPlayersByNameService.searchByName("pau"))
                .assertNext(s -> assertThat(s.name()).isEqualTo("Pau"))
                .assertNext(s -> assertThat(s.name()).isEqualTo("Paula"))
                .verifyComplete();

        verify(queryRepository).searchByName("pau");
    }

    @Test
    void searchByName_should_return_empty_flux_when_no_players_match(){
        when(queryRepository.searchByName("zzz"))
                .thenReturn(Flux.empty());

        StepVerifier.create(searchPlayersByNameService.searchByName("zzz"))
                .verifyComplete();

        verify(queryRepository).searchByName("zzz");
    }


}