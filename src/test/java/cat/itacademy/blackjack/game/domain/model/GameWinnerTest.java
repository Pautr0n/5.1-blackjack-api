package cat.itacademy.blackjack.game.domain.model;

import cat.itacademy.blackjack.game.domain.service.DealerService;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GameWinnerTest {

    private DeckFake deckWherePlayerWins() {
        return new DeckFake(List.of(
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.NINE, Suit.CLUBS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.EIGHT, Suit.DIAMONDS),
                new Card(Rank.THREE, Suit.CLUBS)
        ));
    }

    private DeckFake deckWhereDealerWins() {
        return new DeckFake(List.of(
                new Card(Rank.FIVE, Suit.HEARTS),
                new Card(Rank.SIX, Suit.CLUBS),
                new Card(Rank.TEN, Suit.SPADES),
                new Card(Rank.EIGHT, Suit.DIAMONDS)
        ));
    }

    private DeckFake deckWhereDealerBusts() {
        return new DeckFake(List.of(
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.SEVEN, Suit.CLUBS),
                new Card(Rank.NINE, Suit.SPADES),
                new Card(Rank.SIX, Suit.DIAMONDS),
                new Card(Rank.NINE, Suit.CLUBS)
        ));
    }

    private DeckFake deckWherePlayerBusts() {
        return new DeckFake(List.of(
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.NINE, Suit.CLUBS),
                new Card(Rank.TWO, Suit.SPADES),
                new Card(Rank.KING, Suit.DIAMONDS)
        ));
    }

    private DeckFake deckWherePush() {
        return new DeckFake(List.of(
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.NINE, Suit.CLUBS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.EIGHT, Suit.DIAMONDS),
                new Card(Rank.FOUR, Suit.CLUBS)
        ));
    }

    @Test
    void isPlayerWinner_returns_true_when_status_is_player_wins() {
        DeckFake deck = deckWherePlayerWins();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");
        DealerService dealerService = new DealerService();

        Game game = Game.start(id, playerId, deck);
        Game finishedGame = game.stand(dealerService);

        assertThat(finishedGame.isPlayerWinner()).isTrue();
        assertThat(finishedGame.status()).isEqualTo(GameStatus.PLAYER_WINS);
    }

    @Test
    void isPlayerWinner_returns_true_when_status_is_dealer_bust() {
        DeckFake deck = deckWhereDealerBusts();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");
        DealerService dealerService = new DealerService();

        Game game = Game.start(id, playerId, deck);
        Game finishedGame = game.stand(dealerService);

        assertThat(finishedGame.isPlayerWinner()).isTrue();
        assertThat(finishedGame.status()).isEqualTo(GameStatus.DEALER_BUST);
    }

    @Test
    void isPlayerWinner_returns_false_when_status_is_dealer_wins() {
        DeckFake deck = deckWhereDealerWins();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");
        DealerService dealerService = new DealerService();

        Game game = Game.start(id, playerId, deck);
        Game finishedGame = game.stand(dealerService);

        assertThat(finishedGame.isPlayerWinner()).isFalse();
        assertThat(finishedGame.status()).isEqualTo(GameStatus.DEALER_WINS);
    }

    @Test
    void isPlayerWinner_returns_false_when_status_is_player_bust() {
        DeckFake deck = deckWherePlayerBusts();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");

        Game game = Game.start(id, playerId, deck);
        Game bustGame = game.hit(); // Player busts

        assertThat(bustGame.isPlayerWinner()).isFalse();
        assertThat(bustGame.status()).isEqualTo(GameStatus.PLAYER_BUST);
    }

    @Test
    void isPlayerWinner_returns_false_when_status_is_push() {
        DeckFake deck = deckWherePush();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");
        DealerService dealerService = new DealerService();

        Game game = Game.start(id, playerId, deck);
        Game finishedGame = game.stand(dealerService);

        assertThat(finishedGame.isPlayerWinner()).isFalse();
        assertThat(finishedGame.status()).isEqualTo(GameStatus.PUSH);
    }

    @Test
    void isFinished_returns_false_when_game_is_in_progress() {
        DeckFake deck = deckWherePlayerWins();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");

        Game game = Game.start(id, playerId, deck);

        assertThat(game.isFinished()).isFalse();
        assertThat(game.status()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    void isFinished_returns_true_when_game_is_player_wins() {
        DeckFake deck = deckWherePlayerWins();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");
        DealerService dealerService = new DealerService();

        Game game = Game.start(id, playerId, deck);
        Game finishedGame = game.stand(dealerService);

        assertThat(finishedGame.isFinished()).isTrue();
        assertThat(finishedGame.status()).isEqualTo(GameStatus.PLAYER_WINS);
    }

    @Test
    void isFinished_returns_true_when_game_is_dealer_wins() {
        DeckFake deck = deckWhereDealerWins();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");
        DealerService dealerService = new DealerService();

        Game game = Game.start(id, playerId, deck);
        Game finishedGame = game.stand(dealerService);

        assertThat(finishedGame.isFinished()).isTrue();
        assertThat(finishedGame.status()).isEqualTo(GameStatus.DEALER_WINS);
    }

    @Test
    void isFinished_returns_true_when_game_is_player_bust() {
        DeckFake deck = deckWherePlayerBusts();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");

        Game game = Game.start(id, playerId, deck);
        Game bustGame = game.hit();

        assertThat(bustGame.isFinished()).isTrue();
        assertThat(bustGame.status()).isEqualTo(GameStatus.PLAYER_BUST);
    }

    @Test
    void isFinished_returns_true_when_game_is_dealer_bust() {
        DeckFake deck = deckWhereDealerBusts();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");
        DealerService dealerService = new DealerService();

        Game game = Game.start(id, playerId, deck);
        Game finishedGame = game.stand(dealerService);

        assertThat(finishedGame.isFinished()).isTrue();
        assertThat(finishedGame.status()).isEqualTo(GameStatus.DEALER_BUST);
    }

    @Test
    void isFinished_returns_true_when_game_is_push() {
        DeckFake deck = deckWherePush();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");
        DealerService dealerService = new DealerService();

        Game game = Game.start(id, playerId, deck);
        Game finishedGame = game.stand(dealerService);

        assertThat(finishedGame.isFinished()).isTrue();
        assertThat(finishedGame.status()).isEqualTo(GameStatus.PUSH);
    }
}
