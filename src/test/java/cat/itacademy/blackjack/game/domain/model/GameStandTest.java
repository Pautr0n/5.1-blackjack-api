package cat.itacademy.blackjack.game.domain.model;

import cat.itacademy.blackjack.game.domain.service.DealerService;
import cat.itacademy.blackjack.player.domain.model.PlayerId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameStandTest {

    private DealerService dealerService = new DealerService();

    private DeckFake deckWhereDealerBeatsPlayer() {

        return new DeckFake(List.of(
                new Card(Rank.FIVE, Suit.HEARTS),
                new Card(Rank.SIX, Suit.CLUBS),
                new Card(Rank.TEN, Suit.SPADES),
                new Card(Rank.EIGHT, Suit.DIAMONDS)
        ));
    }

    private DeckFake deckWherePlayerBeatsDealer() {

        return new DeckFake(List.of(
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.NINE, Suit.CLUBS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.EIGHT, Suit.DIAMONDS),
                new Card(Rank.THREE, Suit.CLUBS)
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

    private DeckFake deckWherePush(){

        return new DeckFake(List.of(
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.NINE, Suit.CLUBS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.EIGHT, Suit.DIAMONDS),
                new Card(Rank.FOUR, Suit.CLUBS)
        ));
    }

    @Test
    void stand_returns_new_game_instance() {
        DeckFake deck = deckWhereDealerBeatsPlayer();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");

        Game game = Game.start(id, playerId, deck);
        Game afterStand = game.stand(dealerService);

        assertThat(afterStand).isNotSameAs(game);
    }

    @Test
    void stand_sets_status_to_dealer_wins_when_dealer_beats_player() {
        DeckFake deck = deckWhereDealerBeatsPlayer();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");

        Game game = Game.start(id, playerId, deck);
        Game afterStand = game.stand(dealerService);

        assertThat(afterStand.status()).isEqualTo(GameStatus.DEALER_WINS);
    }

    @Test
    void stand_sets_status_to_player_wins_when_player_beats_dealer() {
        DeckFake deck = deckWherePlayerBeatsDealer();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");

        Game game = Game.start(id, playerId, deck);
        Game afterStand = game.stand(dealerService);

        assertThat(afterStand.status()).isEqualTo(GameStatus.PLAYER_WINS);
    }

    @Test
    void stand_sets_status_to_dealer_bust_when_dealer_exceeds_21() {
        DeckFake deck = deckWhereDealerBusts();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");

        Game game = Game.start(id, playerId, deck);
        Game afterStand = game.stand(dealerService);

        assertThat(afterStand.status()).isEqualTo(GameStatus.DEALER_BUST);
    }

    @Test
    void stand_sets_status_to_dealer_bust_when_dealer_and_player_has_same_score(){
        DeckFake deck = deckWherePush();
        GameId id = GameId.newId();
        PlayerId playerId = new PlayerId("player-123");

        Game game = Game.start(id, playerId, deck);
        Game afterStand = game.stand(dealerService);

        assertThat(afterStand.status()).isEqualTo(GameStatus.PUSH);

    }

}
