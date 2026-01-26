package cat.itacademy.blackjack.game.domain.model;

import cat.itacademy.blackjack.game.domain.model.exception.IllegalGameStateException;
import cat.itacademy.blackjack.game.domain.service.DealerService;
import cat.itacademy.blackjack.player.domain.model.PlayerId;

public class Game {

    private final GameId id;
    private final PlayerId playerId;
    private final Hand playerHand;
    private final Hand dealerHand;
    private final Deck deck;
    private final GameStatus status;

    private Game(GameId id,
                 PlayerId playerId,
                 Hand playerHand,
                 Hand dealerHand,
                 Deck deck,
                 GameStatus status) {
        this.id = id;
        this.playerId = playerId;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.deck = deck;
        this.status = status;
    }

    public static Game start(GameId id, PlayerId playerId, Deck deck) {

        Hand player = Hand.empty()
                .addCard(deck.draw())
                .addCard(deck.draw());

        Hand dealer = Hand.empty()
                .addCard(deck.draw());

        return new Game(id, playerId, player, dealer, deck, GameStatus.IN_PROGRESS);
    }

    public static Game restore(GameId id,
                               PlayerId playerId,
                               Hand playerHand,
                               Hand dealerHand,
                               Deck deck,
                               GameStatus status) {

        return new Game(id, playerId, playerHand, dealerHand, deck, status);
    }

    public Game hit() {
        if (status != GameStatus.IN_PROGRESS) {
            throw new IllegalGameStateException(
                    "Cannot hit when game is not in progress. Current status: " + status
            );
        }
        Hand newPlayerHand = playerHand.addCard(deck.draw());
        GameStatus newStatus = newPlayerHand.isBust() ? GameStatus.PLAYER_BUST : status;
        return new Game(id, playerId, newPlayerHand, dealerHand, deck, newStatus);
    }

    public Game stand(DealerService dealerService) {
        if (status != GameStatus.IN_PROGRESS) {
            throw new IllegalGameStateException(
                    "Cannot hit when game is not in progress. Current status: " + status
            );
        }

        Hand finalDealerHand = dealerService.playDealerTurn(dealerHand, deck);

        GameStatus finalStatus = determineWinner(playerHand, finalDealerHand);

        return new Game(id, playerId, playerHand, finalDealerHand, deck, finalStatus);
    }

    private GameStatus determineWinner(Hand player, Hand dealer) {
        if (dealer.isBust()) return GameStatus.DEALER_BUST;
        if (player.score() > dealer.score()) return GameStatus.PLAYER_WINS;
        if (player.score() < dealer.score()) return GameStatus.DEALER_WINS;
        return GameStatus.PUSH;
    }

    public boolean isPlayerWinner() {
        return status == GameStatus.PLAYER_WINS || status == GameStatus.DEALER_BUST;
    }

    public boolean isFinished() {
        return status != GameStatus.IN_PROGRESS;
    }

    public GameId id() {
        return id;
    }

    public PlayerId playerId() {
        return playerId;
    }

    public Hand playerHand() {
        return playerHand;
    }

    public Hand dealerHand() {
        return dealerHand;
    }

    public GameStatus status() {
        return status;
    }

    public Deck deck(){
        return deck;

    }

}
