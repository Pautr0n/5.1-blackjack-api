package cat.itacademy.blackjack.game.domain.model;

import cat.itacademy.blackjack.player.domain.model.PlayerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameStartTest {

    public Deck deck;
    public GameId gameId;
    public String playerId;

    @BeforeEach
    void setUp(){
        deck = Deck.standard52Cards();
        gameId = GameId.newId();
        playerId = "player-123";

    }

    @Test
    void starting_a_game_deals_two_cards_to_player_and_one_to_dealer() {
        Game game = Game.start(gameId, playerId, deck);

        assertThat(game.playerHand().cards()).hasSize(2);
        assertThat(game.dealerHand().cards()).hasSize(1);
    }

    @Test
    void starting_a_game_reduces_deck_size_by_three() {
        Game game = Game.start(gameId, playerId, deck);

        assertThat(game.getDeck().getCards()).hasSize(52 - 3);
    }

    @Test
    void starting_a_game_sets_status_to_in_progress() {
        Game game = Game.start(gameId, playerId, deck);

        assertThat(game.status()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    void starting_a_game_assigns_player_id_correctly() {
        Game game = Game.start(gameId, playerId, deck);

        assertThat(game.playerId()).isEqualTo(playerId);
    }

    @Test
    void starting_a_game_returns_new_game_instance() {
        Deck deck = Deck.standard52Cards();

        Game game1 = Game.start(gameId, playerId, deck);
        Game game2 = Game.start(gameId, playerId, deck);

        assertThat(game1).isNotSameAs(game2);
    }

}
