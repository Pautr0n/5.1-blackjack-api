package cat.itacademy.blackjack.player.domain.model.exception;

public class InvalidPlayerNameException extends RuntimeException {
    public InvalidPlayerNameException(String message) {
        super(message);
    }
}
