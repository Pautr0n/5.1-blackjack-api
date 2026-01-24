package cat.itacademy.blackjack.game.application.usecase;

import cat.itacademy.blackjack.game.domain.model.*;
import cat.itacademy.blackjack.game.domain.port.out.DeckFactory;
import cat.itacademy.blackjack.game.domain.port.out.GameRepository;
import cat.itacademy.blackjack.game.domain.port.out.PlayerRepository;
import cat.itacademy.blackjack.player.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
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
class CreateGameServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private DeckFactory deckFactory;

    @InjectMocks
    private CreateGameService service;

    @BeforeEach
    void setup() {
        lenient().when(playerRepository.save(any(Player.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        lenient().when(gameRepository.save(any(Game.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
    }


    @Test
    void creates_game_for_existing_player() {
        String playerName = "Pau";
        Player existingPlayer = Player.create(playerName);

        when(playerRepository.findByName(anyString()))
                .thenReturn(Mono.just(existingPlayer));

        Deck deck = mock(Deck.class);
        when(deckFactory.create()).thenReturn(deck);

        when(deck.draw()).thenReturn(
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.NINE, Suit.CLUBS),
                new Card(Rank.FIVE, Suit.SPADES)
        );

        StepVerifier.create(service.create(playerName))
                .assertNext(game -> {
                    assertThat(game.playerId()).isEqualTo(existingPlayer.id().value());
                    assertThat(game.status()).isEqualTo(GameStatus.IN_PROGRESS);
                })
                .verifyComplete();

        verify(playerRepository).findByName(anyString());
        verify(playerRepository, never()).save(any());
        verify(deckFactory).create();
        verify(gameRepository).save(any(Game.class));

    }

    @Test
    void creates_player_if_not_exists_and_then_creates_game() {
        String playerName = "NewPlayer";

        when(playerRepository.findByName(eq(playerName)))
                .thenReturn(Mono.empty());

        Player createdPlayer = Player.create(playerName);

        when(playerRepository.save(any(Player.class)))
                .thenReturn(Mono.just(createdPlayer));

        Deck deck = mock(Deck.class);
        when(deckFactory.create()).thenReturn(deck);

        when(deck.draw()).thenReturn(
                new Card(Rank.FOUR, Suit.SPADES),
                new Card(Rank.THREE, Suit.CLUBS),
                new Card(Rank.SEVEN, Suit.HEARTS)
        );

        StepVerifier.create(service.create(playerName))
                .assertNext(game -> {
                    assertThat(game.playerId()).isEqualTo(createdPlayer.id().value());
                    assertThat(game.status()).isEqualTo(GameStatus.IN_PROGRESS);
                })
                .verifyComplete();

        verify(playerRepository).findByName(eq(playerName));
        verify(playerRepository).save(any(Player.class));
        verify(deckFactory).create();
        verify(gameRepository).save(any(Game.class));
    }

    @Test
    void uses_deck_from_factory() {
        String playerName = "Pau";
        Player existingPlayer = Player.create(playerName);

        when(playerRepository.findByName(eq(playerName)))
                .thenReturn(Mono.just(existingPlayer));

        Deck deck = mock(Deck.class);
        when(deckFactory.create()).thenReturn(deck);

        when(deck.draw()).thenReturn(
                new Card(Rank.ACE, Suit.SPADES),
                new Card(Rank.TWO, Suit.HEARTS),
                new Card(Rank.THREE, Suit.CLUBS)
        );

        StepVerifier.create(service.create(playerName))
                .assertNext(game -> {
                    assertThat(game.getDeck()).isSameAs(deck);
                })
                .verifyComplete();

        verify(deckFactory).create();
    }
}
