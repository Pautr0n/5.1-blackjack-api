package cat.itacademy.blackjack.player.domain.model.exception;

public class InvalidPlayerTotalGamesException extends RuntimeException {
    public InvalidPlayerTotalGamesException(String message) {
        super(message);
    }
}
