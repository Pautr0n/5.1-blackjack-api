package cat.itacademy.blackjack.game.domain.model.exception;

public class IllegalGameStateException extends RuntimeException {
    public IllegalGameStateException(String message) {
        super(message);
    }
}
