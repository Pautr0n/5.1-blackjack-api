package cat.itacademy.blackjack.game.domain.model;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameHitTest {

    private Deck deckWithSafeHit() {
        return new DeckFake(List.of(
                new Card(Rank.TWO, Suit.HEARTS),     // player 1
                new Card(Rank.THREE, Suit.CLUBS),    // player 2
                new Card(Rank.TEN, Suit.SPADES),     // dealer 1
                new Card(Rank.FOUR, Suit.DIAMONDS)   // hit
        ));

    }

    private Deck deckWithBustHit() {
        return new DeckFake(List.of(
                new Card(Rank.TEN, Suit.HEARTS),     // player 1
                new Card(Rank.NINE, Suit.CLUBS),     // player 2
                new Card(Rank.TWO, Suit.SPADES),     // dealer 1
                new Card(Rank.KING, Suit.DIAMONDS)   // hit
        ));

    }

    @Test
    void hit_adds_one_card_to_player_hand() {
        Deck deck = deckWithSafeHit();
        GameId id = GameId.newId();
        String playerId = "player-123";

        Game game = Game.start(id, playerId, deck);
        Game afterHit = game.hit();

        assertThat(afterHit.playerHand().cards()).hasSize(3);
    }

    @Test
    void hit_reduces_deck_size_by_one() {
        Deck deck = deckWithSafeHit();
        GameId id = GameId.newId();
        String playerId = "player-123";

        Game game = Game.start(id, playerId, deck);
        int before = game.getDeck().getCards().size();

        Game afterHit = game.hit();
        int after = afterHit.getDeck().getCards().size();

        assertThat(after).isEqualTo(before - 1);
    }

    @Test
    void hit_returns_new_game_instance() {
        Deck deck = deckWithSafeHit();
        GameId id = GameId.newId();
        String playerId = "player-123";

        Game game = Game.start(id, playerId, deck);
        Game afterHit = game.hit();

        assertThat(afterHit).isNotSameAs(game);
    }

    @Test
    void hit_keeps_game_in_progress_if_player_does_not_bust() {
        Deck deck = deckWithSafeHit();
        GameId id = GameId.newId();
        String playerId = "player-123";

        Game game = Game.start(id, playerId, deck);
        Game afterHit = game.hit();

        assertThat(afterHit.status()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    void hit_sets_status_to_player_bust_when_player_busts() {
        Deck deck = deckWithBustHit();
        GameId id = GameId.newId();
        String playerId = "player-123";

        Game game = Game.start(id, playerId, deck);
        Game afterHit = game.hit();

        assertThat(afterHit.status()).isEqualTo(GameStatus.PLAYER_BUST);
    }

}
