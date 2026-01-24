package cat.itacademy.blackjack.player.domain.model.exception;

public class InvalidPlayerScoreException extends RuntimeException {
    public InvalidPlayerScoreException(String message) {
        super(message);
    }
}
