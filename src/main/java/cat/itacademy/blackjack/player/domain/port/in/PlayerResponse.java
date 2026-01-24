package cat.itacademy.blackjack.player.domain.port.in;

public record PlayerResponse(String id,
                             String name,
                             int score
) {
}
